package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DateCommand;
import seedu.address.model.person.Date;


public class DateCommandParserTest {
    private DateCommandParser parser = new DateCommandParser();
    private final String nonEmptyDate = "Some date.";
    @Test
    public void parse_indexSpecified_success() {
        // have date
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_DATE + nonEmptyDate;
        DateCommand expectedCommand = new DateCommand(INDEX_FIRST_PERSON, new Date(nonEmptyDate));
        assertParseSuccess(parser, userInput, expectedCommand);
        // no date
        userInput = targetIndex.getOneBased() + " " + PREFIX_DATE;
        expectedCommand = new DateCommand(INDEX_FIRST_PERSON, new Date(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }
    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DateCommand.MESSAGE_USAGE);
        // no parameters
        assertParseFailure(parser, DateCommand.COMMAND_WORD, expectedMessage);
        // no index
        assertParseFailure(parser, DateCommand.COMMAND_WORD + " " + nonEmptyDate, expectedMessage);
    }
}
