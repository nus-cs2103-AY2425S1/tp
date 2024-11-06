package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPLICATION_STATUS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_COMPANY;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ApplicationStatusCommand;
import seedu.address.model.company.ApplicationStatus;

public class ApplicationStatusCommandParserTest {
    private ApplicationStatusCommandParser parser = new ApplicationStatusCommandParser();
    private final String nonEmptyStatus = "Some status.";

    @Test
    public void parse_indexSpecified_success() {
        // have status
        Index targetIndex = INDEX_FIRST_COMPANY;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_APPLICATION_STATUS + nonEmptyStatus;
        ApplicationStatusCommand expectedCommand = new ApplicationStatusCommand(INDEX_FIRST_COMPANY,
                new ApplicationStatus(nonEmptyStatus));
        assertParseSuccess(parser, userInput, expectedCommand);
        // no status
        userInput = targetIndex.getOneBased() + " " + PREFIX_APPLICATION_STATUS;
        expectedCommand = new ApplicationStatusCommand(INDEX_FIRST_COMPANY, new ApplicationStatus(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }
    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ApplicationStatusCommand.MESSAGE_USAGE);
        // no parameters
        assertParseFailure(parser, ApplicationStatusCommand.COMMAND_WORD, expectedMessage);
        // no index
        assertParseFailure(parser, ApplicationStatusCommand.COMMAND_WORD + " " + nonEmptyStatus, expectedMessage);
    }
}
