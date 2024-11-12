package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showBuyerAtIndex;
import static seedu.address.logic.commands.CommandTestUtil.showMeetUpAtIndex;
import static seedu.address.logic.commands.CommandTestUtil.showPropertyAtIndex;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.buyer.TypicalBuyers.getTypicalBuyerList;
import static seedu.address.testutil.meetup.TypicalMeetUps.getTypicalMeetUpList;
import static seedu.address.testutil.property.TypicalProperties.getTypicalPropertyList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ParserMode;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code SwitchParserModeCommand}.
 */
public class SwitchParserModeCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalBuyerList(), new UserPrefs(), getTypicalMeetUpList(),
                getTypicalPropertyList());
        expectedModel = new ModelManager(model.getBuyerList(), new UserPrefs(), model.getMeetUpList(),
                model.getPropertyList());
    }

    @Test
    public void execute_validParserModeSwitch_success() {
        SwitchParserModeCommand switchParserModeCommandBuyer = new SwitchParserModeCommand(ParserMode.BUYER);
        String expectedBuyerMessage = String.format(SwitchParserModeCommand.SWITCH_SUCCESS_MESSAGE + ParserMode.BUYER);
        CommandResult expectedBuyerResult = new CommandResult(expectedBuyerMessage, false, false,
                false, true, false);
        assertCommandSuccess(switchParserModeCommandBuyer, model, expectedBuyerResult, expectedModel);

        SwitchParserModeCommand switchParserModeCommandMeetUp = new SwitchParserModeCommand(ParserMode.MEETUP);
        String expectedMeetUpMessage = String.format(SwitchParserModeCommand.SWITCH_SUCCESS_MESSAGE
                + ParserMode.MEETUP);
        CommandResult expectedMeetUpResult = new CommandResult(expectedMeetUpMessage, false, false,
                true, false, false);
        assertCommandSuccess(switchParserModeCommandMeetUp, model, expectedMeetUpResult, expectedModel);

        SwitchParserModeCommand switchParserModeCommandProperty = new SwitchParserModeCommand(ParserMode.PROPERTY);
        String expectedPropertyMessage = String.format(SwitchParserModeCommand.SWITCH_SUCCESS_MESSAGE
                + ParserMode.PROPERTY);
        CommandResult expectedPropertyResult = new CommandResult(expectedPropertyMessage, false, false,
                false, false, true);
        assertCommandSuccess(switchParserModeCommandProperty, model, expectedPropertyResult, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showBuyerAtIndex(model, INDEX_FIRST);
        SwitchParserModeCommand switchParserModeCommandBuyer = new SwitchParserModeCommand(ParserMode.BUYER);
        String expectedBuyerMessage = String.format(SwitchParserModeCommand.SWITCH_SUCCESS_MESSAGE + ParserMode.BUYER);
        CommandResult expectedBuyerResult = new CommandResult(expectedBuyerMessage, false, false,
                false, true, false);
        assertCommandSuccess(switchParserModeCommandBuyer, model, expectedBuyerResult, expectedModel);

        showMeetUpAtIndex(model, INDEX_FIRST);
        SwitchParserModeCommand switchParserModeCommandMeetUp = new SwitchParserModeCommand(ParserMode.MEETUP);
        String expectedMeetUpMessage = String.format(SwitchParserModeCommand.SWITCH_SUCCESS_MESSAGE
                + ParserMode.MEETUP);
        CommandResult expectedMeetUpResult = new CommandResult(expectedMeetUpMessage, false, false,
                true, false, false);
        assertCommandSuccess(switchParserModeCommandMeetUp, model, expectedMeetUpResult, expectedModel);

        showPropertyAtIndex(model, INDEX_FIRST);
        SwitchParserModeCommand switchParserModeCommandProperty = new SwitchParserModeCommand(ParserMode.PROPERTY);
        String expectedPropertyMessage = String.format(SwitchParserModeCommand.SWITCH_SUCCESS_MESSAGE
                + ParserMode.PROPERTY);
        CommandResult expectedPropertyResult = new CommandResult(expectedPropertyMessage, false, false,
                false, false, true);
        assertCommandSuccess(switchParserModeCommandProperty, model, expectedPropertyResult, expectedModel);
    }

    @Test
    public void constructor_nullSwitchParserMode_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SwitchParserModeCommand(null));
    }

    @Test
    public void equals() {
        SwitchParserModeCommand switchBuyerCommand = new SwitchParserModeCommand(ParserMode.BUYER);
        SwitchParserModeCommand switchMeetUpCommand = new SwitchParserModeCommand(ParserMode.MEETUP);

        // same object -> returns true
        assertTrue(switchBuyerCommand.equals(switchBuyerCommand));

        // same values -> returns true
        SwitchParserModeCommand switchBuyerCommandCopy = new SwitchParserModeCommand(ParserMode.BUYER);
        assertTrue(switchBuyerCommand.equals(switchBuyerCommandCopy));

        // different types -> returns false
        assertFalse(switchBuyerCommand.equals(1));

        // null -> returns false
        assertFalse(switchBuyerCommand.equals(null));

        // different switch -> returns false
        assertFalse(switchBuyerCommand.equals(switchMeetUpCommand));
    }

    @Test
    public void toStringMethod() {
        SwitchParserModeCommand switchBuyerCommand = new SwitchParserModeCommand(ParserMode.BUYER);
        String expected = SwitchParserModeCommand.class.getCanonicalName()
                + "{switching to: =" + ParserMode.BUYER + "}";
        assertEquals(expected, switchBuyerCommand.toString());
    }
}
