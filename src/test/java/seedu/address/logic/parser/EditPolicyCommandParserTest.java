package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.POLICY_TYPE_DESC_LIFE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POLICY_TYPE_LIFE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditPolicyCommand;
import seedu.address.model.policy.LifePolicy;
import seedu.address.model.policy.PolicySet;

public class EditPolicyCommandParserTest {
    private final EditPolicyCommandParser parser = new EditPolicyCommandParser();
    private final LifePolicy life = new LifePolicy();

    // TODO: Change these testcases when the logic for UpdatePolicyCommandParser is created
    @Test
    public void parse_allFieldsPresent_success() {
        final PolicySet policies = new PolicySet();
        String userInput = INDEX_FIRST_PERSON.getOneBased() + POLICY_TYPE_DESC_LIFE;
        EditPolicyCommand expectedCommand = new EditPolicyCommand(INDEX_FIRST_PERSON, policies);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPolicyCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, "", expectedMessage);

        // no index
        assertParseFailure(parser, POLICY_TYPE_DESC_LIFE, expectedMessage);

        // no policies
        assertParseFailure(parser, String.valueOf(INDEX_FIRST_PERSON.getOneBased()), expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdatePolicyCommand.MESSAGE_USAGE);

        // invalid index
        assertParseFailure(parser, "foo" + VALID_POLICY_TYPE_LIFE, expectedMessage);

        // TODO: after implementing logic
        // invalid policy type
        // assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + INVALID_POLICY_TYPE_DESC,
        //         Policy.POLICY_TYPE_MESSAGE_CONSTRAINTS);
    }
}
