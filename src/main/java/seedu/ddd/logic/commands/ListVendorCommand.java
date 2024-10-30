package seedu.ddd.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.ddd.logic.Messages.MESSAGE_CONTACTS_LISTED_OVERVIEW;

import java.util.function.Predicate;

import seedu.ddd.commons.util.ToStringBuilder;
import seedu.ddd.model.Model;
import seedu.ddd.model.contact.common.Contact;
import seedu.ddd.model.event.common.Event;

/**
 * Lists vendors in the address book to the user.
 */
public class ListVendorCommand extends ListContactCommand {

    public ListVendorCommand(Predicate<Contact> predicate) {
        super(predicate);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredContactList(this.getPredicate());
        return new CommandResult(String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW,
                model.getFilteredContactListSize()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof ListVendorCommand)) {
            return false;
        }
        ListVendorCommand otherListVendorCommand = (ListVendorCommand) other;
        return this.getPredicate().equals(otherListVendorCommand.getPredicate());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", this.getPredicate())
                .toString();
    }
}
