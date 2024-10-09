package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_POLICY_TYPE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.POLICY_TYPE_DESC_HEALTH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POLICY_TYPE_HEALTH;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddPolicyCommand;
import seedu.address.model.policy.HealthPolicy;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyMap;

public class AddPolicyCommandParserTest {
    private final AddPolicyCommandParser parser = new AddPolicyCommandParser();
    private final HealthPolicy health = new HealthPolicy();

    @Test
    public void parse_allFieldsPresent_success() {
        final PolicyMap policies = new PolicyMap();
        policies.add(health);
        String userInput = INDEX_FIRST_PERSON.getOneBased() + POLICY_TYPE_DESC_HEALTH;
        AddPolicyCommand expectedCommand = new AddPolicyCommand(INDEX_FIRST_PERSON, policies);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPolicyCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, "", expectedMessage);

        // no index
        assertParseFailure(parser, POLICY_TYPE_DESC_HEALTH, expectedMessage);

        // no policies
        assertParseFailure(parser, String.valueOf(INDEX_FIRST_PERSON.getOneBased()), expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPolicyCommand.MESSAGE_USAGE);

        // invalid index
        assertParseFailure(parser, "foo" + VALID_POLICY_TYPE_HEALTH, expectedMessage);

        // invalid policy type
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + INVALID_POLICY_TYPE_DESC,
                Policy.MESSAGE_CONSTRAINTS);
    }
}
