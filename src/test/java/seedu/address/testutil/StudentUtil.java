package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.UpdateCommand.UpdateStudentDescriptor;
import seedu.address.model.student.LessonTime;
import seedu.address.model.student.Student;
import seedu.address.model.student.Subject;

/**
 * A utility class for Student.
 */
public class StudentUtil {

    /**
     * Returns an add command string for adding the {@code student}.
     */
    public static String getAddCommand(Student student) {
        return AddCommand.COMMAND_WORD + " " + getStudentDetails(student);
    }

    /**
     * Returns the part of command string for the given {@code student}'s details.
     */
    public static String getStudentDetails(Student student) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + student.getName().fullName + " ");
        sb.append(PREFIX_PHONE + student.getPhone().value + " ");
        sb.append(PREFIX_EMERGENCY_CONTACT + student.getEmergencyContact().value + " ");
        sb.append(PREFIX_ADDRESS + student.getAddress().value + " ");
        sb.append(PREFIX_LEVEL + student.getLevel().levelName + " ");
        student.getSubjects().stream().forEach(
            s -> sb.append(PREFIX_SUBJECT + s.subjectName + " ")
        );
        student.getLessonTimes().stream().forEach(
            s -> sb.append(PREFIX_LESSON_TIME + s.toString() + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code UpdateStudentDescriptor}'s details.
     */
    public static String getUpdateStudentDescriptorDetails(UpdateStudentDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmergencyContact()
                .ifPresent(phone -> sb.append(PREFIX_EMERGENCY_CONTACT).append(phone.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        descriptor.getNote().ifPresent(note -> sb.append(PREFIX_NOTE).append(note.value).append(" "));
        descriptor.getLevel().ifPresent(level -> sb.append(PREFIX_LEVEL).append(level.levelName).append(" "));
        if (descriptor.getSubjects().isPresent()) {
            Set<Subject> subjects = descriptor.getSubjects().get();
            if (subjects.isEmpty()) {
                sb.append(PREFIX_SUBJECT).append(" ");
            } else {
                subjects.forEach(s -> sb.append(PREFIX_SUBJECT).append(s.subjectName).append(" "));
            }
        }
        if (descriptor.getLessonTimes().isPresent()) {
            Set<LessonTime> lts = descriptor.getLessonTimes().get();
            if (lts.isEmpty()) {
                sb.append(PREFIX_LESSON_TIME);
            } else {
                lts.forEach(s -> sb.append(PREFIX_LESSON_TIME).append(s.toString()).append(" "));
            }
        }
        return sb.toString();
    }
}
