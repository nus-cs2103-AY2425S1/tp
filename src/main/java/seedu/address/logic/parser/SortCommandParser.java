package seedu.address.logic.parser;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;

import java.util.Comparator;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_ID;

public class SortCommandParser implements Parser<SortCommand> {

    private List<Prefix> validPrefixes = List.of(PREFIX_NAME, PREFIX_STUDENT_ID);

    @Override
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_STUDENT_ID);
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
        if (noPrefixesPresent(argMultimap, validPrefixes)) {
            throw new ParseException(SortCommand.MESSAGE_NOT_SORTED);
        }

        if (multiplePrefixesPresent(argMultimap, validPrefixes)) {
            throw new ParseException(SortCommand.MESSAGE_WRONG_NUM_OF_FIELDS);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_STUDENT_ID);

        Comparator<Person> sortComparator;
        Integer order;

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            order = ParserUtil.parseSortOrder(argMultimap.getValue(PREFIX_NAME).get());
            sortComparator = Comparator.comparing(person -> person.getName().toString());
        } else if (argMultimap.getValue(PREFIX_STUDENT_ID).isPresent()) {
            order = ParserUtil.parseSortOrder(argMultimap.getValue(PREFIX_STUDENT_ID).get());
            sortComparator = Comparator.comparing(person -> person.getStudentId().toString());
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
        return new SortCommand(sortComparator, order);
    }

    public static boolean multiplePrefixesPresent(ArgumentMultimap argMultimap, List<Prefix> prefixes) {
        return prefixes.stream().filter(prefix -> argMultimap.getValue(prefix).isPresent()).count() > 1;
    }

    public static boolean noPrefixesPresent(ArgumentMultimap argMultimap, List<Prefix> prefixes) {
        return prefixes.stream().noneMatch(prefix -> argMultimap.getValue(prefix).isPresent());
    }
}
