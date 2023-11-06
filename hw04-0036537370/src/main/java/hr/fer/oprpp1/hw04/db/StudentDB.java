package hr.fer.oprpp1.hw04.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * A simple console-based app that allows interaction with the student database.
 */
public class StudentDB {

    /**
     * Main app simulating the query input.
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        List<String> lines;
        StudentDatabase db;

        Scanner sc = new Scanner(System.in);

        try {
            lines = Files.readAllLines(
                    Paths.get("./database.txt"),
                    StandardCharsets.UTF_8
            );

            db = new StudentDatabase(lines);
        } catch (IOException e) {
            System.out.println("Could not read the data!\n" + e.getMessage());
            return;
        }

        while (true) {
            try {
                System.out.print("> ");
                String query = sc.nextLine().trim();

                if (query.equalsIgnoreCase("exit")) {
                    System.out.println("Goodbye!");
                    break;
                }

                if (query.toLowerCase().startsWith("query")) {
                    QueryParser parser;
                    parser = new QueryParser(query.replaceFirst("query ", ""));

                    List<StudentRecord> records = new ArrayList<>();

                    if (parser.isDirectQuery()) {
                        StudentRecord r = db.forJMBAG(parser.getQueriedJMBAG());
                        if (r != null) {
                            records.add(r);
                        }
                        System.out.println("Using index for record retrieval.");
                    } else {
                        records.addAll(db.filter(new QueryFilter(parser.getQuery())));
                    }

                    if (records.size() > 0) {
                        printQueries(records);
                    }

                    System.out.println("Records selected: " + records.size() + "\n");
                    continue;
                }

                System.out.println("Invalid input!\n");

            } catch (Exception e) {
                System.out.println(e.getMessage() + "\n");
            }
        }

    }

    private static void printQueries(List<StudentRecord> records) {
        int jmbagLen = 10 + 2;
        int prezimeLen = 2 + records.stream()
                .map((studentRecord -> studentRecord.getLastName().length()))
                .max(Comparator.naturalOrder())
                .orElse(0);
        int imeLen = 2 + records.stream()
                .map((studentRecord -> studentRecord.getFirstName().length()))
                .max(Comparator.naturalOrder())
                .orElse(0);
        int gradeLen = 1 + 2;

        int fullLen = jmbagLen + prezimeLen + imeLen + gradeLen + 5;

        StringBuilder divider = new StringBuilder();

        for (int i = 0; i < fullLen; i++) {
            if (i == 0 || i == jmbagLen + 1 || i == jmbagLen + prezimeLen + 2 || i == jmbagLen + prezimeLen + imeLen + 3 || i == fullLen - 1) {
                divider.append("+");
            } else {
                divider.append("=");
            }
        }

        System.out.println(divider);

        for (StudentRecord r : records) {
            printQuery(r, prezimeLen - 2, imeLen - 2);
        }

        System.out.println(divider);
    }

    private static void printQuery(StudentRecord record, int lastNameMaxLen, int firstNameMaxLen) {
        String recordString = "| " +
                record.getJmbag() +
                " | " +
                record.getLastName() +
                " ".repeat(Math.max(0, lastNameMaxLen - record.getLastName().length())) +
                " | " +
                record.getFirstName() +
                " ".repeat(Math.max(0, firstNameMaxLen - record.getFirstName().length())) +
                " | " +
                record.getFinalGrade() +
                " |";

        System.out.println(recordString);
    }

}
