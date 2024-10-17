package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_POLICY_TYPE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.POLICY_TYPE_DESC_HEALTH;
import static seedu.address.logic.commands.CommandTestUtil.POLICY_TYPE_DESC_LIFE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddPolicyCommand;
import seedu.address.model.policy.HealthPolicy;
import seedu.address.model.policy.LifePolicy;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicySet;

public class AddPolicyCommandParserTest {
    private final AddPolicyCommandParser parser = new AddPolicyCommandParser();
    private final HealthPolicy health = new HealthPolicy();
    private final LifePolicy life = new LifePolicy();

    @Test
    public void parse_allFieldsPresent_success() {
        // Single policy
        final PolicySet policies = new PolicySet();
        policies.add(health);
        String userInput = INDEX_FIRST_PERSON.getOneBased() + POLICY_TYPE_DESC_HEALTH;
        AddPolicyCommand expectedCommand = new AddPolicyCommand(INDEX_FIRST_PERSON, policies);
        assertParseSuccess(parser, userInput, expectedCommand);

        // Multiple policies
        final PolicySet multiplePolicies = new PolicySet();
        multiplePolicies.add(health);
        multiplePolicies.add(life);
        userInput = INDEX_FIRST_PERSON.getOneBased() + POLICY_TYPE_DESC_HEALTH + POLICY_TYPE_DESC_LIFE;
        expectedCommand = new AddPolicyCommand(INDEX_FIRST_PERSON, multiplePolicies);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPolicyCommand.MESSAGE_USAGE);

        // No input at all
        assertParseFailure(parser, "", expectedMessage);

        // Missing index
        assertParseFailure(parser, POLICY_TYPE_DESC_HEALTH, expectedMessage);

        // Missing policy type
        assertParseFailure(parser, String.valueOf(INDEX_FIRST_PERSON.getOneBased()), expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPolicyCommand.MESSAGE_USAGE);

        // Invalid index (non-numeric input)
        assertParseFailure(parser, "foo" + POLICY_TYPE_DESC_HEALTH, expectedMessage);

        // Invalid policy type
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + INVALID_POLICY_TYPE_DESC,
                Policy.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_multipleInvalidValues_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPolicyCommand.MESSAGE_USAGE);

        // Invalid index and invalid policy type
        assertParseFailure(parser, "foo" + INVALID_POLICY_TYPE_DESC, expectedMessage);
    }

    @Test
    public void parse_validIndexAndInvalidPolicyType_failure() {
        // Valid index but invalid policy type
        assertParseFailure(parser, INDEX_SECOND_PERSON.getOneBased() + INVALID_POLICY_TYPE_DESC,
                Policy.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_validPolicyAndInvalidIndex_failure() {
        // Valid policy but invalid index
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPolicyCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "foo" + POLICY_TYPE_DESC_HEALTH, expectedMessage);
    }

    @Test
    public void parse_validIndexAndMultiplePolicyTypes_success() {
        final PolicySet policies = new PolicySet();
        policies.add(health);
        policies.add(life);

        String userInput = INDEX_FIRST_PERSON.getOneBased() + POLICY_TYPE_DESC_HEALTH + POLICY_TYPE_DESC_LIFE;
        AddPolicyCommand expectedCommand = new AddPolicyCommand(INDEX_FIRST_PERSON, policies);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validIndexButEmptyPolicySet_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPolicyCommand.MESSAGE_USAGE);

        // Index present but no policies
        assertParseFailure(parser, String.valueOf(INDEX_FIRST_PERSON.getOneBased()), expectedMessage);
    }
}
