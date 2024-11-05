package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindOwnerCommand;
import seedu.address.logic.commands.FindPetCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.owner.OwnerNameContainsKeywordsPredicate;
import seedu.address.model.pet.PetNameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand<?>> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand<?> parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        String[] splitArgs = trimmedArgs.split("\\s+");

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        if (splitArgs.length < 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String entityType = splitArgs[0]; // OWNER or PET
        String[] keywords = Arrays.copyOfRange(splitArgs, 1, splitArgs.length); // gets the rest of the args

        switch (entityType.toLowerCase()) {
        case "owner":
            return new FindOwnerCommand(new OwnerNameContainsKeywordsPredicate(Arrays.asList(keywords)));
        case "pet":
            return new FindPetCommand(new PetNameContainsKeywordsPredicate(Arrays.asList(keywords)));
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }
}
