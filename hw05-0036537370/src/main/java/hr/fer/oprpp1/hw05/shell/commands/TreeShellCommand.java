package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

/**
 * A class representing a shell command for retrieving a directory tree.
 */
public class TreeShellCommand implements ShellCommand {

    /**
     * A command name.
     */
    private final String commandName = "tree";

    /**
     * A command description.
     */
    private final List<String> commandDescription = List.of(
            "It is a shell command to display the directory structure in a tree-like format. \nThe command takes one ",
            "argument, which is the path to the directory whose structure is to be displayed.\n"
    );

    /**
     * A shell command for retrieving a directory tree.
     * @param env Environment for the command
     * @param arguments A single required argument representing a directory
     * @return Shell status upon a command completion
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] commandArgs = CommandsUtil.parseArgs(arguments);

        if (commandArgs.length > 1) {
            env.writeln("Usage: tree directory_path");
            return ShellStatus.CONTINUE;
        }

        try {
            Path root = Paths.get(CommandsUtil.parsePath(commandArgs[0]));
            Files.walkFileTree(root, new SimpleFileTreeVisitor(env));
        } catch (Exception e) {
            env.writeln("Could not get the directory contents");
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

    private static class SimpleFileTreeVisitor extends SimpleFileVisitor<Path> {

        /**
         * A shell environment for printing data.
         */
        private final Environment env;

        /**
         * Level of indentation.
         */
        private int level = 0;

        /**
         * Constructs a simple file tree visitor with a provided environment.
         * @param env Environment for the file tree visitor
         */
        public SimpleFileTreeVisitor(Environment env) {
            this.env = env;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            printTreeItem(dir);
            this.level++;
            return FileVisitResult.CONTINUE;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            printTreeItem(file);
            return FileVisitResult.CONTINUE;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            this.level--;
            return FileVisitResult.CONTINUE;
        }

        /**
         * Prints a tree item.
         * @param path Item to be printed
         */
        private void printTreeItem(Path path) {
            for (int i = 0; i < this.level - 1; i++) {
                env.write("  ");
            }
            if (this.level > 0) env.write("|-");

            env.writeln(path.getFileName().toString());
        }
    }

}
