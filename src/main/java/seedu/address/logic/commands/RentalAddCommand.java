package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER_LIST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEPOSIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTHLY_RENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENTAL_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENTAL_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENT_DUE_DATE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.client.Client;
import seedu.address.model.rentalinformation.RentalInformation;

/**
 * Adds a client to the address book.
 */
public class RentalAddCommand extends Command {

    public static final String COMMAND_WORD = "radd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a rental information to a client. "
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_RENTAL_START_DATE + "RENTAL START DATE "
            + PREFIX_RENTAL_END_DATE + "RENTAL END DATE "
            + PREFIX_RENT_DUE_DATE + "RENT DUE DATE "
            + PREFIX_MONTHLY_RENT + "MONTHLY RENT "
            + PREFIX_DEPOSIT + "DEPOSIT "
            + PREFIX_CUSTOMER_LIST + "CUSTOMER LIST "
            + "\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_ADDRESS + "BLK 1 Bishan "
            + PREFIX_RENTAL_START_DATE + "01/01/2024 "
            + PREFIX_RENTAL_END_DATE + "31/12/2024 "
            + PREFIX_RENT_DUE_DATE + "15 "
            + PREFIX_MONTHLY_RENT + "2700 "
            + PREFIX_DEPOSIT + "8100 "
            + PREFIX_CUSTOMER_LIST + "Steven;David ";

    public static final String MESSAGE_SUCCESS = "New rental information is added to the client: %1$s";
    public static final String MESSAGE_DUPLICATE_RENTAL_INFORMATION =
            "This rental information already exists in the address book";

    private final Index index;
    private final RentalInformation toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Client}
     */
    public RentalAddCommand(Index index, RentalInformation rentalInformation) {
        requireNonNull(index);
        requireNonNull(rentalInformation);

        this.index = index;
        toAdd = rentalInformation;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Client> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Client clientToEdit = lastShownList.get(index.getZeroBased());
        Set<RentalInformation> updatedRentalInformationList = new HashSet<>(clientToEdit.getRentalInformation());
        updatedRentalInformationList.add(toAdd);
        Client updatedClient = new Client(clientToEdit.getName(), clientToEdit.getPhone(), clientToEdit.getEmail(),
                clientToEdit.getTags(), updatedRentalInformationList);

        model.setPerson(clientToEdit, updatedClient);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.formatRentalInformation(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RentalAddCommand)) {
            return false;
        }

        RentalAddCommand otherRentalAddCommand = (RentalAddCommand) other;
        return this.toAdd.equals(otherRentalAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
