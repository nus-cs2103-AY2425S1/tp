package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ECNAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddEcNameCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.EcName;


public class AddEcNameCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            AddEcNameCommand.MESSAGE_USAGE);
    private AddEcNameCommandParser parser = new AddEcNameCommandParser();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private final String validName = "John Doe";
    private final String validEmergencyContactName = "Jane Doe";

    @Test
    public void parse_paramsPresent_success() {
        // EP: all fields provided
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_ECNAME + validEmergencyContactName;
        AddEcNameCommand expectedCommand =
                new AddEcNameCommand(INDEX_FIRST_PERSON,
                        new EcName(validEmergencyContactName));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingParams_failure() {
        String expectedIndexMessage = MESSAGE_INVALID_INDEX;
        String expectedParamMessage = MESSAGE_INVALID_FORMAT;

        // EP: no index
        String userInput = AddEcNameCommand.COMMAND_WORD + " " + PREFIX_ECNAME
                + validEmergencyContactName;
        assertParseFailure(parser, userInput, expectedIndexMessage);

        // EP: no parameters
        userInput = AddEcNameCommand.COMMAND_WORD;
        assertParseFailure(parser, userInput, expectedParamMessage);
    }

    @Test
    public void parse_invalidIndex_failure() {
        Index invalidIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddEcNameCommand addEcNameCommand = new AddEcNameCommand(invalidIndex,
                new EcName(validEmergencyContactName));

        // EP: invalid index
        assertCommandFailure(addEcNameCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void parse_extraWhitespace_success() {
        String userInput = "   " + INDEX_FIRST_PERSON.getOneBased() + "   " + PREFIX_ECNAME + "   "
                + validEmergencyContactName + "   ";
        AddEcNameCommand expectedCommand = new AddEcNameCommand(INDEX_FIRST_PERSON,
                new EcName(validEmergencyContactName));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
