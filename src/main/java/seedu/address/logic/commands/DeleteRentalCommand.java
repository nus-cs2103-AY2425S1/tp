package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLIENT_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENTAL_INDEX;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.client.Client;
import seedu.address.model.rentalinformation.RentalInformation;

/**
 * Deletes a rental information from a client identified using it's displayed index from the address book.
 */
public class DeleteRentalCommand extends Command {
    public static final String COMMAND_WORD = "rdelete";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes the specified rental information "
            + "from the identified client.\n"
            + "Parameters: "
            + PREFIX_CLIENT_INDEX + "CLIENT_INDEX (must be a positive integer) "
            + PREFIX_RENTAL_INDEX + "RENTAL_INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CLIENT_INDEX + "1 "
            + PREFIX_RENTAL_INDEX + "3";
    public static final String MESSAGE_DELETE_RENTAL_SUCCESS = "Deleted Rental Information: %1$s";
    public static final String MESSAGE_PROMPT = "This will delete the rental information:\n"
            + "%1$s\n"
            + "Confirm command? (y/n)";

    private final Index clientIndex;
    private final Index rentalIndex;

    /**
     * Creates a DeleteRentalCommand to delete the rental information identified by {@code rentalIndex} from the
     * client identified by {@code clientIndex}.
     */
    public DeleteRentalCommand(Index clientIndex, Index rentalIndex) {
        this.clientIndex = clientIndex;
        this.rentalIndex = rentalIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Client> lastShownList = model.getFilteredPersonList();

        if (clientIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Client targetClient = lastShownList.get(clientIndex.getZeroBased());
        List<RentalInformation> rentalInformationList = targetClient.getRentalInformation();

        if (rentalIndex.getZeroBased() >= rentalInformationList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RENTAL_DISPLAYED_INDEX);
        }

        RentalInformation targetRental = rentalInformationList.get(rentalIndex.getZeroBased());

        return new CommandResult(String.format(MESSAGE_PROMPT, Messages.formatRentalInformation(targetRental)), () ->
                confirmDelete(model, targetClient, rentalIndex));
    }

    private CommandResult confirmDelete(Model model, Client targetClient, Index rentalIndex) {
        List<RentalInformation> rentalInformationList = new ArrayList<>(targetClient.getRentalInformation());
        RentalInformation rentalToDelete = rentalInformationList.remove(rentalIndex.getZeroBased());

        Client updatedClient = new Client(targetClient.getName(), targetClient.getPhone(), targetClient.getEmail(),
                targetClient.getTags(), rentalInformationList);

        model.setPerson(targetClient, updatedClient);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateVisibleRentalInformationList(rentalInformationList);
        model.setLastViewedClient(updatedClient);

        return new CommandResult(String.format(MESSAGE_DELETE_RENTAL_SUCCESS,
                Messages.formatRentalInformation(rentalToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteRentalCommand)) {
            return false;
        }

        DeleteRentalCommand otherDeleteCommand = (DeleteRentalCommand) other;
        return clientIndex.equals(otherDeleteCommand.clientIndex)
                && rentalIndex.equals(otherDeleteCommand.rentalIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("clientIndex", clientIndex)
                .add("rentalIndex", rentalIndex)
                .toString();
    }
}
