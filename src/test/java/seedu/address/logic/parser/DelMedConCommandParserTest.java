package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_CONSTRAINTS_LENGTH;
import static seedu.address.logic.Messages.MESSAGE_EMPTY_FIELD;
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
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDCON;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DelMedConCommand;
import seedu.address.model.person.MedCon;
import seedu.address.model.person.Nric;

public class DelMedConCommandParserTest {
    private final DelMedConCommandParser parser = new DelMedConCommandParser();

    @Test
    public void parse_allFieldsValid_success() {
        // For Amy with one MedCon
        Set<MedCon> medConsAmy = new HashSet<>();
        medConsAmy.add(new MedCon(VALID_MEDCON_AMY));
        DelMedConCommand expectedCommandForAmy = new DelMedConCommand(new Nric(VALID_NRIC_AMY), medConsAmy);
        assertParseSuccess(parser, NRIC_DESC_AMY + MEDCON_DESC_AMY, expectedCommandForAmy);

        // For Bob with one MedCon
        Set<MedCon> medConsBob = new HashSet<>();
        medConsBob.add(new MedCon(VALID_MEDCON_BOB));
        DelMedConCommand expectedCommandForBob = new DelMedConCommand(new Nric(VALID_NRIC_BOB), medConsBob);
        assertParseSuccess(parser, NRIC_DESC_BOB + MEDCON_DESC_BOB, expectedCommandForBob);

    }

    @Test
    public void parse_allFieldsInvalid_failure() {
        // Invalid NRIC but valid MedCon
        assertParseFailure(parser, INVALID_NRIC_DESC + MEDCON_DESC_AMY, Nric.MESSAGE_CONSTRAINTS);

        // Invalid format for NRIC parameter, replaced with name instead
        assertParseFailure(parser, NAME_DESC_AMY + MEDCON_DESC_AMY, String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, DelMedConCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_compulsoryFieldsMissing_failure() {
        // Both NRIC and MedCon missing
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DelMedConCommand.MESSAGE_USAGE));

        // NRIC parameter missing
        assertParseFailure(parser, MEDCON_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DelMedConCommand.MESSAGE_USAGE));

        // MedCon parameter missing
        assertParseFailure(parser, NRIC_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DelMedConCommand.MESSAGE_USAGE));

        // MedCon prefix present, but no value
        assertParseFailure(parser, NRIC_DESC_AMY + " " + PREFIX_MEDCON,
                "Medical condition " + MESSAGE_EMPTY_FIELD);
    }


    @Test
    public void parse_medConLengthGreaterThan30Characters_failure() {
        // Medical condition exceeds 30 characters
        String input = NRIC_DESC_AMY + INVALID_MEDCON_DESC;
        assertParseFailure(parser, input, "Medical condition " + MESSAGE_CONSTRAINTS_LENGTH);
    }

}
