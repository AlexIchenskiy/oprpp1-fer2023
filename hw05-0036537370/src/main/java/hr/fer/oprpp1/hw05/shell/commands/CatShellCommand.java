package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * A class representing a shell command for concatenating and displaying the content of the files.
 */
public class CatShellCommand implements ShellCommand {

    /**
     * A command name.
     */
    private final String commandName = "cat";

    /**
     * A command description.
     */
    private final List<String> commandDescription = List.of(
            "It is a shell command to concatenate and display the content of files. \n",
            "The command takes one required argument, which is the path to the file whose content is to be displayed, ",
            "and an optional argument representing a charset used. \nIf not provided, the default system charset ",
            "will be used. \nIf the file exists, its content is printed to the shell. \nIf the file does not exist ",
            "or is inaccessible, an error message is displayed.\n"
    );

    /**
     * A shell command for concatenating and displaying the content of the files.
     * @param env Environment for the command
     * @param arguments Required path to the file and an optional charset to be used
     * @return Shell status upon a command completion
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] commandArgs = CommandsUtil.parseArgs(arguments);

        if (commandArgs.length < 1 || commandArgs.length > 2) {
            env.writeln("Usage: cat file_path [optional_charset]");
            return ShellStatus.CONTINUE;
        }

        Charset charset = Charset.defaultCharset();

        if (commandArgs.length == 2) {
            try {
                charset = Charset.forName(commandArgs[1]);
            } catch (Exception e) {
                env.writeln("Please provide a valid charset");
                return ShellStatus.CONTINUE;
            }
        }

        try {
            Path path = Paths.get(CommandsUtil.parsePath(commandArgs[0]));
            Files.lines(path, charset).forEach(env::writeln);
        } catch (Exception e) {
            env.writeln("Could not read the file from a provided path");
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
