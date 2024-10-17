package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.GradeCommand;
import seedu.address.model.tag.Grade;

public class GradeCommandParserTest {
    private final String nonEmptyGradeIndex = "1";
    private GradeCommandParser parser = new GradeCommandParser();

    @Test
    public void parse_indexSpecified_success() {
        // have remark
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_GRADE + nonEmptyGradeIndex;
        GradeCommand expectedCommand = new GradeCommand(INDEX_FIRST_PERSON, new Grade(nonEmptyGradeIndex));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, GradeCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, GradeCommand.COMMAND_WORD, expectedMessage);

        // no index
        assertParseFailure(parser, GradeCommand.COMMAND_WORD + " " + nonEmptyGradeIndex, expectedMessage);
    }
}
