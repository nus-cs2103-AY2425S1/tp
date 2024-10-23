package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EmergencyContactCommand;
import seedu.address.model.person.EmergencyContact;

public class EmergencyContactCommandParserTest {
    private EmergencyContactCommandParser parser = new EmergencyContactCommandParser();
    private final String nonEmptyEmergencyContactName = "Lily";
    private final String nonEmptyEmergencyContactNumber = "12345678";
    @Test
    public void parse_indexSpecified_success() {
        // have remark
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_NAME + nonEmptyEmergencyContactName + " "
                + PREFIX_PHONE + nonEmptyEmergencyContactNumber;
        EmergencyContactCommand expectedCommand = new EmergencyContactCommand(INDEX_FIRST_PERSON,
                new EmergencyContact(nonEmptyEmergencyContactName, nonEmptyEmergencyContactNumber));
        assertParseSuccess(parser, userInput, expectedCommand);
        // no remark
        userInput = targetIndex.getOneBased() + " " + PREFIX_NAME + " " + PREFIX_PHONE;
        expectedCommand = new EmergencyContactCommand(INDEX_FIRST_PERSON,
                new EmergencyContact("", ""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }
    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmergencyContactCommand.MESSAGE_USAGE);
        // no parameters
        assertParseFailure(parser, EmergencyContactCommand.COMMAND_WORD, expectedMessage);
        // no index
        assertParseFailure(parser, EmergencyContactCommand.COMMAND_WORD + " "
                + PREFIX_NAME + nonEmptyEmergencyContactName + " " + PREFIX_PHONE
                + nonEmptyEmergencyContactNumber, expectedMessage);
    }
}
