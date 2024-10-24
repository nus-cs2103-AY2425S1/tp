package seedu.address.logic.commands;

public class FinddelCommand {

    public static final String COMMAND_WORD = "finddel";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all deliveries which items contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + "monitor keyboard";


}
