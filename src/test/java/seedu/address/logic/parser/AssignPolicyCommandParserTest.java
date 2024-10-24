package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_POLICY_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ASSIGN_POLICY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASSIGN_POLICY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INSURANCE_AMOUNT_DUE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INSURANCE_PAYMENT_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POLICY_NAME_LIFE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AssignPolicyCommand;
import seedu.address.model.person.Payment;
import seedu.address.model.person.Policy;


public class AssignPolicyCommandParserTest {

    private AssignPolicyCommandParser parser = new AssignPolicyCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_POLICY_NAME_LIFE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignPolicyCommand.MESSAGE_USAGE));

        //no policy details specified
        assertParseFailure(parser, "1", String.format(MESSAGE_INVALID_POLICY_FORMAT + "\n"
                + AssignPolicyCommand.MESSAGE_USAGE));

        //invalid insurance field
        assertParseFailure(parser, "1" + INVALID_ASSIGN_POLICY, String.format(Payment.MESSAGE_CONSTRAINTS + "\n"
                + AssignPolicyCommand.MESSAGE_USAGE));
    }

    @Test
    public void assignPolicySuccess() {
        Policy policy = new Policy(VALID_POLICY_NAME_LIFE, VALID_DATE_1, VALID_DATE_2,
                VALID_INSURANCE_PAYMENT_DATE + " " + VALID_INSURANCE_AMOUNT_DUE);
        Index targetIndex = Index.fromZeroBased(0);
        AssignPolicyCommand assignPolicyCommand = new AssignPolicyCommand(targetIndex, policy);
        assertParseSuccess(parser, "1" + VALID_ASSIGN_POLICY, assignPolicyCommand);
    }

}
