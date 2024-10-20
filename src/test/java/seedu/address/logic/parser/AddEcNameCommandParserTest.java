package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ECNAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddEcNameCommand;
import seedu.address.model.person.EcName;


public class AddEcNameCommandParserTest {

    private AddEcNameCommandParser parser = new AddEcNameCommandParser();

    private final String validName = "John Doe";
    private final String validEmergencyContactName = "Jane Doe";

    @Test
    public void parse_paramsPresent_success() {
        // all fields provided
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_ECNAME + validEmergencyContactName;
        AddEcNameCommand expectedCommand =
                new AddEcNameCommand(INDEX_FIRST_PERSON,
                        new EcName(validEmergencyContactName));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingParams_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddEcNameCommand.MESSAGE_USAGE);

        // no index
        String userInput = AddEcNameCommand.COMMAND_WORD + " " + PREFIX_ECNAME
                + validEmergencyContactName;
        assertParseFailure(parser, userInput, expectedMessage);

        // no parameters
        userInput = AddEcNameCommand.COMMAND_WORD;
        assertParseFailure(parser, userInput, expectedMessage);
    }
}
