package seedu.internbuddy.logic.parser;

import static seedu.internbuddy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.internbuddy.logic.parser.CliSyntax.PREFIX_APP_INDEX;
import static seedu.internbuddy.logic.parser.CliSyntax.PREFIX_COMPANY_INDEX;
import static seedu.internbuddy.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.internbuddy.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.stream.Stream;

import seedu.internbuddy.commons.core.index.Index;
import seedu.internbuddy.logic.commands.ApplyCommand;
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

        if (!arePrefixesPresent(argMultimap, PREFIX_COMPANY_INDEX, PREFIX_APP_INDEX)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ApplyCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_DESCRIPTION);
        Index companyIndex;
        Index applicationIndex;
        try {
            companyIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_COMPANY_INDEX).get());
            applicationIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_APP_INDEX).get());
            return new WithdrawCommand(companyIndex, applicationIndex);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, WithdrawCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
