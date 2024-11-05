package seedu.address.logic.parser;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ASSIGNMENT_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SCORE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASSIGNMENT_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCORE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCORE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddGradeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

public class AddGradeCommandParserTest {
    private final AddGradeCommandParser parser = new AddGradeCommandParser();

    @Test
    public void parse_allFieldsSpecified_success() throws ParseException {
        String userInput = NAME_DESC_AMY + ASSIGNMENT_DESC_ONE + SCORE_DESC;
        AddGradeCommand expectedCommand = new AddGradeCommand(
                new Name(VALID_NAME_AMY),
                VALID_SCORE,
                VALID_ASSIGNMENT_ONE);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_notAllFieldSpecified_error() throws ParseException {
        String userInput = NAME_DESC_AMY + SCORE_DESC;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGradeCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }


    @Test
    public void parse_emptyNameError() throws ParseException {
        String userInput = " " + PREFIX_NAME + ASSIGNMENT_DESC_ONE + SCORE_DESC;
        assertParseFailure(parser, userInput, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_emptyAsgnNameError() throws ParseException {
        String userInput = NAME_DESC_AMY + " " + PREFIX_ASSIGNMENT + SCORE_DESC;
        String expectedMessage = "Assignment name cannot be empty.";
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_emptyScoreError() throws ParseException {
        String userInput = NAME_DESC_AMY + ASSIGNMENT_DESC_ONE + " " + PREFIX_SCORE;
        String expectedMessage = "Score cannot be empty.";
        assertParseFailure(parser, userInput, expectedMessage);
    }


    @Test
    public void parse_invalidScoreError() throws ParseException {
        String userInput = NAME_DESC_AMY + ASSIGNMENT_DESC_ONE + " " + PREFIX_SCORE + "s";
        String expectedMessage = "Score must be a valid number.";
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_emptyArg() throws Exception {
        assertEquals(parser.parse(""), AddGradeCommand.showAssignmentDefault());
    }

}
