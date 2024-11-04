package seedu.academyassist.logic.parser;

import static seedu.academyassist.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.academyassist.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.academyassist.logic.parser.CliSyntax.PREFIX_YEARGROUP;

import java.util.stream.Stream;

import seedu.academyassist.logic.commands.FilterCommand;
import seedu.academyassist.logic.parser.exceptions.ParseException;
import seedu.academyassist.model.filter.FilterParam;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        // Tokenize the input arguments
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_YEARGROUP,
                PREFIX_SUBJECT);


        // Ensure that either the year group or subject prefix is present, but not both
        boolean hasYearGroup = argMultimap.getValue(PREFIX_YEARGROUP).isPresent();
        boolean hasSubject = argMultimap.getValue(PREFIX_SUBJECT).isPresent();

        if (hasYearGroup && hasSubject) {
            throw new ParseException("Please specify either a year group or a subject, not both.");
        } else if (!hasYearGroup && !hasSubject) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_YEARGROUP, PREFIX_SUBJECT);
        String filterString;
        Object filterValue;

        if (hasYearGroup) {
            // Extract the value after yg/
            filterString = argMultimap.getValue(PREFIX_YEARGROUP).get();
            // Call ParserUtil to parse year group
            filterValue = ParserUtil.parseYearGroup(filterString);
            return new FilterCommand(new FilterParam("yearGroup"), filterValue);
        } else { // hasSubject must be true
            // Extract the value after s/
            filterString = argMultimap.getValue(PREFIX_SUBJECT).get();
            // Call ParserUtil to parse subject
            filterValue = ParserUtil.parseSubject(filterString);
            return new FilterCommand(new FilterParam("subject"), filterValue);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
