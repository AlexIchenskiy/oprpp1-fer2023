package hr.fer.oprpp1.hw05.shell;

import hr.fer.oprpp1.hw05.shell.commands.*;

import java.util.*;

/**
 * Class representing a shell environment.
 */
public class ShellEnvironment implements Environment {

    /**
     * A list of shell commands.
     */
    private final SortedMap<String, ShellCommand> commands;

    /**
     * A shell multiline symbol.
     */
    private Character multilineSymbol;

    /**
     * A shell prompt symbol.
     */
    private Character promptSymbol;

    /**
     * A shell morelines symbol.
     */
    private Character morelinesSymbol;

    /**
     * Constructs a shell CLI environment.
     */
    public ShellEnvironment() {
        this.multilineSymbol = '|';
        this.promptSymbol = '>';
        this.morelinesSymbol = '\\';

        this.commands = new TreeMap<>(Map.of("cat", new CatShellCommand(),
                "charsets", new CharsetsShellCommand(), "copy", new CopyShellCommand(),
                "exit", new ExitShellCommand(), "help", new HelpShellCommand(),
                "hexdump", new HexdumpShellCommand(), "ls", new LsShellCommand(),
                "symbol", new SymbolShellCommand(), "mkdir", new MkdirShellCommand(),
                "tree", new TreeShellCommand()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String readLine() throws ShellIOException {
        try {
            Scanner in = new Scanner(System.in);

            String line = in.nextLine().trim();

            if (!line.endsWith(String.valueOf(this.morelinesSymbol))) {
                return line;
            }

            StringBuilder sb = new StringBuilder(line.substring(0, line.length() - 1).trim());

            while (true) {
                this.write(this.multilineSymbol + " ");
                line = in.nextLine().trim();

                if (!line.endsWith(String.valueOf(this.morelinesSymbol))) {
                    sb.append(" ").append(line);
                    return sb.toString();
                }

                sb.append(" ").append(line.substring(0, line.length() - 1).trim());
            }
        } catch (Exception e) {
            throw new ShellIOException(e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(String text) throws ShellIOException {
        try {
            System.out.print(text);
        } catch (Exception e) {
            throw new ShellIOException(e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeln(String text) throws ShellIOException {
        this.write(text + '\n');
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SortedMap<String, ShellCommand> commands() {
        return Collections.unmodifiableSortedMap(this.commands);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Character getMultilineSymbol() {
        return this.multilineSymbol;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMultilineSymbol(Character symbol) {
        this.multilineSymbol = symbol;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Character getPromptSymbol() {
        return this.promptSymbol;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPromptSymbol(Character symbol) {
        this.promptSymbol = symbol;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Character getMorelinesSymbol() {
        return this.morelinesSymbol;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMorelinesSymbol(Character symbol) {
        this.morelinesSymbol = symbol;
    }

}
