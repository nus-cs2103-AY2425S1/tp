package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.TUTORIAL_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIAL_ONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX_WILDCARD_COMMAND;
import static seedu.address.testutil.TypicalIndexes.INDEX_ALL;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ResetCommand;
import seedu.address.model.person.Tutorial;

public class ResetCommandParserTest {
    private ResetCommandParser parser = new ResetCommandParser();

    @Test
    public void parse_validTutorial_success() {
        // whitespace only preamble
        assertParseSuccess(parser, INDEX_FIRST_PERSON.getOneBased() + TUTORIAL_DESC_ONE,
                new ResetCommand(INDEX_FIRST_PERSON, new Tutorial(VALID_TUTORIAL_ONE)));
    }

    @Test
    public void parse_wildcard_success() {
        assertParseSuccess(parser, ParserUtil.INDEX_WILDCARD + TUTORIAL_DESC_ONE,
                new ResetCommand(INDEX_ALL, new Tutorial(VALID_TUTORIAL_ONE)));
    }

    @Test
    public void parse_missingIndex_failure() {
        assertParseFailure(parser, TUTORIAL_DESC_ONE, MESSAGE_INVALID_INDEX_WILDCARD_COMMAND);
    }

    @Test
    public void parse_missingPrefix_failure() {
        assertParseFailure(parser, String.valueOf(INDEX_FIRST_PERSON.getOneBased()),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ResetCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingTutorial_failure() {
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_TUTORIAL,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, Tutorial.MESSAGE_INVALID_FORMAT));
    }

    @Test
    public void parse_emptyInput_failure() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ResetCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_whitespaceInput_failure() {
        assertParseFailure(parser, "   ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ResetCommand.MESSAGE_USAGE));
    }
}
