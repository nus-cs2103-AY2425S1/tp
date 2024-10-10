package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ECNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddEmergencyContactNameCommand;
import seedu.address.model.person.Name;


public class AddEmergencyContactNameCommandParserTest {

    private AddEmergencyContactNameCommandParser parser = new AddEmergencyContactNameCommandParser();

    private final String validName = "John Doe";
    private final String validEmergencyContactName = "Jane Doe";

    @Test
    public void parse_paramsPresent_success() {
        // all fields provided
        String userInput = " " + PREFIX_NAME + validName + " " + PREFIX_ECNAME + validEmergencyContactName;
        AddEmergencyContactNameCommand expectedCommand =
                new AddEmergencyContactNameCommand(new Name(validName), new Name(validEmergencyContactName));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingParams_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEmergencyContactNameCommand.MESSAGE_USAGE);

        // no name
        String userInput = " " + PREFIX_ECNAME + validEmergencyContactName;
        assertParseFailure(parser, userInput, expectedMessage);

        // no ECName
        userInput = " " + PREFIX_NAME + validName;
        assertParseFailure(parser, userInput, expectedMessage);
    }
}
