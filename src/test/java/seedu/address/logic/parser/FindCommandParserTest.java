package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import java.util.ArrayList;
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
                        new FieldContainsKeywordsPredicate<>(Arrays.asList("Alice"), Person::getFullName,
                                true, FieldContainsKeywordsPredicate.NAME_IDENTIFIER),
                        new FieldContainsKeywordsPredicate<>(Arrays.asList("Bob"), Person::getAddressValue,
                                true, FieldContainsKeywordsPredicate.ADDRESS_IDENTIFIER)
                ), new ArrayList<>());
        assertParseFailure(parser, "n/Alice n/Bob",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        // multiple whitespaces between keywords
        // assertParseSuccess(parser, "n/ \n Alice  a/Bob \n \t Bob  \t", expectedFindCommand);
    }

}
