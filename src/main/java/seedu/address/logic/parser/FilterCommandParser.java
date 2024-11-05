package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Job;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameOrJobContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FilterCommand object.
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_JOB);
        ArrayList<Name> nameList = ParserUtil.parseNames(argMultimap.getAllValues(PREFIX_NAME));
        ArrayList<Job> jobList = ParserUtil.parseJobs(argMultimap.getAllValues(PREFIX_JOB));

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_NAME)
                && !ParserUtil.arePrefixesPresent(argMultimap, PREFIX_JOB)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = new String[nameList.size()];
        String[] jobKeywords = new String[jobList.size()];

        for (int i = 0; i < nameList.size(); i++) {
            Name item = nameList.get(i);
            if (item != null) {
                nameKeywords[i] = item.toString();
            }
        }

        for (int i = 0; i < jobList.size(); i++) {
            Job item = jobList.get(i);
            if (item != null) {
                jobKeywords[i] = item.toString();
            }
        }

        List<String> nameKeywordList = Arrays.stream(nameKeywords).collect(Collectors.toList());
        List<String> jobKeywordList = Arrays.stream(jobKeywords).collect(Collectors.toList());

        return new FilterCommand(new NameOrJobContainsKeywordsPredicate(nameKeywordList, jobKeywordList));
    }

}
