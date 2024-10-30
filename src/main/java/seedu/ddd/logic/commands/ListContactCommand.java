package seedu.ddd.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.ddd.logic.Messages.MESSAGE_CONTACTS_LISTED_OVERVIEW;

import java.util.function.Predicate;

import seedu.ddd.commons.util.ToStringBuilder;
import seedu.ddd.model.Model;
import seedu.ddd.model.contact.common.Contact;
import seedu.ddd.model.event.common.Event;


/**
 * Lists all contacts in the address book to the user.
 */
public class ListContactCommand extends ListCommand {


    public static final String MESSAGE_SUCCESS = "Listed all contacts";

    private static final Predicate<Event> CLEAR_EVENTS = any -> false;

    private final Predicate<Contact> predicate;

    public ListContactCommand(Predicate<Contact> predicate) {
        this.predicate = predicate;
    }

    public Predicate<Contact> getPredicate() {
        return this.predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredEventList(CLEAR_EVENTS);
        model.updateFilteredContactList(predicate);
        return new CommandResult(String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW,
                model.getFilteredContactListSize()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof ListContactCommand)) {
            return false;
        }
        ListContactCommand otherListContactCommand = (ListContactCommand) other;
        return predicate.equals(otherListContactCommand.predicate);
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}

