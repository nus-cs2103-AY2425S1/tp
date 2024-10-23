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
}
