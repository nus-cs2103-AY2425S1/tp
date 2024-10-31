package tuteez.testutil;

import static tuteez.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static tuteez.logic.parser.CliSyntax.PREFIX_EMAIL;
import static tuteez.logic.parser.CliSyntax.PREFIX_LESSON;
import static tuteez.logic.parser.CliSyntax.PREFIX_NAME;
import static tuteez.logic.parser.CliSyntax.PREFIX_PHONE;
import static tuteez.logic.parser.CliSyntax.PREFIX_TAG;
import static tuteez.logic.parser.CliSyntax.PREFIX_TELEGRAM;

import java.util.List;
import java.util.Set;

import tuteez.logic.commands.AddCommand;
import tuteez.logic.commands.EditCommand.EditPersonDescriptor;
import tuteez.model.person.Person;
import tuteez.model.person.lesson.Lesson;
import tuteez.model.tag.Tag;

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
        sb.append(PREFIX_PHONE + person.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + person.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + person.getAddress().value + " ");
        sb.append(PREFIX_TELEGRAM + person.getTelegramUsername().telegramUsername + " ");
        person.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        person.getLessons().stream().forEach(
                s -> sb.append(PREFIX_LESSON + s.getDayAndTime() + " ")
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
        descriptor.getTelegramUsername().ifPresent(username -> sb.append(PREFIX_TELEGRAM)
                .append(username.telegramUsername).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG).append(" ");
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        if (descriptor.getLessons().isPresent()) {
            List<Lesson> lessons = descriptor.getLessons().get();
            if (lessons.isEmpty()) {
                sb.append(PREFIX_LESSON);
            } else {
                lessons.forEach(s -> sb.append(PREFIX_LESSON).append(s.getDayAndTime()).append(" "));
            }
        }
        return sb.toString();
    }
}
