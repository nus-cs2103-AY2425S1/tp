package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_TYPE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListClaimsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.policy.PolicyType;

public class ListClaimsCommandParserTest {

    private final ListClaimsCommandParser parser = new ListClaimsCommandParser();

    @Test
    public void parse_validArgs_returnsListClaimsCommand() throws Exception {
        // valid index and valid policy type
        ListClaimsCommand expectedCommand = new ListClaimsCommand(INDEX_FIRST_PERSON, PolicyType.HEALTH);
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_POLICY_TYPE + "health";

        ListClaimsCommand actualCommand = parser.parse(userInput);
        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void parse_missingPolicyType_throwsParseException() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " ";
        assertThrows(ParseException.class, () -> parser.parse(userInput),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListClaimsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        // non-numeric index
        String userInput = "a " + PREFIX_POLICY_TYPE + "health";
        assertThrows(ParseException.class, () -> parser.parse(userInput),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListClaimsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPolicyType_throwsParseException() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_POLICY_TYPE + "invalidPolicy";
        assertThrows(ParseException.class, () -> parser.parse(userInput),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListClaimsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_extraArgumentsPresent_throwsParseException() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_POLICY_TYPE + "health extraArg";
        assertThrows(ParseException.class, () -> parser.parse(userInput),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListClaimsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        String userInput = "";
        assertThrows(ParseException.class, () -> parser.parse(userInput),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListClaimsCommand.MESSAGE_USAGE));
    }
}
