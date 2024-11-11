package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_CONSTRAINTS_LENGTH;
import static seedu.address.logic.Messages.MESSAGE_EMPTY_FIELD;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ALLERGY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ALLERGY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ALLERGY_DESC_LENGTH;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NRIC_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLERGY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLERGY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DelAllergyCommand;
import seedu.address.model.person.Allergy;
import seedu.address.model.person.Nric;

public class DelAllergyCommandParserTest {
    private final DelAllergyCommandParser parser = new DelAllergyCommandParser();

    @Test
    public void parse_allFieldsValid_success() {
        // For Amy with one Allergy
        Set<Allergy> allergiesAmy = new HashSet<>();
        allergiesAmy.add(new Allergy(VALID_ALLERGY_AMY));
        DelAllergyCommand expectedCommandForAmy = new DelAllergyCommand(new Nric(VALID_NRIC_AMY), allergiesAmy);
        assertParseSuccess(parser, NRIC_DESC_AMY + ALLERGY_DESC_AMY, expectedCommandForAmy);

        // For Bob with one Allergy
        Set<Allergy> allergiesBob = new HashSet<>();
        allergiesBob.add(new Allergy(VALID_ALLERGY_BOB));
        DelAllergyCommand expectedCommandForBob = new DelAllergyCommand(new Nric(VALID_NRIC_BOB), allergiesBob);
        assertParseSuccess(parser, NRIC_DESC_BOB + ALLERGY_DESC_BOB, expectedCommandForBob);
    }

    @Test
    public void parse_allFieldsInvalid_failure() {
        // Invalid NRIC but valid Allergy
        assertParseFailure(parser, INVALID_NRIC_DESC + ALLERGY_DESC_AMY, Nric.MESSAGE_CONSTRAINTS);

        // Invalid format for NRIC parameter, replaced with name instead
        assertParseFailure(parser, NAME_DESC_AMY + ALLERGY_DESC_AMY, String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, DelAllergyCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_compulsoryFieldsMissing_failure() {
        // Both NRIC and Allergy missing
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DelAllergyCommand.MESSAGE_USAGE));

        // NRIC parameter missing
        assertParseFailure(parser, ALLERGY_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DelAllergyCommand.MESSAGE_USAGE));

        // Allergy parameter missing
        assertParseFailure(parser, NRIC_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DelAllergyCommand.MESSAGE_USAGE));

        // Allergy prefix present, but no value
        assertParseFailure(parser, NRIC_DESC_AMY + " " + PREFIX_ALLERGY,
                "Allergy " + MESSAGE_EMPTY_FIELD);
    }

    @Test
    public void parse_allergyLengthGreaterThan30Characters_failure() {
        // Allergy exceeds 30 characters
        String input = NRIC_DESC_AMY + INVALID_ALLERGY_DESC_LENGTH;
        assertParseFailure(parser, input, "Allergy " + MESSAGE_CONSTRAINTS_LENGTH);
    }

}

