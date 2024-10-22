package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ABSENT_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ABSENT_REASON;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddAttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.AbsentDate;
import seedu.address.model.person.AbsentReason;


public class AddAttendanceCommandParser implements Parser<AddAttendanceCommand>{
    public AddAttendanceCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ABSENT_DATE, PREFIX_ABSENT_REASON);

        if (!argMultimap.getValue(PREFIX_ABSENT_DATE).isPresent() ||
                argMultimap.getValue(PREFIX_ABSENT_DATE).get().trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddAttendanceCommand.MESSAGE_USAGE));
        }

        Index index;
        AbsentDate absentDate;
        AbsentReason absentReason;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            absentDate = ParserUtil.parseAbsentDate(argMultimap.getValue(PREFIX_ABSENT_DATE).get());
            if (argMultimap.getValue(PREFIX_ABSENT_REASON).isPresent()) {
                absentReason = ParserUtil.parseAbsentReason(argMultimap.getValue(PREFIX_ABSENT_REASON).get());
            } else {
                absentReason = new AbsentReason("");
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddAttendanceCommand.MESSAGE_USAGE), ive);
        }

        return new AddAttendanceCommand(index, absentDate, absentReason);
    }
}
