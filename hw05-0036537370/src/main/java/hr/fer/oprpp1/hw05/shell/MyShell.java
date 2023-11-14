package hr.fer.oprpp1.hw05.shell;

import hr.fer.oprpp1.hw05.shell.commands.*;

import java.util.Arrays;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Class representing a CLI for shell.
 */
public class MyShell {

    /**
     * Main method representing a shell.
     * @param args No arguments needed
     */
    public static void main(String[] args) {
        ShellEnvironment environment = new ShellEnvironment();
        ShellStatus status = ShellStatus.CONTINUE;

        try {
            environment.writeln("Welcome to MyShell v 1.0");
        } catch (ShellIOException e) {
            return;
        }

        do {
            try {
                environment.write(environment.getPromptSymbol() + " ");

                String[] lineArgs = environment.readLine().trim().split("\\s+", 2);
                if (lineArgs[0].length() == 0) continue;

                ShellCommand command = environment.commands().get(lineArgs[0].toLowerCase());
                if (command == null) {
                    environment.writeln("Command does not exist. Use 'help' to get a list of available commands.");
                    continue;
                }
                String commandArguments = lineArgs.length > 1 ? lineArgs[1] : "";
                status = command.executeCommand(environment, commandArguments);
            } catch (ShellIOException e) {
                return;
            } catch (Exception e) {
                environment.writeln(e.getMessage());
            }
        } while (status != ShellStatus.TERMINATE);
    }

}
