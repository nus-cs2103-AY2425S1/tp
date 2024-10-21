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

    private final List<Index> indexList = new ArrayList<>();
    private final Tutorial tutorial;
    private final boolean shouldUnmarkAll;

    /**
     * @param index of the person in the display list
     * @param tutorial number to remove attendance for
     */
    public UnmarkCommand(Index index, Tutorial tutorial) {
        this.indexList.add(index);
        this.shouldUnmarkAll = false;
        this.tutorial = tutorial;
    }

    /**
     * @param shouldUnmarkAll whether to unmark all persons in the display list
     * @param tutorial number to remove attendance for
     */
    public UnmarkCommand(boolean shouldUnmarkAll, Tutorial tutorial) {
        this.shouldUnmarkAll = shouldUnmarkAll;
        this.tutorial = tutorial;
    }

    /**
     * @param personToEdit person whose attendance will be unmarked
     * @param tutorial tutorial to unmark attendance for
     */
    private Person generateUnmarkedPerson(Person personToEdit, Tutorial tutorial) throws CommandException {
        Map<Tutorial, AttendanceStatus> newTutorials = new LinkedHashMap<>(personToEdit.getTutorials());
        if (newTutorials.get(tutorial) == AttendanceStatus.ABSENT) {
            throw new CommandException(
                    String.format(Messages.MESSAGE_UNMARK_UNNECESSARY,
                            tutorial.tutorial, Messages.format(personToEdit)));
        }
        newTutorials.put(tutorial, AttendanceStatus.ABSENT);

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
        List<Person> personToEditList = CommandUtil.filterPersonsByIndex(currDisplayedList, indexList, shouldUnmarkAll);
        List<Person> editedPersonList = new ArrayList<>();
        for (Person personToEdit : personToEditList) {
            Person editedPerson = this.generateUnmarkedPerson(personToEdit, this.tutorial);
            editedPersonList.add(editedPerson);
            model.setPerson(personToEdit, editedPerson);
        }
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(generateSuccessMessage(editedPersonList));
    }

    /**
     * Generates a command execution success message for removing attendance
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(List<Person> personListToEdit) {
        return String.join("\n", personListToEdit.stream()
                .map(personToEdit -> String.format(Messages.MESSAGE_UNMARK_SUCCESS,
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
        if (!(other instanceof UnmarkCommand)) {
            return false;
        }

        // state check
        UnmarkCommand e = (UnmarkCommand) other;
        return indexList.equals(e.indexList)
                && tutorial.equals(e.tutorial);
    }
}
