package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INFO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddMeetUpCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.meetup.MeetUp;

/**
 * Parses input arguments and creates a new AddMeetUpCommand object
 */
public class AddMeetUpCommandParser implements Parser<AddMeetUpCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddMeetUpCommand
     * and returns an AddMeetUpCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddMeetUpCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_INFO, PREFIX_FROM, PREFIX_TO);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_INFO, PREFIX_FROM, PREFIX_TO)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMeetUpCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_INFO, PREFIX_FROM, PREFIX_TO);
        String name = argMultimap.getValue(PREFIX_NAME).get();
        String info = argMultimap.getValue(PREFIX_INFO).get();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime from = LocalDateTime.parse(argMultimap.getValue(PREFIX_FROM).get(), formatter);
        LocalDateTime to = LocalDateTime.parse(argMultimap.getValue(PREFIX_TO).get(), formatter);

        MeetUp meetUp = new MeetUp(name, info, from, to);

        return new AddMeetUpCommand(meetUp);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
