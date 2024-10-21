package seedu.address.logic.parser;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.SwitchParserModeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SwitchParserModeCommand object
 */
public class SwitchParserModeCommandParser implements Parser<SwitchParserModeCommand> {

    private static final String BUYER_MODE = "b";
    private static final String MEETUP_MODE = "m";
    private static final String PROPERTY_MODE = "p";

    /**
     * Parses the given {@code String} of arguments in the context of the SwitchParserModeCommand
     * and returns a SwitchParserModeCommand object for execution.
     * @throws ParseException if the argument is an invalid ParserMode
     */
    public SwitchParserModeCommand parse(String argument) throws ParseException {

        String trimmedArg = argument.trim();

        switch (trimmedArg) {

        case BUYER_MODE:
            return new SwitchParserModeCommand(ParserMode.BUYER);

        case MEETUP_MODE:
            return new SwitchParserModeCommand(ParserMode.MEETUP);

        default:
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, trimmedArg)
            );
        }
    }
}
