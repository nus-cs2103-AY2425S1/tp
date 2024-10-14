package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEWTAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OLDTAG;

import seedu.address.logic.commands.RenameTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class RenameTagParser implements Parser<RenameTagCommand> {

    public RenameTagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_OLDTAG, PREFIX_NEWTAG);
        String oldTag = argMultimap.getValue(PREFIX_OLDTAG).orElseThrow(() -> new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RenameTagCommand.MESSAGE_USAGE)));
        String newTag = argMultimap.getValue(PREFIX_NEWTAG).orElseThrow(() -> new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RenameTagCommand.MESSAGE_USAGE)));
        return new RenameTagCommand(oldTag, newTag);
    }
}
