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
 * Removes attendance of an existing person in the address book.
 */
public class UnmarkCommand extends Command {

    public static final String COMMAND_WORD = "unmark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks absence for the contact "
            + "by the index number used in the last person listing and for the tutorial number inputted. \n"
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
     * @param index of the person in the display list
     * @param tutorial single tutorial to remove attendance for
     */
    public UnmarkCommand(Index index, Tutorial tutorial) {
        this.index = index;
        this.tutorials = List.of(tutorial);
    }

    /**
     * Constructor for unmarking multiple tutorials.
     * @param index of the person in the display list
     * @param tutorials list of tutorials to unmark attendance for
     */
    public UnmarkCommand(Index index, List<Tutorial> tutorials) {
        this.index = index;
        this.tutorials = tutorials.stream().distinct().collect(Collectors.toList());
    }

    /**
     * @param personToEdit person whose attendance will be unmarked
     * @param tutorials tutorial to unmark attendance for
     */
    private Pair<Person, String> generateUnmarkedPerson(Person personToEdit,
                                                        List<Tutorial> tutorials)
                                                        throws CommandException {
        Map<Tutorial, AttendanceStatus> newTutorials = new LinkedHashMap<>(personToEdit.getTutorials());
        List<String> unnecessaryUnmarkTutorials = new ArrayList<>();
        List<String> unmarkedTutorials = new ArrayList<>();
        String formattedTutorials;

        for (Tutorial tutorial : tutorials) {
            if (newTutorials.get(tutorial) == AttendanceStatus.ABSENT) {
                unnecessaryUnmarkTutorials.add(tutorial.tutorial);
                continue;
            }

            newTutorials.put(tutorial, AttendanceStatus.ABSENT);
            unmarkedTutorials.add(tutorial.tutorial);
        }

        String formattedUnnecessaryTutorials = String.join(", ", unnecessaryUnmarkTutorials);

        if (unmarkedTutorials.isEmpty()) {
            formattedTutorials = String.format(Messages.MESSAGE_UNMARK_UNNECESSARY,
                    personToEdit.getName(), formattedUnnecessaryTutorials);
        } else {
            formattedTutorials = generateSuccessMessage(personToEdit, String.join(", ", unmarkedTutorials));
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
            Pair<Person, String> editedPair = this.generateUnmarkedPerson(personToEdit, this.tutorials);
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
     * Generates a command execution success message for removing attendance
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit, String unmarkedTutorials) {
        return String.format(Messages.MESSAGE_UNMARK_SUCCESS, Messages.format(personToEdit), unmarkedTutorials);
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
                && tutorials.equals(e.tutorials);
    }
}
