package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditMeetingCommand;
import seedu.address.testutil.EditMeetingDescriptorBuilder;

public class EditMeetingCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditMeetingCommand.MESSAGE_USAGE);

    private final EditMeetingCommandParser parser = new EditMeetingCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        String userInput = "1 " + PREFIX_NAME + "Alice Pauline "
                + PREFIX_LOCATION + "Room 101 "
                + PREFIX_START_TIME + "09-10-2024 10:00 "
                + PREFIX_END_TIME + "09-10-2024 11:00";

        EditMeetingCommand.EditMeetingDescriptor descriptor = new EditMeetingDescriptorBuilder()
                .withName("Alice Pauline")
                .withLocation("Room 101")
                .withStartTime("09-10-2024 10:00")
                .withEndTime("09-10-2024 11:00")
                .build();
        EditMeetingCommand expectedCommand = new EditMeetingCommand(Index.fromOneBased(1), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValue_failure() {
        // Invalid start time
        String userInput = "1 " + PREFIX_NAME + "Benson Meier "
                + PREFIX_START_TIME + "invalid-time "
                + PREFIX_END_TIME + "09-10-2024 10:00";

        assertParseFailure(parser, userInput, "Invalid time format. Please use the correct format: 'dd-MM-yyyy HH:mm'");

        // Invalid end time
        userInput = "1 " + PREFIX_NAME + "Benson Meier "
                + PREFIX_START_TIME + "09-10-2024 10:00 "
                + PREFIX_END_TIME + "invalid-time";

        assertParseFailure(parser, userInput, "Invalid time format. Please use the correct format: 'dd-MM-yyyy HH:mm'");
    }

    @Test
    public void parse_missingParts_failure() {
        // No index specified
        String userInput = PREFIX_NAME + "Team Sync " + PREFIX_LOCATION + "Office";
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);

        // No fields given
        assertParseFailure(parser, "1", EditMeetingCommand.MESSAGE_MEETING_NOT_EDITED);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // Invalid index
        String userInput = "invalid " + PREFIX_NAME + "Woohoo";
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);

        // Negative index
        userInput = "-1 " + PREFIX_NAME + "Lols";
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }
}

