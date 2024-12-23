package hallpointer.address.logic.commands;

import static hallpointer.address.logic.parser.CliSyntax.PREFIX_DATE;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_MEMBER;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_NAME;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_POINTS;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_ROOM;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_SESSION_NAME;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_TAG;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_TELEGRAM;
import static hallpointer.address.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hallpointer.address.commons.core.index.Index;
import hallpointer.address.logic.commands.exceptions.CommandException;
import hallpointer.address.model.HallPointer;
import hallpointer.address.model.Model;
import hallpointer.address.model.member.Member;
import hallpointer.address.model.member.NameContainsKeywordsPredicate;
import hallpointer.address.testutil.UpdateMemberDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_TELEGRAM_AMY = "happyAmy";
    public static final String VALID_TELEGRAM_BOB = "bobTheTrain";
    public static final String VALID_ROOM_AMY = "10-10-9";
    public static final String VALID_ROOM_BOB = "3-1-90";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_SESSION_NAME_REHEARSAL = "Rehearsal W1";
    public static final String VALID_SESSION_NAME_MEETING = "Meeting W1";
    public static final String VALID_DATE_REHEARSAL = "28 Aug 2019";
    public static final String VALID_DATE_MEETING = "18 Feb 2020";
    public static final String VALID_POINTS_REHEARSAL = "20";
    public static final String VALID_POINTS_MEETING = "1";
    public static final String VALID_MEMBER_INDEX_ONE = "1";
    public static final String VALID_MEMBER_INDEX_TWO = "2";
    public static final String VALID_MEMBER_INDEX_THREE = "3";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String TELEGRAM_DESC_AMY = " " + PREFIX_TELEGRAM + VALID_TELEGRAM_AMY;
    public static final String TELEGRAM_DESC_BOB = " " + PREFIX_TELEGRAM + VALID_TELEGRAM_BOB;
    public static final String ROOM_DESC_AMY = " " + PREFIX_ROOM + VALID_ROOM_AMY;
    public static final String ROOM_DESC_BOB = " " + PREFIX_ROOM + VALID_ROOM_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String SESSION_NAME_DESC_REHEARSAL = " " + PREFIX_SESSION_NAME + VALID_SESSION_NAME_REHEARSAL;
    public static final String SESSION_NAME_DESC_MEETING = " " + PREFIX_SESSION_NAME + VALID_SESSION_NAME_MEETING;
    public static final String DATE_DESC_REHEARSAL = " " + PREFIX_DATE + VALID_DATE_REHEARSAL;
    public static final String DATE_DESC_MEETING = " " + PREFIX_DATE + VALID_DATE_MEETING;
    public static final String POINTS_DESC_REHEARSAL = " " + PREFIX_POINTS + VALID_POINTS_REHEARSAL;
    public static final String POINTS_DESC_MEETING = " " + PREFIX_POINTS + VALID_POINTS_MEETING;
    public static final String MEMBER_INDEX_DESC_ONE = " " + PREFIX_MEMBER + VALID_MEMBER_INDEX_ONE;
    public static final String MEMBER_INDEX_DESC_TWO = " " + PREFIX_MEMBER + VALID_MEMBER_INDEX_TWO;
    public static final String MEMBER_INDEX_DESC_THREE = " " + PREFIX_MEMBER + VALID_MEMBER_INDEX_THREE;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_TELEGRAM_DESC = " "
            + PREFIX_TELEGRAM + "#"; // '#' not allowed in telegrams
    public static final String INVALID_ROOM_DESC = " " + PREFIX_ROOM + "1-1-1a"; // 'a' not allowed in rooms
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_SESSION_NAME_DESC = " "
            + PREFIX_SESSION_NAME + "Competition:"; // ':' not allowed in session names
    public static final String INVALID_DATE_DESC = " " + PREFIX_DATE + "32 Aug 2009"; // Invalid date
    public static final String INVALID_POINTS_DESC = " " + PREFIX_POINTS + "-1"; // Points out of range
    public static final String INVALID_MEMBER_INDEX_DESC = " " + PREFIX_MEMBER + "-1"; // Index also out of range

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final UpdateMemberCommand.UpdateMemberDescriptor DESC_AMY;
    public static final UpdateMemberCommand.UpdateMemberDescriptor DESC_BOB;

    static {
        DESC_AMY = new UpdateMemberDescriptorBuilder().withName(VALID_NAME_AMY)
                .withTelegram(VALID_TELEGRAM_AMY).withRoom(VALID_ROOM_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new UpdateMemberDescriptorBuilder().withName(VALID_NAME_BOB)
                .withTelegram(VALID_TELEGRAM_BOB).withRoom(VALID_ROOM_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - HallPointer, the filtered member list and selected member in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        HallPointer expectedHallPointer = new HallPointer(actualModel.getHallPointer());
        List<Member> expectedFilteredList = new ArrayList<>(actualModel.getFilteredMemberList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedHallPointer, actualModel.getHallPointer());
        assertEquals(expectedFilteredList, actualModel.getFilteredMemberList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the member at the given {@code targetIndex} in the
     * {@code model}'s hall pointer.
     */
    public static void showMemberAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredMemberList().size());

        Member member = model.getFilteredMemberList().get(targetIndex.getZeroBased());
        final String[] splitName = member.getName().value.split("\\s+");
        model.updateFilteredMemberList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredMemberList().size());
    }

}
