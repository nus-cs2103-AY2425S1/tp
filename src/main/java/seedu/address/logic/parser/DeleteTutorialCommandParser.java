package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUT_ID;

import java.util.stream.Stream;

import seedu.address.logic.commands.DeleteTutorialCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tut.TutName;
import seedu.address.model.tut.Tutorial;


/**
 * Parses input arguments and creates a new DeleteTutorialCommand object
 */
public class DeleteTutorialCommandParser implements Parser<DeleteTutorialCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteTutorialCommand
     * and returns a DeleteTutorial object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteTutorialCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TUT_ID);

        if (!arePrefixesPresent(argMultimap, PREFIX_TUT_ID) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteTutorialCommand.MESSAGE_USAGE));
        }

        Tutorial tutorial = Tutorial.of(new TutName("random"),
                ParserUtil.parseTutorialId(argMultimap.getValue(PREFIX_TUT_ID).get()));

        return new DeleteTutorialCommand(tutorial);

    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
