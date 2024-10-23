package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.Set;

import seedu.address.logic.commands.DownloadCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;


/**
 * Parses input arguments and creates a new DownloadCommand object
 */
public class DownloadCommandParser implements Parser<DownloadCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DownloadCommand
     * and returns a DownloadCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DownloadCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DownloadCommand.MESSAGE_USAGE));
        }

        List<String> allValues = argMultimap.getAllValues(PREFIX_TAG);

        Set<Tag> tagList = ParserUtil.parseTags(allValues);

        return new DownloadCommand(tagList);
    }

}
