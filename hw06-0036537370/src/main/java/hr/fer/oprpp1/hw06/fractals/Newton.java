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
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Class representing a generator for the Newton-Ralph fractal.
 */
public class Newton {

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

        FractalViewer.show(new NewtonFractalProducer(new ComplexRootedPolynomial(Complex.ONE, roots.toArray(Complex[]::new))));
    }

    /**
     * Class representing a fractal producer.
     */
    private static class NewtonFractalProducer implements IFractalProducer {

        ComplexRootedPolynomial rootedPolynomial;
        ComplexPolynomial polynomial;

        /**
         * Creates a new fractal producer with a given complex rooted polynomial.
         * @param rootedPolynomial Complex rooted polynomial for the fractal producer
         */
        public NewtonFractalProducer(ComplexRootedPolynomial rootedPolynomial) {
            this.rootedPolynomial = rootedPolynomial;
            this.polynomial = rootedPolynomial.toComplexPolynom();
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
            int offset = 0;

            for (int y = 0; y < i1; y++) {
                for (int x = 0; x < i; x++) {
                    Complex zn = new Complex(x / (i - 1.0) * (v1 - v) + v,
                            (i1 - 1.0 - y) / (i1 - 1.0) * (v3 - v2) + v2);
                    Complex znold;
                    int iter = 0;

                    do {
                        znold = zn;
                        zn = zn.sub(this.polynomial.apply(zn).divide(this.polynomial.derive().apply(zn)));
                        iter++;
                    } while (zn.sub(znold).module() > CONVERGENCE_THRESHOLD && iter < MAX_ITERATIONS);

                    data[offset++] = (short)(this.rootedPolynomial.indexOfClosestRootFor(zn, ROOT_THRESHOLD) + 1);
                }
            }

            iFractalResultObserver.acceptResult(data, (short) (this.polynomial.order() + 1), l);
        }

    }

}
