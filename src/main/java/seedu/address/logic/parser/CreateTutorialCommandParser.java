package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_LOGGER_FOR_EXCEPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.CreateTutorialCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tutorial.Tutorial;

/**
 * Parses input arguments and creates a new CreateTutorialCommand object
 */
public class CreateTutorialCommandParser implements Parser<CreateTutorialCommand> {
    private final Logger logger = LogsCenter.getLogger(CreateTutorialCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the CreateTutorialCommand
     * and returns a CreateTutorialCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public CreateTutorialCommand parse(String args) throws ParseException {
        logger.info(" - Starting to parse arguments in " + CreateTutorialCommandParser.class);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TUTORIAL);
        if (!argMultimap.getValue(PREFIX_TUTORIAL).isPresent()
                || !argMultimap.getPreamble().isEmpty()) {
            logger.warning(String.format(MESSAGE_LOGGER_FOR_EXCEPTION, CreateTutorialCommandParser.class));
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT,
                    CreateTutorialCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TUTORIAL);
        Tutorial tutorial = ParserUtil.parseTutorial(argMultimap.getValue(PREFIX_TUTORIAL).get());
        logger.info(" - Successfully parsed arguments. Creating CreateTutorialCommand object.");
        return new CreateTutorialCommand(tutorial);
    }
}
