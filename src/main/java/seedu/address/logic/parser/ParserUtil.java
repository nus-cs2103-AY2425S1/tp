package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_CONSTRAINTS_LENGTH;
import static seedu.address.logic.Messages.MESSAGE_EMPTY_FIELD;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.DateUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.AddApptCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Address;
import seedu.address.model.person.Allergy;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.MedCon;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Priority;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String nric} into a {@code Nric}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param nric The Nric to be parsed.
     * @return The parsed Nric.
     * @throws ParseException if the given {@code nric} is invalid.
     */
    public static Nric parseNric(String nric) throws ParseException {
        requireNonNull(nric);
        String trimmedNric = nric.trim();
        if (!Nric.isValidNric(trimmedNric)) {
            throw new ParseException(Nric.MESSAGE_CONSTRAINTS);
        }
        return new Nric(trimmedNric);
    }

    /**
     * Parses a {@code String gender} into a {@code Gender}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param gender The Gender to be parsed.
     * @return The parsed Gender.
     * @throws ParseException if the given {@code gender} is invalid.
     */
    public static Gender parseGender(String gender) throws ParseException {
        requireNonNull(gender);
        String trimmedGender = gender.trim();
        if (!Gender.isValidGender(trimmedGender)) {
            throw new ParseException(Gender.MESSAGE_CONSTRAINTS);
        }
        return new Gender(trimmedGender);
    }

    /**
     * Parses a {@code String dateOfBirth} into a {@code DateOfBirth}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param dob The DateOfBirth to be parsed.
     * @return The parsed DateOfBirth.
     * @throws ParseException if the given {@code dob} is invalid.
     */
    public static DateOfBirth parseDateOfBirth(String dob) throws ParseException {
        requireNonNull(dob);
        String trimmedDob = dob.trim();
        if (!DateUtil.isValidDate(trimmedDob)) {
            throw new ParseException(DateOfBirth.MESSAGE_CONSTRAINTS_WRONG_FORMAT);
        }
        if (!DateOfBirth.isValidDateOfBirth(trimmedDob)) {
            throw new ParseException(DateOfBirth.MESSAGE_CONSTRAINTS_FUTURE_DATE);
        }
        return new DateOfBirth(trimmedDob);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a serialised {@code String appointment} into a {@code Appointment}.
     *
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code appointment} is invalid.
     */
    public static Appointment parseSerialisedAppointment(String appointment) throws ParseException {
        requireNonNull(appointment);
        String[] trimmedAppointments = appointment.trim().split(":");
        if (trimmedAppointments.length < 3) {
            throw new ParseException(Appointment.MESSAGE_CONSTRAINTS);
        }

        if (!Appointment.isValidAppointmentName(trimmedAppointments[0])) {
            throw new ParseException(Appointment.MESSAGE_CONSTRAINTS);
        }

        if (!DateUtil.isValidDate(trimmedAppointments[1])) {
            throw new ParseException(Appointment.MESSAGE_CONSTRAINTS_APPT_DATE_WRONG_FORMAT);
        }
        try {
            return new Appointment(trimmedAppointments[0], trimmedAppointments[1], trimmedAppointments[2]);
        } catch (IllegalValueException e) {
            throw new ParseException(AddApptCommand.MESSAGE_USAGE);
        }
    }

    /**
     * Parses and validates the given allergy string.
     *
     * @param allergyStr The medical condition string to parse.
     * @return A {@code Allergy} object.
     * @throws ParseException If the allergy string is invalid.
     */
    public static Allergy parseAllergy(String allergyStr) throws ParseException {
        requireNonNull(allergyStr);
        if (allergyStr.isEmpty()) {
            throw new ParseException("Allergy " + MESSAGE_EMPTY_FIELD);
        } else if (allergyStr.length() > 30) {
            throw new ParseException("Allergy " + MESSAGE_CONSTRAINTS_LENGTH);
        } else if (!allergyStr.matches(Allergy.VALIDATION_REGEX)) {
            throw new ParseException("Invalid allergy format.");
        }
        return new Allergy(allergyStr);
    }

    /**
     * Parses a string representing a priority and returns a {@link Priority} object.
     *
     * @param priorityStr the string representing the priority to be parsed.
     * @return A {@link Priority} object corresponding to the provided priority string.
     * @throws ParseException if the provided string does not conform to the expected
     *         format or is invalid as per the priority constraints defined in the
     *         {@link Priority} class.
     */
    public static Priority parsePriority(String priorityStr) throws ParseException {
        requireNonNull(priorityStr);
        String trimmedPriority = priorityStr.trim().toUpperCase();
        if (!Priority.isValidPriority(trimmedPriority)) {
            throw new ParseException(Priority.MESSAGE_CONSTRAINTS);
        }
        return new Priority(trimmedPriority);
    }

    /**
     * Parses {@code Collection<String> appointments} into a {@code Set<Appointment>}.
     */
    public static Set<Appointment> parseAppointments(Collection<String> appointments) throws ParseException {
        requireNonNull(appointments);
        if (appointments.isEmpty()) {
            return Collections.emptySet();
        }

        final Set<Appointment> appointmentSet = new HashSet<>();
        for (String appointmentName : appointments) {
            appointmentSet.add(parseSerialisedAppointment(appointmentName));
        }
        return appointmentSet;
    }

    /**
     * Parses and validates the given medical condition string.
     *
     * @param medConStr The medical condition string to parse.
     * @return A {@code MedCon} object.
     * @throws ParseException If the medical condition string is invalid.
     */
    public static MedCon parseMedCon(String medConStr) throws ParseException {
        requireNonNull(medConStr);
        if (medConStr.isEmpty()) {
            throw new ParseException("Medical condition " + MESSAGE_EMPTY_FIELD);
        } else if (medConStr.length() > 30) {
            throw new ParseException("Medical condition " + MESSAGE_CONSTRAINTS_LENGTH);
        } else if (!medConStr.matches(MedCon.VALIDATION_REGEX)) {
            throw new ParseException("Invalid medical condition format.");
        }
        return new MedCon(medConStr);
    }
}
