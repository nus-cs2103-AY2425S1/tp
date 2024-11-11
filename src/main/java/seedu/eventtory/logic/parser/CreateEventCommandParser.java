package seedu.eventtory.logic.parser;

import static seedu.eventtory.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.eventtory.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.eventtory.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.eventtory.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.eventtory.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.eventtory.logic.commands.CreateEventCommand;
import seedu.eventtory.logic.parser.exceptions.ParseException;
import seedu.eventtory.model.commons.name.Name;
import seedu.eventtory.model.commons.tag.Tag;
import seedu.eventtory.model.event.Date;
import seedu.eventtory.model.event.Event;

/**
 * Parses input arguments and creates a new CreateEventCommand object
 */
public class CreateEventCommandParser implements Parser<CreateEventCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the CreateEventCommand
     * and returns an CreateEventCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public CreateEventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_EVENT, PREFIX_NAME, PREFIX_DATE,
                PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_EVENT, PREFIX_NAME, PREFIX_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateEventCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_DATE);

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Event event = new Event(name, date, tagList);

        return new CreateEventCommand(event);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values
     * in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
