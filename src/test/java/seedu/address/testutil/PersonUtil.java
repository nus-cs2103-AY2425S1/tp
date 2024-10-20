package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDY_GROUP_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.Person;
import seedu.address.model.tag.StudyGroupTag;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Person person) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + person.getName().fullName + " ");
        sb.append(PREFIX_EMAIL + person.getEmail().value + " ");
        sb.append(PREFIX_GENDER + person.getGender().value + " ");
        sb.append(PREFIX_AGE + person.getAge().value + " ");
        sb.append(PREFIX_DETAIL + person.getDetail().value + " ");
        person.getStudyGroupTags().stream().forEach(
                s -> sb.append(PREFIX_STUDY_GROUP_TAG + s.studyGroupName + " "));
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given
     * {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getGender().ifPresent(gender -> sb.append(PREFIX_GENDER).append(gender.value).append(" "));
        descriptor.getAge().ifPresent(age -> sb.append(PREFIX_AGE).append(age.value).append(" "));
        descriptor.getDetail().ifPresent(detail -> sb.append(PREFIX_DETAIL).append(detail.value).append(" "));
        if (descriptor.getStudyGroupTags().isPresent()) {
            Set<StudyGroupTag> studyGroups = descriptor.getStudyGroupTags().get();
            if (studyGroups.isEmpty()) {
                sb.append(PREFIX_STUDY_GROUP_TAG);
            } else {
                studyGroups.forEach(s -> sb.append(PREFIX_STUDY_GROUP_TAG).append(s.studyGroupName).append(" "));
            }
        }
        return sb.toString();
    }
}
