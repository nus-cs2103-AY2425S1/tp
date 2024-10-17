package seedu.address.logic.parser;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PUBLIC_ADDRESS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.SearchPublicAddressCommand;
public class SearchPublicAddressCommandParserTest {
    private SearchPublicAddressCommandParser parser = new SearchPublicAddressCommandParser();
    private final String nonEmptyPublicAddress = "Some public address.";
    @Test
    public void parse_indexSpecified_success() {
        // have public address
        String userInput =  PREFIX_PUBLIC_ADDRESS + nonEmptyPublicAddress;
        SearchPublicAddressCommand expectedCommand = new SearchPublicAddressCommand (nonEmptyPublicAddress);
        assertParseSuccess(parser, userInput, expectedCommand);
        // no public address
        userInput = PREFIX_PUBLIC_ADDRESS+ "";
        expectedCommand = new SearchPublicAddressCommand( "");
        assertParseSuccess(parser, userInput, expectedCommand);
    }
    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchPublicAddressCommand.MESSAGE_USAGE);
        // no parameters
        assertParseFailure(parser, SearchPublicAddressCommand.COMMAND_WORD, expectedMessage);
        // no public address
        assertParseFailure(parser, SearchPublicAddressCommand.COMMAND_WORD + " ", expectedMessage);
    }
}