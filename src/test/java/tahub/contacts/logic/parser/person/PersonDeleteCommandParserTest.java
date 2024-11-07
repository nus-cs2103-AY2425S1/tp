package tahub.contacts.logic.parser.person;

import static tahub.contacts.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tahub.contacts.logic.commands.CommandTestUtil.COURSE_CODE_DESC;
import static tahub.contacts.logic.commands.CommandTestUtil.MATRICULATION_NUMBER_DESC_BOB;
import static tahub.contacts.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static tahub.contacts.logic.commands.CommandTestUtil.VALID_MATRICULATION_NUMBER_BOB;
import static tahub.contacts.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tahub.contacts.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static tahub.contacts.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import tahub.contacts.logic.commands.person.PersonDeleteCommand;
import tahub.contacts.model.person.MatriculationNumber;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class PersonDeleteCommandParserTest {

    private PersonDeleteCommandParser parser = new PersonDeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        MatriculationNumber validMatriculationNumber = new MatriculationNumber(VALID_MATRICULATION_NUMBER_BOB);
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + MATRICULATION_NUMBER_DESC_BOB,
                new PersonDeleteCommand(validMatriculationNumber));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PersonDeleteCommand.MESSAGE_USAGE));
    }
}
