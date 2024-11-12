package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACTTYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MOD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEHANDLE;

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
     */
    public static String getPersonDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_CONTACTTYPE + person.getContactType().value.toString() + " ");
        sb.append(PREFIX_NAME + person.getName().fullName + " ");
        person.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE + phone.value + " "));
        person.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL + email.value + " "));
        person.getTelegramHandle().ifPresent(telegramHandle -> sb.append(PREFIX_TELEHANDLE
                + telegramHandle.value + " "));
        person.getModuleName().ifPresent(moduleName -> sb.append(PREFIX_MOD
                + moduleName.getModuleName() + " "));
        person.getRemark().ifPresent(remark -> sb.append(PREFIX_REMARK
                + remark.value + " "));
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
        descriptor.getTelegramHandle().ifPresent(telegramHandle -> sb.append(PREFIX_TELEHANDLE)
                .append(telegramHandle.value).append(" "));
        descriptor.getContactType().ifPresent(contactType -> sb.append(PREFIX_CONTACTTYPE)
                .append(contactType.value).append(" "));
        descriptor.getModuleName().ifPresent(moduleName -> sb.append(PREFIX_MOD).append(moduleName).append(" "));
        descriptor.getRemark().ifPresent(remark -> sb.append(PREFIX_REMARK).append(remark).append(" "));
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
