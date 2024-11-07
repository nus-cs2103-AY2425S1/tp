package careconnect.logic.parser;

import static careconnect.logic.parser.CliSyntax.PREFIX_DATE;
import static careconnect.logic.parser.CliSyntax.PREFIX_REMARK;
import static careconnect.logic.parser.CommandParserTestUtil.assertParseFailure;
import static careconnect.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static careconnect.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import careconnect.logic.Messages;
import careconnect.logic.commands.AddLogCommand;
import careconnect.logic.commands.CommandTestUtil;
import careconnect.model.log.Log;

public class AddLogCommandParserTest {
    private AddLogCommandParser parser = new AddLogCommandParser();

    @Test
    public void parse_optionalFieldsMissing_success() {
        assertParseSuccess(parser, CommandTestUtil.INDEX_FIRST_PERSON
                        + CommandTestUtil.LOG_DESC_REMARK,
                new AddLogCommand(INDEX_FIRST_PERSON, new Log("Meeting 1")));
    }

    @Test
    public void parse_repeated_failure() {
        String validAddLogString = CommandTestUtil.INDEX_FIRST_PERSON
                + CommandTestUtil.LOG_DESC_DATE
                + CommandTestUtil.LOG_DESC_REMARK;

        // multiple remarks
        assertParseFailure(parser, validAddLogString + CommandTestUtil.LOG_DESC_REMARK,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_REMARK));

        // multiple dates
        assertParseFailure(parser, validAddLogString + CommandTestUtil.LOG_DESC_DATE,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddLogCommand.MESSAGE_USAGE);

        // missing index
        assertParseFailure(parser, CommandTestUtil.LOG_DESC_DATE + CommandTestUtil.LOG_DESC_REMARK,
                expectedMessage);

        // missing remark
        assertParseFailure(parser, CommandTestUtil.INDEX_FIRST_PERSON + CommandTestUtil.LOG_DESC_DATE,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, CommandTestUtil.INDEX_FIRST_PERSON,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid date
        assertParseFailure(parser,
                CommandTestUtil.INDEX_FIRST_PERSON + CommandTestUtil.INVALID_DATE_DESC
                + CommandTestUtil.LOG_DESC_REMARK, Log.MESSAGE_CONSTRAINTS);

        // invalid remark
        assertParseFailure(parser, CommandTestUtil.INDEX_FIRST_PERSON + CommandTestUtil.LOG_DESC_DATE
                + CommandTestUtil.INVALID_REMARK_DESC, Log.MESSAGE_CONSTRAINTS);

        // invalid index
        assertParseFailure(parser,
                CommandTestUtil.INVALID_INDEX_DESC + CommandTestUtil.LOG_DESC_DATE
                        + CommandTestUtil.LOG_DESC_REMARK,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                        AddLogCommand.MESSAGE_USAGE));
    }


    @Test
    public void parse_futureDate_failure() {

        assertParseFailure(parser,
                CommandTestUtil.INDEX_FIRST_PERSON + CommandTestUtil.LOG_DESC_REMARK
                + CommandTestUtil.INVALID_DATE_FUTURE,
                String.format(Messages.MESSAGE_LOG_DATE_IN_FUTURE,
                        AddLogCommand.MESSAGE_USAGE));
    }

}
