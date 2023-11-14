package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * A class representing a shell command for displaying the system charsets.
 */
public class CharsetsShellCommand implements ShellCommand {

    /**
     * A command name.
     */
    private final String commandName = "charsets";

    /**
     * A command description.
     */
    private final List<String> commandDescription = List.of(
            "It is a shell command to display the available character sets supported by the system. \n",
            "The command does not take any arguments. \nWhen executed, it prints a list of available ",
            "character sets to the shell.\n"
    );

    /**
     * Prints all system charsets to the shell.
     * @param env Environment for the command
     * @param arguments No arguments are needed, must be an empty string
     * @return Shell status upon a command completion
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        if (!arguments.trim().equals("")) {
            env.writeln("Usage: charsets");
            return ShellStatus.CONTINUE;
        }

        for (Map.Entry<String, Charset> charsetEntry : Charset.availableCharsets().entrySet()) {
            env.writeln(charsetEntry.getKey());
        }

        return ShellStatus.CONTINUE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCommandName() {
        return this.commandName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getCommandDescription() {
        return this.commandDescription;
    }

}
