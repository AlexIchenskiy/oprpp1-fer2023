package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.util.List;
import java.util.Map;

/**
 * A class representing a shell command for help about the shell commands.
 */
public class HelpShellCommand implements ShellCommand {

    /**
     * A command name.
     */
    private final String commandName = "help";

    /**
     * A command description.
     */
    private final List<String> commandDescription = List.of(
            "It is a shell command to display information and usage instructions for other commands. \n",
            "The command may take an optional argument, specifying the command for which help is needed. \n",
            "If no argument is provided, a general list of available commands and their brief descriptions ",
            "is displayed. \nIf an argument is provided, detailed information about the specified command is shown.\n"
    );

    /**
     * A shell command for help about the shell commands.
     * @param env Environment for the command
     * @param arguments One optional argument representing a command name
     * @return Shell status upon a command completion
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] commandArgs = CommandsUtil.parseArgs(arguments);

        if (commandArgs.length > 1) {
            env.writeln("Usage: help [optional_command_name]");
            return ShellStatus.CONTINUE;
        }

        if (commandArgs.length == 0) {
            env.writeln("Available commands: ");
            for (Map.Entry<String, ShellCommand> entry : env.commands().entrySet()) {
                env.writeln(entry.getKey());
            }
            return ShellStatus.CONTINUE;
        }

        ShellCommand command = env.commands().get(commandArgs[0].trim().toLowerCase());

        if (command == null) {
            env.writeln("Provided command does not exist");
            return ShellStatus.CONTINUE;
        }

        env.writeln("Description for '" + command.getCommandName() + "':");
        for (String chunk : command.getCommandDescription()) {
            env.write(chunk);
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
