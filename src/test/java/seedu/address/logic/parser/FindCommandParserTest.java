package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.ArgumentPredicate;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;

import java.util.HashSet;
import java.util.Map;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // Person with 1 parameter
        Map<String, Object> testParameters = Map.of(
                "name", new Name(VALID_NAME_BOB),
                "tags", new HashSet<>()
                );
        FindCommand expectedFindCommand =
                new FindCommand(new ArgumentPredicate(testParameters));
        assertParseSuccess(parser, NAME_DESC_BOB, expectedFindCommand);

        // Person with multiple parameters
        Map<String, Object> testMultipleParameters = Map.of(
                "name", new Name(VALID_NAME_BOB),
                "email", new Email(VALID_EMAIL_BOB),
                "tags", new HashSet<>()
        );
        FindCommand expectedSecondFindCommand =
                new FindCommand(new ArgumentPredicate(testMultipleParameters));
        assertParseSuccess(parser, NAME_DESC_BOB + EMAIL_DESC_BOB, expectedSecondFindCommand);
    }

}
