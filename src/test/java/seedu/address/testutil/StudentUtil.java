package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OWED_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAID_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditStudentDescriptor;
import seedu.address.model.student.Student;

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
     * Returns an add command string for adding the {@code student}.
     */
    public static String getAddCommandRandomCase(Student student) {
        return AddCommand.COMMAND_WORD_RANDOM_CASE + " " + getStudentDetails(student);
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
        sb.append(PREFIX_SCHEDULE + student.getSchedule().value + " ");
        sb.append(PREFIX_SUBJECT + student.getSubject().toString() + " ");
        sb.append(PREFIX_RATE + student.getRate().toString() + " ");
        sb.append(PREFIX_PAID_AMOUNT + student.getPaidAmount().toString() + " ");
        sb.append(PREFIX_OWED_AMOUNT + student.getOwedAmount().toString() + " ");
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
        descriptor.getSchedule().ifPresent(schedule -> sb.append(PREFIX_SCHEDULE).append(schedule.value).append(" "));
        descriptor.getSubject().ifPresent(subject -> sb.append(PREFIX_SUBJECT).append(subject.toString()).append(" "));
        descriptor.getRate().ifPresent(rate -> sb.append(PREFIX_RATE).append(rate.toString()).append(" "));
        descriptor.getPaidAmount().ifPresent(paidAmount -> sb.append(PREFIX_PAID_AMOUNT
        ).append(paidAmount.toString()).append(" "));
        descriptor.getOwedAmount().ifPresent(owedAmount -> sb.append(PREFIX_OWED_AMOUNT)
                                                             .append(owedAmount.toString()).append(" "));
        return sb.toString();
    }
}
