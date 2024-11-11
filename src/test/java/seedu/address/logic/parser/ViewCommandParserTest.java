package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_SPECIAL_CHARACTERS;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.model.person.Name;

public class ViewCommandParserTest {
    private ViewCommandParser parser = new ViewCommandParser();

    @Test
    public void parse_validIndex_returnsViewCommand() {
        // index
        String userInput = "1";
        Index index = Index.fromOneBased(1);
        ViewCommand expected = new ViewCommand(index);
        assertParseSuccess(parser, userInput, expected);
    }

    @Test
    public void parse_validIndexWithSpace_returnsViewCommand() {
        // index
        String userInput = "  1  ";
        Index index = Index.fromOneBased(1);
        ViewCommand expected = new ViewCommand(index);
        assertParseSuccess(parser, userInput, expected);
    }

    @Test
    public void parse_validIndexMaxInt_returnsViewCommand() {
        // case - max int
        String userInput = String.valueOf(Integer.MAX_VALUE);
        Index index = Index.fromOneBased(Integer.MAX_VALUE);
        ViewCommand expected = new ViewCommand(index);
        assertParseSuccess(parser, userInput, expected);
    }

    @Test
    public void parse_validName_returnsViewCommand() {
        // name
        String userInput = "Alice Pauline";
        Name name = new Name("Alice Pauline");
        ViewCommand expected = new ViewCommand(name);
        assertParseSuccess(parser, userInput, expected);
    }

    @Test
    public void parse_validNumericName_returnsViewCommand() {
        // name with numbers
        String userInput = "Alice 123";
        Name name = new Name("Alice 123");
        ViewCommand expected = new ViewCommand(name);
        assertParseSuccess(parser, userInput, expected);
    }

    @Test
    public void parse_validSingleCharacterName_returnsViewCommand() {
        // one char name
        String userInput = "A";
        Name name = new Name("A");
        ViewCommand expected = new ViewCommand(name);
        assertParseSuccess(parser, userInput, expected);
    }

    @Test
    public void parse_validMixedCaseName_returnsViewCommand() {
        // name with uppercase and lowercase letters
        String userInput = "aLiCe pAuLiNe";
        Name name = new Name("aLiCe pAuLiNe");
        ViewCommand expected = new ViewCommand(name);
        assertParseSuccess(parser, userInput, expected);
    }

    @Test
    public void parse_validLongName_returnsViewCommand() {
        // long name
        String userInput = "A".repeat(100);
        Name name = new Name(userInput);
        ViewCommand expected = new ViewCommand(name);
        assertParseSuccess(parser, userInput, expected);
    }

    @Test
    public void parse_invalidEmptyArg_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidWhiteSpaceArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        assertParseFailure(parser, PREAMBLE_WHITESPACE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
    }


    @Test
    public void parse_invalidIndex_throwsParseException() {
        // index
        String userInput = "-1";
        assertParseFailure(parser, userInput, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void parse_invalidZeroIndex_throwsParseException() {
        // case 0 - boundary value
        String userInput = "0";
        assertParseFailure(parser, userInput, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void parse_invalidIndexMinInt_throwsParseException() {
        // case - minimum integer
        String userInput = String.valueOf(Integer.MIN_VALUE);
        assertParseFailure(parser, userInput, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void parse_invalidName_throwsParseException() {
        // name
        String userInput = "Alice /";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        assertParseFailure(parser, PREAMBLE_SPECIAL_CHARACTERS,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
    }

}
