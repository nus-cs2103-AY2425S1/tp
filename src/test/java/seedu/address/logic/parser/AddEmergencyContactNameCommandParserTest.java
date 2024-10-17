package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ECNAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddEmergencyContactNameCommand;
import seedu.address.model.person.EmergencyContactName;


public class AddEmergencyContactNameCommandParserTest {

    private AddEmergencyContactNameCommandParser parser = new AddEmergencyContactNameCommandParser();

    private final String validName = "John Doe";
    private final String validEmergencyContactName = "Jane Doe";

    @Test
    public void parse_paramsPresent_success() {
        // all fields provided
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_ECNAME + validEmergencyContactName;
        AddEmergencyContactNameCommand expectedCommand =
                new AddEmergencyContactNameCommand(INDEX_FIRST_PERSON,
                        new EmergencyContactName(validEmergencyContactName));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingParams_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddEmergencyContactNameCommand.MESSAGE_USAGE);

        // no index
        String userInput = AddEmergencyContactNameCommand.COMMAND_WORD + " " + PREFIX_ECNAME
                + validEmergencyContactName;
        assertParseFailure(parser, userInput, expectedMessage);

        // no parameters
        userInput = AddEmergencyContactNameCommand.COMMAND_WORD;
        assertParseFailure(parser, userInput, expectedMessage);
    }
}
