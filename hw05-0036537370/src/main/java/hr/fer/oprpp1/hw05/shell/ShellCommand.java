package hr.fer.oprpp1.hw05.shell;

import java.util.List;

public interface ShellCommand {

    /**
     * Execute a command.
     * @param env Environment for the command
     * @param arguments Command arguments
     * @return Shell status upon a command completion
     */
    ShellStatus executeCommand(Environment env, String arguments);

    /**
     * Return a command name.
     * @return Comamnd name
     */
    String getCommandName();

    /**
     * Return a command description.
     * @return Command description
     */
    List<String> getCommandDescription();

}
