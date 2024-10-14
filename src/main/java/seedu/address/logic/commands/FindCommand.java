package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive and allows partial matching.
 */
public abstract class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_PHONE + " alice charlie";

    public static final String MESSAGE_FIND_PERSON_UNSUCCESSFUL = "No contacts found.";

}
