package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive and allows partial matching.
 */
public abstract class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons based on the specified keywords "
            + "(case-insensitive) after the prefix representing the field, "
            + "and displays them as a list with index numbers.\n"
            + "Use 'n/' to search by name, 'a/' to search by address etc. \n"
            + "Parameters: PREFIX/ KEYWORDS [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + " alice charlie";

    public static final String MESSAGE_FIND_PERSON_UNSUCCESSFUL = "No contacts found.";

}
