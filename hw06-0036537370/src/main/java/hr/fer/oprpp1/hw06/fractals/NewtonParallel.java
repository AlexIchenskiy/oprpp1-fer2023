package hr.fer.oprpp1.hw06.fractals;

import hr.fer.oprpp1.hw06.math.Complex;
import hr.fer.oprpp1.hw06.math.ComplexPolynomial;
import hr.fer.oprpp1.hw06.math.ComplexRootedPolynomial;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A class representing a generator for the Newton-Ralph fractal generation using multiple threads.
 */
public class NewtonParallel {

    /**
     * Fractal convergence threshold.
     */
    private static final double CONVERGENCE_THRESHOLD = 0.001;

    /**
     * Maximum iterations for the fractal calculation (more = slower, but more detailed fractal).
     */
    private static final int MAX_ITERATIONS = 16 * 16;

    /**
     * Fractal root threshold.
     */
    private static final double ROOT_THRESHOLD = 0.002;

    /**
     * A main program for user input and fractal generation.
     * @param args Two optional arguments can be accepted, number of workers (--workers n or -w n) and a number of
     *             tracks (--tracks n or -t n).
     */
    public static void main(String[] args) {
        int workers = Runtime.getRuntime().availableProcessors();
        int tracks = 4 * workers;
        boolean isWorkersPresent = false, isTracksPresent = false;

        for (int i = 0; i < args.length; i++) {
            if (args[i].toLowerCase().startsWith("--workers") || args[i].toLowerCase().startsWith("-w")) {
                if (isWorkersPresent) throw new IllegalArgumentException("Number of workers can be specified only " +
                        "once!");
                isWorkersPresent = true;
                try {
                    workers = Integer.parseInt(args[i].split("=")[1]);
                } catch (Exception e) {
                    throw new IllegalArgumentException("Invalid workers number!");
                }
            }

            if (args[i].toLowerCase().startsWith("--tracks") || args[i].toLowerCase().startsWith("-t")) {
                if (isTracksPresent) throw new IllegalArgumentException("Number of tracks can be specified only " +
                        "once!");
                isTracksPresent = true;
                try {
                    tracks = Integer.parseInt(args[i].split("=")[1]);
                } catch (Exception e) {
                    throw new IllegalArgumentException("Invalid tracks number!");
                }
            }
        }

        int count = 1;
        String val;
        Scanner in = new Scanner(System.in);
        List<Complex> roots = new ArrayList<>();

        System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.\nPlease enter at least two " +
                "roots, one root per line. Enter 'done' when done.");

        while (true) {
            System.out.print("Root " + count + "> ");
            val = in.nextLine().trim();

            if (val.equals("done")) {
                if (count < 3) {
                    System.out.println("At least two roots must be provided!");
                    continue;
                }

                System.out.println("Image of fractal will appear shortly. Thank you.");
                break;
            }

            if (val.trim().equals("")) {
                System.out.println("A non-empty complex number must be provided!");
                continue;
            }

            try {
                roots.add(Complex.parseComplex(val));
                // System.out.println(Complex.parseComplex(val));
            } catch (Exception e) {
                System.out.println("A valid complex number must be provided!");
                continue;
            }

            count++;
        }

        System.out.println("Number of threads: " + tracks);
        System.out.println("Number of workers: " + workers);

        FractalViewer.show(new NewtonFractalProducer(new ComplexRootedPolynomial(Complex.ONE,
                roots.toArray(Complex[]::new)), workers, tracks));
    }

    /**
     * Class representing a fractal producer.
     */
    private static class NewtonFractalProducer implements IFractalProducer {

        ComplexRootedPolynomial rootedPolynomial;
        ComplexPolynomial polynomial;
        int workers, tracks;

        /**
         * Creates a new fractal producer with a given complex rooted polynomial, number of workers and a number of
         * tracks.
         * @param rootedPolynomial Complex rooted polynomial for the fractal producer
         * @param workers Number of workers
         * @param tracks Number of tracks (= image height if too big)
         */
        public NewtonFractalProducer(ComplexRootedPolynomial rootedPolynomial, int workers, int tracks) {
            this.rootedPolynomial = rootedPolynomial;
            this.polynomial = rootedPolynomial.toComplexPolynom();
            this.workers = workers;
            this.tracks = tracks;
        }

        /**
         * Function for producing the Newton-Ralph fractal.
         * @param v Real number min value
         * @param v1 Real number max value
         * @param v2 Imaginary number min value
         * @param v3 Imaginary number max value
         * @param i Image width
         * @param i1 Image height
         * @param l Request number
         * @param iFractalResultObserver Observer for the fractal visualization
         * @param atomicBoolean Cancellation boolean
         */
        @Override
        public void produce(double v, double v1, double v2, double v3, int i, int i1, long l,
                            IFractalResultObserver iFractalResultObserver, AtomicBoolean atomicBoolean) {
            short[] data = new short[i * i1];
            final BlockingQueue<CalculateJob> jobs = new LinkedBlockingQueue<>();
            Thread[] workers = new Thread[this.workers];

            this.tracks = Math.min(i1, tracks);

            for (int j = 0; j < workers.length; j++) {
                workers[j] = new Thread(() -> {
                    while(true) {
                        CalculateJob p = null;
                        try {
                            p = jobs.take();
                            if(p == CalculateJob.NO_JOB) break;
                        } catch (InterruptedException e) {
                            continue;
                        }
                        p.run();
                    }
                });
            }

            for (Thread worker : workers) {
                worker.start();
            }

            for (int j = 0; j < this.tracks; j++) {
                int yMin = j * (i1 / this.tracks);
                int yMax = (j + 1) * (i1 / this.tracks) - 1;

                if (j == this.tracks - 1) {
                    yMax = i1 - 1;
                }

                CalculateJob job = new CalculateJob(v, v1, v2, v3, i, i1, l, yMin, yMax, MAX_ITERATIONS, data,
                        atomicBoolean, this.rootedPolynomial, this.polynomial);

                while (true) {
                    try {
                        jobs.put(job);
                        break;
                    } catch (InterruptedException e) {}
                }
            }

            for(int j = 0; j < workers.length; j++) {
                while(true) {
                    try {
                        jobs.put(CalculateJob.NO_JOB);
                        break;
                    } catch (InterruptedException e) {
                    }
                }
            }

            for(int j = 0; j < workers.length; j++) {
                while(true) {
                    try {
                        workers[j].join();
                        break;
                    } catch (InterruptedException e) {
                    }
                }
            }

            iFractalResultObserver.acceptResult(data, (short) (this.polynomial.order() + 1), l);
        }

    }

    /**
     * Class representing a calculation job for the fractal calculation.
     */
    public static class CalculateJob implements Runnable {

        double reMin;
        double reMax;
        double imMin;
        double imMax;
        int width;
        int height;
        int yMin;
        int yMax;
        int m;
        short[] data;
        AtomicBoolean cancel;
        public static CalculateJob NO_JOB = new CalculateJob();
        ComplexRootedPolynomial rootedPolynomial;
        ComplexPolynomial polynomial;

        private CalculateJob() {
        }

        /**
         * Creates a job with provided arguments.
         * @param v Real number min value
         * @param v1 Real number max value
         * @param v2 Imaginary number min value
         * @param v3 Imaginary number max value
         * @param i Image width
         * @param i1 Image height
         * @param l Request number
         * @param yMin Height start
         * @param yMax Height finish
         * @param m Max iterations
         * @param data Data array
         * @param atomicBoolean Cancellation boolean
         * @param rootedPolynomial A complex rooted polynomial
         * @param polynomial A complex polynomial
         */
        public CalculateJob(double v, double v1, double v2, double v3, int i, int i1, long l, int yMin, int yMax, int m,
                            short[] data, AtomicBoolean atomicBoolean, ComplexRootedPolynomial rootedPolynomial,
                            ComplexPolynomial polynomial) {
            this.reMin = v;
            this.reMax = v1;
            this.imMin = v2;
            this.imMax = v3;
            this.width = i;
            this.height = i1;
            this.yMin = yMin;
            this.yMax = yMax;
            this.m = m;
            this.data = data;
            this.cancel = atomicBoolean;
            this.rootedPolynomial = rootedPolynomial;
            this.polynomial = polynomial;
        }

        @Override
        public void run() {
            int offset = this.yMin * this.width;

            for (int y = this.yMin; y <= this.yMax; y++) {
                for (int x = 0; x < width; x++) {
                    Complex zn = new Complex(x / (width - 1.0) * (reMax - reMin) + reMin,
                            (height - 1.0 - y) / (height - 1.0) * (imMax - imMin) + imMin);
                    Complex znold;
                    int iter = 0;

                    do {
                        znold = zn;
                        zn = zn.sub(this.polynomial.apply(zn).divide(this.polynomial.derive().apply(zn)));
                        iter++;
                    } while (zn.sub(znold).module() > CONVERGENCE_THRESHOLD && iter < m);

                    data[offset++] = (short)(this.rootedPolynomial.indexOfClosestRootFor(zn, ROOT_THRESHOLD) + 1);
                }
            }
        }

    }

}
