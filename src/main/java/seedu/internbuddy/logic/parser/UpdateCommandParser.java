package seedu.internbuddy.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.internbuddy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.internbuddy.logic.parser.CliSyntax.PREFIX_APP_INDEX;
import static seedu.internbuddy.logic.parser.CliSyntax.PREFIX_APP_STATUS;
import static seedu.internbuddy.logic.parser.CliSyntax.PREFIX_COMPANY_INDEX;

import java.util.logging.Logger;

import seedu.internbuddy.commons.core.LogsCenter;
import seedu.internbuddy.commons.core.index.Index;
import seedu.internbuddy.logic.commands.UpdateCommand;
import seedu.internbuddy.logic.parser.exceptions.ParseException;
import seedu.internbuddy.model.application.AppStatus;

/**
 * Parses input arguments and creates a new UpdateCommand object
 */
public class UpdateCommandParser implements Parser<UpdateCommand> {

    private static final Logger logger = LogsCenter.getLogger(UpdateCommandParser.class);
    /**
     * Parses the given {@code String} of arguments in the context of the UpdateCommand
     * and returns a UpdateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public UpdateCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_COMPANY_INDEX, PREFIX_APP_INDEX, PREFIX_APP_STATUS);

        AppStatus appStatus;
        Index companyIndex;
        Index applicationIndex;

        if (!argMultimap.arePrefixesPresent(PREFIX_COMPANY_INDEX, PREFIX_APP_INDEX, PREFIX_APP_STATUS)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
        }

        try {
            companyIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_COMPANY_INDEX).get());
            applicationIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_APP_INDEX).get());
            appStatus = ParserUtil.parseAppStatus(argMultimap.getValue(PREFIX_APP_STATUS).get());
            return new UpdateCommand(companyIndex, applicationIndex, appStatus);
        } catch (ParseException pe) {
            logger.warning("Update Command Failed: Syntax Error by user");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE), pe);
        }
    }
}
