package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIALID;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.student.Name;
import seedu.address.model.student.PresentDates;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentId;
import seedu.address.model.student.TutorialId;

/**
 * Edits the details of an existing student in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the student identified "
            + "by the index number used in the displayed student list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_STUDENTID + "STUDENT_ID] "
            + "[" + PREFIX_TUTORIALID + "TUTORIAL_ID]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "Samson Chew "
            + PREFIX_STUDENTID + "A1234567U "
            + PREFIX_TUTORIALID + "T1001";

    public static final String MESSAGE_EDIT_STUDENT_SUCCESS = "Edited Student: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_STUDENT = "This student already exists in the address book.";
    public static final String MESSAGE_DUPLICATE_STUDENTID = "This student ID already exists in the address book.";

    public static final String MESSAGE_TUTORIAL_NOT_FOUND = "The tutorial ID provided doesn't exist! \nTutorial ID: ";


    private final Index index;
    private final EditStudentDescriptor editStudentDescriptor;

    /**
     * @param index of the student in the filtered student list to edit
     * @param editStudentDescriptor details to edit the student with
     */
    public EditCommand(Index index, EditStudentDescriptor editStudentDescriptor) {
        requireNonNull(index);
        requireNonNull(editStudentDescriptor);

        this.index = index;
        this.editStudentDescriptor = new EditStudentDescriptor(editStudentDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Student> lastShownList = model.getFilteredStudentList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        Student studentToEdit = lastShownList.get(index.getZeroBased());
        Student editedStudent = createEditedStudent(studentToEdit, editStudentDescriptor);

        if (!studentToEdit.isSameStudent(editedStudent) && model.hasStudent(editedStudent)) {
            throw new CommandException(MESSAGE_DUPLICATE_STUDENT);
        }

        // Check for duplicate student IDs if student ID is changed

        if (!editedStudent.getStudentId().equals(studentToEdit.getStudentId())) {
            if (model.hasStudentWithId(editedStudent.getStudentId())) {
                throw new CommandException(MESSAGE_DUPLICATE_STUDENTID);
            }
        }

        // Check if new tutorial exists if tutorial ID is changed
        if (!editedStudent.getTutorialId().equals(studentToEdit.getTutorialId())) {
            if (!model.hasTutorial(editedStudent.getTutorialId())) {
                throw new CommandException(MESSAGE_TUTORIAL_NOT_FOUND + editedStudent.getTutorialId());
            }
        }

        model.setStudent(studentToEdit, editedStudent);

        // Update tutorial assignments if tutorial ID has changed
        if (!editedStudent.getTutorialId().equals(studentToEdit.getTutorialId())) {
            model.assignStudent(editedStudent, editedStudent.getTutorialId());
        }

        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        return new CommandResult(String.format(MESSAGE_EDIT_STUDENT_SUCCESS, Messages.format(editedStudent)));
    }

    /**
     * Creates and returns a {@code Student} with the details of {@code studentToEdit}
     * edited with {@code editStudentDescriptor}.
     */
    private static Student createEditedStudent(Student studentToEdit, EditStudentDescriptor editStudentDescriptor) {
        assert studentToEdit != null;

        Name updatedName = editStudentDescriptor.getName().orElse(studentToEdit.getName());
        StudentId updatedStudentId = editStudentDescriptor.getStudentId().orElse(studentToEdit.getStudentId());
        TutorialId updatedTutorialId = editStudentDescriptor.getTutorialId()
                .orElse(studentToEdit.getTutorialId());
        PresentDates updatedDates = editStudentDescriptor.getPresentDates().orElse(studentToEdit.getPresentDates());

        return new Student(updatedName, updatedStudentId, updatedTutorialId, updatedDates);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return index.equals(otherEditCommand.index)
                && editStudentDescriptor.equals(otherEditCommand.editStudentDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editStudentDescriptor", editStudentDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the student with. Each non-empty field value will replace the
     * corresponding field value of the student.
     */
    public static class EditStudentDescriptor {
        private Name name;
        private StudentId studentId;
        private TutorialId tutorialId;
        private PresentDates presentDates;

        public EditStudentDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditStudentDescriptor(EditStudentDescriptor toCopy) {
            setName(toCopy.name);
            setStudentId(toCopy.studentId);
            setTutorialId(toCopy.tutorialId);
            setPresentDates(toCopy.presentDates);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, studentId, tutorialId);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPresentDates(PresentDates presentDates) {
            this.presentDates = presentDates;
        }

        public Optional<PresentDates> getPresentDates() {
            return Optional.ofNullable(presentDates);
        }

        public void setStudentId(StudentId studentId) {
            this.studentId = (studentId != null) ? studentId : this.studentId;
        }
        public Optional<StudentId> getStudentId() {
            return Optional.ofNullable(studentId);
        }

        public void setTutorialId(TutorialId tutorialId) {
            this.tutorialId = (tutorialId != null) ? tutorialId : this.tutorialId;
        }
        public Optional<TutorialId> getTutorialId() {
            return Optional.ofNullable(tutorialId);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditStudentDescriptor)) {
                return false;
            }

            EditStudentDescriptor otherEditStudentDescriptor = (EditStudentDescriptor) other;
            return Objects.equals(name, otherEditStudentDescriptor.name)
                    && Objects.equals(presentDates, otherEditStudentDescriptor.presentDates)
                    && Objects.equals(studentId, otherEditStudentDescriptor.studentId)
                    && Objects.equals(tutorialId, otherEditStudentDescriptor.tutorialId);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("studentId", studentId)
                    .add("tutorialId", tutorialId)
                    .add("attendance", presentDates)
                    .toString();
        }
    }
}
