package seedu.address.logic.commands;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.AttendanceStatus;
import seedu.address.model.person.Person;
import seedu.address.model.person.Tutorial;

public class ResetCommand extends Command {

    public static final String COMMAND_WORD = "reset";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Resets attendance for the contact "
            + "by the index number used in the last person listing and for the tutorial number inputted. "
            + "Parameters: INDEX (must be a positive integer) "
            + "tut/TUTORIAL\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "tut/1";

    public static final String MESSAGE_RESET_UNNECESSARY =
            "Tutorial: %2$s is already marked as not taken place for Person: %1$s";

    public static final String MESSAGE_RESET_SUCCESS = "Marked not taken place in Tutorial: %2$s for Person: %1$s";

    private final Index index;
    private final Tutorial tutorial;

    /**
     * @param index The index of the person to be marked.
     * @param tutorial Tutorial to set as not taken place.
     */
    public ResetCommand(Index index, Tutorial tutorial) {
        this.index = index;
        this.tutorial = tutorial;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> currDisplayedList = model.getFilteredPersonList();

        if (index.getZeroBased() >= currDisplayedList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = currDisplayedList.get(index.getZeroBased());
        Map<Tutorial, AttendanceStatus> newTutorials = new LinkedHashMap<>(personToEdit.getTutorials());
        if (newTutorials.get(tutorial) == AttendanceStatus.NOT_TAKEN_PLACE) {
            throw new CommandException(
                    String.format(MESSAGE_RESET_UNNECESSARY, Messages.format(personToEdit), tutorial.tutorial));
        }

        newTutorials.put(tutorial, AttendanceStatus.PRESENT);

        Person editedPerson = new Person(
                personToEdit.getName(),
                personToEdit.getStudentId(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getTags(),
                newTutorials
        );
        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Generates a command execution success message based on whether
     * the tutorial was marked as not taken place.
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit) {
        return String.format(MESSAGE_RESET_SUCCESS, Messages.format(personToEdit), tutorial.tutorial);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ResetCommand)) {
            return false;
        }

        // state check
        ResetCommand e = (ResetCommand) other;
        return index.equals(e.index)
                && tutorial.equals(e.tutorial);
    }
}
