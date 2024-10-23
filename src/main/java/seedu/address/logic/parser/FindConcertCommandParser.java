package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Arrays;

import seedu.address.logic.commands.FindConcertCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.commons.Name;
import seedu.address.model.commons.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindConcertCommand object
 */
public class FindConcertCommandParser implements Parser<FindConcertCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindConcertCommand
     * and returns a FindConcertCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindConcertCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindConcertCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        if (argMultimap.getValue(PREFIX_NAME).isEmpty() || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindConcertCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME);

        assert argMultimap.getValue(PREFIX_NAME).isPresent() : "Prefix for name must be present";

        String[] nameKeywords = argMultimap.getValue(PREFIX_NAME).map(arg-> arg.split("\\s+")).get();
        if (!Arrays.stream(nameKeywords).allMatch(Name::isValidName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }

        return new FindConcertCommand(new NameContainsKeywordsPredicate<>(Arrays.asList(nameKeywords)));
    }
}
