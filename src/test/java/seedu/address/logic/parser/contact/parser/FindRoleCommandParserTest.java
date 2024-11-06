package seedu.address.logic.parser.contact.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.contact.commands.FindRoleCommand;
import seedu.address.model.person.predicates.PersonIsRolePredicate;
import seedu.address.model.role.Sponsor;
import seedu.address.model.role.Volunteer;

public class FindRoleCommandParserTest {

    private FindRoleCommandParser parser = new FindRoleCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindRoleCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindRoleCommand() {
        FindRoleCommand expectedFindRoleCommand =
                new FindRoleCommand(new PersonIsRolePredicate(Arrays.asList(new Sponsor(), new Volunteer())));

        // no leading and trailing whitespaces
        assertParseSuccess(parser, "sponsor volunteer", expectedFindRoleCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n sponsor \n \t volunteer  \t", expectedFindRoleCommand);

        // order does not matter
        assertParseSuccess(parser, "volunteer sponsor", expectedFindRoleCommand);
    }

}
