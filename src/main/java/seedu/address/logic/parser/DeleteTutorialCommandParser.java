package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

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

        try {
            String trimmed = args.trim();

            if (trimmed.isEmpty() || trimmed.isBlank()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTutorialCommand.MESSAGE_USAGE)
                );
            }

            Tutorial tutorial = Tutorial.of(new TutName("random"), ParserUtil.parseTutorialId(args));
            return new DeleteTutorialCommand(tutorial);

        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTutorialCommand.MESSAGE_USAGE), pe
            );
        }
    }
}
