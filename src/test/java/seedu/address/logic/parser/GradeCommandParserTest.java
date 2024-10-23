package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_AMY;
import static seedu.address.logic.commands.GradeCommand.MESSAGE_INVALID_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.GradeCommand;
import seedu.address.model.person.Module;

public class GradeCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, GradeCommand.MESSAGE_USAGE);

    private GradeCommandParser parser = new GradeCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        String userinput = "1" + " " + PREFIX_MODULE + VALID_MODULE_AMY + " " + PREFIX_GRADE + VALID_GRADE_AMY;
        assertParseSuccess(parser, userinput,
                new GradeCommand(Index.fromOneBased(1), VALID_MODULE_AMY, VALID_GRADE_AMY));
    }
    @Test
    public void parse_missingModule_failure() {
        assertParseFailure(parser, "1 s/" + VALID_GRADE_AMY, MESSAGE_INVALID_FORMAT);
    }
    @Test
    public void parse_missingGrade_failure() {
        assertParseFailure(parser, "1 " + PREFIX_MODULE + VALID_MODULE_AMY, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "-1", MESSAGE_INVALID_FORMAT);
    }


}
