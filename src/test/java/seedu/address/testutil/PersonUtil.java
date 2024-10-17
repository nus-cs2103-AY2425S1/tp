package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEALTHSERVICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.healthservice.HealthService;
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
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + person.getName().fullName + " ");
        sb.append(PREFIX_NRIC + person.getNric().value + " ");
        sb.append(PREFIX_SEX + person.getSex().value + " ");
        sb.append(PREFIX_BIRTHDATE + person.getBirthdate().value + " ");
        person.getHealthServices().stream().forEach(
            s -> sb.append(PREFIX_HEALTHSERVICE + s.healthServiceName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getNric().ifPresent(nric -> sb.append(PREFIX_NRIC).append(nric.value).append(" "));
        descriptor.getSex().ifPresent(sex -> sb.append(PREFIX_SEX).append(sex.value).append(" "));
        descriptor.getBirthDate().ifPresent(birthDate -> sb.append(PREFIX_BIRTHDATE).append(birthDate.value).append(" "));
        if (descriptor.getHealthServices().isPresent()) {
            Set<HealthService> healthServices = descriptor.getHealthServices().get();
            if (healthServices.isEmpty()) {
                sb.append(PREFIX_HEALTHSERVICE);
            } else {
                healthServices.forEach(s -> sb.append(PREFIX_HEALTHSERVICE).append(s.healthServiceName).append(" "));
            }
        }
        return sb.toString();
    }
}
