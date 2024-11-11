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
import seedu.address.model.client.Buyer;

/**
 * Adds a {@code Buyer} to the address book.
 * Extends {@link AddClientCommand} and utilizes its functionality to add a specific type of client: {@code Buyer}.
 */
public class AddBuyerCommand extends AddClientCommand {

    /**
     * A string describing the expected parameters for adding a client.
     * Includes name, phone number, and email.
     */
    public static final String BUYER_PARAMETERS = String.format(
            "%sBUYER_NAME %sBUYER_PHONE_NUMBER %sBUYER_EMAIL",
            PREFIX_NAME,
            PREFIX_PHONE,
            PREFIX_EMAIL
    );

    /** The command word used to trigger the AddBuyerCommand. */
    public static final String COMMAND_WORD = "addbuyer";

    /**
     * Describes the format and usage of the addbuyer command.
     * Includes the command word and expected parameters inherited from {@link AddBuyerCommand#BUYER_PARAMETERS}.
     */
    public static final String MESSAGE_USAGE = String.format(
            "%s: Adds a buyer to the client book.\nParameters: %s\n%s",
            COMMAND_WORD,
            AddBuyerCommand.BUYER_PARAMETERS,
            AddClientCommand.CLIENT_RESTRICTIONS
    );

    /** Success message for adding a new buyer, with a placeholder for the buyer's details. */
    public static final String MESSAGE_SUCCESS_BUYER = "New buyer added: %1$s";

    /** Error message shown when attempting to add a duplicate buyer. */
    public static final String MESSAGE_DUPLICATE_BUYER = "A buyer with this phone number "
            + "already exists in the client book";

    /** Error message shown when attempting to add a buyer with the same email as an existing buyer. */
    public static final String MESSAGE_DUPLICATE_EMAIL = "Duplicate email detected. "
            + "For the addbuyer command, duplicate emails are detected if:\n"
            + "1. there is a buyer with the same email already in the client book.\n"
            + "2. there is a seller with the same email but a different phone number in the client book. "
            + "Having the same email address as an existing seller with a different phone number is not allowed as "
            + "emails should be unique to a client.";

    /** Logger to log relevant information for debugging purposes. */
    private static final Logger logger = LogsCenter.getLogger(AddBuyerCommand.class);

    /**
     * Constructs an {@code AddBuyerCommand} to add the specified {@code Buyer}.
     *
     * @param buyer The {@code Buyer} to be added.
     */
    public AddBuyerCommand(Buyer buyer) {
        super(buyer);
        // Defensive programming: Ensure that the buyer is not null
        assert buyer != null : "Buyer should not be null";
        logger.info("AddBuyerCommand created for buyer: " + buyer);
    }

    /**
     * Executes the AddBuyerCommand.
     * Adds the buyer to the model if the buyer does not already exist in the address book.
     *
     * @param model The model which contains the address book data.
     * @return A {@link CommandResult} with a success message if the buyer was added.
     * @throws CommandException if the buyer already exists in the address book.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredClientList(Model.PREDICATE_SHOW_ALL_CLIENTS);

        // Ensure that toAdd is a valid buyer and not null
        assert toAdd != null : "Buyer to be added cannot be null";
        logger.info("Executing AddBuyerCommand for buyer: " + toAdd);

        if (model.hasClient(toAdd) && toAdd instanceof Buyer) {
            logger.warning("Attempted to add a duplicate buyer: " + toAdd);
            throw new CommandException(MESSAGE_DUPLICATE_BUYER);
        }

        if (model.sameEmailExists(toAdd)) {
            logger.warning("Attempted to add a buyer with the same email as an existing buyer: " + toAdd);
            throw new CommandException(MESSAGE_DUPLICATE_EMAIL);
        }

        model.addClient(toAdd);

        logger.info("Successfully added buyer: " + toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS_BUYER, Messages.format(toAdd)));
    }

    /**
     * Checks if another object is equal to this AddBuyerCommand.
     * Returns true if both objects are AddBuyerCommand instances and their buyer clients are equal.
     *
     * @param other The object to be compared with this AddBuyerCommand.
     * @return True if both commands are equal, otherwise false.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AddBuyerCommand)) {
            return false;
        }
        AddBuyerCommand otherAddCommand = (AddBuyerCommand) other;
        return this.toAdd.equals(otherAddCommand.toAdd);
    }
}
