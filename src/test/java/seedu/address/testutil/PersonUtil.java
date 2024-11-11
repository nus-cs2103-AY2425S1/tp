package seedu.address.testutil;

//import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DIAGNOSIS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WARD;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

//import java.util.Set;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.Person;
//import seedu.address.model.tag.Tag;

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
        sb.append(PREFIX_NAME + person.getName().value + " ");
        sb.append(PREFIX_ID + person.getId().value + " ");
        sb.append(PREFIX_WARD + person.getWard().value + " ");
        sb.append(PREFIX_DIAGNOSIS + person.getDiagnosis().value + " ");
        sb.append(PREFIX_MEDICATION + person.getMedication().value + " ");
        /*
        person.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );

        */
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.value).append(" "));
        descriptor.getId().ifPresent(id -> sb.append(PREFIX_ID).append(id.value).append(" "));
        descriptor.getWard().ifPresent(ward -> sb.append(PREFIX_WARD).append(ward.value).append(" "));
        descriptor.getDiagnosis().ifPresent(diagnosis -> sb.append(PREFIX_DIAGNOSIS).append(diagnosis.value)
                .append(" "));
        descriptor.getMedication().ifPresent(medication -> sb.append(PREFIX_MEDICATION).append(medication.value)
                .append(" "));
        /*
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
         */
        return sb.toString();
    }
}
