package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Buyer;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Property;
import seedu.address.model.person.Seller;

/**
 * Deletes a client's property identified by the client's name.
 * The name must correspond to a person in the current list of persons.
 */
public class DeletePropertyCommand extends Command {
    public static final String COMMAND_WORD = "delprop";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the client's property corresponding to the client's name.\n"
            + "Parameters: CLIENT_NAME (case-insensitive)\n"
            + "Example: " + COMMAND_WORD + " n/Tan Wen Xuan";

    public static final String MESSAGE_DELETE_PROPERTY_SUCCESS = "Successfully deleted property from %1$s";

    private final Name targetName;

    /**
     * Creates a {@code DeletePropertyCommand} to delete the property of the person with the specified {@code Name}.
     *
     * @param targetName The name of the person whose property will be deleted.
     */
    public DeletePropertyCommand(Name targetName) {
        this.targetName = targetName;
    }

    /**
     * Executes the command to delete the property of the person identified by the {@code targetName}.
     * If the person is found, the property is removed by setting it to an empty {@code Property}.
     *
     * @param model The model which contains the list of persons.
     * @return The result of the command execution.
     * @throws CommandException If the person cannot be found in the list.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        Person personToDeleteProperty = model.getPersonByName(targetName);
        if (!lastShownList.contains(personToDeleteProperty)) {
            String closestMatch = findClosestMatch(targetName.toString(), lastShownList);

            if (closestMatch != null) {
                throw new CommandException(String.format(Messages.MESSAGE_SUGGESTION, closestMatch));
            } else {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_INPUT);
            }
        }

        Person personWithoutProperty;
        if (personToDeleteProperty instanceof Buyer buyer) {
            personWithoutProperty = new Buyer(buyer.getName(),
                    buyer.getPhone(), buyer.getEmail(),
                    buyer.getTags(), buyer.getAppointment(), new Property(""));
        } else {
            Seller seller = (Seller) personToDeleteProperty;
            personWithoutProperty = new Seller(seller.getName(),
                    seller.getPhone(), seller.getEmail(),
                    seller.getTags(), seller.getAppointment(), new Property(""));
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PROPERTY_SUCCESS,
                personToDeleteProperty.getName()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeletePropertyCommand otherDeletePropertyCommand)) {
            return false;
        }

        return targetName.equals(otherDeletePropertyCommand.targetName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetName", targetName)
                .toString();
    }
}
