package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Phone;

import java.util.Arrays;
import java.util.List;

public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validIndex_returnsDeleteCommand() {
        // Test with index
        assertParseSuccess(parser, "1", new DeleteCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_validPhone_returnsDeleteCommand() {
        // Test with phone number
        String phone = "98765432";
        assertParseSuccess(parser, phone, new DeleteCommand(new Phone(phone)));
    }

    @Test
    public void parse_validNamePredicate_returnsDeleteCommand() {
        // Test with name predicate
        String name = "John Doe";
        List<String> nameKeywords = Arrays.asList("John", "Doe");
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(nameKeywords);
        assertParseSuccess(parser, name, new DeleteCommand(predicate));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        // Test invalid index
        assertParseFailure(parser, "a", String.format(DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPhone_throwsParseException() {
        // Test invalid phone number
        assertParseFailure(parser, "9876abcd", String.format(DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        // Test empty arguments
        assertParseFailure(parser, "", String.format(DeleteCommand.MESSAGE_USAGE));
    }
}
