package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.util.List;

/**
 * A class representing a shell command for retrieving or changing symbols.
 */
public class SymbolShellCommand implements ShellCommand {

    /**
     * A command name.
     */
    private final String commandName = "symbol";

    /**
     * A command description.
     */
    private final List<String> commandDescription = List.of(
            "It is a shell command for either retrieving the value of a specific symbol ",
            "or changing its value. \n",
            "If one argument is given, it prints the symbol value to the shell from the given ",
            "environment. \nIf two arguments are given, it sets the value of the first symbol to the ",
            "second argument.\n"
    );

    /**
     * A shell command for retrieving or changing symbols.
     * @param env Environment for the command
     * @param arguments A symbol name (PROMPT, MORELINES or MULTILINE) for retrieving
     *                  and an optional symbol to be set instead of existing value for that symbol
     * @return Shell status upon a command completion
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] commandArgs = CommandsUtil.parseArgs(arguments);

        if (commandArgs.length < 1 || commandArgs.length > 2) {
            env.writeln("Usage: symbol PROMPT/MORELINES/MULTILINE [optional_symbol]");
            return ShellStatus.CONTINUE;
        }

        String symbolName = commandArgs[0].toLowerCase();
        if (!symbolName.equals("prompt") && !symbolName.equals("morelines") && !symbolName.equals("multiline")) {
            env.writeln("Symbol must be of type PROMPT/MORELINES/MULTILINE");
            return ShellStatus.CONTINUE;
        }

        if (commandArgs.length == 1) {
            Character val = null;
            switch (symbolName) {
                case "prompt" -> val = env.getPromptSymbol();
                case "morelines" -> val = env.getMorelinesSymbol();
                case "multiline" -> val = env.getMultilineSymbol();
            }
            env.writeln("Symbol for " + symbolName.toUpperCase() + " is '" + val + "'");

            return ShellStatus.CONTINUE;
        }

        if (commandArgs[1].trim().length() > 1) {
            env.writeln("[optional_symbol] must be a single character");
            return ShellStatus.CONTINUE;
        }

        Character symbolValue = commandArgs[1].trim().charAt(0);

        if (commandArgs.length == 2) {
            Character oldVal = null;
            switch (symbolName) {
                case "prompt" -> {
                    oldVal = env.getPromptSymbol();
                    env.setPromptSymbol(symbolValue);
                }
                case "morelines" -> {
                    oldVal = env.getMorelinesSymbol();
                    env.setMorelinesSymbol(symbolValue);
                }
                case "multiline" -> {
                    oldVal = env.getMultilineSymbol();
                    env.setMultilineSymbol(symbolValue);
                }
            }
            env.writeln("Symbol for " + symbolName.toUpperCase() + " changed from '" + oldVal + "' to '" +
                    symbolValue + "'");
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
