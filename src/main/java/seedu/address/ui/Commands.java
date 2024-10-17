package seedu.address.ui;

/**
 * Enum representing the various commands available in the application.
 * Each command has a name, an example format, and associated prefixes for parameters.
 */
public enum Commands {
    ADD("add", "add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]",
            new String[]{"n/", "p/", "e/", "a/", "t/"}),
    DELETE("delete", "delete INDEX", new String[]{}),
    CLEAR("clear", "clear", new String[]{}),
    FIND("find", "find KEYWORD [MORE_KEYWORDS]...", new String[]{}),
    LIST("list", "list", new String[]{}),
    EXIT("exit", "exit", new String[]{}),
    HELP("help", "help", new String[]{}),
    EDIT("edit", "edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]",
            new String[]{"n/", "p/", "e/", "a/", "t/"}),
    SORT("sort", "sort [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]",
            new String[]{"n/", "p/", "e/", "a/", "t/"}),
    VIEW("view", "view INDEX", new String[]{});

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
