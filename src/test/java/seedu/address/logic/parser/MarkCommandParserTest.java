package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NOTES_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.MarkCommand;
import seedu.address.model.contactrecord.ContactRecord;
import seedu.address.model.person.Nric;
import seedu.address.testutil.ContactRecordBuilder;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the MarkCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the MarkCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class MarkCommandParserTest {

    private MarkCommandParser parser = new MarkCommandParser();

    @Test
    public void parse_validIndex_returnsMarkCommand() {
        ContactRecord validRecord = new ContactRecordBuilder().withNotes(VALID_NOTES).build();
        assertParseSuccess(parser, "1" + NOTES_DESC,
                new MarkCommand(INDEX_FIRST_PERSON, validRecord));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validNric_returnsMarkCommand() {
        ContactRecord validRecord = new ContactRecordBuilder().withNotes(VALID_NOTES).build();
        assertParseSuccess(parser, VALID_NRIC_AMY + NOTES_DESC,
                new MarkCommand(new Nric(VALID_NRIC_AMY), validRecord));
    }

    @Test
    public void parse_emptyNotes_returnsMarkCommand() {
        ContactRecord validRecord = new ContactRecordBuilder().withNotes("").build();
        assertParseSuccess(parser, "1", new MarkCommand(INDEX_FIRST_PERSON, validRecord));
    }

    @Test
    public void parse_validDate_returnsMarkCommand() {
        ContactRecord validRecord = new ContactRecordBuilder().withDate(VALID_DATE).withNotes(VALID_NOTES).build();
        assertParseSuccess(parser, "1" + DATE_DESC + NOTES_DESC,
                new MarkCommand(INDEX_FIRST_PERSON, validRecord));
    }

    @Test
    public void parse_invalidDate_throwsParseException() {
        // invalid date format
        assertParseFailure(parser, "1" + INVALID_DATE_DESC,
                ContactRecord.MESSAGE_CONSTRAINTS);
    }
}
