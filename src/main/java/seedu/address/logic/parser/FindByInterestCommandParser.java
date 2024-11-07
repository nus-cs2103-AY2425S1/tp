package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.FindByInterestCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.InterestContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindByInterestCommand object.
 */
public class FindByInterestCommandParser implements Parser<FindByInterestCommand> {

    // Pattern to match each i/ group, allowing spaces or commas within it
    private static final Pattern INTEREST_KEYWORD_PATTERN = Pattern.compile("i/\\s*([^\\s]+(?:\\s*,\\s*[^\\s]+)*)");

    /**
     * Parses the given {@code String} of arguments in the context of the FindByInterestCommand
     * and returns a FindByInterestCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public FindByInterestCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindByInterestCommand.MESSAGE_USAGE));
        }

        // Two lists: one for AND keywords, one for OR keywords
        List<List<String>> andKeywords = new ArrayList<>();
        List<String> orKeywords = new ArrayList<>();

        // Match all "i/..." patterns in the input
        Matcher matcher = INTEREST_KEYWORD_PATTERN.matcher(trimmedArgs);

        while (matcher.find()) {
            // Extract the keyword group from the match
            String keywordGroup = matcher.group(1).trim();

            if (!keywordGroup.isEmpty()) {
                // Check if there is a comma in the keyword group
                if (keywordGroup.contains(",")) {
                    // Split by commas to handle "AND" condition
                    List<String> keywords = Arrays.asList(keywordGroup.split("\\s*,\\s*"));
                    andKeywords.add(new ArrayList<>(keywords));
                } else {
                    // No comma present, treat it as an "OR" condition
                    orKeywords.add(keywordGroup);
                }
            }
        }

        // If no valid keywords were found, throw a ParseException
        if (andKeywords.isEmpty() && orKeywords.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindByInterestCommand.MESSAGE_USAGE));
        }

        return new FindByInterestCommand(new InterestContainsKeywordsPredicate(andKeywords, orKeywords));
    }
}
