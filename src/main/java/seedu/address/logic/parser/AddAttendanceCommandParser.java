package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ABSENT_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ABSENT_REASON;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddAttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.AbsentDate;
import seedu.address.model.person.AbsentReason;

/**
 * Parses input arguments and creates a new AddAttendanceCommand object
 */
public class AddAttendanceCommandParser implements Parser<AddAttendanceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddAttendanceCommand
     * and returns an AddAttendanceCommand object for execution.
     *
     * @param args The input arguments provided by the user.
     * @return An AddAttendanceCommand object that contains the parsed index, absent date, and absent reason.
     * @throws ParseException if the user input does not conform to the expected format,
     *         including missing or invalid absent date or reason.
     */
    public AddAttendanceCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ABSENT_DATE, PREFIX_ABSENT_REASON);

        if (!argMultimap.getValue(PREFIX_ABSENT_DATE).isPresent()
                || argMultimap.getValue(PREFIX_ABSENT_DATE).get().trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddAttendanceCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
        AbsentDate absentDate = ParserUtil.parseAbsentDate(argMultimap.getValue(PREFIX_ABSENT_DATE).get());
        AbsentReason absentReason;
        if (argMultimap.getValue(PREFIX_ABSENT_REASON).isPresent()) {
            absentReason = ParserUtil.parseAbsentReason(argMultimap.getValue(PREFIX_ABSENT_REASON).get());
        } else {
            absentReason = new AbsentReason("");
        }

        return new AddAttendanceCommand(index, absentDate, absentReason);
    }
}
