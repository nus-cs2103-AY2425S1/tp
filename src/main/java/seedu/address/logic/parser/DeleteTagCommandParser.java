package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new DeleteTagCommand object
 */
public class DeleteTagCommandParser implements Parser<DeleteTagCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteTagCommand
     * and returns a DeleteTagCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public DeleteTagCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        if (argumentMultimap.getPreamble() == null || argumentMultimap.getAllValues(PREFIX_TAG).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(argumentMultimap.getPreamble());
        Set<Tag> tagsToDelete = parseTagsToDelete(argumentMultimap.getAllValues(PREFIX_TAG));


        return new DeleteTagCommand(index, tagsToDelete);
    }

    /**
     * parses the list to a set of tags
     * @param list of strings that have the prefix 't/'
     * @return set of tags to be deleted
     * @throws ParseException
     */
    private Set<Tag> parseTagsToDelete(List<String> list) throws ParseException {
        assert list != null;
        assert !list.isEmpty();

        return ParserUtil.parseTags(list);
    }
}
