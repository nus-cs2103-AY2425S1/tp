package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_STUDENT_ID_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_NAME_CANNOT_BE_EMPTY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.CompositePredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.StudentIdMatchesPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_noPrefixesProvided_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validNamePrefix_returnsFindCommand() {
        CompositePredicate expectedPredicate = new CompositePredicate();
        expectedPredicate.addPredicate(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        FindCommand expectedFindCommand = new FindCommand(expectedPredicate);

        assertParseSuccess(parser, " n/ Alice Bob", expectedFindCommand);
    }

    @Test
    public void parse_validIdPrefix_returnsFindCommand() {
        CompositePredicate expectedPredicate = new CompositePredicate();
        expectedPredicate.addPredicate(new StudentIdMatchesPredicate(Arrays.asList("A1234567E", "A7654321B")));
        FindCommand expectedFindCommand = new FindCommand(expectedPredicate);

        assertParseSuccess(parser, " id/ A1234567E A7654321B", expectedFindCommand);
    }

    @Test
    public void parse_validNameAndIdPrefixes_returnsFindCommand() {
        CompositePredicate expectedPredicate = new CompositePredicate();
        expectedPredicate.addPredicate(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        expectedPredicate.addPredicate(new StudentIdMatchesPredicate(Arrays.asList("A1234567E", "A7654321B")));

        FindCommand expectedFindCommand = new FindCommand(expectedPredicate);

        assertParseSuccess(parser, " n/ Alice Bob id/ A1234567E A7654321B", expectedFindCommand);
    }

    @Test
    public void parse_duplicatePrefixes_throwsParseException() {
        Prefix duplicatePrefix = new Prefix("n/");
        String expectedMessage = Messages.getErrorMessageForDuplicatePrefixes(duplicatePrefix);

        assertParseFailure(parser, " n/ Alice n/ Bob", expectedMessage);
    }

    @Test
    public void parse_invalidStudentIdFormat_throwsParseException() {
        assertParseFailure(parser, " id/ A034r",
                MESSAGE_INVALID_STUDENT_ID_FORMAT);
    }

    @Test
    public void parse_namePrefixWithEmptyValue_throwsParseException() {
        assertParseFailure(parser, " n/    ",
                MESSAGE_NAME_CANNOT_BE_EMPTY);
    }

    @Test
    public void parse_unrecognizedPrefix_throwsParseException() {
        // Since we cannot detect unknown prefixes, this input will be treated as invalid due to non-empty preamble
        assertParseFailure(parser, " x/ value",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

}
