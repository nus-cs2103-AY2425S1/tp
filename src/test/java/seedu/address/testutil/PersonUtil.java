package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOC_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOC_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOC_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_CONTACT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_CONTACT_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_CONTACT_RELATIONSHIP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_CONTACT_TO_EDIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

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
     * Take note that this method is only applicable when the person has only one emergency contact
     */
    public static String getPersonDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + person.getName().fullName + " ");
        sb.append(PREFIX_PHONE + person.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + person.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + person.getAddress().value + " ");
        sb.append(PREFIX_EMERGENCY_CONTACT_NAME + person.getFirstEmergencyContact().getName().fullName + " ");
        sb.append(PREFIX_EMERGENCY_CONTACT_PHONE + person.getFirstEmergencyContact().getPhone().value + " ");
        sb.append(PREFIX_EMERGENCY_CONTACT_RELATIONSHIP
                + person.getFirstEmergencyContact().getRelationship().relationship + " ");
        sb.append(PREFIX_DOC_NAME + person.getDoctor().getName().fullName + " ");
        sb.append(PREFIX_DOC_PHONE + person.getDoctor().getPhone().value + " ");
        sb.append(PREFIX_DOC_EMAIL + person.getDoctor().getEmail().value + " ");

        person.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        descriptor.getIndexOfEmergencyContactToEdit().ifPresent(indexOfEmergencyContactToEdit ->
                sb.append(PREFIX_EMERGENCY_CONTACT_TO_EDIT).append(indexOfEmergencyContactToEdit.getOneBased())
                        .append(" "));
        descriptor.getEmergencyContactName().ifPresent(emergencyContactName ->
                sb.append(PREFIX_EMERGENCY_CONTACT_NAME).append(emergencyContactName.fullName).append(" "));
        descriptor.getEmergencyContactPhone().ifPresent(emergencyContactPhone ->
                sb.append(PREFIX_EMERGENCY_CONTACT_PHONE).append(emergencyContactPhone.value).append(" "));
        descriptor.getEmergencyContactRelationship().ifPresent(emergencyContactRelationship ->
                sb.append(PREFIX_EMERGENCY_CONTACT_RELATIONSHIP)
                        .append(emergencyContactRelationship.relationship).append(" "));
        descriptor.getDoctorName().ifPresent(doctorName ->
                sb.append(PREFIX_DOC_NAME).append(doctorName.fullName).append(" "));
        descriptor.getDoctorPhone().ifPresent(doctorPhone ->
                sb.append(PREFIX_DOC_PHONE).append(doctorPhone.value).append(" "));
        descriptor.getDoctorEmail().ifPresent(doctorEmail ->
                sb.append(PREFIX_DOC_EMAIL).append(doctorEmail.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
