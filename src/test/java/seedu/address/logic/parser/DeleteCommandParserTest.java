package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

import java.util.HashSet;
import java.util.Set;

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
        Phone phone = new Phone("94351253");
        assertParseSuccess(parser, "p/94351253", new DeleteCommand(phone));
    }

    @Test
    public void parse_validAddress_returnsDeleteCommand() {
        Address address = new Address("123, Jurong West Ave 6, #08-111");
        assertParseSuccess(parser, "a/123, Jurong West Ave 6, #08-111", new DeleteCommand(address));
    }

    @Test
    public void parse_validTags_returnsDeleteCommand() {
        Tag tag = new Tag("friends");
        Set<Tag> tags = new HashSet<>();
        tags.add(tag);
        assertParseSuccess(parser, "t/friends", new DeleteCommand(tags));
    }


    @Test
    public void parse_validEmail_returnsDeleteCommand() {
        Email email = new Email("alice@example.com");
        assertParseSuccess(parser, "e/alice@example.com", new DeleteCommand(email));
    }


}
