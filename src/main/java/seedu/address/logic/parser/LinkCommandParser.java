package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;

import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.personcommands.LinkPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;
import seedu.address.model.types.common.Address;
import seedu.address.model.types.common.DateTime;
import seedu.address.model.types.common.Name;
import seedu.address.model.types.event.Event;

/**
 * Parses input arguments and creates a new LinkPersonCommand object
 */
public class LinkCommandParser implements Parser<LinkPersonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LinkPersonCommand
     * and returns a LinkPersonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LinkPersonCommand parse(ModelType model, String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EVENT);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    LinkPersonCommand.MESSAGE_USAGE),
                    pe);
        }

        if (!argMultimap.getValue(PREFIX_EVENT).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkPersonCommand.MESSAGE_USAGE));
        }

        Name eventName = ParserUtil.parseName(argMultimap.getValue(PREFIX_EVENT).get());

        //Is there a better way to implement this? Please comment
        Event event = new Event(
                eventName,
                new Address("temp"),
                new DateTime("2024-10-15 14:30"),
                Set.of(new Tag("temp")));

        return new LinkPersonCommand(index, event);
    }
}
