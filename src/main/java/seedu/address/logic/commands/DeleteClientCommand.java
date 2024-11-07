package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.client.Phone;

/**
 * Represents an abstract command to delete a client (either a seller or a buyer) in the client management system.
 * This command is designed to be extended by more specific commands such as {@code DeleteSellerCommand}
 * and {@code DeleteBuyerCommand},
 * which implement client-type specific deletion logic.
 */
public abstract class DeleteClientCommand extends Command {

    /**
     * Command restrictions indicating that the phone number must be an
     * 8-digit positive integer starting with 8 or 9.
     */
    public static final String CLIENT_RESTRICTIONS = String.format("Restrictions: \n\t%s", Phone.MESSAGE_CONSTRAINTS);


    /** The phone number of the client to delete. */
    protected final Phone phoneNumber;

    /**
     * Constructs a {@code DeleteClientCommand} with the specified phone number.
     * This constructor is called by subclasses to set the phone number of the client to delete.
     *
     * @param phoneNumber The phone number of the client to delete.
     * @throws NullPointerException If {@code phone} is null.
     */
    public DeleteClientCommand(Phone phoneNumber) {
        requireNonNull(phoneNumber);
        this.phoneNumber = phoneNumber;
    }
}
