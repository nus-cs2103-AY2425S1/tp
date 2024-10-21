package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddScheduleCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddScheduleCommand object.
 */
public class AddScheduleCommandParser implements Parser<AddScheduleCommand> {

    @Override
    public AddScheduleCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_CONTACT, PREFIX_NAME, PREFIX_DATE, PREFIX_TIME);

        if (!arePrefixesPresent(argMultimap, PREFIX_CONTACT, PREFIX_NAME, PREFIX_DATE, PREFIX_TIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddScheduleCommand.MESSAGE_USAGE));
        }

        try {
            // input index must be one-based
            List<Index> contactIndexes = argMultimap.getAllValues(PREFIX_CONTACT).stream()
                    .map(s -> {
                        try {
                            return ParserUtil.parseIndex(s);
                        } catch (Exception e) {
                            // you see nothing :>
                            throw new RuntimeException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, 
                                    AddScheduleCommand.MESSAGE_USAGE));
                        }
                    }).collect(Collectors.toList());

            // name of the schedule
            String name = argMultimap.getValue(PREFIX_NAME).get().trim();

            // Parse and validate the date
            LocalDate date;
            try {
                date = LocalDate.parse(argMultimap.getValue(PREFIX_DATE).get(),
                        DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            } catch (DateTimeParseException dtpe) {
                throw new ParseException(AddScheduleCommand.MESSAGE_INVALID_DATE);
            }

            // Parse and validate the time
            LocalTime time;
            try {
                time = LocalTime.parse(argMultimap.getValue(PREFIX_TIME).get(),
                        DateTimeFormatter.ofPattern("HHmm"));
            } catch (DateTimeParseException dtpe) {
                throw new ParseException(AddScheduleCommand.MESSAGE_INVALID_TIME);
            }

            // Convert contactIndex to its zero-based integer value if needed.
            return new AddScheduleCommand(contactIndexes, name, date, time);
        } catch (Exception e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddScheduleCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Returns true if all specified prefixes are present in the given ArgumentMultimap.
     */
    private boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        for (Prefix prefix : prefixes) {
            if (!argumentMultimap.getValue(prefix).isPresent()) {
                return false;
            }
        }
        return true;
    }
}
