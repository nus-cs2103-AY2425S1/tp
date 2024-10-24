package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL;

import seedu.address.logic.commands.CreateTutorialCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tutorial.Tutorial;

/**
 * Parses input arguments and creates a new CreateTutorialCommand object
 */
public class CreateTutorialCommandParser implements Parser<CreateTutorialCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CreateTutorialCommand
     * and returns a CreateTutorialCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public CreateTutorialCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TUTORIAL);
        if (!argMultimap.getValue(PREFIX_TUTORIAL).isPresent()
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT,
                    CreateTutorialCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TUTORIAL);
        Tutorial tutorial = ParserUtil.parseTutorial(argMultimap.getValue(PREFIX_TUTORIAL).get());
        return new CreateTutorialCommand(tutorial);
    }
}
