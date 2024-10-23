package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

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

    /** The command word used to trigger the AddBuyerCommand. */
    public static final String COMMAND_WORD = "addbuyer";

    /**
     * Describes the format and usage of the addbuyer command.
     * Includes the command word and expected parameters inherited from {@link AddClientCommand#CLIENT_PARAMETERS}.
     */
    public static final String MESSAGE_USAGE = String.format(
            "%s: Adds a buyer to the address book.\nParameters: %s\n%s",
            COMMAND_WORD,
            AddClientCommand.CLIENT_PARAMETERS,
            AddClientCommand.CLIENT_RESTRICTIONS
    );

    /** Success message for adding a new buyer, with a placeholder for the buyer's details. */
    public static final String MESSAGE_SUCCESS_BUYER = "New buyer added: %1$s";

    /** Error message shown when attempting to add a duplicate buyer. */
    public static final String MESSAGE_DUPLICATE_BUYER = "This buyer already exists in the address book";

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

        // Ensure that toAdd is a valid buyer and not null
        assert toAdd != null : "Buyer to be added cannot be null";
        logger.info("Executing AddBuyerCommand for buyer: " + toAdd);

        if (model.hasClient(toAdd) && toAdd instanceof Buyer) {
            logger.warning("Attempted to add a duplicate buyer: " + toAdd);
            throw new CommandException(MESSAGE_DUPLICATE_BUYER);
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
