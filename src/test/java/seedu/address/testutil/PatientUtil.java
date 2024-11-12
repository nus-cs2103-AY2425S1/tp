package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditPatientDescriptor;
import seedu.address.model.patient.Patient;

/**
 * A utility class for Patient.
 */
public class PatientUtil {

    /**
     * Returns an add command string for adding the {@code patient}.
     */
    public static String getAddCommand(Patient patient) {
        return AddCommand.COMMAND_WORD + " " + getPatientDetails(patient);
    }

    /**
     * Returns the part of command string for the given {@code patient}'s details.
     */
    public static String getPatientDetails(Patient patient) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + patient.getName().fullName + " ");
        sb.append(PREFIX_NRIC + patient.getNric().value + " ");
        sb.append(PREFIX_SEX + patient.getSex().value + " ");
        sb.append(PREFIX_BIRTHDATE + patient.getBirthdate().value + " ");
        sb.append(PREFIX_PHONE + patient.getPhone().value);
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPatientDescriptor}'s details.
     */
    public static String getEditPatientDescriptorDetails(EditPatientDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getNric().ifPresent(nric -> sb.append(PREFIX_NRIC).append(nric.value).append(" "));
        descriptor.getSex().ifPresent(sex -> sb.append(PREFIX_SEX).append(sex.value).append(" "));
        descriptor.getBirthDate().ifPresent(birthDate -> sb.append(PREFIX_BIRTHDATE)
                .append(birthDate.value).append(" "));
        return sb.toString();
    }
}
