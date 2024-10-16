package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEWTAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OLDTAG;
import static seedu.address.model.tag.Tag.MESSAGE_CONSTRAINTS;
import static seedu.address.model.tag.Tag.isValidTagName;

import seedu.address.logic.commands.RenameTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RenameTagCommand object
 */
public class RenameTagParser implements Parser<RenameTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RenameTagCommand
     * and returns an RenameTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RenameTagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_OLDTAG, PREFIX_NEWTAG);
        String oldTag = argMultimap.getValue(PREFIX_OLDTAG).orElseThrow(() -> new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RenameTagCommand.MESSAGE_USAGE)));
        String newTag = argMultimap.getValue(PREFIX_NEWTAG).orElseThrow(() -> new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RenameTagCommand.MESSAGE_USAGE)));
        if (!isValidTagName(newTag)) {
            throw new ParseException(MESSAGE_CONSTRAINTS);
        }
        return new RenameTagCommand(oldTag, newTag);
    }
}
