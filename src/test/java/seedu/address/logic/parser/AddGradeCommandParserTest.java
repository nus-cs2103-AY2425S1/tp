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

/**
 * Tests for the AddGradeCommandParser class, which parses user input into AddGradeCommand objects.
 * This class contains test cases that verify the parsing behavior for different user inputs,
 * including cases with all fields specified, missing fields, empty fields, and invalid formats.
 */
public class AddGradeCommandParserTest {
    private final AddGradeCommandParser parser = new AddGradeCommandParser();

    /**
     * Tests parsing when all required fields are specified in the input.
     * Verifies that the correct AddGradeCommand object is created.
     *
     * @throws ParseException if there is an error during parsing.
     */
    @Test
    public void parse_allFieldsSpecified_success() throws ParseException {
        String userInput = NAME_DESC_AMY + ASSIGNMENT_DESC_ONE + SCORE_DESC;
        AddGradeCommand expectedCommand = new AddGradeCommand(
                new Name(VALID_NAME_AMY),
                VALID_SCORE,
                VALID_ASSIGNMENT_ONE);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    /**
     * Tests parsing when not all required fields are specified.
     * Verifies that an error message is returned indicating an invalid command format.
     *
     * @throws ParseException if there is an error during parsing.
     */
    @Test
    public void parse_notAllFieldSpecifiedError() throws ParseException {
        String userInput = NAME_DESC_AMY + SCORE_DESC;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGradeCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }


    /**
     * Tests parsing when the name field is empty.
     * Verifies that an error message is returned for the empty name field.
     *
     * @throws ParseException if there is an error during parsing.
     */
    @Test
    public void parse_emptyNameError() throws ParseException {
        String userInput = " " + PREFIX_NAME + ASSIGNMENT_DESC_ONE + SCORE_DESC;
        assertParseFailure(parser, userInput, Name.MESSAGE_CONSTRAINTS);
    }

    /**
     * Tests parsing when the assignment name field is empty.
     * Verifies that an error message is returned for the empty assignment name field.
     *
     * @throws ParseException if there is an error during parsing.
     */
    @Test
    public void parse_emptyAsgnNameError() throws ParseException {
        String userInput = NAME_DESC_AMY + " " + PREFIX_ASSIGNMENT + SCORE_DESC;
        String expectedMessage = "Assignment name cannot be empty.";
        assertParseFailure(parser, userInput, expectedMessage);
    }

    /**
     * Tests parsing when the score field is empty.
     * Verifies that an error message is returned for the empty score field.
     *
     * @throws ParseException if there is an error during parsing.
     */
    @Test
    public void parse_emptyScoreError() throws ParseException {
        String userInput = NAME_DESC_AMY + ASSIGNMENT_DESC_ONE + " " + PREFIX_SCORE;
        String expectedMessage = "Score cannot be empty.";
        assertParseFailure(parser, userInput, expectedMessage);
    }



    /**
     * Tests parsing when the score field contains an invalid value.
     * Verifies that an error message is returned for the invalid score.
     *
     * @throws ParseException if there is an error during parsing.
     */
    @Test
    public void parse_invalidScoreError() throws ParseException {
        String userInput = NAME_DESC_AMY + ASSIGNMENT_DESC_ONE + " " + PREFIX_SCORE + "s";
        String expectedMessage = "Score must be a valid number.";
        assertParseFailure(parser, userInput, expectedMessage);
    }

    /**
     * Tests parsing when the input argument is empty.
     * Verifies that the default AddGradeCommand is returned when no arguments are provided.
     *
     * @throws Exception if there is an error during parsing.
     */
    @Test
    public void parse_emptyArg() throws Exception {
        assertEquals(parser.parse(""), AddGradeCommand.showAssignmentDefault());
    }

}
