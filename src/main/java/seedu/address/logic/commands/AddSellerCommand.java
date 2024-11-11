package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.client.Seller;

/**
 * Adds a {@code Seller} to the address book.
 * Extends {@link AddClientCommand} and uses its functionality to add a specific type of client: {@code Seller}.
 */
public class AddSellerCommand extends AddClientCommand {

    /**
     * A string describing the expected parameters for adding a client.
     * Includes name, phone number, and email.
     */
    public static final String SELLER_PARAMETERS = String.format(
            "%sSELLER_NAME %sSELLER_PHONE_NUMBER %sSELLER_EMAIL",
            PREFIX_NAME,
            PREFIX_PHONE,
            PREFIX_EMAIL
    );

    /** The command word used to trigger the AddSellerCommand. */
    public static final String COMMAND_WORD = "addseller";

    /**
     * Describes the format and usage of the addseller command.
     * Includes the command word and expected parameters inherited from {@link AddSellerCommand#SELLER_PARAMETERS}.
     */
    public static final String MESSAGE_USAGE = String.format(
            "%s: Adds a seller to the client book.\nParameters: %s\n%s",
            COMMAND_WORD,
            AddSellerCommand.SELLER_PARAMETERS,
            AddClientCommand.CLIENT_RESTRICTIONS
    );

    /** Success message for adding a new seller, with a placeholder for the seller's details. */
    public static final String MESSAGE_SUCCESS_SELLER = "New seller added: %1$s";

    /** Error message shown when attempting to add a duplicate seller. */
    public static final String MESSAGE_DUPLICATE_SELLER = "This seller already exists in the client book";

    /** Error message shown when attempting to add a buyer with the same email as an existing buyer. */
    public static final String MESSAGE_DUPLICATE_EMAIL = "Duplicate email detected. "
            + "For the addseller command, duplicate emails are detected if:\n"
            + "1. there is a seller with the same email already in the client book.\n"
            + "2. there is a buyer with the same email but a different phone number in the client book. "
            + "Having the same email address as an existing buyer with a different phone number is not allowed as "
            + "emails should be unique to a client.";

    /** Logger to log relevant information for debugging purposes. */
    private static final Logger logger = LogsCenter.getLogger(AddSellerCommand.class);

    /**
     * Constructs an {@code AddSellerCommand} to add the specified {@code Seller}.
     *
     * @param seller The {@code Seller} to be added.
     */
    public AddSellerCommand(Seller seller) {
        super(seller);
        // Ensure that the seller is not null
        assert seller != null : "Seller should not be null";
        logger.info("AddSellerCommand created for seller: " + seller);
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
        model.updateFilteredClientList(Model.PREDICATE_SHOW_ALL_CLIENTS);

        // Defensive programming: Ensure that toAdd is a valid seller and not null
        assert toAdd != null : "Seller to be added cannot be null";
        logger.info("Executing AddSellerCommand for seller: " + toAdd);

        if (model.hasClient(toAdd)) {
            if (toAdd instanceof Seller) {
                logger.warning("Attempted to add a duplicate seller: " + toAdd);
                throw new CommandException(MESSAGE_DUPLICATE_SELLER);
            }
        }

        if (model.sameEmailExists(toAdd)) {
            logger.warning("Attempted to add a buyer with the same email as an existing buyer: " + toAdd);
            throw new CommandException(MESSAGE_DUPLICATE_EMAIL);
        }

        model.addClient(toAdd);

        logger.info("Successfully added seller: " + toAdd);
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
