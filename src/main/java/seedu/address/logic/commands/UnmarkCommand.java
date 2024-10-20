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
 * Removes attendance of an existing person in the address book.
 */
public class UnmarkCommand extends Command {

    public static final String COMMAND_WORD = "unmark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks absence for the contact "
            + "by the index number used in the last person listing and for the tutorial number inputted. "
            + "Parameters: INDEX (must be a positive integer) "
            + "tut/TUTORIAL\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "tut/1";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Tutorial: %2$s";

    public static final String MESSAGE_UNMARK_SUCCESS = "Marked absent from Tutorial %s for Person: %s";
    public static final String MESSAGE_UNMARK_UNNECESSARY = "Person: %2$s is already marked absent from Tutorial %1$s";

    private final Index index;
    private final Tutorial tutorial;

    /**
     * @param index of the person in the display list
     * @param tutorial number to remove attendance for
     */
    public UnmarkCommand(Index index, Tutorial tutorial) {
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
        if (newTutorials.get(tutorial) == AttendanceStatus.ABSENT) {
            throw new CommandException(
                    String.format(MESSAGE_UNMARK_UNNECESSARY, tutorial.tutorial, Messages.format(personToEdit)));
        }

        newTutorials.put(tutorial, AttendanceStatus.ABSENT);

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
     * Generates a command execution success message for removing attendance
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit) {
        return String.format(MESSAGE_UNMARK_SUCCESS, tutorial.tutorial, Messages.format(personToEdit));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnmarkCommand)) {
            return false;
        }

        // state check
        UnmarkCommand e = (UnmarkCommand) other;
        return index.equals(e.index)
                && tutorial.equals(e.tutorial);
    }
}
