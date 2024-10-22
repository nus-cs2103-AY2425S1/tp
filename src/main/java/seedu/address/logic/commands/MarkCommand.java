package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
            + "by the index number used in the last person listing and for the tutorial number inputted. \n"
            + "Parameters: \n"
            + "INDEX (must be a positive integer) \n"
            + "tut/TUTORIAL in the format of \n"
            + "1) A positive number between 1-12 \n"
            + "2) A list of numbers eg. [1,3,5] \n"
            + "3) A range of two numbers eg. 3-6 \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "tut/1-5";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Tutorial: %2$s";

    public static final String MESSAGE_MARK_SUCCESS = "Marked present in Tutorial(s): %2$s for Person: %1$s";
    public static final String MESSAGE_MARK_UNNECESSARY =
            "Person: %1$s is already marked as present for Tutorial(s): %2$s";

    private final Index index;
    private final List<Tutorial> tutorials;

    /**
     * @param index of the person in the display list
     * @param tutorial single tutorial to mark attendance for
     */
    public MarkCommand(Index index, Tutorial tutorial) {
        this.index = index;
        this.tutorials = List.of(tutorial);
    }

    /**
     * Constructor for marking multiple tutorials.
     * @param index of the person in the display list
     * @param tutorials list of tutorials to mark attendance for
     */
    public MarkCommand(Index index, List<Tutorial> tutorials) {
        this.index = index;
        this.tutorials = tutorials.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> currDisplayedList = model.getFilteredPersonList();

        if (index.getZeroBased() >= currDisplayedList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = currDisplayedList.get(index.getZeroBased());
        Map<Tutorial, AttendanceStatus> newTutorials = new LinkedHashMap<>(personToEdit.getTutorials());
        List<String> unnecessaryMarkTutorials = new ArrayList<>();
        List<String> markedTutorials = new ArrayList<>();

        // Handle marking for list of tutorials
        for (Tutorial tutorial : tutorials) {
            if (newTutorials.get(tutorial) == AttendanceStatus.PRESENT) {
                unnecessaryMarkTutorials.add(tutorial.tutorial);
                continue;
            }

            newTutorials.put(tutorial, AttendanceStatus.PRESENT);
            markedTutorials.add(tutorial.tutorial);
        }

        String formattedUnnecessaryTutorials = String.join(", ", unnecessaryMarkTutorials);

        if (markedTutorials.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_MARK_UNNECESSARY,
                    personToEdit.getName(), formattedUnnecessaryTutorials));
        }

        String formattedTutorials = String.join(", ", markedTutorials);

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

        return new CommandResult(generateSuccessMessage(editedPerson, formattedTutorials));
    }

    /**
     * Generates a command execution success message based on whether
     * the remark is added to or removed from
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit, String markedTutorials) {
        return String.format(MESSAGE_MARK_SUCCESS, Messages.format(personToEdit), markedTutorials);
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
                && tutorials.equals(e.tutorials);
    }
}
