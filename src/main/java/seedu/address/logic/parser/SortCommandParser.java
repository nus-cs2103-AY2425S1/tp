package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL;

import java.util.List;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Tutorial;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    public static final String MESSAGE_NAME_MUST_BE_EMPTY =
            "The name field must be left empty when sorting by name.";
    public static final String MESSAGE_ID_MUST_BE_EMPTY =
            "The student ID field must be left empty when sorting by student ID.";
    public static final String MESSAGE_TUT_MUST_NOT_BE_EMPTY =
            "A tutorial number must be provided for sorting by tutorial attendance.";
    public static final String MESSAGE_ONLY_ONE_TUT =
            "Only 1 tutorial number should be provided for sorting by tutorial attendance.";

    private List<Prefix> validPrefixes = List.of(PREFIX_NAME, PREFIX_STUDENT_ID, PREFIX_TUTORIAL);

    @Override
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, validPrefixes.toArray(new Prefix[0]));

        if (argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        Integer order = ParserUtil.parseSortOrder(argMultimap.getPreamble());

        if (noPrefixesPresent(argMultimap, validPrefixes)) {
            throw new ParseException(SortCommand.MESSAGE_NOT_SORTED);
        }
        if (multiplePrefixesPresent(argMultimap, validPrefixes)) {
            throw new ParseException(SortCommand.MESSAGE_WRONG_NUM_OF_FIELDS);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(validPrefixes.toArray(new Prefix[0]));

        if (argMultimap.getValue(PREFIX_NAME).map(String::isEmpty).orElse(false)) {
            return SortCommand.sortByName(order);
        } else if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            throw new ParseException(MESSAGE_NAME_MUST_BE_EMPTY);
        }

        if (argMultimap.getValue(PREFIX_STUDENT_ID).map(String::isEmpty).orElse(false)) {
            return SortCommand.sortByStudentId(order);
        } else if (argMultimap.getValue(PREFIX_STUDENT_ID).isPresent()) {
            throw new ParseException(MESSAGE_ID_MUST_BE_EMPTY);
        }

        if (argMultimap.getValue(PREFIX_TUTORIAL).map(String::isEmpty).orElse(false)) {
            throw new ParseException(MESSAGE_TUT_MUST_NOT_BE_EMPTY);
        } else if (argMultimap.getValue(PREFIX_TUTORIAL).isPresent()) {
            List<Tutorial> tutorials = ParserUtil.parseTutorials(argMultimap.getValue(PREFIX_TUTORIAL).get());
            if (tutorials.size() != 1) {
                throw new ParseException(MESSAGE_ONLY_ONE_TUT);
            }
            return SortCommand.sortByTutorialAttendance(order, tutorials.get(0));
        }

        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    public static boolean multiplePrefixesPresent(ArgumentMultimap argMultimap, List<Prefix> prefixes) {
        return prefixes.stream().filter(prefix -> argMultimap.getValue(prefix).isPresent()).count() > 1;
    }

    public static boolean noPrefixesPresent(ArgumentMultimap argMultimap, List<Prefix> prefixes) {
        return prefixes.stream().noneMatch(prefix -> argMultimap.getValue(prefix).isPresent());
    }
}
