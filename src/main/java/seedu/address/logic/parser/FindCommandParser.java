package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.student.Days;
import seedu.address.model.student.predicates.AttributeContainsKeywordsPredicate;
import seedu.address.model.student.predicates.NameContainsKeywordsPredicate;
import seedu.address.model.student.predicates.ScheduleContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    private static final Logger logger = LogsCenter.getLogger(FindCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DAY);
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_DAY);

        List<AttributeContainsKeywordsPredicate<?>> predicates = new ArrayList<>();


        if (argMultimap.getValue(PREFIX_DAY).isPresent()) {
            List<String> dayKeywords = argMultimap.getAllValues(PREFIX_DAY);
            Collection<Days> days = parseDaysForFind(dayKeywords);
            predicates.add(new ScheduleContainsKeywordsPredicate(days));
        }
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            List<String> nameKeywords = argMultimap.getAllValues(PREFIX_NAME);
            Collection<String> names = parseNameStringsForFind(nameKeywords);
            predicates.add(new NameContainsKeywordsPredicate(names));
        }
        if (predicates.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_NO_PARAMETERS));
        }
        return new FindCommand(predicates);
    }

    /**
     * Parses {@code Collection<String> names} into a {@code Set<String>} if {@code names} is non-empty and valid.
     * @throws ParseException if {@code names} is empty or contains an empty or invalid name string.
     */
    private Set<String> parseNameStringsForFind(Collection<String> names) throws ParseException {
        assert names != null;

        requireNonNull(names);

        if (names.isEmpty() || names.contains("")) {
            logger.finer("Name keywords are empty or contain an empty string: " + names);

            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_NO_NAME_KEYWORDS_AFTER_PREFIX));
        }

        return ParserUtil.parseNameStrings(names);

    }

    /**
     * Parses {@code Collection<String> days} into a {@code Set<Days>} if {@code days} is non-empty.
     * @throws ParseException if {@code days} is empty or contains an empty or invalid day string.
     */
    private Set<Days> parseDaysForFind(Collection<String> days) throws ParseException {
        assert days != null;

        if (days.isEmpty() || days.contains("")) {
            logger.finer("Day keywords are empty or contain an empty string: " + days);
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindCommand.MESSAGE_NO_SCHEDULE_KEYWORDS_AFTER_PREFIX));
        }

        return ParserUtil.parseDays(days);
    }








}
