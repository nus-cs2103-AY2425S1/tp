package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_UNIQUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAREGIVER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteLinkCommand;
import seedu.address.model.person.Nric;

public class DeleteLinkCommandParserTest {

    private DeleteLinkCommandParser parser = new DeleteLinkCommandParser();

    @Test
    public void parse_validArgs_returnsLinkCommand() {
        // Typical valid input
        Nric validPatientNric = new Nric(VALID_NRIC_AMY);
        Nric validCaregiverNric = new Nric(VALID_NRIC_BOB);

        assertParseSuccess(parser,
                " " + PREFIX_PATIENT + validPatientNric.value + " " + PREFIX_CAREGIVER + validCaregiverNric.value,
                new DeleteLinkCommand(validPatientNric, validCaregiverNric));
    }

    @Test
    public void parse_missingPatientNric_throwsParseException() {
        // Missing patient NRIC
        String input = " " + PREFIX_CAREGIVER + VALID_NRIC_UNIQUE;
        assertParseFailure(parser, input,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteLinkCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingCaregiverNric_throwsParseException() {
        // Missing caregiver NRIC
        String input = " " + PREFIX_PATIENT + VALID_NRIC_UNIQUE;
        assertParseFailure(parser, input,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteLinkCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidNricFormat_throwsParseException() {
        // Invalid patient NRIC
        String input = " " + PREFIX_PATIENT + "INVALID_NRIC" + " " + PREFIX_CAREGIVER + "S7654321B";
        assertParseFailure(parser, input, Nric.MESSAGE_CONSTRAINTS);

        // Invalid caregiver NRIC
        input = " " + PREFIX_PATIENT + "S1234567A" + " " + PREFIX_CAREGIVER + "INVALID_NRIC";
        assertParseFailure(parser, input, Nric.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_missingPrefixes_throwsParseException() {
        // Missing both prefixes
        String input = "S1234567A S7654321B";
        assertParseFailure(parser, input,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteLinkCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyInput_throwsParseException() {
        // Empty input
        String input = "";
        assertParseFailure(parser, input,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteLinkCommand.MESSAGE_USAGE));
    }

}
