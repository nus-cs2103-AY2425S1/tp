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
 * Resets attendance of a person in the address book.
 */
public class ResetCommand extends Command {

    public static final String COMMAND_WORD = "reset";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Resets attendance for the contact "
            + "by the index number used in the last person listing and for the tutorial number inputted. "
            + "Parameters: INDEX (must be a positive integer) "
            + "tut/TUTORIAL\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "tut/1";

    private final Index index;
    private final Tutorial tutorial;

    /**
     * @param index The index of the person to be marked.
     * @param tutorial The tutorial to set as not taken place.
     */
    public ResetCommand(Index index, Tutorial tutorial) {
        this.index = index;
        this.tutorial = tutorial;
    }

    /**
     * Takes a person and tutorial and resets the specified tutorial and returns that person with the new tutorials.
     * @param personToEdit The person whose attendance will be reset.
     * @param tutorial The tutorial to reset attendance for.
     */
    private Person generateResetPerson(Person personToEdit, Tutorial tutorial) throws CommandException {
        Map<Tutorial, AttendanceStatus> newTutorials = new LinkedHashMap<>(personToEdit.getTutorials());
        if (newTutorials.get(tutorial) == AttendanceStatus.NOT_TAKEN_PLACE) {
            throw new CommandException(
                    String.format(Messages.MESSAGE_RESET_UNNECESSARY, Messages.format(personToEdit),
                            tutorial.tutorial));
        }
        newTutorials.put(tutorial, AttendanceStatus.NOT_TAKEN_PLACE);

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
        List<Person> personToEditList = CommandUtil.filterPersonsByIndex(currDisplayedList, index);
        List<Person> editedPersonList = new ArrayList<>();
        for (Person personToEdit : personToEditList) {
            Person editedPerson = this.generateResetPerson(personToEdit, this.tutorial);
            editedPersonList.add(editedPerson);
            model.setPerson(personToEdit, editedPerson);
        }
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(generateSuccessMessage(editedPersonList));
    }

    /**
     * Generates a command execution success message for the tutorials that were marked as not taken place.
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(List<Person> personListToEdit) {
        return String.join("\n", personListToEdit.stream()
                .map(personToEdit -> String.format(Messages.MESSAGE_RESET_SUCCESS,
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
        if (!(other instanceof ResetCommand)) {
            return false;
        }

        // state check
        ResetCommand e = (ResetCommand) other;
        return index.equals(e.index)
                && tutorial.equals(e.tutorial);
    }
}
