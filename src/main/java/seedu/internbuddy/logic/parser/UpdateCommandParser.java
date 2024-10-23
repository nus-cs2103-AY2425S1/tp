package seedu.internbuddy.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.internbuddy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.internbuddy.logic.parser.CliSyntax.PREFIX_APP_STATUS;

import java.util.stream.Stream;

import seedu.internbuddy.commons.core.index.Index;
import seedu.internbuddy.logic.commands.UpdateCommand;
import seedu.internbuddy.logic.parser.exceptions.ParseException;
import seedu.internbuddy.model.application.AppStatus;

/**
 * Parses input arguments and creates a new UpdateCommand object
 */
public class UpdateCommandParser implements Parser<UpdateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UpdateCommand
     * and returns a UpdateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public UpdateCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_APP_STATUS);

        AppStatus appStatus;
        Index companyIndex;
        Index applicationIndex;

        if (!arePrefixesPresent(argMultimap, PREFIX_APP_STATUS)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
        }

        try {
            Index[] indexes = ParserUtil.parseWithdrawIndex(argMultimap.getPreamble());
            companyIndex = indexes[0];
            applicationIndex = indexes[1];
            appStatus = ParserUtil.parseAppStatus(argMultimap.getValue(PREFIX_APP_STATUS).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE), pe);
        }

        return new UpdateCommand(companyIndex, applicationIndex, appStatus);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
