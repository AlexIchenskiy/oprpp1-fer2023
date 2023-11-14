package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * A class representing a shell command for creating a directory or a directory structure.
 */
public class MkdirShellCommand implements ShellCommand {

    /**
     * A command name.
     */
    private final String commandName = "mkdir";

    /**
     * A command description.
     */
    private final List<String> commandDescription = List.of(
            "It is a shell command to create a new directory or a directory structure. \nThe command takes one ",
            "argument, which is the name of the directory to be created.\n"
    );

    /**
     * A shell command for creating a directory or a directory structure.
     * @param env Environment for the command
     * @param arguments A single required argument representing a directory or a directory structure
     * @return Shell status upon a command completion
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] commandArgs = CommandsUtil.parseArgs(arguments);

        if (commandArgs.length > 1) {
            env.writeln("Usage: mkdir directory_path");
            return ShellStatus.CONTINUE;
        }

        Path directoryPath;

        try {
            directoryPath = Paths.get(CommandsUtil.parsePath(commandArgs[0]));
        } catch (Exception e) {
            env.writeln("Could not read the directory from the provided path");
            return ShellStatus.CONTINUE;
        }

        if (Files.exists(directoryPath)) {
            env.writeln("Directory already exists");
            return ShellStatus.CONTINUE;
        }

        try {
            Files.createDirectory(directoryPath);
        } catch (Exception e) {
            env.writeln("Could not create a directory");
            return ShellStatus.CONTINUE;
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
