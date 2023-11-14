package hr.fer.oprpp1.hw05.shell;

import java.util.SortedMap;

/**
 * Interface for the shell CLI envirnoment.
 */
public interface Environment {

    /**
     * Read a single line.
     * @return A single line from stdio
     * @throws ShellIOException IO Exception on unsuccessful line read
     */
    String readLine() throws ShellIOException;

    /**
     * Writes text to the console.
     * @param text Text to be written
     * @throws ShellIOException IO Exception on unsuccessful writing
     */
    void write(String text) throws ShellIOException;

    /**
     * Writes a text line to the console.
     * @param text Text to be written
     * @throws ShellIOException IO Exception on unsuccessful line writing
     */
    void writeln(String text) throws ShellIOException;

    /**
     * Returns an unmodifiable map of all shell commands.
     * @return Unmodifiable map of available commands
     */
    SortedMap<String, ShellCommand> commands();

    /**
     * Returns a multiline shell CLI symbol.
     * @return A multiline shell CLI symbol
     */
    Character getMultilineSymbol();

    /**
     * Sets a multiline shell CLI symbol.
     * @param symbol A multiline symbol to be set
     */
    void setMultilineSymbol(Character symbol);

    /**
     * Returns a prompt shell CLI symbol.
     * @return A prompt shell CLI symbol
     */
    Character getPromptSymbol();

    /**
     * Sets a prompt shell CLI symbol.
     * @param symbol A prompt symbol to be set
     */
    void setPromptSymbol(Character symbol);

    /**
     * Returns a more lines shell CLI symbol.
     * @return A more lines shell CLI symbol
     */
    Character getMorelinesSymbol();

    /**
     * Sets a more lines shell CLI symbol.
     * @param symbol A more lines symbol to be set
     */
    void setMorelinesSymbol(Character symbol);

}
