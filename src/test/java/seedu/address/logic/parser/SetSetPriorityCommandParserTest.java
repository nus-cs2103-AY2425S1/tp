package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NRIC_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRIORITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_BOB;
import static seedu.address.logic.commands.SetPriorityCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.SetPriorityCommand;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Priority;

public class SetSetPriorityCommandParserTest {
    private final SetPriorityCommandParser parser = new SetPriorityCommandParser();

    @Test
    public void parse_allFieldsValid_success() {
        // For Amy
        SetPriorityCommand expectedCommandForAmy = new SetPriorityCommand(new Nric(VALID_NRIC_AMY),
                new Priority(VALID_PRIORITY_AMY));
        assertParseSuccess(parser, NRIC_DESC_AMY + PRIORITY_DESC_AMY, expectedCommandForAmy);

        // For Bob
        SetPriorityCommand expectedCommandForBob = new SetPriorityCommand(new Nric(VALID_NRIC_BOB),
                new Priority(VALID_PRIORITY_BOB));
        assertParseSuccess(parser, NRIC_DESC_BOB + PRIORITY_DESC_BOB, expectedCommandForBob);

        // Reverse Position of Nric and Priority for Amy
        SetPriorityCommand expectedCommandForAmyReverseParameters = new SetPriorityCommand(new Nric(VALID_NRIC_AMY),
                new Priority(VALID_PRIORITY_AMY));
        assertParseSuccess(parser, PRIORITY_DESC_AMY + NRIC_DESC_AMY, expectedCommandForAmyReverseParameters);

        // Reverse position of Nric and Priority for Bob
        SetPriorityCommand expectedCommandForBobReverseParameters = new SetPriorityCommand(new Nric(VALID_NRIC_BOB),
                new Priority(VALID_PRIORITY_BOB));
        assertParseSuccess(parser, PRIORITY_DESC_BOB + NRIC_DESC_BOB, expectedCommandForBobReverseParameters);
    }

    @Test
    public void parse_allFieldsInvalid_failure() {
        // Both Nric and Priority invalid
        assertParseFailure(parser, INVALID_NRIC_DESC + INVALID_PRIORITY_DESC, Nric.MESSAGE_CONSTRAINTS);

        // Invalid Nric but valid Priority
        assertParseFailure(parser, INVALID_NRIC_DESC + PRIORITY_DESC_AMY, Nric.MESSAGE_CONSTRAINTS);

        // Valid Nric but invalid Priority
        assertParseFailure(parser, NRIC_DESC_AMY + INVALID_PRIORITY_DESC, Priority.MESSAGE_CONSTRAINTS);

        // Invalid format for Nric parameter, replaced with name instead
        assertParseFailure(parser, NAME_DESC_AMY + PRIORITY_DESC_AMY, String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

        // Invalid format for Priority parameter, replaced with phone instead
        assertParseFailure(parser, NRIC_DESC_AMY + PHONE_DESC_AMY, String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_compulsoryFieldsMissing_failure() {
        // Both parameters missing
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

        // Nric parameter missing
        assertParseFailure(parser, PRIORITY_DESC_AMY, String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

        // Priority parameter missing
        assertParseFailure(parser, NRIC_DESC_AMY, String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleParameter_failure() {
        // Multiple NRIC input
        assertParseFailure(parser, NRIC_DESC_AMY + NRIC_DESC_BOB + PRIORITY_DESC_AMY,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NRIC));

        // Multiple Priority input
        assertParseFailure(parser, NRIC_DESC_AMY + PRIORITY_DESC_AMY + PRIORITY_DESC_BOB,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PRIORITY));

        // Multiple NRIC and Priority input
        assertParseFailure(parser, NRIC_DESC_AMY + NRIC_DESC_BOB + PRIORITY_DESC_AMY
                + PRIORITY_DESC_BOB, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NRIC, PREFIX_PRIORITY));
    }
}
