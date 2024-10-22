package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DemoteCommand;
import seedu.address.logic.commands.PromoteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ContractEndDate;

/**
 * Parses input arguments and creates a new PromoteCommand object
 */
public class PromoteCommandParser implements Parser<PromoteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PromoteCommand
     * and returns a PromoteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PromoteCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DemoteCommand.MESSAGE_USAGE));
        }
        String[] argsArray = trimmedArgs.split("\\s+");

        if (argsArray.length != 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PromoteCommand.MESSAGE_USAGE));
        }

        Index index;
        ContractEndDate contractEndDate;
        String indexArg = argsArray[0];
        String contractEndDateArg = argsArray[1];

        try {
            index = ParserUtil.parseIndex(indexArg);
            contractEndDate = ParserUtil.parseContractEndDate(contractEndDateArg);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PromoteCommand.MESSAGE_USAGE), pe);
        }

        return new PromoteCommand(index, contractEndDate);
    }
}
