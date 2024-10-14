package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Priority;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        // Check if no filters are given
        if (args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(
                        args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_PRIORITY, PREFIX_REMARK, PREFIX_TAG);

        // Check for filtering by invalid prefixes
        if (hasPrefixes(argMultimap, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_REMARK, PREFIX_TAG)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        List<String> names = argMultimap.getAllValues(PREFIX_NAME);
        List<String> addresses = argMultimap.getAllValues(PREFIX_ADDRESS);
        List<String> priorities = argMultimap.getAllValues(PREFIX_PRIORITY);

        names = splitStrings(names);
        if (!areValidKeywords(names, Name.VALIDATION_REGEX)) {
            throw new ParseException("Name keyword was invalid! " + Name.MESSAGE_CONSTRAINTS + "\n"
                    + FindCommand.MESSAGE_USAGE);
        }

        if (!areValidKeywords(addresses, Address.VALIDATION_REGEX)) {
            throw new ParseException("Address keyword was invalid!" + Address.MESSAGE_CONSTRAINTS + "\n"
                    + FindCommand.MESSAGE_USAGE);
        }

        priorities = splitStrings(priorities);
        if (!areValidPriorities(priorities)) {
            throw new ParseException("Invalid priority given! " + Priority.MESSAGE_CONSTRAINTS + "\n"
                    + FindCommand.MESSAGE_USAGE);
        }


        return new FindCommand(names, addresses, priorities);
    }

    /**
     * Returns true if any of the prefixes are used in the given
     * {@code ArgumentMultimap}.
     */
    private boolean hasPrefixes(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Splits each string in {@code list} into multiple strings if there is whitespace between characters.
     *
     * @return New list of all strings without any whitespace.
     */
    private List<String> splitStrings(List<String> list) {
        List<String> newList = new ArrayList<>();

        for (String currentString : list) {
            String[] separatedStrings = currentString.split("\\s+");
            newList.addAll(Arrays.asList(separatedStrings));
        }
        return newList;
    }

    /**
     * Checks if the keywords in the given list follow the format of their corresponding field.
     *
     * @param keywords Keywords given by the user to filter a parameter by.
     * @param validFormat The format that the keyword needs to adhere to.
     * @return True if all keywords are valid according to the validFormat.
     */
    private boolean areValidKeywords(List<String> keywords, String validFormat) {
        for (String keyword : keywords) {
            if (!keyword.matches(validFormat)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check for exact matching of priorities since it is an enum.
     *
     * @param priorities List of priorities given to filter the address book by.
     * @return True if all priorities correspond to available priorities.
     */
    private boolean areValidPriorities(List<String> priorities) {
        for (String priority : priorities) {
            try {
                ParserUtil.parsePriority(priority);
            } catch (ParseException pe) {
                return false;
            }
        }
        return true;
    }
}
