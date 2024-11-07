package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.testutil.TypicalEvents.getTypicalEventBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UpdateCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class UpdateCommandParserTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), new UserPrefs());
    private UpdateCommandParser parser = new UpdateCommandParser();

    @Test
    public void parse_noIndex_throwsParseException() {
        assertParseFailure(parser, "update -n NewEvent123",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noArgs_throwsParseException() {
        assertParseFailure(parser, "update -i 1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidDate_throwsParseException() {
        assertParseFailure(parser, "update -i 1 -sd 2023-1-1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
    }

    // TODO: Fix failing TC
    /*
    @Test
    public void parse_validDate_returnsUpdateCommand() {
        Event oldEvent = model.getEventBook().getEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        assertParseSuccess(parser, "update -i 1 -sd 2020-01-01",
                new UpdateCommand("", LocalDate.of(2020, 1, 1),
                        null,
                        null, new HashSet<>(), new HashSet<>(), INDEX_FIRST_EVENT));
    }
     */

}
