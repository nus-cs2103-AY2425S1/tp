package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Grade;
import seedu.address.model.person.Person;


/**
 * Deletes a grade for a person identified by the index in the filtered person list.
 */
public class DeleteGradeCommand extends Command {

    public static final String COMMAND_WORD = "deleteGrade";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a grade of the person identified by the "
            + "index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "n/TEST_NAME\n"
            + "Example: " + COMMAND_WORD + " 1 n/Midterm";

    public static final String MESSAGE_DELETE_GRADE_SUCCESS = "Deleted grade from %1$s: %2$s";
    public static final String MESSAGE_GRADE_NOT_FOUND = "Grade for test '%1$s' not found.";

    private final Index index;
    private final String testName;

    /**
     * Creates a DeleteGradeCommand to remove the specified grade from a person.
     *
     * @param index Index of the person in the filtered person list.
     * @param testName Index of the test whose grade is to be deleted.
     */
    public DeleteGradeCommand(Index index, String testName) {
        requireNonNull(index);
        requireNonNull(testName);
        this.index = index;
        this.testName = testName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException("The person index provided is invalid.");
        }

        final Person personToEdit = lastShownList.get(index.getZeroBased());

        Grade gradeToDelete = personToEdit.getGrade(this.testName);
        if (gradeToDelete == null) {
            throw new CommandException(String.format(MESSAGE_GRADE_NOT_FOUND, testName));
        }

        final Person editedPerson = personToEdit.removeGrade(testName);
        model.setPerson(personToEdit, editedPerson);

        return new CommandResult(
                String.format(MESSAGE_DELETE_GRADE_SUCCESS, personToEdit.getName(), this.testName));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteGradeCommand)) {
            return false;
        }

        DeleteGradeCommand otherCommand = (DeleteGradeCommand) other;
        return index.equals(otherCommand.index) && testName.equals(otherCommand.testName);
    }
}

