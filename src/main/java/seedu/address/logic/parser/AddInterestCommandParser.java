package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTEREST;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddInterestCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Interest;

/**
 * Parses input arguments and creates a new AddInterestCommand object.
 */
public class AddInterestCommandParser implements Parser<AddInterestCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddInterestCommand
     * and returns an AddInterestCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public AddInterestCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_INDEX, PREFIX_INTEREST);

        if (!argMultimap.getValue(PREFIX_INDEX).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddInterestCommand.MESSAGE_USAGE));
        }

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddInterestCommand.MESSAGE_USAGE),
                    pe);
        }

        List<String> interestStrings = argMultimap.getAllValues(PREFIX_INTEREST);
        if (interestStrings.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddInterestCommand.MESSAGE_USAGE));
        }

        Set<Interest> interests = new HashSet<>();
        for (String interestStr : interestStrings) {
            Interest interest = ParserUtil.parseInterest(interestStr);
            if (!interests.add(interest)) {
                throw new ParseException(AddInterestCommand.MESSAGE_DUPLICATE_INTERESTS);
            }
        }

        return new AddInterestCommand(index, interests);
    }
}
