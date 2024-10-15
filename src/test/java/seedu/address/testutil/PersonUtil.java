package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OWED_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.Person;

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
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommandRandomCase(Person person) {
        return AddCommand.COMMAND_WORD_RANDOM_CASE + " " + getPersonDetails(person);
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
        sb.append(PREFIX_SCHEDULE + person.getSchedule().value + " ");
        sb.append(PREFIX_SUBJECT + person.getSubject().toString() + " ");
        sb.append(PREFIX_RATE + person.getRate().toString() + " ");
        sb.append(PREFIX_PAID + person.getPaid().toString() + " ");
        sb.append(PREFIX_OWED_AMOUNT + person.getOwedAmount().toString() + " ");
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
        descriptor.getSchedule().ifPresent(schedule -> sb.append(PREFIX_SCHEDULE).append(schedule.value).append(" "));
        descriptor.getSubject().ifPresent(subject -> sb.append(PREFIX_SUBJECT).append(subject.toString()).append(" "));
        descriptor.getRate().ifPresent(rate -> sb.append(PREFIX_RATE).append(rate.toString()).append(" "));
        descriptor.getPaid().ifPresent(paid -> sb.append(PREFIX_PAID).append(paid.toString()).append(" "));
        descriptor.getOwedAmount().ifPresent(owedAmount -> sb.append(PREFIX_OWED_AMOUNT)
                                                             .append(owedAmount.toString()).append(" "));
        return sb.toString();
    }
}
