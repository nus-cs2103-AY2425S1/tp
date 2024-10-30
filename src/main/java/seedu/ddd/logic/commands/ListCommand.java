package seedu.ddd.logic.commands;

import static seedu.ddd.logic.parser.CliFlags.FLAG_CLIENT;
import static seedu.ddd.logic.parser.CliFlags.FLAG_EVENT;
import static seedu.ddd.logic.parser.CliFlags.FLAG_VENDOR;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_SERVICE;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.ddd.model.Model;

/**
 * Lists either Contacts or Events in the address book to the user.
 */
public abstract class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_DESCRIPTION = COMMAND_WORD + ": lists contacts with optional filters";
    public static final String COMMAND_USAGE = "usage: " + COMMAND_WORD + " [{"
            + FLAG_CLIENT + " | "
            + FLAG_VENDOR + " | "
            + FLAG_EVENT + "}] "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "{[" + PREFIX_DATE + "DATE] | "
            + "[" + PREFIX_SERVICE + "SERVICE]} "
            + "[" + PREFIX_TAG + "TAG ...] "
            + "[" + PREFIX_ID + "ID]";
    public static final String CLIENT_EXAMPLE_USAGE = "example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Jane";
    public static final String VENDOR_EXAMPLE_USAGE = "example: " + COMMAND_WORD + " "
            + FLAG_VENDOR + " "
            + PREFIX_SERVICE + "catering";
    public static final String EVENT_EXAMPLE_USAGE = "example: " + COMMAND_WORD + " "
            + FLAG_EVENT + " "
            + PREFIX_DESC + "wedding";

    public static final String MESSAGE_USAGE = COMMAND_DESCRIPTION + "\n"
            + COMMAND_USAGE + "\n"
            + CLIENT_EXAMPLE_USAGE + "\n"
            + VENDOR_EXAMPLE_USAGE + "\n"
            + EVENT_EXAMPLE_USAGE + "\n";
}
