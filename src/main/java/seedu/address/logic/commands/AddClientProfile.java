package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Abstract class for adding clients (buyers or sellers) to the address book.
 */
public abstract class AddClientProfile extends Command {

    protected final Person toAdd;

    /**
     * Adds a client profile.
     */
    public AddClientProfile(Person toAdd) {
        requireNonNull(toAdd);
        this.toAdd = toAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(getDuplicatePersonMessage());
        }

        model.addPerson(toAdd);
        return new CommandResult(String.format(getSuccessMessage(), Messages.format(toAdd)));
    }

    /**
     * Gets the specific success message for adding the client (either buyer or seller).
     */
    protected abstract String getSuccessMessage();

    /**
     * Gets the specific duplicate person message for buyers or sellers.
     */
    protected abstract String getDuplicatePersonMessage();

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddClientProfile otherAddCommand)) {
            return false;
        }

        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
