package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.util.List;

/**
 * A class representing a shell command for exiting the shell.
 */
public class ExitShellCommand implements ShellCommand {

    /**
     * A command name.
     */
    private final String commandName = "exit";

    /**
     * A command description.
     */
    private final List<String> commandDescription = List.of(
            "It is a shell command to exit the current shell session. \n",
            "The command does not take any arguments.\n"
    );

    /**
     * A shell command for exiting the shell.
     * @param env Environment for the command
     * @param arguments No arguments are needed, must be an empty string
     * @return Shell status upon a command completion
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        return ShellStatus.TERMINATE;
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
