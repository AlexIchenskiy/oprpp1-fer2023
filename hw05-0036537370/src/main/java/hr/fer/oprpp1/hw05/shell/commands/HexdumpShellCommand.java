package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * A class representing a shell command for getting a hex representation of the files.
 */
public class HexdumpShellCommand implements ShellCommand {

    /**
     * A command name.
     */
    private final String commandName = "hexdump";

    /**
     * A command description.
     */
    private final List<String> commandDescription = List.of(
            "It is a shell command to display the hexadecimal representation of a file's content. \n",
            "The command takes one argument, which is the path to the file to be displayed.\n"
    );

    /**
     * A shell command for getting a hex representation of the files.
     * @param env Environment for the command
     * @param arguments A single argument representing a file name
     * @return Shell status upon a command completion
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] commandArgs = CommandsUtil.parseArgs(arguments);

        if (commandArgs.length > 1) {
            env.writeln("Usage: hexdump file_path");
            return ShellStatus.CONTINUE;
        }

        try {
            Path path = Paths.get(CommandsUtil.parsePath(commandArgs[0]));
            InputStream inputStream = Files.newInputStream(path);

            int count, offset = 0;
            byte[] bytes = new byte[16];

            while ((count = inputStream.read(bytes)) != -1) {
                env.write(String.format("%08d: ", offset));

                for (int i = 0; i < 16; i++) {
                    if (i < count) {
                        env.write(String.format("%02X ", bytes[i]));
                    } else {
                        env.write("   ");
                    }
                    if (i == 7) env.write("| ");
                }

                env.write("| ");

                for (int i = 0; i < count; i++) {
                    char val = (char) bytes[i];
                    if (val > 127 || val < 32) {
                        env.write(".");
                    } else {
                        env.write(String.valueOf(val));
                    }
                }

                env.write("\n");
                offset += 10;
            }
        } catch (Exception e) {
            env.writeln("Could not read the file provided");
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
