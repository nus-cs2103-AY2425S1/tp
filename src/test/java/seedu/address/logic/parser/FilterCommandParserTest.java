package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.skill.SkillsContainsKeywordsPredicate;
import seedu.address.model.tag.TagsContainsKeywordsPredicate;

public class FilterCommandParserTest {

    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterCommand() {
        // no leading and trailing whitespaces
        FilterCommand expectedFilterCommand =
                new FilterCommand(new SkillsContainsKeywordsPredicate(Arrays.asList("frontend", "backend")),
                        new TagsContainsKeywordsPredicate(Arrays.asList("swe", "pm")));
        assertParseSuccess(parser, " s/frontend s/backend t/swe t/pm", expectedFilterCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n s/frontend  \n \t s/backend  \t t/swe \n \t t/pm \n", expectedFilterCommand);
    }

}
