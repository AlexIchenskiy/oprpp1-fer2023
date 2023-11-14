package hr.fer.oprpp1.hw05.shell.commands;

import java.util.ArrayList;
import java.util.List;

/**
 * A class with utility functions for the shell commands.
 */
public class CommandsUtil {

    public static String[] parseArgs(String args) {
        boolean isInString = false;
        StringBuilder sb = new StringBuilder();
        List<String> list = new ArrayList<>();

        for (int i = 0; i < args.length(); i++) {
            if (isInString) {
                if (args.charAt(i) == '"') {
                    isInString = false;
                    if (sb.length() > 0) list.add(sb.toString());
                    sb.setLength(0);
                    continue;
                }

                sb.append(args.charAt(i));
                continue;
            }

            if (args.charAt(i) == '"') {
                isInString = true;
                continue;
            }

            if (args.charAt(i) == ' ' || args.charAt(i) == '\t' || args.charAt(i) == '\r' || args.charAt(i) == '\n') {
                if (sb.length() > 0) list.add(sb.toString());
                sb.setLength(0);
                continue;
            }

            sb.append(args.charAt(i));
        }

        if (sb.length() > 0) list.add(sb.toString());
        return list.toArray(new String[0]);
    }

    /**
     * Method for parsing a path.
     * @param path Path to be parsed
     * @return Parsed path
     */
    public static String parsePath(String path) {
        return path.replaceAll("^\"|\"$", "");
    }

}
