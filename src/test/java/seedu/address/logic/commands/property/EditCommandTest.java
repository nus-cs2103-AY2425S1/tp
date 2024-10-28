package seedu.address.logic.commands.property;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ALAN;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BRENDA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BRENDA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LANDLORD_NAME_BRENDA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BRENDA;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPropertyAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.buyer.TypicalBuyers.getTypicalBuyerList;
import static seedu.address.testutil.meetup.TypicalMeetUps.getTypicalMeetUpList;
import static seedu.address.testutil.property.TypicalProperties.getTypicalPropertyList;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.property.EditCommand.EditPropertyDescriptor;
import seedu.address.model.BuyerList;
import seedu.address.model.MeetUpList;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PropertyList;
import seedu.address.model.UserPrefs;
import seedu.address.model.property.Property;
import seedu.address.testutil.property.EditPropertyDescriptorBuilder;
import seedu.address.testutil.property.PropertyBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalBuyerList(), new UserPrefs(), getTypicalMeetUpList(),
            getTypicalPropertyList());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Property editedProperty = new PropertyBuilder().build();
        EditPropertyDescriptor descriptor = new EditPropertyDescriptorBuilder(editedProperty).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PROPERTY_SUCCESS,
                Messages.format(editedProperty));

        Model expectedModel = new ModelManager(new BuyerList(model.getBuyerList()), new UserPrefs(),
                new MeetUpList(model.getMeetUpList()), new PropertyList(model.getPropertyList()));
        expectedModel.setProperty(model.getFilteredPropertyList().get(0), editedProperty);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastProperty = Index.fromOneBased(model.getFilteredPropertyList().size());
        Property lastProperty = model.getFilteredPropertyList().get(indexLastProperty.getZeroBased());

        PropertyBuilder propertyInList = new PropertyBuilder(lastProperty);
        Property editedProperty = propertyInList.withLandlordName(VALID_LANDLORD_NAME_BRENDA)
                .withPhone(VALID_PHONE_BRENDA).withAddress(VALID_ADDRESS_BRENDA).build();

        EditPropertyDescriptor descriptor = new EditPropertyDescriptorBuilder()
                .withLandlordName(VALID_LANDLORD_NAME_BRENDA).withPhone(VALID_PHONE_BRENDA)
                .withAddress(VALID_ADDRESS_BRENDA).build();
        EditCommand editCommand = new EditCommand(indexLastProperty, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PROPERTY_SUCCESS,
                Messages.format(editedProperty));

        Model expectedModel = new ModelManager(new BuyerList(model.getBuyerList()), new UserPrefs(),
                new MeetUpList(model.getMeetUpList()), new PropertyList(model.getPropertyList()));
        expectedModel.setProperty(lastProperty, editedProperty);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST, new EditPropertyDescriptor());
        Property editedProperty = model.getFilteredPropertyList().get(INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PROPERTY_SUCCESS,
                Messages.format(editedProperty));

        Model expectedModel = new ModelManager(new BuyerList(model.getBuyerList()), new UserPrefs(),
                new MeetUpList(model.getMeetUpList()), new PropertyList(model.getPropertyList()));

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPropertyAtIndex(model, INDEX_FIRST);

        Property propertyInFilteredList = model.getFilteredPropertyList().get(INDEX_FIRST.getZeroBased());
        Property editedProperty = new PropertyBuilder(propertyInFilteredList)
                .withLandlordName(VALID_LANDLORD_NAME_BRENDA).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST,
                new EditPropertyDescriptorBuilder().withLandlordName(VALID_LANDLORD_NAME_BRENDA).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PROPERTY_SUCCESS,
                Messages.format(editedProperty));

        Model expectedModel = new ModelManager(new BuyerList(model.getBuyerList()), new UserPrefs(),
                new MeetUpList(model.getMeetUpList()), new PropertyList(model.getPropertyList()));
        expectedModel.setProperty(model.getFilteredPropertyList().get(0), editedProperty);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePropertyUnfilteredList_failure() {
        Property firstProperty = model.getFilteredPropertyList().get(INDEX_FIRST.getZeroBased());
        EditPropertyDescriptor descriptor = new EditPropertyDescriptorBuilder(firstProperty).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PROPERTY);
    }

    @Test
    public void execute_duplicatePropertyFilteredList_failure() {
        showPropertyAtIndex(model, INDEX_FIRST);

        // edit property in filtered list into a duplicate in property list
        Property propertyInList = model.getPropertyList().getPropertyList().get(INDEX_SECOND.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST,
                new EditPropertyDescriptorBuilder(propertyInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PROPERTY);
    }

    @Test
    public void execute_invalidPropertyIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPropertyList().size() + 1);
        EditPropertyDescriptor descriptor = new EditPropertyDescriptorBuilder()
                .withLandlordName(VALID_LANDLORD_NAME_BRENDA).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PROPERTY_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of property list
     */
    @Test
    public void execute_invalidPropertyIndexFilteredList_failure() {
        showPropertyAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of property list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getPropertyList().getPropertyList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditPropertyDescriptorBuilder().withLandlordName(VALID_LANDLORD_NAME_BRENDA).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PROPERTY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST, DESC_ALAN);

        // same values -> returns true
        EditPropertyDescriptor copyDescriptor = new EditPropertyDescriptor(DESC_ALAN);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND, DESC_ALAN)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST, DESC_BRENDA)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditPropertyDescriptor editPropertyDescriptor = new EditPropertyDescriptor();
        EditCommand editCommand = new EditCommand(index, editPropertyDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{index=" + index + ", editPropertyDescriptor="
                + editPropertyDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}
