package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NRIC_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MEDCON_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.MEDCON_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEDCON_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEDCON_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddMedConCommand;
import seedu.address.model.person.MedCon;
import seedu.address.model.person.Nric;

public class AddMedConCommandParserTest {
    private final AddMedConCommandParser parser = new AddMedConCommandParser();

    @Test
    public void parse_allFieldsValid_success() {
        // For Amy with MedCon
        AddMedConCommand expectedCommandForAmy = new AddMedConCommand(new Nric(VALID_NRIC_AMY),
                new MedCon(VALID_MEDCON_AMY));
        assertParseSuccess(parser, NRIC_DESC_AMY + MEDCON_DESC_AMY, expectedCommandForAmy);

        // For Bob with MedCon
        AddMedConCommand expectedCommandForBob = new AddMedConCommand(new Nric(VALID_NRIC_BOB),
                new MedCon(VALID_MEDCON_BOB));
        assertParseSuccess(parser, NRIC_DESC_BOB + MEDCON_DESC_BOB, expectedCommandForBob);

        // For Amy without MedCon (MedCon is optional)
        AddMedConCommand expectedCommandForAmyNoMedCon = new AddMedConCommand(new Nric(VALID_NRIC_AMY), new MedCon(""));
        assertParseSuccess(parser, NRIC_DESC_AMY, expectedCommandForAmyNoMedCon);

        // For Bob without MedCon (MedCon is optional)
        AddMedConCommand expectedCommandForBobNoMedCon = new AddMedConCommand(new Nric(VALID_NRIC_BOB), new MedCon(""));
        assertParseSuccess(parser, NRIC_DESC_BOB, expectedCommandForBobNoMedCon);
    }

    @Test
    public void parse_allFieldsInvalid_failure() {
        // Invalid NRIC but valid MedCon
        assertParseFailure(parser, INVALID_NRIC_DESC + MEDCON_DESC_AMY, Nric.MESSAGE_CONSTRAINTS);

        // Invalid format for NRIC parameter, replaced with name instead
        assertParseFailure(parser, NAME_DESC_AMY + MEDCON_DESC_AMY, String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, AddMedConCommand.MESSAGE_USAGE));

    }

    @Test
    public void parse_compulsoryFieldsMissing_failure() {
        // Both NRIC and MedCon missing
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddMedConCommand.MESSAGE_USAGE));

        // NRIC parameter missing
        assertParseFailure(parser, MEDCON_DESC_AMY, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddMedConCommand.MESSAGE_USAGE));
    }
}

