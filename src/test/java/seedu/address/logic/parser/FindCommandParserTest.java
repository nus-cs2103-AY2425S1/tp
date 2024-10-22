package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.util.FieldQuery;
import seedu.address.logic.commands.util.SearchField;
import seedu.address.model.person.PersonSearchPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        List<FieldQuery> fieldQueryList = List.of(
                new FieldQuery(SearchField.NAME, "name"),
                new FieldQuery(SearchField.PHONE, "phone"),
                new FieldQuery(SearchField.EMAIL, "email"),
                new FieldQuery(SearchField.LOCATION, "location"),
                new FieldQuery(SearchField.REMARK, "remark"));
        FindCommand expectedFindCommand =
                new FindCommand(new PersonSearchPredicate(fieldQueryList));
        assertParseSuccess(parser, " " + PREFIX_NAME + " name " + PREFIX_PHONE + " phone "
                        + PREFIX_EMAIL + " email " + PREFIX_LOCATION + " location "
                + PREFIX_REMARK + " remark", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " " + PREFIX_NAME + " name  \n " + PREFIX_PHONE + " phone "
                + PREFIX_EMAIL + " \t email " + PREFIX_LOCATION + "\t location "
                + PREFIX_REMARK + " remark \t", expectedFindCommand);
    }

}
