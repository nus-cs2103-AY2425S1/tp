package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validName_returnsFindCommand() {
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(VALID_NAME_AMY);
        String userInput = NAME_DESC_AMY;
        FindCommand expectedFindCommand =
                new FindCommand(predicate);
        assertParseSuccess(parser, userInput, expectedFindCommand);

    }

    @Test
    public void parse_validEmail_returnsFindCommand() {
        Email email = new Email(VALID_EMAIL_AMY);
        String userInput = EMAIL_DESC_AMY;
        FindCommand expectedFindCommand =
                new FindCommand(email);
        assertParseSuccess(parser, userInput, expectedFindCommand);

    }


    @Test
    public void parse_validAddress_returnsFindCommand() {
        Address address = new Address(VALID_ADDRESS_AMY);
        String userInput = ADDRESS_DESC_AMY;
        FindCommand expectedFindCommand =
                new FindCommand(address);
        assertParseSuccess(parser, userInput, expectedFindCommand);

    }


    @Test
    public void parse_validPhoneNumber_returnsFindCommand() {
        Phone phone = new Phone(VALID_PHONE_AMY);
        String userInput = PHONE_DESC_AMY;
        FindCommand expectedFindCommand =
                new FindCommand(phone);
        assertParseSuccess(parser, userInput, expectedFindCommand);

    }


    @Test
    public void parse_validTag_returnsFindCommand() {
        Tag tag = new Tag(VALID_TAG_FRIEND);
        Set<Tag> tags = new HashSet<>();
        tags.add(tag);
        String userInput = TAG_DESC_FRIEND;
        FindCommand expectedFindCommand =
                new FindCommand(tags);
        assertParseSuccess(parser, userInput, expectedFindCommand);

    }

    @Test
    public void parse_validRole_returnsFindCommand() {
        Role role = new Role(VALID_ROLE_AMY);
        String userInput = ROLE_DESC_AMY;
        FindCommand expectedFindCommand =
                new FindCommand(role);
        assertParseSuccess(parser, userInput, expectedFindCommand);

    }





    @Test
    public void parse_validIndex_returnsFindCommand() {
        assertParseSuccess(parser, "1", new FindCommand(INDEX_FIRST_PERSON));

    }


}
