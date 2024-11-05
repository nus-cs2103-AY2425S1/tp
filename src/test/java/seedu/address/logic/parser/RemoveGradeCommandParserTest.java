package seedu.address.logic.parser;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ASSIGNMENT_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASSIGNMENT_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.RemoveGradeCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.RemoveGradeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class RemoveGradeCommandParserTest {
    private final RemoveGradeCommandParser parser = new RemoveGradeCommandParser();

    @Test
    public void parse_allFieldsSpecified_success() throws ParseException {
        String userInput = NAME_DESC_AMY + ASSIGNMENT_DESC_ONE;
        RemoveGradeCommand expectedCommand = new RemoveGradeCommand(
                VALID_NAME_AMY,
                VALID_ASSIGNMENT_ONE);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_notAllFieldSpecified_error() {
        String userInput = NAME_DESC_AMY;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }


    @Test
    public void parse_emptyNameError() {
        String userInput = " " + PREFIX_NAME + ASSIGNMENT_DESC_ONE;
        String expectedMessage = "Name cannot be empty.";
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_emptyAsgnNameError() {
        String userInput = NAME_DESC_AMY + " " + PREFIX_ASSIGNMENT;
        String expectedMessage = "Assignment name cannot be empty.";
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_emptyArgs() {
        String userInput = " ";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(""),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveGradeCommand.MESSAGE_USAGE));
    }

}
