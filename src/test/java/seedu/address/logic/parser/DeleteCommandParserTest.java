package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_INDEX;
import static seedu.address.logic.Messages.MESSAGE_MISSING_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_COMPANY;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteCompanyCommand;
import seedu.address.logic.commands.DeleteContactCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside the DeleteContactCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteContactCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "contact 1", new DeleteContactCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_validCompanyArgs_returnsDeleteCompanyCommand() {
        assertParseSuccess(parser, "company 1", new DeleteCompanyCommand(INDEX_FIRST_COMPANY));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "contact", String.format(MESSAGE_MISSING_INDEX,
                DeleteContactCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "contact a", MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_invalidArgsCompany_throwsParseException() {
        assertParseFailure(parser, "company", MESSAGE_MISSING_INDEX); // No index provided
        assertParseFailure(parser, "company a", MESSAGE_INVALID_INDEX); // Non-numeric index
    }
}
