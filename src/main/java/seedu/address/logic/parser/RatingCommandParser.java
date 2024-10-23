package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;

import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RatingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.restaurant.Rating;

/**
 * Parses input arguments and creates a new RatingCommand object
 */
public class RatingCommandParser implements Parser<RatingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RatingCommand
     * and returns a RatingCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RatingCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_RATING);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RatingCommand.MESSAGE_USAGE), ive);
        }

        Optional<String> ratingValue = argMultimap.getValue(PREFIX_RATING);
        Rating rating = new Rating(null);
        if (ratingValue.isPresent()) {
            rating = ParserUtil.parseRating(ratingValue.get());
        }

        return new RatingCommand(index, rating);
    }
}
