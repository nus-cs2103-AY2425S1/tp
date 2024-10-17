package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.CompositePredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() throws Exception {
        FindCommandParser parser = new FindCommandParser();
        String input = " n/Alice Bob";

        List<Predicate<Person>> predicatesList = new ArrayList<>();
        predicatesList.add(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        Predicate<Person> combinedPredicate = new CompositePredicate(predicatesList);

        FindCommand expectedCommand = new FindCommand(combinedPredicate);
        FindCommand actualCommand = parser.parse(input);

        assertEquals(expectedCommand, actualCommand);
    }

}
