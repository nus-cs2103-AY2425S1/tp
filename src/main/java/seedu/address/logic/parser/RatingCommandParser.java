package seedu.address.logic.parser;

import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RatingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.restaurant.Rating;

public class RatingCommandParser implements Parser<RatingCommand> {
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
