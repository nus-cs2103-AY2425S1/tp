package seedu.address.logic.parser;

import seedu.address.logic.commands.AddLogCommand;
import seedu.address.logic.commands.AddLogEntryCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.log.AppointmentDate;
import seedu.address.model.person.IdentityNumber;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IDENTITY_NUMBER;

public class AddLogEntryCommandParser implements Parser<AddLogEntryCommand> {
    @Override
    public AddLogEntryCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_IDENTITY_NUMBER, PREFIX_DATE);

        if (argMultimap.getValue(PREFIX_IDENTITY_NUMBER).isEmpty() || argMultimap.getValue(PREFIX_DATE).isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLogCommand.MESSAGE_USAGE));
        }

        IdentityNumber identityNumber = ParserUtil.parseIdentityNumber(
                argMultimap.getValue(PREFIX_IDENTITY_NUMBER).get());

        AppointmentDate appointmentDate = new AppointmentDate(
                argMultimap.getValue(PREFIX_DATE).get());

        return new AddLogEntryCommand(identityNumber, appointmentDate);
    }
}
