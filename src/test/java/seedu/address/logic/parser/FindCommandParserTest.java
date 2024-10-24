package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.Person;
import seedu.address.model.predicate.FieldContainsKeywordsPredicate;


public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(Arrays.asList(
                        new FieldContainsKeywordsPredicate<>(Arrays.asList("Alice"), Person::getFullName, true),
                        new FieldContainsKeywordsPredicate<>(Arrays.asList("Bob"), Person::getAddressValue, true)
                ));
        assertParseFailure(parser, "n/Alice n/Bob", "Invalid command format! \n"
                + "find: Finds persons in the address book matching the given keywords across specified fields. "
                + "You can search by name, address, attendance, tags, and more.\n"
                + "Name, address, and tag searches support multiple words and duplicate prefix; other fields accept "
                + "only a single word and single prefix. Partial matching is allowed, E.g. Doing find n/dav will match"
                + " a person with the name David.Search is case-insensitive, and multiple conditions are combined "
                + "with logical AND.\n"
                + "Parameters: [n/NAME] [attend/DATE_RANGE (in the format of dd/MM/yy:dd/MM/yy)][t/TAG]...\n"
                + "Example: find n/John Doe t/friend attend/24/10/2024:27/10/2024");

        // multiple whitespaces between keywords
        // assertParseSuccess(parser, "n/ \n Alice  a/Bob \n \t Bob  \t", expectedFindCommand);
    }

}
