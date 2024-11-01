package seedu.ddd.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.ddd.logic.Messages.MESSAGE_CONTACTS_LISTED_OVERVIEW;

import java.util.function.Predicate;

import seedu.ddd.commons.util.ToStringBuilder;
import seedu.ddd.model.Model;
import seedu.ddd.model.contact.common.Contact;

/**
 * Lists clients in the address book to the user.
 */
public class ListClientCommand extends ListContactCommand {

    public ListClientCommand(Predicate<Contact> predicate) {
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
        if (!(other instanceof ListClientCommand)) {
            return false;
        }
        ListClientCommand otherListClientCommand = (ListClientCommand) other;
        return this.getPredicate().equals(otherListClientCommand.getPredicate());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", this.getPredicate())
                .toString();
    }
}
