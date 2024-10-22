package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.ContainsKeywordsPredicate;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        List<String> userInput = Arrays.asList("Alice", "Bob");
        ArgumentMultimap mapForUserInput = new ArgumentMultimap();
        mapForUserInput.put(new Prefix(""), "");
        userInput.stream().forEach(input -> mapForUserInput.put(PREFIX_NAME, input));
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new ContainsKeywordsPredicate(mapForUserInput));
        assertParseSuccess(parser, " n/Alice n/Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n n/Alice \n \t n/Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_inValidArgs_throwsParseException() {
        assertParseFailure(parser, " 1 n/Alex",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_argsWithInvalidTag_throwsParseException() {
        // Tags should be Alphanumeric
        assertParseFailure(parser, " t/Alex Tang",
                "You have entered an invalid Tag!\n"
                        + Tag.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_argsWithInvalidRole_throwsParseException() {
        // Roles should only be "patient" or "caregiver"
        assertParseFailure(parser, " role/random",
                "You have entered an invalid Role!\n"
                        + Role.MESSAGE_CONSTRAINTS);
    }
    @Test
    public void parse_argsWithInvalidNric_throwsParseException() {
        // Nrics should pass the checksum validation
        assertParseFailure(parser, " nric/s1234567e",
                "You have entered an invalid Nric!\n"
                        + Nric.MESSAGE_CONSTRAINTS);
    }
    @Test
    public void parse_argsWithInvalidName_throwsParseException() {
        // Names should only have alphanumeric characters and spaces
        assertParseFailure(parser, " n/bob /",
                "You have entered an invalid name!\n"
                        + Name.MESSAGE_CONSTRAINTS);
    }

}
