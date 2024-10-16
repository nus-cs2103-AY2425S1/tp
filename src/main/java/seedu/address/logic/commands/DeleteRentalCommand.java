package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLIENT_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENTAL_INDEX;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.client.Client;
import seedu.address.model.rentalinformation.RentalInformation;

public class DeleteRentalCommand extends Command {
    public static final String COMMAND_WORD = "rdelete";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes the specified rental information "
            + "from the identified client.\n"
            + "Parameters: "
            + PREFIX_CLIENT_INDEX + "CLIENT_INDEX (must be a positive integer) "
            + PREFIX_RENTAL_INDEX + "RENTAL_INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CLIENT_INDEX + "1"
            + PREFIX_RENTAL_INDEX + "3";
    public static final String MESSAGE_DELETE_RENTAL_SUCCESS = "Deleted Rental Information: %1$s";

    private final Index clientIndex;
    private final Index rentalIndex;

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

        RentalInformation targetRental = targetClient.removeRentalInformation(rentalIndex.getZeroBased());
        // TODO: update the rental information list in Model, waiting for implementation from "rview".
        return new CommandResult(String.format(MESSAGE_DELETE_RENTAL_SUCCESS,
                Messages.formatRentalInformation(targetRental)));
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
