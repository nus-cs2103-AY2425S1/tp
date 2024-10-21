package seedu.address.logic.commands;

import java.util.ArrayList;
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

    private final List<Index> indexList = new ArrayList<>();
    private final Tutorial tutorial;
    private final boolean shouldMarkAll;

    /**
     * @param index of the person in the display list
     * @param tutorial number to mark attendance for
     */
    public MarkCommand(Index index, Tutorial tutorial) {
        this.indexList.add(index);
        this.shouldMarkAll = false;
        this.tutorial = tutorial;
    }

    /**
     * @param shouldMarkAll whether to mark all persons in the display list
     * @param tutorial number to mark attendance for
     */
    public MarkCommand(boolean shouldMarkAll, Tutorial tutorial) {
        this.shouldMarkAll = shouldMarkAll;
        this.tutorial = tutorial;
    }

    /**
     * @param currDisplayedList list of persons currently displayed
     * @return personList
     * @throws CommandException when index is out of range
     */
    private List<Person> filterPersonsByIndex(List<Person> currDisplayedList) throws CommandException {
        if (this.shouldMarkAll) {
            return currDisplayedList;
        } else {
            List<Person> personList = new ArrayList<>();
            for (Index index : this.indexList) {
                if (index.getZeroBased() >= currDisplayedList.size()) {
                    throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
                }
                personList.add(currDisplayedList.get(index.getZeroBased()));
            }
            return personList;
        }
    }

    /**
     * @param personToEdit person whose attendance will be marked
     */
    private Person generateMarkedPerson(Person personToEdit) throws CommandException {
        Map<Tutorial, AttendanceStatus> newTutorials = new LinkedHashMap<>(personToEdit.getTutorials());
        if (newTutorials.get(tutorial) == AttendanceStatus.PRESENT) {
            throw new CommandException(
                    String.format(MESSAGE_MARK_UNNECESSARY, Messages.format(personToEdit), tutorial.tutorial));
        }
        newTutorials.put(tutorial, AttendanceStatus.PRESENT);

        return new Person(
                personToEdit.getName(),
                personToEdit.getStudentId(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getTags(),
                newTutorials
        );
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> currDisplayedList = model.getFilteredPersonList();
        List<Person> personToEditList = this.filterPersonsByIndex(currDisplayedList);
        List<Person> editedPersonList = new ArrayList<>();
        for (Person personToEdit : personToEditList) {
            Person editedPerson = this.generateMarkedPerson(personToEdit);
            editedPersonList.add(editedPerson);
            model.setPerson(personToEdit, editedPerson);
        }
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(generateSuccessMessage(editedPersonList));
    }

    /**
     * Generates a command execution success message based on whether
     * the remark is added to or removed from
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(List<Person> personListToEdit) {
        return String.join("\n", personListToEdit.stream()
                .map(personToEdit -> String.format(MESSAGE_MARK_SUCCESS,
                        Messages.format(personToEdit), tutorial.tutorial))
                .toArray(String[]::new));
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
        return indexList.equals(e.indexList)
                && tutorial.equals(e.tutorial);
    }
}
