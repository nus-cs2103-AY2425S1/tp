package seedu.edulog.testutil;

import static seedu.edulog.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.edulog.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.edulog.logic.parser.CliSyntax.PREFIX_FEE;
import static seedu.edulog.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.edulog.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.edulog.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.edulog.logic.commands.AddCommand;
import seedu.edulog.logic.commands.EditCommand.EditStudentDescriptor;
import seedu.edulog.model.student.Student;
import seedu.edulog.model.tag.Tag;

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
        sb.append(PREFIX_EMAIL + student.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + student.getAddress().value + " ");
        student.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        sb.append(PREFIX_FEE + String.valueOf(student.getFee().value) + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditStudentDescriptor}'s details.
     */
    public static String getEditStudentDescriptorDetails(EditStudentDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        descriptor.getFee().ifPresent(fee -> sb.append(PREFIX_FEE).append(String.valueOf(fee.value)));
        return sb.toString();
    }
}
