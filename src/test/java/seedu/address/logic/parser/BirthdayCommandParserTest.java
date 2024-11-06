package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_DATE_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BIRTHDAY_EARLY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BIRTHDAY_LATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDAY_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.person.Birthday.MESSAGE_INVALID_BIRTHDAY_AFTER_PRESENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.BirthdayCommand;
import seedu.address.model.person.Birthday;

public class BirthdayCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, BirthdayCommand.MESSAGE_USAGE);

    private BirthdayCommandParser parser = new BirthdayCommandParser();
    @Test
    public void parse_indexSpecified_success() {
        // have birthday
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_BIRTHDAY + VALID_BIRTHDAY_AMY;
        BirthdayCommand expectedCommand = new BirthdayCommand(INDEX_FIRST_PERSON, new Birthday(VALID_BIRTHDAY_AMY));
        assertParseSuccess(parser, userInput, expectedCommand);

        // no birthday
        userInput = targetIndex.getOneBased() + " " + PREFIX_BIRTHDAY;
        expectedCommand = new BirthdayCommand(INDEX_FIRST_PERSON, new Birthday(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, BirthdayCommand.MESSAGE_USAGE);
        // no parameters
        assertParseFailure(parser, BirthdayCommand.COMMAND_WORD, expectedMessage);
        // no index
        assertParseFailure(parser, BirthdayCommand.COMMAND_WORD + " " + VALID_BIRTHDAY_AMY, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput1 = targetIndex.getOneBased() + INVALID_DATE_DESC;
        String userInput2 = targetIndex.getOneBased() + INVALID_BIRTHDAY_EARLY_DESC;
        String userInput3 = targetIndex.getOneBased() + INVALID_BIRTHDAY_LATE_DESC;

        String expectedMessage = MESSAGE_INVALID_BIRTHDAY_AFTER_PRESENT;

        // Invalid date
        assertParseFailure(parser, userInput1, MESSAGE_INVALID_DATE_FORMAT);

        // Date must not be in the future
        assertParseFailure(parser, userInput3, MESSAGE_INVALID_BIRTHDAY_AFTER_PRESENT);
    }
}
