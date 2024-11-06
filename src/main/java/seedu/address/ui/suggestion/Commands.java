package seedu.address.ui.suggestion;

/**
 * Enum representing the various commands available in the application.
 * Each command has a name, an example format, and associated prefixes for parameters.
 */
public enum Commands {
    ADD("add", "add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [r/ROLE] [w/WEDDING...]",
            new String[]{"n/", "p/", "e/", "a/", "r/", "w/"}),
    DELETE("delete", "delete INDEX/NAME", new String[]{}),
    CLEAR("clear", "clear", new String[]{}),
    VIEW("view", "view INDEX/NAME", new String[]{}),
    FILTER("filter", "filter [n/NAME] [r/ROLE] [e/EMAIL] [p/PHONE] [a/ADDRESS]...",
            new String[]{"n/", "r/", "e/", "p/", "a/"}),
    LIST("list", "list", new String[]{}),
    EXIT("exit", "exit", new String[]{}),
    HELP("help", "help", new String[]{}),
    ADDWEDDING("addw", "addw n/WEDDINGNAME c/CLIENT [d/DATE] [v/VENUE]",
            new String[]{"d/", "v/", "c/", "n/"}),
    EDIT("edit", "edit INDEX/NAME [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS]",
            new String[]{"n/", "p/", "e/", "a/"}),
    VIEWWEDDING("vieww", "vieww INDEX/WEDDINGNAME", new String[]{}),
    EDITWEDDING("editw", "editw w/INDEX [n/NAME] [d/DATE] [v/VENUE]",
            new String[]{"w/", "n/", "d/", "v/"}),
    DELETEWEDDING("deletew", "deletew INDEX/WEDDINGNAME", new String[]{}),
    ASSIGN("assign", "assign INDEX/NAME [r/ROLE] [w/WEDDING...]", new String[]{"w/", "r/"});

    private final String commandName;
    private final String formatExample;
    private final String[] formatPrefixes;

    /**
     * Constructor for Commands enum.
     *
     * @param commandName   The name of the command.
     * @param formatExample  The example format of the command.
     * @param formatPrefixes The prefixes for parameters associated with the command.
     */
    Commands(String commandName, String formatExample, String[] formatPrefixes) {
        this.commandName = commandName;
        this.formatExample = formatExample;
        this.formatPrefixes = formatPrefixes;
    }

    /**
     * Gets the command name.
     *
     * @return The name of the command.
     */
    public String getCommand() {
        return commandName;
    }

    /**
     * Gets the example format of the command.
     *
     * @return The format example of the command.
     */
    public String getExample() {
        return formatExample;
    }

    /**
     * Gets the prefixes associated with the command.
     *
     * @return An array of prefixes for the command's parameters.
     */
    public String[] getPrefix() {
        return formatPrefixes;
    }
}
