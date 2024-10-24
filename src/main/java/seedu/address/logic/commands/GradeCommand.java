package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;
import seedu.address.model.tag.Grade;

/**
 * Changes the grade of an existing person in the address book.
 */
public class GradeCommand extends Command {

    public static final String COMMAND_WORD = "grade";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the grade of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing grade will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_GRADE + "[GRADE_INDEX] (must be an integer from 1 to 4)\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_GRADE + "1.";

    public static final String MESSAGE_ADD_GRADE_SUCCESS = "Added grade to Person: %1$s";
    public static final String MESSAGE_DELETE_GRADE_SUCCESS = "Removed grade from Person: %1$s";

    private final Index index;
    private final Grade grade;

    /**
     * @param index of the person in the filtered person list to edit the grade
     * @param grade of the person to be updated
     */
    public GradeCommand(Index index, Grade grade) {
        requireAllNonNull(index, grade);

        this.index = index;
        this.grade = grade;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        if (!(personToEdit instanceof Student)) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_INDEX);
        }
        Student studentToEdit = (Student) personToEdit;
        Student editedPerson = new Student(studentToEdit.getName(), studentToEdit.getPhone(), studentToEdit.getEmail(),
                studentToEdit.getAddress(), studentToEdit.getEducation(), grade, studentToEdit.getParentName(),
                studentToEdit.getParentPhone(), studentToEdit.getParentEmail(), personToEdit.getTags());

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Generates a command execution success message based on whether the remark is added to or removed from
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Student studentToEdit) {
        String message = studentToEdit.getGrade().gradeIndex.equals("0")
                ? MESSAGE_DELETE_GRADE_SUCCESS
                : MESSAGE_ADD_GRADE_SUCCESS;
        return String.format(message, studentToEdit.getName().fullName);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof GradeCommand)) {
            return false;
        }
        // state check
        GradeCommand e = (GradeCommand) other;
        return index.equals(e.index)
                && grade.equals(e.grade);
    }
}
