package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFilePermission;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * A class representing a shell command for listing a directory content.
 */
public class LsShellCommand implements ShellCommand {

    /**
     * A command name.
     */
    private final String commandName = "ls";

    /**
     * A command description.
     */
    private final List<String> commandDescription = List.of(
            "It is a shell command to list the contents of a directory. \nThe command takes one argument, which is ",
            "the path to the directory whose contents are to be listed.\n"
    );

    /**
     * A shell command for listing a directory content.
     * @param env Environment for the command
     * @param arguments A single required argument representing a directory
     * @return Shell status upon a command completion
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] commandArgs = CommandsUtil.parseArgs(arguments);

        if (commandArgs.length > 1) {
            env.writeln("Usage: ls directory_path");
            return ShellStatus.CONTINUE;
        }

        Path directoryPath;

        try {
            directoryPath = Paths.get(CommandsUtil.parsePath(commandArgs[0]));
        } catch (Exception e) {
            env.writeln("Could not open the directory provided");
            return ShellStatus.CONTINUE;
        }

        if (!Files.isDirectory(directoryPath)) {
            env.writeln("Provided path is not a directory path");
            return ShellStatus.CONTINUE;
        }

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directoryPath)) {
            for (Path file : stream) {
                env.writeln(getRow(file));
            }
        } catch (Exception e) {
            env.writeln("Could not read the directory contents");
            return ShellStatus.CONTINUE;
        }

        return null;
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

    /**
     * A method for creating a formatted ls row for the provided file.
     * @param file File to be checked
     * @return A formatted row
     */
    private static String getRow(Path file) throws IOException {
        String permissions = getPermissions(file);
        long fileSize = Files.size(file);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String lastModified = dateFormat.format(new Date(Files.getLastModifiedTime(file).toMillis()));

        return String.format("%s %8d %s %s", permissions, fileSize, lastModified, file.getFileName());
    }

    /**
     * A method for creating a formatted UNIX permission string.
     * @param file File to be checked
     * @return Formatted string
     */
    private static String getPermissions(Path file) {
        return String.format("%s%s%s%s",
                Files.isDirectory(file) ? "d" : "-",
                Files.isReadable(file) ? "r" : "-",
                Files.isWritable(file) ? "w" : "-",
                Files.isExecutable(file) ? "x" : "-");
    }

}
