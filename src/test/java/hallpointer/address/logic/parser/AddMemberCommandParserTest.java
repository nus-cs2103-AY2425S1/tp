package hallpointer.address.logic.parser;

import static hallpointer.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static hallpointer.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static hallpointer.address.logic.commands.CommandTestUtil.INVALID_ROOM_DESC;
import static hallpointer.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static hallpointer.address.logic.commands.CommandTestUtil.INVALID_TELEGRAM_DESC;
import static hallpointer.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static hallpointer.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static hallpointer.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static hallpointer.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static hallpointer.address.logic.commands.CommandTestUtil.ROOM_DESC_AMY;
import static hallpointer.address.logic.commands.CommandTestUtil.ROOM_DESC_BOB;
import static hallpointer.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static hallpointer.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static hallpointer.address.logic.commands.CommandTestUtil.TELEGRAM_DESC_AMY;
import static hallpointer.address.logic.commands.CommandTestUtil.TELEGRAM_DESC_BOB;
import static hallpointer.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static hallpointer.address.logic.commands.CommandTestUtil.VALID_ROOM_BOB;
import static hallpointer.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static hallpointer.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static hallpointer.address.logic.commands.CommandTestUtil.VALID_TELEGRAM_BOB;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_NAME;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_ROOM;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_TELEGRAM;
import static hallpointer.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static hallpointer.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static hallpointer.address.testutil.TypicalMembers.AMY;
import static hallpointer.address.testutil.TypicalMembers.BOB;

import org.junit.jupiter.api.Test;

import hallpointer.address.logic.Messages;
import hallpointer.address.logic.commands.AddMemberCommand;
import hallpointer.address.model.member.Member;
import hallpointer.address.model.member.Name;
import hallpointer.address.model.member.Room;
import hallpointer.address.model.member.Telegram;
import hallpointer.address.model.tag.Tag;
import hallpointer.address.testutil.MemberBuilder;

public class AddMemberCommandParserTest {
    private AddMemberCommandParser parser = new AddMemberCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Member expectedMember = new MemberBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + TELEGRAM_DESC_BOB + ROOM_DESC_BOB
                + TAG_DESC_FRIEND, new AddMemberCommand(expectedMember));


        // multiple tags - all accepted
        Member expectedMemberMultipleTags = new MemberBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + TELEGRAM_DESC_BOB + ROOM_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddMemberCommand(expectedMemberMultipleTags));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedMemberString = NAME_DESC_BOB + TELEGRAM_DESC_BOB + ROOM_DESC_BOB + TAG_DESC_FRIEND;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedMemberString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple telegrams
        assertParseFailure(parser, TELEGRAM_DESC_AMY + validExpectedMemberString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TELEGRAM));

        // multiple rooms
        assertParseFailure(parser, ROOM_DESC_AMY + validExpectedMemberString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ROOM));

        // multiple fields repeated
        // note that this will fail if the Message part does not have the prefixes sorted in lexicographical order
        assertParseFailure(parser,
                validExpectedMemberString + TELEGRAM_DESC_AMY + NAME_DESC_AMY + ROOM_DESC_AMY
                        + validExpectedMemberString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_TELEGRAM, PREFIX_ROOM));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedMemberString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid telegram
        assertParseFailure(parser, INVALID_TELEGRAM_DESC + validExpectedMemberString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TELEGRAM));

        // invalid room
        assertParseFailure(parser, INVALID_ROOM_DESC + validExpectedMemberString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ROOM));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedMemberString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid telegram
        assertParseFailure(parser, validExpectedMemberString + INVALID_TELEGRAM_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TELEGRAM));

        // invalid room
        assertParseFailure(parser, validExpectedMemberString + INVALID_ROOM_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ROOM));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Member expectedMember = new MemberBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + TELEGRAM_DESC_AMY + ROOM_DESC_AMY,
                new AddMemberCommand(expectedMember));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMemberCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + TELEGRAM_DESC_BOB + ROOM_DESC_BOB,
                expectedMessage);

        // missing telegram prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_TELEGRAM_BOB + ROOM_DESC_BOB,
                expectedMessage);

        // missing room prefix
        assertParseFailure(parser, NAME_DESC_BOB + TELEGRAM_DESC_BOB + VALID_ROOM_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_TELEGRAM_BOB + VALID_ROOM_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + TELEGRAM_DESC_BOB + ROOM_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid telegram
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_TELEGRAM_DESC + ROOM_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Telegram.MESSAGE_CONSTRAINTS);

        // invalid room
        assertParseFailure(parser, NAME_DESC_BOB + TELEGRAM_DESC_BOB + INVALID_ROOM_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Room.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + TELEGRAM_DESC_BOB + ROOM_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + TELEGRAM_DESC_BOB + INVALID_ROOM_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + TELEGRAM_DESC_BOB
                + ROOM_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMemberCommand.MESSAGE_USAGE));
    }
}
