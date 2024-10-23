package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.util.Pair;
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
            + "Parameters: \n"
            + "INDEX (must be a positive integer) \n"
            + "tut/TUTORIAL in the format of \n"
            + "1) A positive number between 1-12 \n"
            + "2) A list of numbers eg. [1,3,5] \n"
            + "3) A range of two numbers eg. 3-6 \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "tut/1-5";

    private final Index index;
    private final List<Tutorial> tutorials;

    /**
     * @param index The index of the person to be marked.
     * @param tutorial single tutorial to set as not taken place.
     */
    public ResetCommand(Index index, Tutorial tutorial) {
        this.index = index;
        this.tutorials = List.of(tutorial);
    }

    /**
     * Constructor for resetting multiple tutorials.
     * @param index of the person in the display list.
     * @param tutorials list of tutorials to set as not taken place.
     */
    public ResetCommand(Index index, List<Tutorial> tutorials) {
        this.index = index;
        this.tutorials = tutorials.stream().distinct().collect(Collectors.toList());
    }

    /**
     * Takes a person and tutorial and resets the specified tutorial and returns that person with the new tutorials.
     * @param personToEdit The person whose attendance will be reset.
     * @param tutorials tutorials to reset attendance for.
     */
    private Pair<Person, String> generateResetPerson(Person personToEdit,
                                                     List<Tutorial> tutorials)
                                       throws CommandException {
        Map<Tutorial, AttendanceStatus> newTutorials = new LinkedHashMap<>(personToEdit.getTutorials());
        List<String> unnecessaryResetTutorials = new ArrayList<>();
        List<String> resetTutorials = new ArrayList<>();
        String formattedTutorials;

        for (Tutorial tutorial : tutorials) {
            if (newTutorials.get(tutorial) == AttendanceStatus.NOT_TAKEN_PLACE) {
                unnecessaryResetTutorials.add(tutorial.tutorial);
                continue;
            }

            newTutorials.put(tutorial, AttendanceStatus.NOT_TAKEN_PLACE);
            resetTutorials.add(tutorial.tutorial);
        }

        String formattedUnnecessaryTutorials = String.join(", ", unnecessaryResetTutorials);

        if (resetTutorials.isEmpty()) {
            formattedTutorials = String.format(Messages.MESSAGE_RESET_UNNECESSARY,
                    personToEdit.getName(), formattedUnnecessaryTutorials);
        } else {
            formattedTutorials = generateSuccessMessage(personToEdit, String.join(", ", resetTutorials));
        }

        return new Pair<Person, String>(
                new Person(
                    personToEdit.getName(),
                    personToEdit.getStudentId(),
                    personToEdit.getPhone(),
                    personToEdit.getEmail(),
                    personToEdit.getTags(),
                    newTutorials
                ),
                formattedTutorials
        );
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> currDisplayedList = model.getFilteredPersonList();
        List<Person> personToEditList = CommandUtil.filterPersonsByIndex(currDisplayedList, index);
        List<Person> editedPersonList = new ArrayList<>();
        String finalSuccessMessage = "";
        for (Person personToEdit : personToEditList) {
            Pair<Person, String> editedPair = this.generateResetPerson(personToEdit, this.tutorials);
            Person editedPerson = editedPair.getKey();
            String formattedTutorials = editedPair.getValue();
            editedPersonList.add(editedPerson);
            model.setPerson(personToEdit, editedPerson);
            String successMessage = formattedTutorials + "\n";
            finalSuccessMessage += successMessage;
        }
        finalSuccessMessage = finalSuccessMessage.trim();
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(finalSuccessMessage);
    }

    /**
     * Generates a command execution success message for the tutorials that were marked as not taken place.
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit, String resetTutorials) {
        return String.format(Messages.MESSAGE_RESET_SUCCESS, Messages.format(personToEdit), resetTutorials);
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
                && tutorials.equals(e.tutorials);
    }
}
