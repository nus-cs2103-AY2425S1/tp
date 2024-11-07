package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CHILD;

import java.util.Set;

import javafx.util.Pair;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.LessonTime;
import seedu.address.model.person.Name;
import seedu.address.model.person.Parent;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Student;
import seedu.address.model.tag.Education;
import seedu.address.model.tag.Grade;
import seedu.address.model.tag.Tag;

/**
 * Removes any links to and from a person identified with an index.
 */
public class UnlinkCommand extends Command {

    public static final String COMMAND_WORD = "unlink";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": removes any links involving the Student provided.\n"
            + "Parameters: " + PREFIX_CHILD + "CHILD_NAME\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_CHILD + "John Doe";

    public static final String MESSAGE_UNLINK_CONTACT_SUCCESS = "Successfully unlinked Student: %1$s from Parent: %2$s";
    public static final String MESSAGE_CONTACT_HAS_NO_LINKS = "Student: %1$s has no links";
    public static final String MESSAGE_CHILD_NOT_FOUND = "Student: %1$s does not exist in Address Book";
    public static final String MESSAGE_PARENT_NOT_FOUND = "Student's Parent: %1$s does not exist in Address Book";

    private final Name name;

    /**
     * Creates an UnlinkCommand to unlink the person at the specified {@code Index}.
     */
    public UnlinkCommand(Name name) {
        requireNonNull(name);
        this.name = name;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Person child;
        child = model.personFromName(name);
        if (!(child instanceof Student)) {
            throw new CommandException(generateChildNotFoundMessage());
        }

        Student castedChild = (Student) child;
        Name parentName = castedChild.getParentName();
        if (parentName == null) {
            throw new CommandException(String.format(MESSAGE_CONTACT_HAS_NO_LINKS, castedChild.getName()));
        }

        Person parent;
        parent = model.personFromName(parentName);
        if (!(parent instanceof Parent)) {
            throw new CommandException(generateParentNotFoundMessage(parentName));
        }
        Parent castedParent = (Parent) parent;

        Pair<Student, Parent> unlinkedPair = unlink(castedChild, castedParent);
        Student unlinkedStudent = unlinkedPair.getKey();
        Parent unlinkedParent = unlinkedPair.getValue();
        model.setPerson(child, unlinkedStudent);
        model.setPerson(parent, unlinkedParent);

        return new CommandResult(String.format(MESSAGE_UNLINK_CONTACT_SUCCESS,
                unlinkedStudent.getName(), unlinkedParent.getName()));
    }

    private Pair<Student, Parent> unlink(Student student, Parent parent) {
        // Student Information
        Name studentName = student.getName();
        Phone studentPhone = student.getPhone();
        Email studentEmail = student.getEmail();
        Address studentAddress = student.getAddress();
        Set<Tag> studentTags = student.getTags();
        boolean studentPinned = student.isPinned();
        boolean studentArchived = student.isArchived();
        LessonTime studentLessonTime = student.getLessonTime();
        Education studentEducation = student.getEducation();
        Grade studentGrade = student.getGrade();
        Student newStudent = new Student(studentName, studentPhone, studentEmail, studentAddress, studentLessonTime,
                studentEducation, studentGrade, null, studentTags, studentPinned, studentArchived);

        // Parent Information
        Name parentName = parent.getName();
        Phone parentPhone = parent.getPhone();
        Email parentEmail = parent.getEmail();
        Address parentAddress = parent.getAddress();
        Set<Tag> parentTags = parent.getTags();
        boolean parentPinned = parent.isPinned();
        boolean parentArchived = parent.isArchived();
        Set<Name> parentChildrensNames = parent.getChildrensNames();
        parentChildrensNames.remove(studentName);
        Parent newParent = new Parent(parentName, parentPhone, parentEmail, parentAddress, parentChildrensNames,
                parentTags, parentPinned, parentArchived);

        return new Pair<>(newStudent, newParent);
    }

    private String generateChildNotFoundMessage() {
        return String.format(MESSAGE_CHILD_NOT_FOUND, name.fullName);
    }

    private String generateParentNotFoundMessage(Name name) {
        return String.format(MESSAGE_PARENT_NOT_FOUND, name.fullName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnlinkCommand)) {
            return false;
        }

        UnlinkCommand otherUnlinkCommand = (UnlinkCommand) other;
        return name.equals(otherUnlinkCommand.name);
    }
}
