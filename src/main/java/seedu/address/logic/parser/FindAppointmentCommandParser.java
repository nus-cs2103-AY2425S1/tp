package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.FindAppointmentCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.criteria.SearchCriteria;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindAppointmentCommandParser implements Parser<FindCommand> {
    private static final Logger logger = LogsCenter.getLogger(FindCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindAppointmentCommand parse(String args) throws ParseException {
        List<SearchCriteria> criteria = new ArrayList<>();
        ArgumentMultimap keywords = ArgumentTokenizer.tokenize(
                args, PREFIX_START_DATE, PREFIX_START_TIME, PREFIX_END_DATE, PREFIX_END_TIME);
        // Needs to be less than 2 because the argument tokenized will always produce
        // a key-value pair between Prefix("") and the preamble (the values before the first valid prefix)
        if (keywords.getPrefixes().size() < 2) {
            throw new ParseException("Please enter at least one keyword!\n"
                    + FindCommand.MESSAGE_USAGE);
        }

        if (!keywords.getPreamble().equals("")) {
            throw new ParseException("Please do not enter anything before the keywords!\n"
                    + "Please remove this from your input: " + keywords.getPreamble());
        }

        keywords.verifyNoDuplicatePrefixesFor(PREFIX_START_DATE, PREFIX_START_TIME, PREFIX_END_DATE, PREFIX_END_TIME);
        if (keywords.getValue(PREFIX_START_DATE).isPresent()
                && keywords.getValue(PREFIX_END_DATE).isPresent()
                && keywords.getValue(PREFIX_START_TIME).isPresent()
                && keywords.getValue(PREFIX_END_TIME).isPresent()) {
            criteria.add(checkKeywords(keywords));
        }

        if (criteria.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindAppointmentCommand.MESSAGE_USAGE));
        }

        return new FindAppointmentCommand(new ContainsKeywordsPredicate(criteria));
    }

    /**
     * Checks if all the arguments after the respective prefixes are valid
     * @param keywords
     * @throws ParseException
     */
    public AppointmentSearchCriteria checkKeywords(ArgumentMultimap keywords) throws ParseException {
        LocalDate startDate = ParserUtil.parseDate(keywords.getValue(PREFIX_START_DATE).get());
        LocalTime startTime = ParserUtil.parseTime(keywords.getValue(PREFIX_START_TIME).get());
        LocalDate endDate = ParserUtil.parseDate(keywords.getValue(PREFIX_END_DATE).get());
        LocalTime endTime = ParserUtil.parseTime(keywords.getValue(PREFIX_END_TIME).get());
        return new AppointmentSearchCriteria(startDate, startTime, endDate, endTime);
    }

}
