package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

/**
 * A class representing a shell command for copying files.
 */
public class CopyShellCommand implements ShellCommand {

    /**
     * A command name.
     */
    private final String commandName = "copy";

    /**
     * A command description.
     */
    private final List<String> commandDescription = List.of(
            "It is a shell command to copy files from one location to another. \nThe command takes two ",
            "arguments: the source file and the destination path. \nIf the destination is an existing directory, ",
            "the source is copied into it. \nIf the destination is a non-existent directory, a new directory ",
            "is created with that name. \nIf the destination is a file, the command asks if is it allowed to ",
            "overwrite it.\n"
    );

    /**
     * A shell command for copying files.
     * @param env Environment for the command
     * @param arguments Source file name and a destination file name (i.e. paths and names)
     * @return Shell status upon a command completion
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] commandArgs = CommandsUtil.parseArgs(arguments);

        if (commandArgs.length != 2) {
            env.writeln("Usage: copy source_path destination_path");
            return ShellStatus.CONTINUE;
        }

        Path sourcePath;
        Path destinationPath;

        try {
            sourcePath = Paths.get(CommandsUtil.parsePath(commandArgs[0]));
        } catch (Exception e) {
            env.writeln("Could not open the provided source file");
            return ShellStatus.CONTINUE;
        }

        try {
            destinationPath = Paths.get(CommandsUtil.parsePath(commandArgs[1]));
        } catch (Exception e) {
            env.writeln("Could not open the provided destination file");
            return ShellStatus.CONTINUE;
        }

        if (!Files.exists(sourcePath)) {
            env.writeln("The provided source file does not exist");
            return ShellStatus.CONTINUE;
        }

        try {
            if (Files.isDirectory(destinationPath)) destinationPath = Paths.get(destinationPath.toString() +
                    FileSystems.getDefault().getSeparator() + sourcePath.getFileName());

            if (Files.exists(destinationPath) && !Files.isDirectory(destinationPath)) {
                env.writeln("File exists on the provided destination path. Overwrite? y/n");
                String answer = env.readLine();
                if (!Objects.equals(answer, "y")) {
                    return ShellStatus.CONTINUE;
                }
            }

            InputStream in = Files.newInputStream(sourcePath);
            OutputStream out = Files.newOutputStream(destinationPath);

            byte[] bytes = new byte[1024];
            int count;
            while ((count = in.read(bytes)) > 0) {
                out.write(bytes, 0, count);
            }

            in.close();
            out.close();
        } catch (Exception e) {
            env.writeln("Could not copy the file provided");
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
