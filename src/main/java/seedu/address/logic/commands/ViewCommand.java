package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

/**
 * Views the full profile of a patient in the database.
 */
public class ViewCommand extends Command {

    public static final String MESSAGE_ARGUMENTS = "View: %2$s, Nric: %1$s";
    public static final String COMMAND_WORD = "view";
    public static final String MESSAGE_VIEW_SUCCESS = "Here are the patient details.";
    public static final String MESSAGE_PATIENT_NOT_FOUND = "Patient not found";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views patient details with the given NRIC. "
            + "Format: view i/NRIC\n"
            + "Example: " + COMMAND_WORD + " i/S1234567A";

    private final Nric nric;

    public ViewCommand(Nric nric) {
        this.nric = nric;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        // Find the person with the given nric
        Optional<Person> optionalPerson = lastShownList.stream()
                .filter(person -> person.getNric().equals(nric))
                .findFirst();

        if (!optionalPerson.isPresent()) {
            throw new CommandException(MESSAGE_PATIENT_NOT_FOUND);
        }

        Person person = optionalPerson.get();

        return new CommandResult(generateSuccessMessage(person), null, false, person,
                true, false);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewCommand)) {
            return false;
        }

        ViewCommand e = (ViewCommand) other;
        return nric.equals(e.nric);
    }

    private String generateSuccessMessage(Person person) {
        return String.format(MESSAGE_VIEW_SUCCESS, person.getName());
    }

}
