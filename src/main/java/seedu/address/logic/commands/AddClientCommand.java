package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.client.Client;
import seedu.address.model.client.Email;
import seedu.address.model.client.NameWithoutNumber;
import seedu.address.model.client.Phone;

/**
 * Represents an abstract command to add a {@link Client} to the address book.
 * This class is designed to be extended by specific commands that add different types of clients,
 * such as buyers or sellers.
 */
public abstract class AddClientCommand extends Command {

    public static final String CLIENT_RESTRICTIONS = String.format(
            "Restrictions: \n\t%s\n\t%s\n\t%s",
            NameWithoutNumber.MESSAGE_CONSTRAINTS,
            Phone.MESSAGE_CONSTRAINTS,
            Email.MESSAGE_CONSTRAINTS
    );


    /** The client to be added to the address book. */
    protected final Client toAdd;

    /**
     * Constructs an {@code AddClientCommand} to add the specified {@code Client}.
     *
     * @param client The client to be added. Must not be null.
     */
    public AddClientCommand(Client client) {
        requireNonNull(client);
        this.toAdd = client;
    }

    /**
     * Returns the string representation of this AddClientCommand, showing the client to be added.
     * The string is generated using {@link ToStringBuilder}.
     *
     * @return A string representation of the AddClientCommand.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
