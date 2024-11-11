package spleetwaise.address.testutil;

import java.util.Set;

import spleetwaise.address.logic.commands.AddCommand;
import spleetwaise.address.logic.commands.EditCommand.EditPersonDescriptor;
import spleetwaise.address.logic.parser.CliSyntax;
import spleetwaise.address.model.person.Person;
import spleetwaise.address.model.tag.Tag;

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
        sb.append(CliSyntax.PREFIX_NAME + person.getName().fullName + " ");
        sb.append(CliSyntax.PREFIX_PHONE + person.getPhone().value + " ");
        sb.append(CliSyntax.PREFIX_EMAIL + person.getEmail().value + " ");
        sb.append(CliSyntax.PREFIX_ADDRESS + person.getAddress().value + " ");
        if (!person.getRemark().value.isBlank()) {
            sb.append(CliSyntax.PREFIX_REMARK + person.getRemark().value + " ");
        }
        person.getTags().stream().forEach(
                s -> sb.append(CliSyntax.PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(CliSyntax.PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(CliSyntax.PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(CliSyntax.PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress()
                .ifPresent(address -> sb.append(CliSyntax.PREFIX_ADDRESS).append(address.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(CliSyntax.PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(CliSyntax.PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }

    /**
     * Returns the part of comparison result for the respective {@code Person} details.
     */
    public static boolean compareWithoutId(Person p1, Person p2) {
        // Compare all fields except ID
        return p1.getName().equals(p2.getName())
                && p1.getPhone().equals(p2.getPhone())
                && p1.getEmail().equals(p2.getEmail())
                && p1.getAddress().equals(p2.getAddress())
                && p1.getRemark().equals(p2.getRemark())
                && p1.getTags().equals(p2.getTags());
    }
}
