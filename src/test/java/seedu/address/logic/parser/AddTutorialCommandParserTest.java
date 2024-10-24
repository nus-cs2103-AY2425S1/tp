package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TUT_ID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TUT_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.TUT_ID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.TUT_NAME_DESC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
//import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
//import static seedu.address.testutil.TutUtil.TUT_SAMPLE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddTutCommand;
//import seedu.address.model.student.TutorialId;
import seedu.address.model.tut.Tutorial;

public class AddTutorialCommandParserTest {

    private final AddTutCommandParser parser = new AddTutCommandParser();
    /*
    @Test
    public void parse_allFieldsPresent_success() {
        Tut expectedTut = TUT_SAMPLE;

        assertParseSuccess(parser, " " + TUT_NAME_DESC + TUT_ID_DESC, new AddTutCommand(expectedTut));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // Example: If you have optional fields, test scenarios where they are missing
        Tut expectedTut = new Tut("CS2103T", new TutorialId("1001"));
        assertParseSuccess(parser, TUT_NAME_DESC + TUT_ID_DESC, new AddTutCommand(expectedTut));
    }
    */

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTutCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, TUT_ID_DESC, expectedMessage);

        // missing ID prefix
        assertParseFailure(parser, TUT_NAME_DESC, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid tutorial name
        assertParseFailure(parser, INVALID_TUT_NAME_DESC + TUT_ID_DESC, Tutorial.MESSAGE_NAME_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_TUT_NAME_DESC + INVALID_TUT_ID_DESC, Tutorial.MESSAGE_NAME_CONSTRAINTS);
    }
}
