package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.client.Seller;

/**
 * Adds a {@code Seller} to the address book.
 * Extends {@link AddClientCommand} and uses its functionality to add a specific type of client: {@code Seller}.
 */
public class AddSellerCommand extends AddClientCommand {

    /** The command word used to trigger the AddSellerCommand. */
    public static final String COMMAND_WORD = "addseller";

    /**
     * Describes the format and usage of the addseller command.
     * Includes the command word and expected parameters inherited from {@link AddClientCommand#CLIENT_PARAMETERS}.
     */
    public static final String MESSAGE_USAGE = String.format(
            "%s: Adds a seller to the address book.\nParameters: %s\n%s",
            COMMAND_WORD,
            AddClientCommand.CLIENT_PARAMETERS,
            AddClientCommand.CLIENT_RESTRICTIONS
    );

    /** Success message for adding a new seller, with a placeholder for the seller's details. */
    public static final String MESSAGE_SUCCESS_SELLER = "New seller added: %1$s";

    /** Error message shown when attempting to add a duplicate seller. */
    public static final String MESSAGE_DUPLICATE_SELLER = "This seller already exists in the address book";

    /**
     * Constructs an {@code AddSellerCommand} to add the specified {@code Seller}.
     *
     * @param seller The {@code Seller} to be added.
     */
    public AddSellerCommand(Seller seller) {
        super(seller);
    }

    /**
     * Executes the AddSellerCommand.
     * Adds the seller to the model if the seller does not already exist in the address book.
     *
     * @param model The model which contains the address book data.
     * @return A {@link CommandResult} with a success message if the seller was added.
     * @throws CommandException if the seller already exists in the address book.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.hasClient(toAdd)) {
            if (toAdd instanceof Seller) {
                throw new CommandException(MESSAGE_DUPLICATE_SELLER);
            }
        }
        model.addClient(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS_SELLER, Messages.format(toAdd)));
    }

    /**
     * Checks if another object is equal to this AddSellerCommand.
     * Returns true if both objects are AddSellerCommand instances and their seller clients are equal.
     *
     * @param other The object to be compared with this AddSellerCommand.
     * @return True if both commands are equal, otherwise false.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AddSellerCommand)) {
            return false;
        }
        AddSellerCommand otherAddCommand = (AddSellerCommand) other;
        return this.toAdd.equals(otherAddCommand.toAdd);
    }
}
