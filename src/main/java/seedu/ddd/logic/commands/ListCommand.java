package seedu.ddd.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.ddd.logic.Messages.MESSAGE_CONTACTS_LISTED_OVERVIEW;
import static seedu.ddd.logic.parser.CliFlags.FLAG_CLIENT;
import static seedu.ddd.logic.parser.CliFlags.FLAG_EVENT;

import java.util.function.Predicate;

import seedu.ddd.commons.util.ToStringBuilder;
import seedu.ddd.model.Model;
import seedu.ddd.model.contact.common.Contact;


/**
 * Lists all contacts in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all contacts";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " -CONTACT_FLAG (either `-v` or `-c`)"
            + ": Lists all clients in the addressbook.\n"
            + "Example: " + COMMAND_WORD + " " + FLAG_CLIENT
            + " n/Jane , lists all clients with name Jane in the addressbook.";
    public static final String MESSAGE_EVENT_USAGE = COMMAND_WORD + " " + FLAG_EVENT
            + ": Lists all events in the addressbook.\n"
                    + "Example: " + COMMAND_WORD + " " + FLAG_CLIENT
            + " des/George and Amy, lists all events with description George and Amy in the addressbook.";


    private final Predicate<Contact> predicate;
    public ListCommand(Predicate<Contact> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
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
