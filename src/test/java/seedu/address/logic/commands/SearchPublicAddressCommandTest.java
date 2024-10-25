package seedu.address.logic.commands;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PUBLIC_ADDRESS_BTC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PUBLIC_ADDRESS_ETH;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for SearchPublicAddressCommand.
 */
public class SearchPublicAddressCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    //    @Test
    //    public void execute_searchPublicAddress_success() {
    //        Person secondPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
    //        PublicAddress SamplePublicAddress = new BtcAddress(VALID_PUBLIC_ADDRESS_BTC, "BTC");
    //        SearchPublicAddressCommand searchPublicAddressCommand = new SearchPublicAddressCommand("123");
    //        String expectedMessage =
    //        String.format(SearchPublicAddressCommand.MESSAGE_SEARCH_PUBLIC_ADDRESS_SUCCESS, secondPerson);
    //
    //
    //        assertCommandSuccess(searchPublicAddressCommand, model, expectedMessage, model);
    //    }

    //    @Test
    //    public void execute_deleteRemarkUnfilteredList_success() {
    //        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
    //        Person editedPerson = new PersonBuilder(firstPerson).withRemark("").build();
    //        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_PERSON,
    //                new Remark(editedPerson.getRemark().toString()));
    //        String expectedMessage = String.format(RemarkCommand.MESSAGE_DELETE_REMARK_SUCCESS, editedPerson);
    //        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
    //        expectedModel.setPerson(firstPerson, editedPerson);
    //        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    //    }
    //    @Test
    //    public void execute_filteredList_success() {
    //        showPersonAtIndex(model, INDEX_FIRST_PERSON);
    //        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
    //        Person editedPerson = new PersonBuilder(model.getFilteredPersonList()
    //                .get(INDEX_FIRST_PERSON.getZeroBased()))
    //                .withRemark(REMARK_STUB).build();
    //        RemarkCommand remarkCommand =
    //        new RemarkCommand(INDEX_FIRST_PERSON, new Remark(editedPerson.getRemark().value));
    //        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, editedPerson);
    //        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
    //        expectedModel.setPerson(firstPerson, editedPerson);
    //        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    //    }
    //    @Test
    //    public void execute_invalidPersonIndexUnfilteredList_failure() {
    //        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
    //        RemarkCommand remarkCommand = new RemarkCommand(outOfBoundIndex, new Remark(VALID_REMARK_BOB));
    //        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    //    }
    //    /**
    //     * Edit filtered list where index is larger than size of filtered list,
    //     * but smaller than size of address book
    //     */
    //    @Test
    //    public void execute_invalidPersonIndexFilteredList_failure() {
    //        showPersonAtIndex(model, INDEX_FIRST_PERSON);
    //        Index outOfBoundIndex = INDEX_SECOND_PERSON;
    //        // ensures that outOfBoundIndex is still in bounds of address book list
    //        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());
    //        RemarkCommand remarkCommand = new RemarkCommand(outOfBoundIndex, new Remark(VALID_REMARK_BOB));
    //        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    //    }
    @Test
    public void equals() {
        final SearchPublicAddressCommand standardCommand = new SearchPublicAddressCommand(VALID_PUBLIC_ADDRESS_ETH);
        // same values -> returns true
        SearchPublicAddressCommand commandWithSameValues = new SearchPublicAddressCommand(VALID_PUBLIC_ADDRESS_ETH);
        assertEquals(standardCommand, commandWithSameValues);
        // same object -> returns true
        assertEquals(standardCommand, standardCommand);
        // null -> returns false
        assertNotEquals(null, standardCommand);
        // different types -> returns false
        assertNotEquals(standardCommand, new ClearCommand());
        // different publicAddress -> returns false
        assertNotEquals(standardCommand, new SearchPublicAddressCommand(VALID_PUBLIC_ADDRESS_BTC));
    }
}
