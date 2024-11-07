package bizbook.logic.parser;

import static bizbook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static bizbook.logic.parser.CliSyntax.PREFIX_TAG;
import static java.util.Objects.requireNonNull;

import bizbook.commons.core.index.Index;
import bizbook.commons.exceptions.IllegalValueException;
import bizbook.logic.commands.DeleteTagCommand;
import bizbook.logic.parser.exceptions.ParseException;
import bizbook.model.tag.Tag;

/**
 * Parses input arguments and returns a new DeleteTagCommand object
 */
public class DeleteTagCommandParser implements Parser<DeleteTagCommand> {

    /**
     * Parses the given {@code userInput} of arguments in the context of the DeleteTagCommand
     * and returns a DeleteTagCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteTagCommand parse(String userInput) throws ParseException {

        requireNonNull(userInput);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_TAG);

        Index personIndex;
        Tag tagToDelete;

        try {
            personIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
            String tagName = argMultimap.getValue(PREFIX_TAG).orElse("");
            tagToDelete = ParserUtil.parseTag(tagName);

        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteTagCommand.MESSAGE_USAGE), ive);
        }

        return new DeleteTagCommand(personIndex, tagToDelete);
    }

}
