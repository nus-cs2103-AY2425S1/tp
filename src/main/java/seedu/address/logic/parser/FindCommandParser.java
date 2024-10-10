package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;

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
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        Pattern argFormat = Pattern.compile("(?<typeWord>\\S+)(?<keywords>.*)");
        Matcher matcher = argFormat.matcher(trimmedArgs);
        String typeWord = matcher.group("typeWord");
        String keywords = matcher.group("keywords");

        String[] keywordsSplit = keywords.split("\\s+");


        return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(keywordsSplit)));
    }

}
