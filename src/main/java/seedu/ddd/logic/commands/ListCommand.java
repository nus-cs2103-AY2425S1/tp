package seedu.ddd.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.ddd.logic.Messages.MESSAGE_CONTACTS_LISTED_OVERVIEW;
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

import java.util.function.Predicate;

import seedu.ddd.commons.util.ToStringBuilder;
import seedu.ddd.model.Model;
import seedu.ddd.model.contact.common.Contact;
import seedu.ddd.model.event.common.Event;


/**
 * Lists all contacts in the address book to the user.
 */
public class ListCommand extends Command {

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

    public static final String MESSAGE_SUCCESS = "Listed all contacts";

    private static final Predicate<Event> CLEAR_EVENTS = any -> false;

    private final Predicate<Contact> predicate;

    public ListCommand(Predicate<Contact> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredEventList(CLEAR_EVENTS);
        model.updateFilteredContactList(predicate);
        return new CommandResult(String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW,
                model.getFilteredContactList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof ListCommand)) {
            return false;
        }
        ListCommand otherListCommand = (ListCommand) other;
        return predicate.equals(otherListCommand.predicate);
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
