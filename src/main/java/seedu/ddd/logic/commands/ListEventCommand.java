package seedu.ddd.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.ddd.logic.Messages.MESSAGE_EVENTS_LISTED_OVERVIEW;

import java.util.function.Predicate;

import seedu.ddd.commons.util.ToStringBuilder;
import seedu.ddd.model.Model;
import seedu.ddd.model.contact.common.Contact;
import seedu.ddd.model.event.common.Event;

/**
 * Lists all events in the address book to the user.
 */
public class ListEventCommand extends ListCommand {

    public static final Predicate<Contact> CLEAR_CONTACTS = any -> false;

    private final Predicate<Event> predicate;

    /**
     * Creates a ListEventCommand object.
     * @param predicate comparing two events.
     */
    public ListEventCommand(Predicate<Event> predicate) {
        //I will never input a Predicate<Contact> if I have a ListEventCommand.
        super(null);
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredContactList(CLEAR_CONTACTS);
        model.updateFilteredEventList(predicate);
        return new CommandResult(String.format(MESSAGE_EVENTS_LISTED_OVERVIEW,
                model.getFilteredEventList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof ListEventCommand)) {
            return false;
        }
        ListEventCommand otherListEventCommand = (ListEventCommand) other;
        return predicate.equals(otherListEventCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
