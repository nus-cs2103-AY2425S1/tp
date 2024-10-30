package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.UpdateCommand.UpdateStudentDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.student.Address;
import seedu.address.model.student.EmergencyContact;
import seedu.address.model.student.LessonTime;
import seedu.address.model.student.Level;
import seedu.address.model.student.Name;
import seedu.address.model.student.Note;
import seedu.address.model.student.Phone;
import seedu.address.model.student.Student;
import seedu.address.model.student.Subject;
import seedu.address.model.student.task.TaskList;
import seedu.address.ui.Ui.UiState;

/**
 * Tags an existing student in the address book with a subject, school level or both.
 */
public class TagCommand extends Command {

    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Tags students with a given subject, level or both. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + "[" + PREFIX_SUBJECT + "SUBJECT]"
            + PREFIX_LEVEL + "LEVEL...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_SUBJECT + "MATH "
            + PREFIX_LEVEL + "S1 NT";

    public static final String MESSAGE_TAG_STUDENT_SUCCESS = "Tagged Student: %1$s";
    public static final String MESSAGE_STUDENT_NOT_FOUND = "Student does not exist in address book.";


    public final Name nameToTag;
    public final UpdateStudentDescriptor tagsToAdd;

    /**
     * @param nameToTag name of the student in the address book to tag
     * @param tagsToAdd details to tag the student with
     */
    public TagCommand(Name nameToTag, UpdateStudentDescriptor tagsToAdd) {
        requireNonNull(nameToTag);
        requireNonNull(tagsToAdd);
        this.nameToTag = nameToTag;
        this.tagsToAdd = tagsToAdd;

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Student> lastShownList = model.getFilteredStudentList();

        Optional<Student> optionalStudentToTag = lastShownList.stream()
                .filter(x -> x.getName()
                    .equals(nameToTag)).findFirst();

        Student studentToTag;
        if (optionalStudentToTag.isPresent()) {
            studentToTag = optionalStudentToTag.get();
        } else {
            throw new CommandException(MESSAGE_STUDENT_NOT_FOUND);
        }

        Student studentWithTags = createStudentWithTags(studentToTag, tagsToAdd);

        model.setStudent(studentToTag, studentWithTags);
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);

        return new CommandResult(String.format(MESSAGE_TAG_STUDENT_SUCCESS, Messages.format(studentWithTags)),
                UiState.DETAILS);

    }

    private static Student createStudentWithTags(Student studentToTag, UpdateStudentDescriptor tagsToAdd)
            throws CommandException {
        assert studentToTag != null;

        Name updatedName = tagsToAdd.getName().orElse(studentToTag.getName());
        Phone updatedPhone = tagsToAdd.getPhone().orElse(studentToTag.getPhone());
        EmergencyContact updatedEmergencyContact = tagsToAdd.getEmergencyContact()
                .orElse(studentToTag.getEmergencyContact());
        Address updatedAddress = tagsToAdd.getAddress().orElse(studentToTag.getAddress());
        Note updatedNote = tagsToAdd.getNote().orElse(studentToTag.getNote());

        Level updatedLevel = tagsToAdd.getLevel().orElse(studentToTag.getLevel());

        if (updatedLevel != null && tagsToAdd.getSubjects().isPresent()) {
            if (!Subject.isValidSubjectsByLevel(updatedLevel,
                            tagsToAdd
                                .getSubjects()
                                .get())) {
                throw new CommandException(Subject.getValidSubjectMessage());
            }
        }
        Set<Subject> updatedSubjects = tagsToAdd.getSubjects().orElse(studentToTag.getSubjects());

        TaskList updatedTaskList = tagsToAdd.getTaskList().orElse(studentToTag.getTaskList());
        Set<LessonTime> updatedLessonTimes = tagsToAdd.getLessonTimes()
                .orElse(studentToTag.getLessonTimes());
        return new Student(updatedName, updatedPhone, updatedEmergencyContact,
                updatedAddress, updatedNote, updatedSubjects, updatedLevel, updatedTaskList, updatedLessonTimes);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagCommand t)) {
            return false;
        }

        return this.nameToTag.equals(t.nameToTag) && this.tagsToAdd.equals(t.tagsToAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", nameToTag)
                .add("level", tagsToAdd.getLevel())
                .add("subjects", tagsToAdd.getSubjects())
                .toString();
    }

}
