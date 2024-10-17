// Todo when EDIT feature is implemented

//package seedu.address.logic.commands;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static seedu.address.logic.commands.CommandTestUtil.DESC_APPLE;
//import static seedu.address.logic.commands.CommandTestUtil.DESC_BOFA;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
//import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
//import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
////import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
//import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_INTERNSHIP_APPLICATION;
//import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_INTERNSHIP_APPLICATION;
//import static seedu.address.testutil.TypicalInternshipApplications.getTypicalAddressBook;
//
//import org.junit.jupiter.api.Test;
//
//import seedu.address.commons.core.index.Index;
//import seedu.address.logic.Messages;
//import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
//import seedu.address.model.AddressBook;
//import seedu.address.model.Model;
//import seedu.address.model.ModelManager;
//import seedu.address.model.UserPrefs;
//import seedu.address.model.internshipapplication.Person;
//import seedu.address.testutil.EditPersonDescriptorBuilder;
//import seedu.address.testutil.PersonBuilder;
//
///**
// * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
// */
//public class EditCommandTest {
//
//    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());


//    @Test
//    public void execute_allFieldsSpecifiedUnfilteredList_success() {
//        Person editedPerson = new PersonBuilder().build();
//        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
//        EditCommand editCommand = new EditCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, descriptor);
//
//        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages
//        .format(editedPerson));
//
//        Model expectedModel = new ModelManager(new AddressBook<InternshipApplication>(model.getAddressBook()),
//        new UserPrefs());
//        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);
//
//        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
//    }
//
//    @Test
//    public void execute_someFieldsSpecifiedUnfilteredList_success() {
//        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
//        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());
//
//        PersonBuilder personInList = new PersonBuilder(lastPerson);
//        Person editedPerson = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
//                .withTags(VALID_TAG_HUSBAND).build();
//
//        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
//                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
//        EditCommand editCommand = new EditCommand(indexLastPerson, descriptor);
//
//        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS,
//        Messages.format(editedPerson));
//
//        Model expectedModel = new ModelManager(new AddressBook<InternshipApplication>(model.getAddressBook()),
//        new UserPrefs());
//        expectedModel.setPerson(lastPerson, editedPerson);
//
//        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
//    }
//
//    @Test
//    public void execute_noFieldSpecifiedUnfilteredList_success() {
//        EditCommand editCommand = new EditCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, new EditPersonDescriptor());
//        Person editedPerson = model.getFilteredPersonList().get(INDEX_FIRST_INTERNSHIP_APPLICATION.getZeroBased());
//
//        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS,
//        Messages.format(editedPerson));
//
//        Model expectedModel = new ModelManager(new AddressBook<InternshipApplication>(model.getAddressBook()),
//        new UserPrefs());
//
//        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
//    }
////Todo when FILTER feature is implemented
////    @Test
////    public void execute_filteredList_success() {
////        showPersonAtIndex(model, INDEX_FIRST_INTERNSHIP_APPLICATION);
////
////        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_INTERNSHIP_APPLICATION.getZeroBased());
////        Person editedPerson = new PersonBuilder(personInFilteredList).withName(VALID_NAME_BOB).build();
////        EditCommand editCommand = new EditCommand(INDEX_FIRST_INTERNSHIP_APPLICATION,
////                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());
////
////        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS,
// Messages.format(editedPerson));
////
////        Model expectedModel = new ModelManager(new AddressBook<InternshipApplication>(model.getAddressBook()),
// new UserPrefs());
////        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);
////
////        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
////    }
//
//    @Test
//    public void execute_duplicatePersonUnfilteredList_failure() {
//        Person firstPerson = model.getFilteredList().get(INDEX_FIRST_INTERNSHIP_APPLICATION.getZeroBased());
//        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(firstPerson).build();
//        EditCommand editCommand = new EditCommand(INDEX_SECOND_INTERNSHIP_APPLICATION, descriptor);
//
//        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_INTERNSHIP_APPLICATION);
//    }
//
////Todo when FILTER feature is implemented
////    @Test
////    public void execute_duplicatePersonFilteredList_failure() {
////        showPersonAtIndex(model, INDEX_FIRST_INTERNSHIP_APPLICATION);
////
////        // edit person in filtered list into a duplicate in address book
////        Person personInList = model.getAddressBook().getPersonList().get(INDEX_SECOND_INTERNSHIP_APPLICATION.getZeroBased());
////        EditCommand editCommand = new EditCommand(INDEX_FIRST_INTERNSHIP_APPLICATION,
////                new EditPersonDescriptorBuilder(personInList).build());
////
////        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_INTERNSHIP_APPLICATION);
////    }
//
//    @Test
//    public void execute_invalidPersonIndexUnfilteredList_failure() {
//        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
//        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
//        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);
//
//        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_INTERNSHIP_APPLICATION_DISPLAYED_INDEX);
//    }
//
//    //Todo when FILTER feature is implemented
////    /**
////     * Edit filtered list where index is larger than size of filtered list,
////     * but smaller than size of address book
////     */
////    @Test
////    public void execute_invalidPersonIndexFilteredList_failure() {
////        showPersonAtIndex(model, INDEX_FIRST_INTERNSHIP_APPLICATION);
////        Index outOfBoundIndex = INDEX_SECOND_INTERNSHIP_APPLICATION;
////        // ensures that outOfBoundIndex is still in bounds of address book list
////        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());
////
////        EditCommand editCommand = new EditCommand(outOfBoundIndex,
////                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());
////
////        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_INTERNSHIP_APPLICATION_DISPLAYED_INDEX);
////    }
//
//    @Test
//    public void equals() {
//        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, DESC_AMY);
//
//        // same values -> returns true
//        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
//        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, copyDescriptor);
//        assertTrue(standardCommand.equals(commandWithSameValues));
//
//        // same object -> returns true
//        assertTrue(standardCommand.equals(standardCommand));
//
//        // null -> returns false
//        assertFalse(standardCommand.equals(null));
//
//        // different types -> returns false
//        assertFalse(standardCommand.equals(new ClearCommand()));
//
//        // different index -> returns false
//        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_INTERNSHIP_APPLICATION, DESC_AMY)));
//
//        // different descriptor -> returns false
//        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, DESC_BOB)));
//    }
//
//    @Test
//    public void toStringMethod() {
//        Index index = Index.fromOneBased(1);
//        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
//        EditCommand editCommand = new EditCommand(index, editPersonDescriptor);
//        String expected = EditCommand.class.getCanonicalName() + "{index=" + index + ", editPersonDescriptor="
//                + editPersonDescriptor + "}";
//        assertEquals(expected, editCommand.toString());
//    }

//}
