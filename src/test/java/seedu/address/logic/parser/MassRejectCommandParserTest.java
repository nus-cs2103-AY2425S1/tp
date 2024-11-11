package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_DUPLICATE_FIELDS;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.JOBCODE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.JOBCODE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_NEW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JOBCODE_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.MassRejectCommand;
import seedu.address.model.person.JobCodePredicate;
import seedu.address.model.person.JobCodeTagPredicate;
import seedu.address.model.person.TagPredicate;

public class MassRejectCommandParserTest {

    private MassRejectCommandParser parser = new MassRejectCommandParser();

    @Test
    public void parse_validJobCodeAndTag_success() {
        String userInput = JOBCODE_DESC_AMY + TAG_DESC_NEW;
        MassRejectCommand expectedCommand = new MassRejectCommand(new JobCodeTagPredicate(VALID_JOBCODE_AMY, "N"));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validJobCodeOnly_success() {
        String userInput = JOBCODE_DESC_AMY;
        MassRejectCommand expectedCommand = new MassRejectCommand(new JobCodePredicate(VALID_JOBCODE_AMY));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validTagOnly_success() {
        String userInput = TAG_DESC_NEW;
        MassRejectCommand expectedCommand = new MassRejectCommand(new TagPredicate("N"));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingAllFields_failure() {
        String userInput = "";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MassRejectCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingOneField_failure() {
        String userInput = "j/";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MassRejectCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidCombinationOfFields_failure() {
        String userInput = JOBCODE_DESC_AMY + " " + JOBCODE_DESC_BOB;
        assertParseFailure(parser, userInput, String.format(MESSAGE_DUPLICATE_FIELDS + "j/"));
    }
}
