package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_DUPLICATE_FIELDS;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.JOBCODE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_NEW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JOBCODE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_NEW;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.EmailPredicate;
import seedu.address.model.person.FullNameContainsPredicate;
import seedu.address.model.person.JobCodePredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PhonePredicate;
import seedu.address.model.person.RemarkPredicate;
import seedu.address.model.person.TagPredicate;

public class FindCommandParserTest {

    private final FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_validName_success() {
        Predicate<Person> predicate = new FullNameContainsPredicate(VALID_NAME_AMY);
        FindCommand expectedCommand = new FindCommand(predicate);
        assertParseSuccess(parser, NAME_DESC_AMY, expectedCommand);
    }

    @Test
    public void parse_validPhone_success() {
        Predicate<Person> predicate = new PhonePredicate(VALID_PHONE_AMY);
        FindCommand expectedCommand = new FindCommand(predicate);
        assertParseSuccess(parser, PHONE_DESC_AMY, expectedCommand);
    }

    @Test
    public void parse_validEmail_success() {
        Predicate<Person> predicate = new EmailPredicate(VALID_EMAIL_AMY);
        FindCommand expectedCommand = new FindCommand(predicate);
        assertParseSuccess(parser, EMAIL_DESC_AMY, expectedCommand);
    }

    @Test
    public void parse_validTag_success() {
        Predicate<Person> predicate = new TagPredicate(VALID_TAG_NEW);
        FindCommand expectedCommand = new FindCommand(predicate);
        assertParseSuccess(parser, TAG_DESC_NEW, expectedCommand);
    }

    @Test
    public void parse_validJobCode_success() {
        Predicate<Person> predicate = new JobCodePredicate(VALID_JOBCODE_AMY);
        FindCommand expectedCommand = new FindCommand(predicate);
        assertParseSuccess(parser, JOBCODE_DESC_AMY, expectedCommand);
    }

    @Test
    public void parse_validRemark_success() {
        Predicate<Person> predicate = new RemarkPredicate(VALID_REMARK_AMY);
        FindCommand expectedCommand = new FindCommand(predicate);
        assertParseSuccess(parser, REMARK_DESC_AMY, expectedCommand);
    }

    @Test
    public void parse_noPredicates_failure() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPreamble_failure() {
        assertParseFailure(parser, "invalidPreamble " + NAME_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicatePrefixes_failure() {
        String userInput = NAME_DESC_AMY + NAME_DESC_AMY;
        assertParseFailure(parser, userInput, String.format(MESSAGE_DUPLICATE_FIELDS + "n/"));
    }
}
