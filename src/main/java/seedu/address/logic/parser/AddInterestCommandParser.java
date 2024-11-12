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
     * This method tokenizes the input arguments, checks for the required index, and validates that there is
     * at least one interest provided. It normalizes the format of each interest (capitalizing the first letter)
     * and verifies that there are no duplicate interests within the command (case-insensitive).
     *
     * @param args User input arguments in the format expected by the AddInterestCommand.
     * @return An AddInterestCommand object configured with the specified index and interests.
     * @throws ParseException if the user input does not conform to the expected format, if the index is missing or
     *                        invalid, or if there are duplicate interests within the input.
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
        Set<String> normalizedInterests = new HashSet<>(); // to check for duplicates within command

        for (String interestStr : interestStrings) {
            // Normalize the interest to have only the first letter capitalized
            String normalizedInterestStr = capitalizeFirstLetter(interestStr);

            if (!normalizedInterests.add(normalizedInterestStr)) {
                // If interest is already in normalizedInterests, throw a duplicate within command error
                throw new ParseException(String.format(AddInterestCommand.MESSAGE_DUPLICATE_INTERESTS,
                        normalizedInterestStr));
            }

            Interest interest = ParserUtil.parseInterest(normalizedInterestStr);
            interests.add(interest);
        }

        return new AddInterestCommand(index, interests);
    }

    private String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
}

