package hallpointer.address.logic.parser;

import static hallpointer.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_NAME;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_ROOM;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_TAG;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_TELEGRAM;

import java.util.Set;
import java.util.stream.Stream;

import hallpointer.address.logic.commands.AddCommand;
import hallpointer.address.logic.parser.exceptions.ParseException;
import hallpointer.address.model.member.Member;
import hallpointer.address.model.member.Name;
import hallpointer.address.model.member.Room;
import hallpointer.address.model.member.Telegram;
import hallpointer.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TELEGRAM, PREFIX_ROOM, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ROOM, PREFIX_TELEGRAM)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_TELEGRAM, PREFIX_ROOM);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Telegram telegram = ParserUtil.parseTelegram(
                argMultimap.getValue(PREFIX_TELEGRAM).get());
        Room room = ParserUtil.parseRoom(argMultimap.getValue(PREFIX_ROOM).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Member member = new Member(name, telegram, room, tagList);

        return new AddCommand(member);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
