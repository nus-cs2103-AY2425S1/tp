package hallpointer.address.logic.parser;

import static hallpointer.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static hallpointer.address.logic.Messages.MESSAGE_MAX_SESSION_POINTS;
import static hallpointer.address.logic.commands.CommandTestUtil.DATE_DESC_MEETING;
import static hallpointer.address.logic.commands.CommandTestUtil.DATE_DESC_REHEARSAL;
import static hallpointer.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static hallpointer.address.logic.commands.CommandTestUtil.INVALID_MEMBER_INDEX_DESC;
import static hallpointer.address.logic.commands.CommandTestUtil.INVALID_POINTS_DESC;
import static hallpointer.address.logic.commands.CommandTestUtil.INVALID_SESSION_NAME_DESC;
import static hallpointer.address.logic.commands.CommandTestUtil.MEMBER_INDEX_DESC_ONE;
import static hallpointer.address.logic.commands.CommandTestUtil.MEMBER_INDEX_DESC_THREE;
import static hallpointer.address.logic.commands.CommandTestUtil.MEMBER_INDEX_DESC_TWO;
import static hallpointer.address.logic.commands.CommandTestUtil.POINTS_DESC_MEETING;
import static hallpointer.address.logic.commands.CommandTestUtil.POINTS_DESC_REHEARSAL;
import static hallpointer.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static hallpointer.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static hallpointer.address.logic.commands.CommandTestUtil.SESSION_NAME_DESC_MEETING;
import static hallpointer.address.logic.commands.CommandTestUtil.SESSION_NAME_DESC_REHEARSAL;
import static hallpointer.address.logic.commands.CommandTestUtil.VALID_DATE_MEETING;
import static hallpointer.address.logic.commands.CommandTestUtil.VALID_MEMBER_INDEX_ONE;
import static hallpointer.address.logic.commands.CommandTestUtil.VALID_MEMBER_INDEX_TWO;
import static hallpointer.address.logic.commands.CommandTestUtil.VALID_POINTS_MEETING;
import static hallpointer.address.logic.commands.CommandTestUtil.VALID_SESSION_NAME_MEETING;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_DATE;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_POINTS;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_SESSION_NAME;
import static hallpointer.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static hallpointer.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static hallpointer.address.testutil.TypicalIndexes.INDEX_FIRST_MEMBER;
import static hallpointer.address.testutil.TypicalIndexes.INDEX_SECOND_MEMBER;
import static hallpointer.address.testutil.TypicalSessions.REHEARSAL;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import hallpointer.address.commons.core.index.Index;
import hallpointer.address.logic.Messages;
import hallpointer.address.logic.commands.AddSessionCommand;
import hallpointer.address.model.point.Point;
import hallpointer.address.model.session.Session;
import hallpointer.address.model.session.SessionDate;
import hallpointer.address.model.session.SessionName;
import hallpointer.address.testutil.SessionBuilder;

public class AddSessionCommandParserTest {
    private final AddSessionCommandParser parser = new AddSessionCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Session expectedSession = new SessionBuilder(REHEARSAL).build();

        // whitespace only preamble
        Set<Index> indexSet = new HashSet<Index>(Arrays.asList(INDEX_FIRST_MEMBER));
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + SESSION_NAME_DESC_REHEARSAL
                + DATE_DESC_REHEARSAL + POINTS_DESC_REHEARSAL
                + MEMBER_INDEX_DESC_ONE, new AddSessionCommand(expectedSession, indexSet));


        // multiple indices - all accepted
        indexSet = new HashSet<Index>(Arrays.asList(INDEX_FIRST_MEMBER, INDEX_SECOND_MEMBER));
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + SESSION_NAME_DESC_REHEARSAL
                + DATE_DESC_REHEARSAL + POINTS_DESC_REHEARSAL
                + MEMBER_INDEX_DESC_ONE + MEMBER_INDEX_DESC_TWO, new AddSessionCommand(expectedSession, indexSet));
    }

    @Test
    public void parse_repeatedNonIndexValue_failure() {
        String validExpectedSessionString = SESSION_NAME_DESC_MEETING + DATE_DESC_MEETING + POINTS_DESC_MEETING;
        String validMemberIndexString = MEMBER_INDEX_DESC_TWO + MEMBER_INDEX_DESC_THREE;

        // multiple session names
        assertParseFailure(parser, SESSION_NAME_DESC_REHEARSAL
                        + validExpectedSessionString + validMemberIndexString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SESSION_NAME));

        // multiple dates
        assertParseFailure(parser, DATE_DESC_REHEARSAL
                        + validExpectedSessionString + validMemberIndexString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        // multiple points
        assertParseFailure(parser, POINTS_DESC_REHEARSAL
                        + validExpectedSessionString + validMemberIndexString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_POINTS));


        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedSessionString
                        + DATE_DESC_REHEARSAL + SESSION_NAME_DESC_REHEARSAL + POINTS_DESC_REHEARSAL
                        + validExpectedSessionString + validMemberIndexString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SESSION_NAME, PREFIX_DATE, PREFIX_POINTS));

        // invalid value followed by valid value

        // invalid session name
        assertParseFailure(parser, INVALID_SESSION_NAME_DESC
                        + validExpectedSessionString + validMemberIndexString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SESSION_NAME));

        // invalid date
        assertParseFailure(parser, INVALID_DATE_DESC
                        + validExpectedSessionString + validMemberIndexString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        // invalid points
        assertParseFailure(parser, INVALID_POINTS_DESC
                        + validExpectedSessionString + validMemberIndexString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_POINTS));

        // valid value followed by invalid value

        // invalid session name
        assertParseFailure(parser, validExpectedSessionString
                        + INVALID_SESSION_NAME_DESC + validMemberIndexString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SESSION_NAME));

        // invalid date
        assertParseFailure(parser, validExpectedSessionString
                        + INVALID_DATE_DESC + validMemberIndexString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        // invalid points
        assertParseFailure(parser, validExpectedSessionString
                        + INVALID_POINTS_DESC + validMemberIndexString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_POINTS));
    }

    @Test
    public void parse_repeatedIndexValue_success() {
        Session expectedSession = new SessionBuilder(REHEARSAL).build();

        // single index
        Set<Index> indexSet = new HashSet<Index>(Arrays.asList(INDEX_FIRST_MEMBER, INDEX_FIRST_MEMBER));
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + SESSION_NAME_DESC_REHEARSAL
                + DATE_DESC_REHEARSAL + POINTS_DESC_REHEARSAL
                + MEMBER_INDEX_DESC_ONE + MEMBER_INDEX_DESC_ONE, new AddSessionCommand(expectedSession, indexSet));

        // multiple indices, out of order
        indexSet = new HashSet<Index>(Arrays.asList(INDEX_FIRST_MEMBER, INDEX_SECOND_MEMBER, INDEX_FIRST_MEMBER));
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + SESSION_NAME_DESC_REHEARSAL
                + DATE_DESC_REHEARSAL + POINTS_DESC_REHEARSAL
                + MEMBER_INDEX_DESC_ONE + MEMBER_INDEX_DESC_TWO + MEMBER_INDEX_DESC_ONE,
                new AddSessionCommand(expectedSession, indexSet));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSessionCommand.MESSAGE_USAGE);

        // missing session name prefix
        assertParseFailure(parser, VALID_SESSION_NAME_MEETING + DATE_DESC_MEETING
                        + POINTS_DESC_MEETING + MEMBER_INDEX_DESC_ONE,
                expectedMessage);

        // missing date prefix
        assertParseFailure(parser, SESSION_NAME_DESC_MEETING + VALID_DATE_MEETING
                        + POINTS_DESC_MEETING + MEMBER_INDEX_DESC_ONE,
                expectedMessage);

        // missing point prefix
        assertParseFailure(parser, SESSION_NAME_DESC_MEETING + DATE_DESC_MEETING
                        + VALID_POINTS_MEETING + MEMBER_INDEX_DESC_ONE,
                expectedMessage);

        // missing member index prefix (not tested in AddSessionCommandTest as here should block out such cases anyway)
        assertParseFailure(parser, SESSION_NAME_DESC_MEETING + DATE_DESC_MEETING
                        + POINTS_DESC_MEETING + VALID_MEMBER_INDEX_ONE,
                expectedMessage);
        assertParseFailure(parser, SESSION_NAME_DESC_MEETING + DATE_DESC_MEETING
                        + POINTS_DESC_MEETING + VALID_MEMBER_INDEX_ONE + VALID_MEMBER_INDEX_TWO,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_SESSION_NAME_MEETING + VALID_DATE_MEETING
                        + VALID_POINTS_MEETING + VALID_MEMBER_INDEX_ONE,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid session name
        assertParseFailure(parser, INVALID_SESSION_NAME_DESC + DATE_DESC_REHEARSAL
                + POINTS_DESC_REHEARSAL + MEMBER_INDEX_DESC_ONE, SessionName.MESSAGE_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, SESSION_NAME_DESC_REHEARSAL + INVALID_DATE_DESC
                + POINTS_DESC_REHEARSAL + MEMBER_INDEX_DESC_ONE, SessionDate.MESSAGE_CONSTRAINTS);

        // invalid points
        assertParseFailure(parser, SESSION_NAME_DESC_REHEARSAL + DATE_DESC_REHEARSAL
                + INVALID_POINTS_DESC + MEMBER_INDEX_DESC_ONE, Point.MESSAGE_CONSTRAINTS);

        // invalid index
        assertParseFailure(parser, SESSION_NAME_DESC_REHEARSAL + DATE_DESC_REHEARSAL
                + POINTS_DESC_REHEARSAL + INVALID_MEMBER_INDEX_DESC, ParserUtil.MESSAGE_INVALID_INDEX);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_SESSION_NAME_DESC + DATE_DESC_REHEARSAL
                + INVALID_POINTS_DESC + MEMBER_INDEX_DESC_ONE, SessionName.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + SESSION_NAME_DESC_REHEARSAL + DATE_DESC_REHEARSAL
                        + POINTS_DESC_REHEARSAL + MEMBER_INDEX_DESC_ONE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSessionCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_pointsExceedMax_failure() {
        // Case where points exceed 100, expecting a failure due to max points constraint
        String pointsExceedMax = SESSION_NAME_DESC_MEETING + DATE_DESC_MEETING + " p/101" + MEMBER_INDEX_DESC_ONE;
        assertParseFailure(parser, pointsExceedMax, MESSAGE_MAX_SESSION_POINTS);
    }

}
