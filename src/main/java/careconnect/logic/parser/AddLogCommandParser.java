package careconnect.logic.parser;

import static careconnect.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static java.util.Objects.requireNonNull;

import java.util.Date;

import careconnect.commons.core.index.Index;
import careconnect.logic.Messages;
import careconnect.logic.commands.AddLogCommand;
import careconnect.logic.parser.exceptions.ParseException;
import careconnect.model.log.Log;


/**
 * Parses input arguments and creates a new AddLogCommand object
 */
public class AddLogCommandParser implements Parser<AddLogCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddLogCommand
     * and returns an AddLogCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddLogCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_DATE, CliSyntax.PREFIX_REMARK);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddLogCommand.MESSAGE_USAGE), pe);
        }

        if (!ParserUtil.arePrefixesPresent(argMultimap, CliSyntax.PREFIX_REMARK)) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    AddLogCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(CliSyntax.PREFIX_DATE, CliSyntax.PREFIX_REMARK);

        Log log;
        String remark =
                ParserUtil.parseLogRemark(argMultimap.getValue(CliSyntax.PREFIX_REMARK).get());

        if (argMultimap.getValue(CliSyntax.PREFIX_DATE).isPresent()) {
            Date date =
                    ParserUtil.parseLogDate((argMultimap.getValue(CliSyntax.PREFIX_DATE).get()));
            log = new Log(date, remark);
        } else {
            log = new Log(remark);
        }
        return new AddLogCommand(index, log);
    }

}
