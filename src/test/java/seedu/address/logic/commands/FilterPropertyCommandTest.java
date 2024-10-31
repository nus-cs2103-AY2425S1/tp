package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MATCHINGPRICE_ADMIRALTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MATCHINGPRICE_BEDOK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_CONDO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_HDB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalClients.getTypicalClientBook;
import static seedu.address.testutil.TypicalMeetings.getTypicalMeetingBook;
import static seedu.address.testutil.TypicalProperty.getTypicalPropertyBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.property.MatchingPrice;
import seedu.address.model.property.Type;

public class FilterPropertyCommandTest {
    private Model model = new ModelManager(new UserPrefs(), getTypicalPropertyBook(),
            getTypicalClientBook(), getTypicalMeetingBook());

    @Test
    public void execute_matchingName_filterSuccessful() {
        // Test case where a property's type matches the given type (valid match)
        String typePrefix = "CONDO";
        FilterPropertyCommand command = new FilterPropertyCommand(new Type(typePrefix), null, null);

        Model expectedModel = new ModelManager(new UserPrefs(), model.getPropertyBook(),
                model.getClientBook(), model.getMeetingBook());
        expectedModel.updateFilteredPropertyList(property ->
                property.getType().toString().matches("(?i)^" + typePrefix + ".*"));

        assertCommandSuccess(command,
                model, "Listed filtered properties",
                expectedModel);
    }

    @Test
    public void execute_caseInsensitiveMatching_filterSuccessful() {
        // Test case where the filter should work regardless of case (case insensitive)
        String typePrefix = "condO";
        FilterPropertyCommand command = new FilterPropertyCommand(new Type(typePrefix), null, null);

        Model expectedModel = new ModelManager(new UserPrefs(), model.getPropertyBook(),
                model.getClientBook(), model.getMeetingBook());
        expectedModel.updateFilteredPropertyList(property ->
                property.getType().toString().matches("(?i)^" + typePrefix + ".*"));

        assertCommandSuccess(command,
                model, "Listed filtered properties", expectedModel);
    }

    @Test
    public void execute_lteMatching_filterSuccessful() {
        FilterPropertyCommand command = new FilterPropertyCommand(null,
                new MatchingPrice(VALID_MATCHINGPRICE_ADMIRALTY), null);

        Model expectedModel = new ModelManager(new UserPrefs(), model.getPropertyBook(),
                model.getClientBook(), model.getMeetingBook());
        expectedModel.updateFilteredPropertyList(property ->
                property.getType().toString().matches("(?i)^" + "" + ".*"));

        assertCommandSuccess(command,
                model, "Listed filtered properties", expectedModel);
    }

    @Test
    public void execute_gteMatching_filterSuccessful() {
        FilterPropertyCommand command = new FilterPropertyCommand(null, null,
                new MatchingPrice(VALID_MATCHINGPRICE_ADMIRALTY));

        Model expectedModel = new ModelManager(new UserPrefs(), model.getPropertyBook(),
                model.getClientBook(), model.getMeetingBook());
        expectedModel.updateFilteredPropertyList(property ->
                property.getType().toString().matches("(?i)^" + "" + ".*"));

        assertCommandSuccess(command,
                model, "Listed filtered properties", expectedModel);
    }

    @Test
    public void execute_lteGteEqualsMatching_filterSuccessful() {
        FilterPropertyCommand command = new FilterPropertyCommand(null,
                new MatchingPrice(VALID_MATCHINGPRICE_ADMIRALTY), new MatchingPrice(VALID_MATCHINGPRICE_ADMIRALTY));

        Model expectedModel = new ModelManager(new UserPrefs(), model.getPropertyBook(),
                model.getClientBook(), model.getMeetingBook());
        expectedModel.updateFilteredPropertyList(property ->
                property.getType().toString().matches("(?i)^" + "" + ".*"));

        assertCommandSuccess(command,
                model, "Listed filtered properties", expectedModel);
    }

    @Test
    public void execute_lteGteCrossMatching_filterSuccessful() {
        // Test case where the filter should work regardless of case (case insensitive)
        FilterPropertyCommand command = new FilterPropertyCommand(null,
                new MatchingPrice(VALID_MATCHINGPRICE_BEDOK), new MatchingPrice(VALID_MATCHINGPRICE_ADMIRALTY));

        Model expectedModel = new ModelManager(new UserPrefs(), model.getPropertyBook(),
                model.getClientBook(), model.getMeetingBook());
        expectedModel.updateFilteredPropertyList(property ->
                property.getType().toString().matches("(?i)^" + "" + ".*"));

        assertCommandSuccess(command,
                model, "Listed filtered properties", expectedModel);
    }

    @Test
    public void execute_lteGteNoCrossMatching_filterSuccessful() {
        FilterPropertyCommand command = new FilterPropertyCommand(null,
                new MatchingPrice(VALID_MATCHINGPRICE_ADMIRALTY), new MatchingPrice(VALID_MATCHINGPRICE_BEDOK));

        Model expectedModel = new ModelManager(new UserPrefs(), model.getPropertyBook(),
                model.getClientBook(), model.getMeetingBook());
        expectedModel.updateFilteredPropertyList(property ->
                property.getType().toString().matches("(?i)^" + "" + ".*"));

        assertCommandSuccess(command,
                model, "Listed filtered properties", expectedModel);
    }

    @Test
    public void execute_noMissingCommandsMatching_filterSuccessful() {
        // Test case where the filter should work regardless of case (case insensitive)
        String typePrefix = "CONDO";
        FilterPropertyCommand command = new FilterPropertyCommand(new Type(typePrefix),
                new MatchingPrice(VALID_MATCHINGPRICE_ADMIRALTY), new MatchingPrice(VALID_MATCHINGPRICE_BEDOK));

        Model expectedModel = new ModelManager(new UserPrefs(), model.getPropertyBook(),
                model.getClientBook(), model.getMeetingBook());
        expectedModel.updateFilteredPropertyList(property ->
                property.getType().toString().matches("(?i)^" + typePrefix + ".*"));

        assertCommandSuccess(command,
                model, "Listed filtered properties", expectedModel);
    }


    @Test
    public void equals() {
        final FilterPropertyCommand standardCommand = new FilterPropertyCommand(new Type(VALID_TYPE_HDB), null, null);
        // same values -> returns true
        FilterPropertyCommand commandWithSameValues = new FilterPropertyCommand(new Type(VALID_TYPE_HDB), null, null);
        assertTrue(standardCommand.equals(commandWithSameValues));
        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        // null -> returns false
        assertFalse(standardCommand.equals(null));
        // different types -> returns false
        assertFalse(standardCommand.equals(new ExitCommand()));
        // different name -> returns false
        assertFalse(standardCommand.equals(new FilterPropertyCommand(new Type((VALID_TYPE_CONDO)), null, null)));
    }
}
