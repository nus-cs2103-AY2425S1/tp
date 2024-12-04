package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindSubjectCommand;
import seedu.address.model.person.PersonHaveSubjectPredicate;
import seedu.address.model.person.Subject;


public class FindSubjectCommandParserTest {
    private FindSubjectCommandParser parser = new FindSubjectCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser,
                "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindSubjectCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidSubject_throwsParseException() {
        assertParseFailure(parser,
                "Invalid Subject", Subject.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_validSubject_returnsFindSubjectCommand() {
        // no leading and trailing whitespaces
        FindSubjectCommand expectedFindSubjectCommand =
                new FindSubjectCommand(new PersonHaveSubjectPredicate("English"));
        assertParseSuccess(parser, "English", expectedFindSubjectCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n English  \n", expectedFindSubjectCommand);
    }
}

