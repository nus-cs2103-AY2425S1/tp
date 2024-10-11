package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MARKVIP;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MarkVipCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class MarkVipCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the MarkVipCommand
     * and returns a MarkVipCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MarkVipCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();

        String[] splitArgs = trimmedArgs.split("\\s+");

        if (splitArgs.length != 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkVipCommand.MESSAGE_USAGE));
        }
        Index index;
        try {
            index = ParserUtil.parseIndex(splitArgs[0]);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkVipCommand.MESSAGE_USAGE), pe);
        }
        switch (splitArgs[1]) {
        case "true":
            return new MarkVipCommand(index, true);
        case "false":
            return new MarkVipCommand(index, false);
        default:
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkVipCommand.MESSAGE_USAGE));
        }
    }
}
