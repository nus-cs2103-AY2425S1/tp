package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.addcommands.AddStudentCommand;
import seedu.address.logic.commands.editcommands.EditStudentCommand.EditPersonDescriptor;
import seedu.address.model.student.Student;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Student.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code student}.
     */
    public static String getAddCommand(Student student) {
        return AddStudentCommand.COMMAND_WORD + " " + getPersonDetails(student);
    }

    /**
     * Returns the part of command string for the given {@code student}'s details.
     */
    public static String getPersonDetails(Student student) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_STUDENT_NAME + student.getName().getFullName() + " ");
        sb.append(PREFIX_EMAIL + student.getEmail().getEmail() + " ");
        student.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.getTagName() + " ")
        );
        sb.append(PREFIX_STUDENT_NUMBER + student.getStudentNumber().getStudentNumber() + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_STUDENT_NAME).append(name.getFullName()).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.getEmail()).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG).append(" ");
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.getTagName()).append(" "));
            }
        }
        descriptor.getStudentNumber().ifPresent(studentNumber -> sb.append(PREFIX_STUDENT_NUMBER)
            .append(studentNumber.getStudentNumber()));
        return sb.toString();
    }
}
