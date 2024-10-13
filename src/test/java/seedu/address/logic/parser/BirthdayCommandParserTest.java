package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.BirthdayCommand;

public class BirthdayCommandParserTest {
    private BirthdayCommandParser parser = new BirthdayCommandParser();
    private final String nonEmptyBirthday = "Some birthday.";
    @Test
    public void parse_indexSpecified_success() {
        // have birthday
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_BIRTHDAY + nonEmptyBirthday;
        BirthdayCommand expectedCommand = new BirthdayCommand(INDEX_FIRST_PERSON, nonEmptyBirthday);
        assertParseSuccess(parser, userInput, expectedCommand);
        // no birthday
        userInput = targetIndex.getOneBased() + " " + PREFIX_BIRTHDAY;
        expectedCommand = new BirthdayCommand(INDEX_FIRST_PERSON, "");
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, BirthdayCommand.MESSAGE_USAGE);
        // no parameters
        assertParseFailure(parser, BirthdayCommand.COMMAND_WORD, expectedMessage);
        // no index
        assertParseFailure(parser, BirthdayCommand.COMMAND_WORD + " " + nonEmptyBirthday, expectedMessage);
    }
}
