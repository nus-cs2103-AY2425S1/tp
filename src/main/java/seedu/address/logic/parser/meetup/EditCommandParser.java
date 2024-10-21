package seedu.address.logic.parser.meetup;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INFO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.meetup.EditCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_INFO, PREFIX_FROM, PREFIX_TO);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_INFO, PREFIX_FROM, PREFIX_TO);

        EditCommand.EditMeetUpDescriptor editMeetUpDescriptor = new EditCommand.EditMeetUpDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editMeetUpDescriptor.setMeetUpName(ParserUtil.parseMeetUpName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_INFO).isPresent()) {
            editMeetUpDescriptor.setMeetUpInfo(ParserUtil.parseMeetUpInfo(argMultimap.getValue(PREFIX_INFO).get()));
        }
        if (argMultimap.getValue(PREFIX_FROM).isPresent()) {
            editMeetUpDescriptor.setMeetUpFrom(ParserUtil.parseMeetUpFrom(argMultimap.getValue(PREFIX_FROM).get()));
        }
        if (argMultimap.getValue(PREFIX_TO).isPresent()) {
            editMeetUpDescriptor.setMeetUpTo(ParserUtil.parseMeetUpTo(argMultimap.getValue(PREFIX_TO).get()));
        }

        if (!editMeetUpDescriptor.isAnyMeetUpFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_MEETUP_NOT_EDITED);
        }

        return new EditCommand(index, editMeetUpDescriptor);
    }
}
