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

        if (!argMultiMap.getPreamble().isEmpty() || argMultiMap.getValue(PREFIX_CHILD).isEmpty()
                || argMultiMap.getValue(PREFIX_PARENT).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkCommand.MESSAGE_USAGE));
        }
        argMultiMap.verifyNoDuplicatePrefixesFor(PREFIX_CHILD, PREFIX_PARENT);

        Name child = ParserUtil.parseName(argMultiMap.getValue(PREFIX_CHILD).get());
        Name parent = ParserUtil.parseName(argMultiMap.getValue(PREFIX_PARENT).get());

        return new LinkCommand(child, parent);
    }
}
