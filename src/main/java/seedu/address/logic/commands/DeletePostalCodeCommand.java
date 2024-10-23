package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.PostalCode;
/**
 * Deletes all customers with given postal code
 */
public class DeletePostalCodeCommand extends Command {

    public static final String COMMAND_WORD = "deletePC";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes all customers with given postal code.\n"
            + "Parameters: POSTAL CODE (must be a 6 digit code)\n"
            + "Example: " + COMMAND_WORD + " 540123";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted customer(s) with postal code %1$s: %2$s";

    private final PostalCode postalCode;

    public DeletePostalCodeCommand(PostalCode postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!isValidPostalCode(postalCode)) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_POSTAL, postalCode));
        }
        List<Person> peopleToDelete = model.getPeopleByPostalCode(postalCode);

        if (peopleToDelete.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_NO_MATCH);
        }

        for (Person personToDelete : peopleToDelete) {
            model.deletePerson(personToDelete);
        }

        List<String> deletedNames = peopleToDelete.stream()
                .map(person -> person.getName().toString())
                .collect(Collectors.toList());
        String namesString = String.join(", ", deletedNames);

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, postalCode, namesString));
    }
    /**
     * Validates the postal code format.
     * @param postalCode The postal code to validate.
     * @return true if the postal code is valid; false otherwise.
     */
    private boolean isValidPostalCode(PostalCode postalCode) {

        return Pattern.matches("\\d{6}", postalCode.toString());
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeletePostalCodeCommand)) {
            return false;
        }

        DeletePostalCodeCommand otherDeleteCommand = (DeletePostalCodeCommand) other;
        return postalCode.equals(otherDeleteCommand.postalCode);
    }

    @Override
    public String toString() {
        return "DeletePostalCommand{"
                + "postalCode='" + postalCode + '\''
                + '}';
    }
}
