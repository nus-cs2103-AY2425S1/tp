package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_LOGGER_FOR_EXCEPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.CloseTutorialCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tutorial.Tutorial;

/**
 * Parses input arguments and creates a new CloseTutorialCommand object
 */
public class CloseTutorialCommandParser implements Parser<CloseTutorialCommand> {
    private final Logger logger = LogsCenter.getLogger(CloseTutorialCommandParser.class);


    /**
     * Parses the given {@code String} of arguments in the context of the CloseTutorialCommand
     * and returns a CloseTutorialCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public CloseTutorialCommand parse(String args) throws ParseException {
        logger.info(" - Starting to parse arguments in " + CloseTutorialCommandParser.class);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TUTORIAL);
        if (!argMultimap.getValue(PREFIX_TUTORIAL).isPresent()
                || !argMultimap.getPreamble().isEmpty()) {
            logger.warning(String.format(MESSAGE_LOGGER_FOR_EXCEPTION, CloseTutorialCommandParser.class));
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CloseTutorialCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TUTORIAL);
        Tutorial tutorial = ParserUtil.parseTutorial(argMultimap.getValue(PREFIX_TUTORIAL).get());
        logger.info(" - Successfully parsed arguments. Creating CloseTutorialCommand object.");
        return new CloseTutorialCommand(tutorial);
    }
}
