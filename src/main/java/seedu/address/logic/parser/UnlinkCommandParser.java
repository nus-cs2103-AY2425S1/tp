package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CHILD;

import seedu.address.logic.commands.UnlinkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parses input and creates an UnlinkCommand object.
 */
public class UnlinkCommandParser implements Parser<UnlinkCommand> {

    @Override
    public UnlinkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultiMap = ArgumentTokenizer.tokenize(args, PREFIX_CHILD);

        if (!argMultiMap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnlinkCommand.MESSAGE_USAGE));
        }

        Name name = argMultiMap.getValue(PREFIX_CHILD).map(Name::new)
                .orElseThrow(() -> new ParseException(String.format(
                        MESSAGE_INVALID_COMMAND_FORMAT, UnlinkCommand.MESSAGE_USAGE)));

        return new UnlinkCommand(name);
    }
}
