package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ListPoliciesCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code ListPoliciesCommand} object.
 */
public class ListPoliciesCommandParser implements Parser<ListPoliciesCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code ListPoliciesCommand}
     * and returns a {@code ListPoliciesCommand} object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public ListPoliciesCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args);

        Index index;
        try {
            // parse preamble as the person index
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListPoliciesCommand.MESSAGE_USAGE), ive);
        }

        // Return the new ListPoliciesCommand with the parsed index
        return new ListPoliciesCommand(index);
    }
}
