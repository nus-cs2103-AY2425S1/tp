package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.SetVolunteerHoursCommand.MESSAGE_NOT_EDITED;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

public class SetVolunteerHoursCommandParserTest {
    private SetVolunteerHoursCommandParser parser = new SetVolunteerHoursCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_COMMAND_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_NOT_EDITED);

        // no index and field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_COMMAND_FORMAT);
    }

}
