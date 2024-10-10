package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSURANCE_ID;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteInsuranceCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and adds {@code DeleteInsurance} object
 */
public class DeleteInsuranceCommandParser implements Parser<DeleteInsuranceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code DeleteInsuranceCommand}
     * and returns a {@code DeleteInsuranceCommand} object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public DeleteInsuranceCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_INSURANCE_ID);
        Index index;
        int insuranceId;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            if (argMultimap.getValue(PREFIX_INSURANCE_ID).isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        DeleteInsuranceCommand.MESSAGE_USAGE));
            }
            insuranceId = ParserUtil.parseInsurancePlan(argMultimap.getValue(PREFIX_INSURANCE_ID).get());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, DeleteInsuranceCommand.MESSAGE_USAGE), ive);
        }
        return new DeleteInsuranceCommand(index, insuranceId);
    }
}
