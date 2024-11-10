package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.FilterStatusCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Status;
import seedu.address.model.person.StatusContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FilterStatusCommand object
 */
public class FilterStatusCommandParser implements Parser<FilterStatusCommand> {
    public static final List<String> VALID_STATUSES = Arrays.asList(
        "Applied", "Screening", "Interview Scheduled", "Interviewed",
        "Offer", "Onboarding", "Hired", "Rejected"
    );
    /**
     * Parses the given {@code String} of arguments in the context of the FilterStatusCommand
     * and returns a FilterStatusCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public FilterStatusCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterStatusCommand.MESSAGE_USAGE));
        }

        String[] statusKeywords = trimmedArgs.split("\\s+");

        // Validate each keyword using Status.VALID_STATUSES
        for (String keyword : statusKeywords) {
            if (!Status.VALID_STATUSES.contains(keyword)) {
                throw new ParseException("Invalid status: " + keyword + ". Valid statuses are: "
                    + String.join(", ", Status.VALID_STATUSES));
            }
        }

        assert statusKeywords.length > 0 : "At least one status keyword is expected";
        return new FilterStatusCommand(new StatusContainsKeywordsPredicate(Arrays.asList(statusKeywords)));
    }
}
