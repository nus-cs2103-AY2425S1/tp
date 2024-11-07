package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.DateUtil.DATE_FORMATTER;
import static seedu.address.commons.util.DateUtil.DATE_TIME_FORMATTER;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.*;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String DATETIME_INVALID_INDEX = "Could not recognise the date and time provided, please use "
            + "the format dd-MM-yyyy-HH-mm.";
    public static final String DATETIME_INVALID_SPECIFY = "\nInput causing the error: ";
    public static final String DATE_INVALID_INDEX = "Could not recognise the date provided, please use the format "
            + "dd-MM-yyyy.";

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
     * Parses a {@code String Id} into a {@code Id}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code id} is invalid.
     */
    public static Id parseId(String id) throws ParseException {
        requireNonNull(id);
        String trimmedId = id.trim();
        if (!Id.isValidId(trimmedId)) {
            throw new ParseException(Id.MESSAGE_CONSTRAINTS);
        }
        return new Id(trimmedId);
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
     * Parses a {@code String Ward} into a {@code Ward}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code ward} is invalid.
     */
    public static Ward parseWard(String ward) throws ParseException {
        requireNonNull(ward);
        String trimmedWard = ward.trim();
        if (!Ward.isValidWard(trimmedWard)) {
            throw new ParseException(Ward.MESSAGE_CONSTRAINTS);
        }
        return new Ward(trimmedWard);
    }

    /**
     * Parses a {@code String Diagnosis} into a {@code Diagnosis}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code diagnosis} is invalid.
     */
    public static Diagnosis parseDiagnosis(String diagnosis) throws ParseException {
        requireNonNull(diagnosis);
        String trimmedDiagnosis = diagnosis.trim();
        if (!Diagnosis.isValidDiagnosis(trimmedDiagnosis)) {
            throw new ParseException(Diagnosis.MESSAGE_CONSTRAINTS);
        }
        return new Diagnosis(trimmedDiagnosis);
    }

    /**
     * Parses a {@code String Medication} into a {@code Medication}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code medication} is invalid.
     */
    public static Medication parseMedication(String medication) throws ParseException {
        requireNonNull(medication);
        String trimmedMedication = medication.trim();
        if (!Medication.isValidMedication(trimmedMedication)) {
            throw new ParseException(Medication.MESSAGE_CONSTRAINTS);
        }
        return new Medication(trimmedMedication);
    }

    /**
     * Parses a {@code String Notes} into a {@code Notes}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code notes} is invalid.
     */
    public static Notes parseNotes(String notes) throws ParseException {
        requireNonNull(notes);
        String trimmedNotes = notes.trim();
        if (!Notes.isValidNote(trimmedNotes)) {
            throw new ParseException(Notes.MESSAGE_CONSTRAINTS);
        }
        return new Notes(trimmedNotes);
    }

    /**
     * Parses a {@code String appointmentDescription} into a {@code String appointmentDescription}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code appointmentDescription} is empty.
     */
    public static String parseAppointmentDescription(String appointmentDescription) throws ParseException {
        requireNonNull(appointmentDescription);
        String trimmedAppointmentDescription = appointmentDescription.trim();
        if (!Appointment.isValidDescription(trimmedAppointmentDescription)) {
            throw new ParseException(Appointment.MESSAGE_CONSTRAINTS);
        }
        return trimmedAppointmentDescription;
    }

    /**
     * Parses a {@code String dateTime} into a {@code localDateTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code dateTime} is invalid.
     */
    public static LocalDateTime parseLocalDateTime(String dateTime) throws ParseException {
        requireNonNull(dateTime);
        String trimmedDateTime = dateTime.trim();
        try {
            return LocalDateTime.parse(trimmedDateTime, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new ParseException(DATETIME_INVALID_INDEX + DATETIME_INVALID_SPECIFY + trimmedDateTime, e);
        }
    }

    /**
     * Parses a {@code String date} into a {@code localDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static LocalDate parseLocalDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        try {
            return LocalDate.parse(trimmedDate, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new ParseException(DATE_INVALID_INDEX + "\nInput causing the error: " + trimmedDate, e);
        }
    }

}
