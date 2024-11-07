package keycontacts.testutil;

import static keycontacts.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static keycontacts.logic.parser.CliSyntax.PREFIX_DAY;
import static keycontacts.logic.parser.CliSyntax.PREFIX_END_TIME;
import static keycontacts.logic.parser.CliSyntax.PREFIX_GRADE_LEVEL;
import static keycontacts.logic.parser.CliSyntax.PREFIX_GROUP;
import static keycontacts.logic.parser.CliSyntax.PREFIX_NAME;
import static keycontacts.logic.parser.CliSyntax.PREFIX_PHONE;
import static keycontacts.logic.parser.CliSyntax.PREFIX_START_TIME;

import keycontacts.logic.commands.AddCommand;
import keycontacts.logic.commands.EditCommand.EditStudentDescriptor;
import keycontacts.model.lesson.RegularLesson;
import keycontacts.model.student.Student;

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
        sb.append(PREFIX_ADDRESS + student.getAddress().value + " ");
        sb.append(PREFIX_GRADE_LEVEL + student.getGradeLevel().value + " ");
        sb.append(PREFIX_GROUP + student.getGroup().groupName + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditStudentDescriptor}'s details.
     */
    public static String getEditStudentDescriptorDetails(EditStudentDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        descriptor.getGradeLevel().ifPresent(gradeLevel -> sb.append(PREFIX_GRADE_LEVEL).append(gradeLevel.value)
            .append(" "));
        descriptor.getGroup().ifPresent(group -> sb.append(PREFIX_GROUP).append(group.groupName).append(" "));
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code regularLesson}'s details.
     */
    public static String getRegularLessonDetails(RegularLesson regularLesson) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_DAY + regularLesson.getLessonDay().toString() + " ");
        sb.append(PREFIX_START_TIME + regularLesson.getStartTime().toString() + " ");
        sb.append(PREFIX_END_TIME + regularLesson.getEndTime().toString() + " ");
        return sb.toString();
    }
}
