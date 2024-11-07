package seedu.sellsavvy.logic.commands.customercommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.sellsavvy.commons.util.StringUtil.normalise;
import static seedu.sellsavvy.logic.Messages.MESSAGE_SIMILAR_NAME_WARNING;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.DESC_AMY;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.DESC_BOB;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.VALID_NAME_BOB;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.VALID_PHONE_BOB;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.assertCommandFailure;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.assertCommandSuccess;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.showCustomerAtIndex;
import static seedu.sellsavvy.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.sellsavvy.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.sellsavvy.testutil.TypicalCustomers.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.sellsavvy.commons.core.index.Index;
import seedu.sellsavvy.logic.Messages;
import seedu.sellsavvy.logic.commands.generalcommands.ClearCommand;
import seedu.sellsavvy.logic.commands.customercommands.EditCustomerCommand.EditCustomerDescriptor;
import seedu.sellsavvy.model.AddressBook;
import seedu.sellsavvy.model.Model;
import seedu.sellsavvy.model.ModelManager;
import seedu.sellsavvy.model.UserPrefs;
import seedu.sellsavvy.model.customer.Customer;
import seedu.sellsavvy.testutil.CustomerBuilder;
import seedu.sellsavvy.testutil.EditCustomerDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCustomerCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Customer editedCustomer = new CustomerBuilder().build();
        EditCustomerDescriptor descriptor = new EditCustomerDescriptorBuilder(editedCustomer).build();
        EditCustomerCommand editCustomerCommand = new EditCustomerCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(EditCustomerCommand.MESSAGE_EDIT_CUSTOMER_SUCCESS,
                Messages.format(editedCustomer));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setCustomer(model.getFilteredCustomerList().get(0), editedCustomer);

        assertCommandSuccess(editCustomerCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastCustomer = Index.fromOneBased(model.getFilteredCustomerList().size());
        Customer lastCustomer = model.getFilteredCustomerList().get(indexLastCustomer.getZeroBased());

        CustomerBuilder customerInList = new CustomerBuilder(lastCustomer);
        Customer editedCustomer = customerInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditCustomerDescriptor descriptor = new EditCustomerDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCustomerCommand editCustomerCommand = new EditCustomerCommand(indexLastCustomer, descriptor);

        String expectedMessage = String.format(EditCustomerCommand.MESSAGE_EDIT_CUSTOMER_SUCCESS,
                Messages.format(editedCustomer));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setCustomer(lastCustomer, editedCustomer);

        assertCommandSuccess(editCustomerCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_similarTags_warning() {
        Index indexLastCustomer = Index.fromOneBased(model.getFilteredCustomerList().size());
        Customer lastCustomer = model.getFilteredCustomerList().get(indexLastCustomer.getZeroBased());

        CustomerBuilder customerInList = new CustomerBuilder(lastCustomer);
        Customer editedCustomer = customerInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_HUSBAND.toUpperCase()).build();

        EditCustomerDescriptor descriptor = new EditCustomerDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_HUSBAND.toUpperCase()).build();
        EditCustomerCommand editCustomerCommand = new EditCustomerCommand(indexLastCustomer, descriptor);

        String expectedMessage = EditCustomerCommand.MESSAGE_SIMILAR_TAGS_WARNING
                + String.format(EditCustomerCommand.MESSAGE_EDIT_CUSTOMER_SUCCESS,
                Messages.format(editedCustomer));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setCustomer(lastCustomer, editedCustomer);

        assertCommandSuccess(editCustomerCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_similarCustomer_warning() {
        Index indexLastCustomer = Index.fromOneBased(model.getFilteredCustomerList().size());
        Customer lastCustomer = model.getFilteredCustomerList().get(indexLastCustomer.getZeroBased());
        Customer firstCustomer = model.getFilteredCustomerList().get(INDEX_FIRST.getZeroBased());
        String similarName = normalise(firstCustomer.getName().fullName);

        CustomerBuilder customerInList = new CustomerBuilder(lastCustomer);
        Customer editedCustomer = customerInList.withName(similarName).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditCustomerDescriptor descriptor = new EditCustomerDescriptorBuilder().withName(similarName)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCustomerCommand editCustomerCommand = new EditCustomerCommand(indexLastCustomer, descriptor);

        String expectedMessage = MESSAGE_SIMILAR_NAME_WARNING
                + String.format(EditCustomerCommand.MESSAGE_EDIT_CUSTOMER_SUCCESS,
                Messages.format(editedCustomer));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setCustomer(lastCustomer, editedCustomer);

        assertCommandSuccess(editCustomerCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_customerEditedWithOrderDisplayed_success() {
        // ensures that the selectedCustomer got updated
        Index indexLastCustomer = Index.fromOneBased(model.getFilteredCustomerList().size());
        Customer lastCustomer = model.getFilteredCustomerList().get(indexLastCustomer.getZeroBased());
        model.updateSelectedCustomer(lastCustomer);

        CustomerBuilder customerInList = new CustomerBuilder(lastCustomer);
        Customer editedCustomer = customerInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditCustomerDescriptor descriptor = new EditCustomerDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCustomerCommand editCustomerCommand = new EditCustomerCommand(indexLastCustomer, descriptor);

        String expectedMessage = String.format(EditCustomerCommand.MESSAGE_EDIT_CUSTOMER_SUCCESS,
                Messages.format(editedCustomer));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setCustomer(lastCustomer, editedCustomer);
        expectedModel.updateSelectedCustomer(editedCustomer);

        assertCommandSuccess(editCustomerCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCustomerCommand editCustomerCommand = new EditCustomerCommand(INDEX_FIRST, new EditCustomerDescriptor());
        Customer editedCustomer = model.getFilteredCustomerList().get(INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(EditCustomerCommand.MESSAGE_EDIT_CUSTOMER_SUCCESS,
                Messages.format(editedCustomer));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editCustomerCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showCustomerAtIndex(model, INDEX_FIRST);

        Customer customerInFilteredList = model.getFilteredCustomerList().get(INDEX_FIRST.getZeroBased());
        Customer editedCustomer = new CustomerBuilder(customerInFilteredList).withName(VALID_NAME_BOB).build();
        EditCustomerCommand editCustomerCommand = new EditCustomerCommand(INDEX_FIRST,
                new EditCustomerDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCustomerCommand.MESSAGE_EDIT_CUSTOMER_SUCCESS,
                Messages.format(editedCustomer));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setCustomer(model.getFilteredCustomerList().get(0), editedCustomer);

        assertCommandSuccess(editCustomerCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateCustomerUnfilteredList_failure() {
        Customer firstCustomer = model.getFilteredCustomerList().get(INDEX_FIRST.getZeroBased());
        EditCustomerDescriptor descriptor = new EditCustomerDescriptorBuilder(firstCustomer).build();
        EditCustomerCommand editCustomerCommand = new EditCustomerCommand(INDEX_SECOND, descriptor);

        assertCommandFailure(editCustomerCommand, model, EditCustomerCommand.MESSAGE_DUPLICATE_CUSTOMER);
    }

    @Test
    public void execute_duplicateCustomerFilteredList_failure() {
        showCustomerAtIndex(model, INDEX_FIRST);

        // edit customer in filtered list into a duplicate in address book
        Customer customerInList = model.getAddressBook().getCustomerList().get(INDEX_SECOND.getZeroBased());
        EditCustomerCommand editCustomerCommand = new EditCustomerCommand(INDEX_FIRST,
                new EditCustomerDescriptorBuilder(customerInList).build());

        assertCommandFailure(editCustomerCommand, model, EditCustomerCommand.MESSAGE_DUPLICATE_CUSTOMER);
    }

    @Test
    public void execute_invalidCustomerIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCustomerList().size() + 1);
        EditCustomerDescriptor descriptor = new EditCustomerDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCustomerCommand editCustomerCommand = new EditCustomerCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCustomerCommand, model, Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
    }

    /**
     * Edits filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidCustomerIndexFilteredList_failure() {
        showCustomerAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getCustomerList().size());

        EditCustomerCommand editCustomerCommand = new EditCustomerCommand(outOfBoundIndex,
                new EditCustomerDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCustomerCommand, model, Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCustomerCommand standardCommand = new EditCustomerCommand(INDEX_FIRST, DESC_AMY);

        // same values -> returns true
        EditCustomerDescriptor copyDescriptor = new EditCustomerDescriptor(DESC_AMY);
        EditCustomerCommand commandWithSameValues = new EditCustomerCommand(INDEX_FIRST, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCustomerCommand(INDEX_SECOND, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCustomerCommand(INDEX_FIRST, DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditCustomerDescriptor editCustomerDescriptor = new EditCustomerDescriptor();
        EditCustomerCommand editCustomerCommand = new EditCustomerCommand(index, editCustomerDescriptor);
        String expected = EditCustomerCommand.class.getCanonicalName() + "{index=" + index + ", editCustomerDescriptor="
                + editCustomerDescriptor + "}";
        assertEquals(expected, editCustomerCommand.toString());
    }

}
