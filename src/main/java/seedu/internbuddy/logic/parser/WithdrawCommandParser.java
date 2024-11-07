package seedu.internbuddy.logic.parser;

import static seedu.internbuddy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.internbuddy.logic.parser.CliSyntax.PREFIX_APP_INDEX;
import static seedu.internbuddy.logic.parser.CliSyntax.PREFIX_COMPANY_INDEX;

import seedu.internbuddy.commons.core.index.Index;
import seedu.internbuddy.logic.commands.WithdrawCommand;
import seedu.internbuddy.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new WithdrawCommand object
 */
public class WithdrawCommandParser implements Parser<WithdrawCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the WithdrawCommand
     * and returns a WithdrawCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public WithdrawCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_COMPANY_INDEX, PREFIX_APP_INDEX);

        if (!argMultimap.arePrefixesPresent(PREFIX_COMPANY_INDEX, PREFIX_APP_INDEX)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, WithdrawCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_COMPANY_INDEX, PREFIX_APP_INDEX);
        Index companyIndex;
        try {
            companyIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_COMPANY_INDEX).get());
        } catch (ParseException pe) {
            throw new ParseException("Company " + ParserUtil.MESSAGE_INVALID_INDEX);
        }
        Index applicationIndex;
        try {
            applicationIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_APP_INDEX).get());
        } catch (ParseException pe) {
            throw new ParseException("Application " + ParserUtil.MESSAGE_INVALID_INDEX);
        }
        return new WithdrawCommand(companyIndex, applicationIndex);
    }
}
