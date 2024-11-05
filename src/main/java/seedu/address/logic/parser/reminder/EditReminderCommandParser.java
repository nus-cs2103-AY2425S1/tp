package seedu.address.logic.parser.reminder;
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.reminder.EditReminderCommand;
import seedu.address.logic.commands.reminder.EditReminderCommand.EditReminderFields;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input to create a new {@code EditReminderCommand}.
 */
public class EditReminderCommandParser implements Parser<EditReminderCommand> {
    public EditReminderCommandParser() {
    }

    @Override
    public EditReminderCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATE_TIME, PREFIX_DESCRIPTION);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditReminderCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_DATE_TIME, PREFIX_DESCRIPTION);

        EditReminderFields editReminderFields = new EditReminderFields();

        if (argMultimap.getValue(PREFIX_DATE_TIME).isPresent()) {
            editReminderFields.setDateTime(ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_DATE_TIME).get()));
        }
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editReminderFields.setDescription(ParserUtil.parseReminderDescription(
                    argMultimap.getValue(PREFIX_DESCRIPTION).get()));
        }

        if (!editReminderFields.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditReminderCommand(index, editReminderFields);
    }
}
