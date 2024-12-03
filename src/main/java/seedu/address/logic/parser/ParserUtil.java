package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Messages;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.note.Note;
import seedu.address.model.person.Address;
import seedu.address.model.person.Age;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Sex;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
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
     * All whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.replaceAll("\\s+", "");
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
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
     * Parses a {@code String age} into a {@code Age}.
     * All whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code age} is invalid.
     */
    public static Age parseAge(String age) throws ParseException {
        requireNonNull(age);
        String trimmedAge = age.replaceAll("\\s+", "");
        if (!Age.isValidAge(trimmedAge)) {
            throw new ParseException(Age.MESSAGE_CONSTRAINTS);
        }
        return new Age(trimmedAge);
    }

    /**
     * Parses a {@code String sex} into a {@code Sex}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code sex} is invalid.
     */
    public static Sex parseSex(String sex) throws ParseException {
        requireNonNull(sex);
        String trimmedSex = sex.trim();
        if (!Sex.isValidSex(trimmedSex)) {
            throw new ParseException(Sex.MESSAGE_CONSTRAINTS);
        }
        return new Sex(trimmedSex);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String appointment} into a {@code Appointment}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Appointment} is invalid.
     */
    public static Appointment parseAppointment(String appointment) throws ParseException {
        requireNonNull(appointment);
        String trimmedAppointment = appointment.trim();
        if (!Appointment.isValidAppointment(trimmedAppointment)
            || !Appointment.isFutureAppointment(trimmedAppointment)) {
            throw new ParseException(Appointment.MESSAGE_CONSTRAINTS);
        }
        return new Appointment(trimmedAppointment);
    }

    /**
     * Parses {@code Collection<String> Appointments} into a {@code Set<Appointment> with only one appointment}.
     */
    public static Set<Appointment> parseAppointments(Collection<String> appointments) throws ParseException {
        requireNonNull(appointments);
        final Set<Appointment> appointmentSet = new HashSet<>();
        for (String appointmentDates : appointments) {
            appointmentSet.add(parseAppointment(appointmentDates));
        }
        return appointmentSet;
    }

    /**
     * Parses a {@code String appointment} into a {@code Appointment} for Note.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Appointment} is invalid.
     */
    public static Appointment parseAppointmentForNote(String appointment) throws ParseException {
        requireNonNull(appointment);
        String trimmedAppointment = appointment.trim();
        if (!Note.isValidAppointment(trimmedAppointment)) {
            throw new ParseException(Note.MESSAGE_CONSTRAINTS_APPOINTMENT);
        }
        return new Appointment(trimmedAppointment);
    }

    /**
     * Parses {@code Collection<String> Appointments} into a
     * {@code Set<Appointment> with only one appointment} for Note.
     */
    public static Set<Appointment> parseAppointmentsForNote(Collection<String> appointments) throws ParseException {
        requireNonNull(appointments);
        final Set<Appointment> appointmentSet = new HashSet<>();
        for (String appointmentDates : appointments) {
            appointmentSet.add(parseAppointmentForNote(appointmentDates));
        }
        return appointmentSet;
    }

    /**
     * Parses a {@code String medications} into a {@code String}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Medication} is invalid.
     */
    public static String parseMedication(String medication) throws ParseException {
        requireNonNull(medication);
        String trimmedMedication = medication.trim();
        if (!Note.isValidString(trimmedMedication)) {
            throw new ParseException(Note.MESSAGE_CONSTRAINTS);
        }
        return trimmedMedication;
    }

    /**
     * Parses {@code Collection<String> Medications} into a {@code Set<String>}.
     */
    public static Set<String> parseMedications(Collection<String> medications) throws ParseException {
        requireNonNull(medications);
        final Set<String> medicationSet = new HashSet<>();
        for (String medication : medications) {
            medicationSet.add(parseMedication(medication));
        }
        return medicationSet;
    }

    /**
     * Parses a {@code String remark} into a {@code String}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Remark} is invalid.
     */
    public static String parseRemark(String remark) throws ParseException {
        requireNonNull(remark);
        String trimmedRemark = remark.trim();
        if (!Note.isValidString(trimmedRemark)) {
            throw new ParseException(Note.MESSAGE_CONSTRAINTS);
        }
        return trimmedRemark;
    }

    /**
     * Parses {@code Collection<String> Remarks} into a {@code Set<String>}.
     */
    public static Set<String> parseRemarks(Collection<String> remarks) throws ParseException {
        requireNonNull(remarks);
        final Set<String> remarkSet = new HashSet<>();
        for (String remark : remarks) {
            remarkSet.add(parseRemark(remark));
        }
        return remarkSet;
    }
}
