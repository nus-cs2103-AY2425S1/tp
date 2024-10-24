package seedu.address.ui;

/**
 * Enum representing the various commands available in the application.
 * Each command has a name, an example format, and associated prefixes for parameters.
 */
public enum Commands {
    ADD("add", "add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG...]",
            new String[]{"n/", "p/", "e/", "a/", "t/"}),
    DELETE("delete", "delete INDEX/NAME", new String[]{}),
    CLEAR("clear", "clear", new String[]{}),
    VIEW("view", "view NAME", new String[]{}),
    FILTER("filter", "filter KEYWORD [MORE_KEYWORDS...]", new String[]{}),
    LIST("list", "list", new String[]{}),
    EXIT("exit", "exit", new String[]{}),
    HELP("help", "help", new String[]{}),
    ADDWEDDING("addw", "addw n1/NAME1 n2/NAME2 p1/PHONE_NUMBER1 p2/PHONE_NUMBER2 [d/DATE] [v/VENUE]",
            new String[]{"d/", "v/", "p1/", "p2/", "n1/", "n2/"}),
    TAGWEDDING("tagw", "tagw INDEX/NAME w/WEDDING r/ROLE ", new String[]{"w/", "r/"}),
    EDIT("edit", "edit INDEX/NAME [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [w/WEDDING]",
            new String[]{"n/", "p/", "e/", "a/", "w/"}),
    VIEWWEDDING("vieww", "vieww NAME", new String[]{}),

    DELETEWEDDING("deletew", "deletew INDEX", new String[]{}),
    TAGGING("tag", "tag INDEX/NAME [t/TAG...]", new String[]{"t/"});

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
