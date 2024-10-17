package seedu.address.ui;

public enum Commands {
    ADD("add", "add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]", new String[]{"n/", "p/", "e/", "a/", "t/"}),
    DELETE("delete", "delete INDEX", new String[]{}),
    CLEAR("clear", "clear", new String[]{}),
    FIND("find", "find KEYWORD [MORE_KEYWORDS]...", new String[]{}),
    LIST("list", "list", new String[]{}),
    EXIT("exit", "exit", new String[]{}),
    HELP("help", "help", new String[]{}),
    EDIT("edit", "edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]", new String[]{"n/", "p/", "e/", "a/", "t/"}),
    SORT("sort", "sort [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]", new String[]{"n/", "p/", "e/", "a/", "t/"}),
    VIEW("view", "view INDEX", new String[]{});

    private final String commandName;
    private final String formatExample;
    private final String[] formatPrefixes;

    Commands(String commandName, String formatExample, String[] formatPrefixes) {
        this.commandName = commandName;
        this.formatExample = formatExample;
        this.formatPrefixes = formatPrefixes;
    }

    // Getter to retrieve the command string
    public String getCommand() {
        return commandName;
    }

    // Getter to retrieve the example
    public String getExample() {
        return formatExample;
    }

    // Getter to retrieve the prefix
    public String[] getPrefix() {
        return formatPrefixes;
    }
}
