package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CHILD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENT;

import seedu.address.logic.commands.LinkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new {@code LinkCommand} object.
 */
public class LinkCommandParser implements Parser<LinkCommand> {

    @Override
    public LinkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultiMap = ArgumentTokenizer.tokenize(args, PREFIX_CHILD, PREFIX_PARENT);

        if (!argMultiMap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkCommand.MESSAGE_USAGE));
        }
        argMultiMap.verifyNoDuplicatePrefixesFor(PREFIX_CHILD, PREFIX_PARENT);

        Name child = argMultiMap.getValue(PREFIX_CHILD).map(Name::new)
                .orElseThrow(() -> new ParseException(String.format(
                        MESSAGE_INVALID_COMMAND_FORMAT, LinkCommand.MESSAGE_USAGE)));
        Name parent = argMultiMap.getValue(PREFIX_PARENT).map(Name::new)
                .orElseThrow(() -> new ParseException(String.format(
                        MESSAGE_INVALID_COMMAND_FORMAT, LinkCommand.MESSAGE_USAGE)));

        return new LinkCommand(child, parent);
    }
}
