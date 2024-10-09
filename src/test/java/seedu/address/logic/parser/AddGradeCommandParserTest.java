package seedu.address.logic.parser;


import static seedu.address.logic.commands.CommandTestUtil.ASSIGNMENT_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SCORE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASSIGNMENT_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCORE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddGradeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class AddGradeCommandParserTest {
    private final AddGradeCommandParser parser = new AddGradeCommandParser();

    @Test
    public void parse_allFieldsSpecified_success() throws ParseException {
        String userInput = NAME_DESC_AMY + ASSIGNMENT_DESC_ONE + SCORE_DESC;
        AddGradeCommand expectedCommand = new AddGradeCommand(VALID_NAME_AMY, Float.parseFloat(VALID_SCORE), VALID_ASSIGNMENT_ONE);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
