package seedu.ddd.logic.commands;

import static seedu.ddd.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_SERVICE;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_TAG;

/**
 * Edits the details of either a contact or an event in the address book.
 */
public abstract class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String COMMAND_DESCRIPTION = COMMAND_WORD + ": edits a contact";
    public static final String COMMAND_USAGE = "usage: " + COMMAND_WORD + " {INDEX | "
            + PREFIX_ID + "ID} "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_SERVICE + "SERVICE] "
            + "[" + PREFIX_TAG + "TAG ...]";
    public static final String INDEX_EXAMPLE_USAGE = "example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567";
    public static final String ID_EXAMPLE_USAGE = "example: " + COMMAND_WORD + " id/0 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";
    public static final String MESSAGE_USAGE = COMMAND_DESCRIPTION + "\n"
            + COMMAND_USAGE + "\n"
            + INDEX_EXAMPLE_USAGE + "\n"
            + ID_EXAMPLE_USAGE;
}
