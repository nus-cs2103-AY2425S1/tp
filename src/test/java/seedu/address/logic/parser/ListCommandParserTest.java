package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListCommand;

public class ListCommandParserTest {
    private static final ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_success() {
        ListCommand newListCommand = new ListCommand();

        // EP: string with no visible characters
        assertParseSuccess(parser, "", newListCommand);
        assertParseSuccess(parser, "    ", newListCommand);


        // EP: string with parameters input
        String allWord = "all";
        String contactsWord = "contacts";
        String allContactsWord = allWord + contactsWord;

        // normal parameter adding
        assertParseSuccess(parser, allWord, newListCommand);
        assertParseSuccess(parser, contactsWord + "    ", newListCommand);

        // Case insensitivity
        assertParseSuccess(parser, allContactsWord.toUpperCase(), newListCommand);

        // different combination and permutation with spacing
        assertParseSuccess(parser, contactsWord + " " + allWord, newListCommand);
    }

    @Test
    public void parse_failure() {
        assertParseFailure(parser, "contactsall", ListCommand.MESSAGE_WRONG_ARGUMENTS); // edge case

        // Some parameters are accepted, some not accepted
        assertParseFailure(parser, "add haha", ListCommand.MESSAGE_WRONG_ARGUMENTS);
        assertParseFailure(parser, "contacts with masters", ListCommand.MESSAGE_WRONG_ARGUMENTS);
    }
}
