package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddWorkExperienceCommand;
import seedu.address.model.person.WorkExp;

public class AddWorkExperienceCommandParserTest {

    private final AddWorkExperienceCommandParser parser = new AddWorkExperienceCommandParser();

    @Test
    public void parse_validArgs_returnsAddWorkExperienceCommand() {
        // Test valid input with all fields
        Index index = Index.fromOneBased(1);
        WorkExp workExp = new WorkExp("Intern,Google,2024");
        AddWorkExperienceCommand expectedCommand = new AddWorkExperienceCommand(index, workExp);
        assertParseSuccess(parser, " in/1 w/Intern,Google,2024", expectedCommand);
    }

    @Test
    public void parseInvalidArgs_missingIndex_throwsParseException() {
        // Test input with missing index
        assertParseFailure(parser, " w/Intern,Google,2024", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddWorkExperienceCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseInvalidArgs_missingWorkExperience_throwsParseException() {
        // Test input with missing work experience
        assertParseFailure(parser, " in/1 ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddWorkExperienceCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseInvalidArgs_emptyArgs_throwsParseException() {
        // Test input with completely empty arguments
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddWorkExperienceCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseInvalidArgs_incorrectPrefixFormat_throwsParseException() {
        // Test input with incorrect prefix formats
        assertParseFailure(parser, " index/1 work/Intern,Google,2024",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddWorkExperienceCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseInvalidArgs_onlyPrefixes_throwsParseException() {
        // Test input with only the prefixes and no data
        assertParseFailure(parser, " in/ w/", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddWorkExperienceCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseInvalidArgs_missingIndexPrefix_throwsParseException() {
        // Test input where the index prefix is missing
        assertParseFailure(parser, " 1 w/Intern,Google,2024", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddWorkExperienceCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseInvalidArgs_missingWorkExperiencePrefix_throwsParseException() {
        // Test input where the work experience prefix is missing
        assertParseFailure(parser, " in/1 Intern,Google,2024", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddWorkExperienceCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseValidArgs_withExtraWhitespace_returnsAddWorkExperienceCommand() {
        // Test input with extra spaces between arguments
        Index index = Index.fromOneBased(1);
        WorkExp workExp = new WorkExp("Intern,Google,2024");
        AddWorkExperienceCommand expectedCommand = new AddWorkExperienceCommand(index, workExp);
        assertParseSuccess(parser, "  in/1    w/Intern,Google,2024  ", expectedCommand);
    }

    @Test
    public void parseInvalidArgs_nonNumericIndex_throwsParseException() {
        // Test input with a non-numeric index
        assertParseFailure(parser, " in/abc w/Intern,Google,2024",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddWorkExperienceCommand.MESSAGE_USAGE));
    }

}
