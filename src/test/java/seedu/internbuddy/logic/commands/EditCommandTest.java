package seedu.internbuddy.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.internbuddy.logic.Messages.MESSAGE_INDEX_EXCEEDS_SIZE;
import static seedu.internbuddy.logic.commands.CommandTestUtil.DESC_GOOGLE;
import static seedu.internbuddy.logic.commands.CommandTestUtil.DESC_MICROSOFT;
import static seedu.internbuddy.logic.commands.CommandTestUtil.VALID_NAME_MICROSOFT;
import static seedu.internbuddy.logic.commands.CommandTestUtil.VALID_PHONE_MICROSOFT;
import static seedu.internbuddy.logic.commands.CommandTestUtil.VALID_TAG_TECH;
import static seedu.internbuddy.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.internbuddy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.internbuddy.logic.commands.CommandTestUtil.showCompanyAtIndex;
import static seedu.internbuddy.testutil.TypicalCompanies.getTypicalAddressBook;
import static seedu.internbuddy.testutil.TypicalIndexes.INDEX_FIRST_COMPANY;
import static seedu.internbuddy.testutil.TypicalIndexes.INDEX_SECOND_COMPANY;

import org.junit.jupiter.api.Test;

import seedu.internbuddy.commons.core.index.Index;
import seedu.internbuddy.logic.Messages;
import seedu.internbuddy.logic.commands.EditCommand.EditCompanyDescriptor;
import seedu.internbuddy.model.AddressBook;
import seedu.internbuddy.model.Model;
import seedu.internbuddy.model.ModelManager;
import seedu.internbuddy.model.UserPrefs;
import seedu.internbuddy.model.company.Company;
import seedu.internbuddy.testutil.CompanyBuilder;
import seedu.internbuddy.testutil.EditCompanyDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Company editedcompany = new CompanyBuilder().build();
        EditCompanyDescriptor descriptor = new EditCompanyDescriptorBuilder(editedcompany).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_COMPANY, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_COMPANY_SUCCESS,
                Messages.format(editedcompany));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setCompany(model.getFilteredCompanyList().get(0), editedcompany);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastCompany = Index.fromOneBased(model.getFilteredCompanyList().size());
        Company lastCompany = model.getFilteredCompanyList().get(indexLastCompany.getZeroBased());

        CompanyBuilder companyInList = new CompanyBuilder(lastCompany);
        Company editedcompany = companyInList.withName(VALID_NAME_MICROSOFT).withPhone(VALID_PHONE_MICROSOFT)
                .withTags(VALID_TAG_TECH).build();

        EditCompanyDescriptor descriptor = new EditCompanyDescriptorBuilder().withName(VALID_NAME_MICROSOFT)
                .withPhone(VALID_PHONE_MICROSOFT).withTags(VALID_TAG_TECH).build();
        EditCommand editCommand = new EditCommand(indexLastCompany, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_COMPANY_SUCCESS,
                Messages.format(editedcompany));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setCompany(lastCompany, editedcompany);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_COMPANY, new EditCompanyDescriptor());
        Company editedCompany = model.getFilteredCompanyList().get(INDEX_FIRST_COMPANY.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_COMPANY_SUCCESS,
                Messages.format(editedCompany));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showCompanyAtIndex(model, INDEX_FIRST_COMPANY);

        Company companyInFilteredList = model.getFilteredCompanyList().get(INDEX_FIRST_COMPANY.getZeroBased());
        Company editedcompany = new CompanyBuilder(companyInFilteredList).withName(VALID_NAME_MICROSOFT).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_COMPANY,
                new EditCompanyDescriptorBuilder().withName(VALID_NAME_MICROSOFT).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_COMPANY_SUCCESS,
                Messages.format(editedcompany));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setCompany(model.getFilteredCompanyList().get(0), editedcompany);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateCompanyUnfilteredList_failure() {
        Company firstcompany = model.getFilteredCompanyList().get(INDEX_FIRST_COMPANY.getZeroBased());
        EditCompanyDescriptor descriptor = new EditCompanyDescriptorBuilder(firstcompany).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_COMPANY, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_COMPANY);
    }

    @Test
    public void execute_duplicateCompanyFilteredList_failure() {
        showCompanyAtIndex(model, INDEX_FIRST_COMPANY);

        // edit company in filtered list into a duplicate in address book
        Company companyInList = model.getAddressBook().getCompanyList().get(INDEX_SECOND_COMPANY.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_COMPANY,
                new EditCompanyDescriptorBuilder(companyInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_COMPANY);
    }

    @Test
    public void execute_invalidCompanyIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCompanyList().size() + 1);
        EditCompanyDescriptor descriptor = new EditCompanyDescriptorBuilder().withName(VALID_NAME_MICROSOFT).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);
        String expectedMessage = String.format(MESSAGE_INDEX_EXCEEDS_SIZE,
                model.getFilteredCompanyList().size());

        assertCommandFailure(editCommand, model, expectedMessage);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidCompanyIndexFilteredList_failure() {
        showCompanyAtIndex(model, INDEX_FIRST_COMPANY);
        Index outOfBoundIndex = INDEX_SECOND_COMPANY;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getCompanyList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditCompanyDescriptorBuilder().withName(VALID_NAME_MICROSOFT).build());
        String expectedMessage = String.format(MESSAGE_INDEX_EXCEEDS_SIZE,
                model.getFilteredCompanyList().size());

        assertCommandFailure(editCommand, model, expectedMessage);
    }

    @Test
    public void test_setCompanyStatus() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_COMPANY, new EditCompanyDescriptor());
        Company companyInList = model.getAddressBook().getCompanyList().get(INDEX_FIRST_COMPANY.getZeroBased());
        Company updatedCompany = editCommand.setStatusApplied(companyInList);
        assertFalse(companyInList.equals(updatedCompany));

        Company updatedCompany2 = editCommand.setStatusClosed(updatedCompany);
        assertFalse(updatedCompany2.equals(updatedCompany));
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_COMPANY, DESC_GOOGLE);

        // same values -> returns true
        EditCompanyDescriptor copyDescriptor = new EditCompanyDescriptor(DESC_GOOGLE);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_COMPANY, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_COMPANY, DESC_GOOGLE)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_COMPANY, DESC_MICROSOFT)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditCompanyDescriptor editCompanyDescriptor = new EditCompanyDescriptor();
        EditCommand editCommand = new EditCommand(index, editCompanyDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{index=" + index + ", editCompanyDescriptor="
                + editCompanyDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}
