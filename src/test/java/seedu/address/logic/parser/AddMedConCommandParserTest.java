package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_CONSTRAINTS_LENGTH;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MEDCON_DESC;
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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddMedConCommand;
import seedu.address.model.person.MedCon;
import seedu.address.model.person.Nric;

public class AddMedConCommandParserTest {
    private final AddMedConCommandParser parser = new AddMedConCommandParser();

    @Test
    public void parse_allFieldsValid_success() {
        // For Amy with one MedCon
        Set<MedCon> medConsAmy = new HashSet<>();
        medConsAmy.add(new MedCon(VALID_MEDCON_AMY));
        AddMedConCommand expectedCommandForAmy = new AddMedConCommand(new Nric(VALID_NRIC_AMY), medConsAmy);
        assertParseSuccess(parser, NRIC_DESC_AMY + MEDCON_DESC_AMY, expectedCommandForAmy);

        // For Bob with one MedCon
        Set<MedCon> medConsBob = new HashSet<>();
        medConsBob.add(new MedCon(VALID_MEDCON_BOB));
        AddMedConCommand expectedCommandForBob = new AddMedConCommand(new Nric(VALID_NRIC_BOB), medConsBob);
        assertParseSuccess(parser, NRIC_DESC_BOB + MEDCON_DESC_BOB, expectedCommandForBob);

        // For Amy with multiple MedCons
        Set<MedCon> multipleMedConsAmy = new HashSet<>();
        multipleMedConsAmy.add(new MedCon(VALID_MEDCON_AMY));
        multipleMedConsAmy.add(new MedCon(VALID_MEDCON_BOB));
        AddMedConCommand expectedCommandForAmyMultiple = new AddMedConCommand(new Nric(VALID_NRIC_AMY),
                multipleMedConsAmy);
        assertParseSuccess(parser, NRIC_DESC_AMY + MEDCON_DESC_AMY + MEDCON_DESC_BOB,
                expectedCommandForAmyMultiple);

        // For Amy without MedCon (MedCon is optional)
        Set<MedCon> noMedCons = Collections.emptySet();
        AddMedConCommand expectedCommandForAmyNoMedCon = new AddMedConCommand(new Nric(VALID_NRIC_AMY), noMedCons);
        assertParseSuccess(parser, NRIC_DESC_AMY, expectedCommandForAmyNoMedCon);

        // For Bob without MedCon (MedCon is optional)
        AddMedConCommand expectedCommandForBobNoMedCon = new AddMedConCommand(new Nric(VALID_NRIC_BOB), noMedCons);
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


    @Test
    public void parse_medConLengthGreaterThan45Characters_failure() {
        // Medical condition exceeds 45 characters
        String input = NRIC_DESC_AMY + INVALID_MEDCON_DESC;

        assertParseFailure(parser, input, MESSAGE_CONSTRAINTS_LENGTH);
    }

}

