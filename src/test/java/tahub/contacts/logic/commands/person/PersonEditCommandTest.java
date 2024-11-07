//package tahub.contacts.logic.commands.person;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static tahub.contacts.logic.commands.CommandTestUtil.DESC_AMY;
//import static tahub.contacts.logic.commands.CommandTestUtil.DESC_BOB;
//import static tahub.contacts.logic.commands.CommandTestUtil.VALID_NAME_BOB;
//import static tahub.contacts.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
//import static tahub.contacts.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
//import static tahub.contacts.logic.commands.CommandTestUtil.assertCommandFailure;
//import static tahub.contacts.logic.commands.CommandTestUtil.assertCommandSuccess;
//import static tahub.contacts.logic.commands.CommandTestUtil.showPersonAtIndex;
//import static tahub.contacts.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
//import static tahub.contacts.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
//import static tahub.contacts.testutil.TypicalPersons.getTypicalAddressBook;
//
//import org.junit.jupiter.api.Test;
//
//import tahub.contacts.commons.core.index.Index;
//import tahub.contacts.logic.Messages;
//import tahub.contacts.logic.commands.ClearCommand;
//import tahub.contacts.logic.commands.person.PersonEditCommand.EditPersonDescriptor;
//import tahub.contacts.model.AddressBook;
//import tahub.contacts.model.Model;
//import tahub.contacts.model.ModelManager;
//import tahub.contacts.model.UserPrefs;
//import tahub.contacts.model.course.UniqueCourseList;
//import tahub.contacts.model.person.Person;
//import tahub.contacts.model.studentcourseassociation.StudentCourseAssociationList;
//import tahub.contacts.testutil.EditPersonDescriptorBuilder;
//import tahub.contacts.testutil.PersonBuilder;
//
///**
// * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
// */
//public class PersonEditCommandTest {
//
//    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new UniqueCourseList(),
//            new StudentCourseAssociationList());
//
//    @Test
//    public void execute_allFieldsSpecifiedUnfilteredList_success() {
//        Person editedPerson = model.getFilteredPersonList().get(0);
//        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
//        PersonEditCommand personEditCommand = new PersonEditCommand(INDEX_FIRST_PERSON, descriptor);
//
//        String expectedMessage = String.format(PersonEditCommand.MESSAGE_EDIT_PERSON_SUCCESS,
//                Messages.format(editedPerson));
//
//        Model expectedModel =
//                new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(), model.getCourseList(),
//                        model.getScaList());
//        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);
//
//        assertCommandSuccess(personEditCommand, model, expectedMessage, expectedModel);
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
//        PersonEditCommand personEditCommand = new PersonEditCommand(indexLastPerson, descriptor);
//
//        String expectedMessage = String.format(PersonEditCommand.MESSAGE_EDIT_PERSON_SUCCESS,
//                Messages.format(editedPerson));
//
//        Model expectedModel =
//                new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(), model.getCourseList(),
//                        model.getScaList());
//        expectedModel.setPerson(lastPerson, editedPerson);
//
//        assertCommandSuccess(personEditCommand, model, expectedMessage, expectedModel);
//    }
//
//    @Test
//    public void execute_noFieldSpecifiedUnfilteredList_success() {
//        PersonEditCommand personEditCommand = new PersonEditCommand(INDEX_FIRST_PERSON, new EditPersonDescriptor());
//        Person editedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
//
//        String expectedMessage = String.format(PersonEditCommand.MESSAGE_EDIT_PERSON_SUCCESS,
//                Messages.format(editedPerson));
//
//        Model expectedModel =
//                new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(), model.getCourseList(),
//                        model.getScaList());
//
//        assertCommandSuccess(personEditCommand, model, expectedMessage, expectedModel);
//    }
//
//    @Test
//    public void execute_filteredList_success() {
//        showPersonAtIndex(model, INDEX_FIRST_PERSON);
//
//        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
//        Person editedPerson = new PersonBuilder(personInFilteredList).withName(VALID_NAME_BOB).build();
//        PersonEditCommand personEditCommand = new PersonEditCommand(INDEX_FIRST_PERSON,
//                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());
//
//        String expectedMessage = String.format(PersonEditCommand.MESSAGE_EDIT_PERSON_SUCCESS,
//                Messages.format(editedPerson));
//
//        Model expectedModel =
//                new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(), model.getCourseList(),
//                        model.getScaList());
//        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);
//
//        assertCommandSuccess(personEditCommand, model, expectedMessage, expectedModel);
//    }
//
//    @Test
//    public void execute_invalidPersonIndexUnfilteredList_failure() {
//        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
//        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
//        PersonEditCommand personEditCommand = new PersonEditCommand(outOfBoundIndex, descriptor);
//
//        assertCommandFailure(personEditCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
//    }
//
//
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
//
//        PersonEditCommand personEditCommand = new PersonEditCommand(outOfBoundIndex,
//                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());
//
//        assertCommandFailure(personEditCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
//    }
//
//    @Test
//    public void equals() {
//        final PersonEditCommand standardCommand = new PersonEditCommand(INDEX_FIRST_PERSON, DESC_AMY);
//
//        // same values -> returns true
//        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
//        PersonEditCommand commandWithSameValues = new PersonEditCommand(INDEX_FIRST_PERSON, copyDescriptor);
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
//        assertFalse(standardCommand.equals(new PersonEditCommand(INDEX_SECOND_PERSON, DESC_AMY)));
//
//        // different descriptor -> returns false
//        assertFalse(standardCommand.equals(new PersonEditCommand(INDEX_FIRST_PERSON, DESC_BOB)));
//    }
//
//    @Test
//    public void toStringMethod() {
//        Index index = Index.fromOneBased(1);
//        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
//        PersonEditCommand personEditCommand = new PersonEditCommand(index, editPersonDescriptor);
//        String expected = PersonEditCommand.class.getCanonicalName() + "{index=" + index + ", editPersonDescriptor="
//                + editPersonDescriptor + "}";
//        assertEquals(expected, personEditCommand.toString());
//    }
//
//}
