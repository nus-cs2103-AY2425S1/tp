package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindByWorkExperienceCommand;
import seedu.address.model.person.WorkExperienceContainsKeywordsPredicate;

public class FindByWorkExperienceCommandParserTest {

    private FindByWorkExperienceCommandParser parser = new FindByWorkExperienceCommandParser();

    @Test
    public void parse_validArgs_returnsFindCommand() {
        FindByWorkExperienceCommand expectedCommand =
                new FindByWorkExperienceCommand(new WorkExperienceContainsKeywordsPredicate("Intern",
                        "Google", "2024"));
        assertParseSuccess(parser, " w/Intern, Google, 2024", expectedCommand);
    }

    @Test
    public void parse_missingRole_returnsFindCommand() {
        FindByWorkExperienceCommand expectedCommand =
                new FindByWorkExperienceCommand(new WorkExperienceContainsKeywordsPredicate("", "Google", "2024"));
        assertParseSuccess(parser, " w/Google, 2024", expectedCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindByWorkExperienceCommand.MESSAGE_USAGE));
    }
}
