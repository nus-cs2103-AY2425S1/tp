package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.logic.commands.NoteCommand;
import seedu.address.logic.commands.NoteCommand.NoteDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.note.Note;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class NoteCommandParser implements Parser<NoteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an NoteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public NoteCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_APPOINTMENT, PREFIX_MEDICATION, PREFIX_REMARK);

        Name name;

        try {
            name = ParserUtil.parseName(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteCommand.MESSAGE_USAGE), pe);
        }

        NoteDescriptor noteDescriptor = new NoteDescriptor();

        parseAppointmentsForNote(argMultimap.getAllValues(PREFIX_APPOINTMENT))
                .ifPresent(noteDescriptor::setAppointments);

        parseMedicationsForNote(argMultimap.getAllValues(PREFIX_MEDICATION))
                .ifPresent(noteDescriptor::setMedications);

        parseRemarksForNote(argMultimap.getAllValues(PREFIX_REMARK))
                .ifPresent(noteDescriptor::setRemarks);

        if (!noteDescriptor.isAnyFieldEdited()) {
            throw new ParseException(NoteCommand.MESSAGE_NOT_EDITED);
        }

        return new NoteCommand(name, noteDescriptor);
    }

    /**
     * Parses {@code Collection<String> appointments} into a {@code Set<Appointment>} if {@code appointment} is
     * non-empty
     * If {@code Appointments} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Appointment>} containing zero tags.
     */
    private Optional<Set<Appointment>> parseAppointmentsForNote(Collection<String> appointments) throws ParseException {
        assert appointments != null;

        if (appointments.isEmpty()) {
            return Optional.empty();
        }

        Collection<String> appointmentSet = appointments.size() == 1 && appointments.contains("")
                ? Collections.emptySet()
                : appointments;

        return Optional.of(ParserUtil.parseAppointments(appointmentSet));
    }

    /**
     * Parses {@code Collection<String> appointments} into a {@code Set<Appointment>} if {@code appointment} is
     * non-empty
     * If {@code Appointments} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Appointment>} containing zero tags.
     */
    private Optional<Set<String>> parseMedicationsForNote(Collection<String> medications) throws ParseException {
        assert medications != null;

        if (medications.isEmpty()) {
            return Optional.empty();
        }

        if (medications.size() != 1 && medications.contains("")) {
            throw new ParseException(Note.MESSAGE_CONSTRAINTS);
        }

        Collection<String> medicationSet = medications.size() == 1 && medications.contains("")
                ? Collections.emptySet()
                : medications;

        return Optional.of(ParserUtil.parseMedications(medicationSet));
    }

    /**
     * Parses {@code Collection<String> appointments} into a {@code Set<Appointment>} if {@code appointment} is
     * non-empty
     * If {@code Appointments} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Appointment>} containing zero tags.
     */
    private Optional<Set<String>> parseRemarksForNote(Collection<String> remarks) throws ParseException {
        assert remarks != null;

        if (remarks.isEmpty()) {
            return Optional.empty();
        }

        if (remarks.size() != 1 && remarks.contains("")) {
            throw new ParseException(Note.MESSAGE_CONSTRAINTS);
        }

        Collection<String> remarkSet = remarks.size() == 1 && remarks.contains("")
                ? Collections.emptySet()
                : remarks;

        return Optional.of(ParserUtil.parseRemarks(remarkSet));
    }
}
