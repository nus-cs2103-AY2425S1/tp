package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.logic.commands.UpdateCommand;
import seedu.address.logic.commands.UpdateCommand.UpdateStudentDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.student.LessonTime;
import seedu.address.model.student.Name;
import seedu.address.model.student.Subject;

/**
 * Parses input arguments and creates a new UpdateCommand object
 */
public class UpdateCommandParser implements Parser<UpdateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UpdateCommand
     * and returns an UpdateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UpdateCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMERGENCY_CONTACT,
                        PREFIX_ADDRESS, PREFIX_NOTE, PREFIX_SUBJECT, PREFIX_LEVEL, PREFIX_LESSON_TIME);

        if (argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
        }

        Name name;

        try {
            name = ParserUtil.parseName(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMERGENCY_CONTACT, PREFIX_ADDRESS);

        UpdateStudentDescriptor updateStudentDescriptor = new UpdateStudentDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            updateStudentDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            updateStudentDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMERGENCY_CONTACT).isPresent()) {
            updateStudentDescriptor.setEmergencyContact(ParserUtil
                    .parseEmergencyContact(argMultimap.getValue(PREFIX_EMERGENCY_CONTACT).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            updateStudentDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        if (argMultimap.getValue(PREFIX_NOTE).isPresent()) {
            updateStudentDescriptor.setNote(ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE).get()));
        }
        if (argMultimap.getValue(PREFIX_LEVEL).isPresent()) {
            updateStudentDescriptor.setLevel(ParserUtil.parseLevel(argMultimap.getValue(PREFIX_LEVEL).get()));
        }

        parseSubjectsForUpdate(argMultimap.getAllValues(PREFIX_SUBJECT))
                .ifPresent(updateStudentDescriptor::setSubjects);
        parseLessonTimesForUpdate(argMultimap.getAllValues(PREFIX_LESSON_TIME))
                .ifPresent(updateStudentDescriptor::setLessonTimes);
        if (!updateStudentDescriptor.isAnyFieldUpdated()) {
            throw new ParseException(UpdateCommand.MESSAGE_NOT_UPDATED);
        }

        return new UpdateCommand(name, updateStudentDescriptor);
    }

    /**
     * Parses {@code Collection<String> subjects} into a {@code Set<Subject>} if {@code subjects} is non-empty.
     * If {@code subjects} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Subject>} containing zero subjects.
     */
    private Optional<Set<Subject>> parseSubjectsForUpdate(Collection<String> subjects) throws ParseException {
        assert subjects != null;

        if (subjects.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> subjectSet = subjects.size() == 1 && subjects.contains("")
                ? Collections.emptySet()
                : subjects;
        return Optional.of(ParserUtil.parseSubjects(subjectSet));
    }

    /**
     * Parses {@code Collection<String> lts} into a {@code Set<LessonTime>} if {@code lts} is non-empty.
     * If {@code lts} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<LessonTime>} containing zero lesson times.
     */
    private Optional<Set<LessonTime>> parseLessonTimesForUpdate(Collection<String> lts) throws ParseException {
        assert lts != null;

        if (lts.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> ltSet = lts.size() == 1 && lts.contains("")
                ? Collections.emptySet()
                : lts;
        return Optional.of(ParserUtil.parseLessonTimes(ltSet));
    }

}
