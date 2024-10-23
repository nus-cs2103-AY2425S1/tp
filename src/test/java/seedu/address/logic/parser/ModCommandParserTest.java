package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MOD;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ModCommand;
import seedu.address.model.person.ModuleName;

public class ModCommandParserTest {
    private ModCommandParser parser = new ModCommandParser();
    private final String nonEmptyModName = "CS1101S";
    @Test
    public void parse_indexSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_MOD + nonEmptyModName;
        ModCommand expectedCommand = new ModCommand(INDEX_FIRST_PERSON, new ModuleName(nonEmptyModName));
        assertParseSuccess(parser, userInput, expectedCommand);

        userInput = targetIndex.getOneBased() + " " + PREFIX_MOD;
        expectedCommand = new ModCommand(INDEX_FIRST_PERSON, new ModuleName(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ModCommand.MESSAGE_USAGE);

        assertParseFailure(parser, ModCommand.COMMAND_WORD, expectedMessage);

        assertParseFailure(parser, ModCommand.COMMAND_WORD + " " + nonEmptyModName, expectedMessage);
    }
}
