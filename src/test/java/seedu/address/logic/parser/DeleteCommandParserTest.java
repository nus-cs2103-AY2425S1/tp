package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validIndex_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_validPhoneNumber_returnsDeleteCommand() {
        Phone phone = new Phone(VALID_PHONE_BOB);
        assertParseSuccess(parser, PHONE_DESC_BOB, new DeleteCommand(phone));
    }

    @Test
    public void parse_validAddress_returnsDeleteCommand() {
        Address address = new Address(VALID_ADDRESS_BOB);
        assertParseSuccess(parser, ADDRESS_DESC_BOB, new DeleteCommand(address));
    }

    @Test
    public void parse_validTags_returnsDeleteCommand() {
        Tag tag = new Tag(VALID_TAG_FRIEND);
        Set<Tag> tags = new HashSet<>();
        tags.add(tag);
        assertParseSuccess(parser, TAG_DESC_FRIEND, new DeleteCommand(tags));
    }


    @Test
    public void parse_validEmail_returnsDeleteCommand() {
        Email email = new Email(VALID_EMAIL_BOB);
        assertParseSuccess(parser, EMAIL_DESC_BOB, new DeleteCommand(email));
    }


}
