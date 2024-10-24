package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.contact.commands.FindCommand;
import seedu.address.logic.commands.contact.commands.SearchCommand;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PersonIsRolePredicate;
import seedu.address.model.role.Sponsor;
import seedu.address.model.role.Volunteer;

public class SearchCommandParserTest {

    private SearchCommandParser parser = new SearchCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SearchCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsSearchCommand() {
        SearchCommand expectedSearchCommand =
                new SearchCommand(new PersonIsRolePredicate(Arrays.asList(new Sponsor(), new Volunteer())));

        // no leading and trailing whitespaces
        assertParseSuccess(parser, "sponsor volunteer", expectedSearchCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n sponsor \n \t volunteer  \t", expectedSearchCommand);

        // order does not matter
        assertParseSuccess(parser, "volunteer sponsor", expectedSearchCommand);
    }

}
