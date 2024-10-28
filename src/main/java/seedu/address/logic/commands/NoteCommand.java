package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

import java.util.List;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.student.Name;
import seedu.address.model.student.Note;
import seedu.address.model.student.Student;
import seedu.address.ui.Ui.UiState;

/**
 * * Changes the notes of an existing student in the address book.
 */
public class NoteCommand extends Command {

    public static final String COMMAND_WORD = "note";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the note of the student identified "
            + "by their name (must already exist in the address book). "
            + "Existing note will be overwritten by the input.\n"
            + "Parameters: " + PREFIX_NAME + "NAME "
            + PREFIX_NOTE + "NOTE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_NOTE + "Very mischievous.";

    public static final String MESSAGE_ADD_NOTE_SUCCESS = "Added note to Student: %1$s";
    public static final String MESSAGE_DELETE_NOTE_SUCCESS = "Removed note from Student: %1$s";

    private final Name name;
    private final Note note;

    /**
     * @param name of the student to edit the notes
     * @param note of the student to be updated to
     */
    public NoteCommand(Name name, Note note) {
        requireAllNonNull(name, note);

        this.name = name;
        this.note = note;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Student> allStudents = model.getAddressBook().getStudentList();
        Student studentToEdit = null;

        for (Student student : allStudents) {
            if (student.getName().equals(name)) {
                studentToEdit = student;
            }
        }

        if (studentToEdit == null) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_NAME);
        }

        Student editedStudent = new Student(
                studentToEdit.getName(), studentToEdit.getPhone(), studentToEdit.getEmergencyContact(),
                studentToEdit.getAddress(), note, studentToEdit.getSubjects(), studentToEdit.getLevel(),
                studentToEdit.getTaskList(), studentToEdit.getLessonTimes());

        model.setStudent(studentToEdit, editedStudent);
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);

        return new CommandResult(generateSuccessMessage(editedStudent), UiState.DETAILS);
    }

    /**
     * Generates a command execution success message based on whether the note is added to or removed from
     * {@code studentToEdit}.
     */
    private String generateSuccessMessage(Student studentToEdit) {
        String message = !note.value.isEmpty() ? MESSAGE_ADD_NOTE_SUCCESS : MESSAGE_DELETE_NOTE_SUCCESS;
        return String.format(message, Messages.format(studentToEdit));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NoteCommand)) {
            return false;
        }

        NoteCommand e = (NoteCommand) other;
        return name.equals(e.name)
                && note.equals(e.note);
    }

}
