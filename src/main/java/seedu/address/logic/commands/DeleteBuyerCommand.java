package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.client.Client;
import seedu.address.model.client.Phone;

/**
 * Represents a command to delete a buyer in the buyer management system.
 */
public class DeleteBuyerCommand extends DeleteClientCommand {
    /** The command word for this specific action. */
    public static final String COMMAND_WORD = "deletebuyer";

    /** Command parameter format for phone. */
    public static final String BUYER_PARAMETERS = String.format("%sBUYER_PHONE_NUMBER", PREFIX_PHONE);

    public static final String MESSAGE_USAGE = String.format(
            "%s: Deletes a buyer from the client book.\nParameters: %s\n%s",
            COMMAND_WORD,
            DeleteBuyerCommand.BUYER_PARAMETERS,
            DeleteClientCommand.CLIENT_RESTRICTIONS
    );
    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Buyer: %1$s";

    private static final Logger logger = LogsCenter.getLogger(DeleteBuyerCommand.class);

    /**
     * Constructs a {@code DeleteBuyerCommand} with the specified phone number.
     *
     * @param phoneNumber The phone number of the buyer to delete.
     */
    public DeleteBuyerCommand(Phone phoneNumber) {
        super(phoneNumber);
        assert phoneNumber != null : "phone number should never be null";
    }
    /**
     * Executes the delete buyer command and removes the buyer from the model.
     *
     * @param model The model which the command should operate on.
     * @return A {@code CommandResult} object representing the result of the delete operation.
     * @throws CommandException If the buyer cannot be found or deleted.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredClientList(Model.PREDICATE_SHOW_ALL_CLIENTS);
        // Search for the person with the specified phone number
        logger.info("Executing DeleteBuyer command with phone number: " + phoneNumber);
        Client personToDelete = model.getFilteredClientList().stream()
                .filter(Client::isBuyer)
                .filter(person -> person.getPhone().equals(phoneNumber))
                .findFirst()
                .orElseThrow(() -> {
                    logger.warning("Buyer with phone number " + phoneNumber + " not found");
                    return new CommandException(String.format("Buyer not found. Phone: %s", phoneNumber));
                });
        logger.info("Deleting Buyer: " + personToDelete);
        model.deleteClient(personToDelete);
        assert !model.getFilteredClientList().contains(personToDelete) : "Buyer should be deleted from the client book";
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
    }

    /**
     * Checks if this {@code DeleteBuyerCommand} is equal to another object.
     *
     * @param other The object to compare with this command.
     * @return {@code true} if the other object is an instance of {@code DeleteBuyerCommand} with the same phone number.
     */
    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof DeleteBuyerCommand)) {
            return false;
        }
        // state check
        DeleteBuyerCommand d = (DeleteBuyerCommand) other;
        return this.phoneNumber.equals(d.phoneNumber);
    }
}
