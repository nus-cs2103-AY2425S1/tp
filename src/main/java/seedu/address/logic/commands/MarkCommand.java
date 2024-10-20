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

/**
 * Marks attendance of an existing person in the address book.
 */
public class MarkCommand extends Command {

    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks attendance for the contact "
            + "by the index number used in the last person listing and for the tutorial number inputted. "
            + "Parameters: INDEX (must be a positive integer) "
            + "tut/TUTORIAL\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "tut/1";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Tutorial: %2$s";

    public static final String MESSAGE_MARK_SUCCESS = "Marked present in Tutorial: %2$s for Person: %1$s";
    public static final String MESSAGE_MARK_UNNECESSARY =
            "Person: %1$s is already marked as present for Tutorial: %2$s";

    private final Index index;
    private final Tutorial tutorial;

    /**
     * @param index of the person in the display list
     * @param tutorial number to mark attendance for
     */
    public MarkCommand(Index index, Tutorial tutorial) {
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
        if (newTutorials.get(tutorial) == AttendanceStatus.PRESENT) {
            throw new CommandException(
                    String.format(MESSAGE_MARK_UNNECESSARY, Messages.format(personToEdit), tutorial.tutorial));
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
     * the remark is added to or removed from
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit) {
        return String.format(MESSAGE_MARK_SUCCESS, Messages.format(personToEdit), tutorial.tutorial);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MarkCommand)) {
            return false;
        }

        // state check
        MarkCommand e = (MarkCommand) other;
        return index.equals(e.index)
                && tutorial.equals(e.tutorial);
    }
}
