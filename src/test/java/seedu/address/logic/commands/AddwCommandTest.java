package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBookFilterWithWeddings;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.NameMatchesKeywordPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Wedding;
import seedu.address.testutil.WeddingBuilder;

public class AddwCommandTest {

    private AddwCommandTest.ModelStubAcceptingWeddingAdded modelStub;
    @BeforeEach
    public void setUp() {
        modelStub = new AddwCommandTest.ModelStubAcceptingWeddingAdded(getTypicalAddressBookFilterWithWeddings());
    }

    @Test
    public void constructor_nullWedding_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddwCommand(null, null, null));
    }

    @Test
    public void execute_indexBasedWeddingAcceptedByModel_addSuccessful() throws Exception {
        // given date and given venue
        Wedding weddingToAdd = new WeddingBuilder().build();
        Person tobeClient = modelStub.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        tobeClient.resetOwnWedding(tobeClient.getOwnWedding());

        AddwCommand addwCommand = new AddwCommand(INDEX_FIRST_PERSON, null, weddingToAdd);
        CommandResult commandResult = addwCommand.execute(modelStub);

        tobeClient.resetOwnWedding(tobeClient.getOwnWedding());
        tobeClient.setOwnWedding(weddingToAdd);
        weddingToAdd.setClient(tobeClient);

        assertEquals(String.format(AddwCommand.MESSAGE_SUCCESS, Messages.format(weddingToAdd)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(weddingToAdd), modelStub.weddingsAdded);
    }
    @Test
    public void execute_indexBasedWeddingWithNullDateAcceptedByModel_addSuccessful() throws Exception {
        // null date and null venue
        Person tobeClient = modelStub.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        tobeClient.resetOwnWedding(tobeClient.getOwnWedding());
        tobeClient.resetOwnWedding(tobeClient.getOwnWedding());
        modelStub = new AddwCommandTest.ModelStubAcceptingWeddingAdded(getTypicalAddressBookFilterWithWeddings());
        Wedding weddingToAdd = new WeddingBuilder().withDate(null).withVenue(null).build();

        AddwCommand addwCommand = new AddwCommand(INDEX_FIRST_PERSON, null, weddingToAdd);
        CommandResult commandResult = addwCommand.execute(modelStub);

        tobeClient.setOwnWedding(weddingToAdd);
        weddingToAdd.setClient(tobeClient);

        assertEquals(String.format(AddwCommand.MESSAGE_SUCCESS, Messages.format(weddingToAdd)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(weddingToAdd), modelStub.weddingsAdded);
    }

    // Duplicate wedding checks for name, client, date and venue
    @Test
    public void execute_duplicateWedding_throwsCommandException() {
        Person tobeClient = modelStub.getAddressBook().getPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Wedding weddingToAdd = new WeddingBuilder().build();
        modelStub.addWedding(weddingToAdd);
        tobeClient.setOwnWedding(weddingToAdd);
        weddingToAdd.setClient(tobeClient);

        tobeClient.resetOwnWedding(tobeClient.getOwnWedding()); //turns ownWedding to null

        AddwCommand addwCommand = new AddwCommand(INDEX_FIRST_PERSON, null, weddingToAdd);

        assertThrows(CommandException.class,
                AddwCommand.MESSAGE_DUPLICATE_WEDDING, () -> addwCommand.execute(modelStub));
    }

    @Test
    public void execute_alreadyClient_throwsCommandException() {
        Wedding createdWedding = new WeddingBuilder().build();
        Wedding weddingToAdd = new WeddingBuilder().build();
        Person toBeClient = modelStub.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        toBeClient.resetOwnWedding(null);

        toBeClient.setOwnWedding(createdWedding);

        AddwCommand addwCommand = new AddwCommand(INDEX_FIRST_PERSON, null, weddingToAdd);

        assertThrows(CommandException.class,
                AddwCommand.MESSAGE_ALREADY_A_CLIENT, () -> addwCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Wedding aliceWedding = new WeddingBuilder().withName("Alice's Wedding").build();
        Wedding bobWedding = new WeddingBuilder().withName("Bob's Wedding").build();

        AddwCommand addAliceIndexWedding = new AddwCommand(INDEX_FIRST_PERSON, null, aliceWedding);
        String keyword = "Alice";
        NameMatchesKeywordPredicate predicate =
                new NameMatchesKeywordPredicate(Arrays.asList(keyword.split("\\s+")));
        AddwCommand addAliceNameWedding = new AddwCommand(null, predicate, aliceWedding);

        AddwCommand addBobIndexWedding = new AddwCommand(INDEX_FIRST_PERSON, null, bobWedding);
        keyword = "Bob";
        predicate = new NameMatchesKeywordPredicate(Arrays.asList(keyword.split("\\s+")));
        AddwCommand addBobNameWedding = new AddwCommand(null, predicate, bobWedding);

        // same object -> returns true
        assertTrue(addAliceIndexWedding.equals(addAliceIndexWedding));

        // same values -> returns true
        AddwCommand addAliceIndexWeddingCopy = new AddwCommand(INDEX_FIRST_PERSON, null, aliceWedding);
        assertTrue(addAliceIndexWeddingCopy.equals(addAliceIndexWeddingCopy));

        // different types -> returns false
        assertFalse(addAliceIndexWeddingCopy.equals(1));

        // null -> returns false
        assertFalse(addAliceIndexWeddingCopy.equals(null));

        // different formats
        assertFalse(addAliceIndexWedding.equals(addAliceNameWedding));

        // different wedding -> returns false
        assertFalse(addAliceIndexWedding.equals(addBobIndexWedding));

        AddwCommand nullIndexAddwCommand1 = new AddwCommand(null, predicate, aliceWedding);
        AddwCommand nullIndexAddwCommand2 = new AddwCommand(null, predicate, aliceWedding);
        AddwCommand nullPredicateAddwCommand1 = new AddwCommand(INDEX_FIRST_PERSON, null, aliceWedding);
        AddwCommand nullPredicateAddwCommand2 = new AddwCommand(INDEX_FIRST_PERSON, null, aliceWedding);

        // null indexes -> return true
        assertTrue(nullIndexAddwCommand1.equals(nullIndexAddwCommand2));

        // null predicates -> return true
        assertTrue(nullPredicateAddwCommand1.equals(nullPredicateAddwCommand2));

        // null index and null predicate with same wedding -> return false
        assertFalse(nullIndexAddwCommand1.equals(nullPredicateAddwCommand1));
    }

    @Test
    public void toStringMethod() {
        Wedding aliceWedding = new WeddingBuilder().withName("Alice's Wedding").build();
        AddwCommand addwCommand = new AddwCommand(INDEX_FIRST_PERSON, null, aliceWedding);
        String expected = AddwCommand.class.getCanonicalName()
                + "{index=" + INDEX_FIRST_PERSON + ", "
                + "predicate=" + null + ", "
                + "toAddw=" + aliceWedding + "}";
        assertEquals(expected, addwCommand.toString());
    }

    // =========== Stubs ============================================================================================

    /**
     * A default model stub that have all the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updatePersonEditedWedding(Wedding target, Wedding editedWedding) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAllPersonNotClient() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonListWithClient(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPhone(Person person) {
            requireNonNull(person);
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasEmail(Person person) {
            requireNonNull(person);
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addWedding(Wedding wedding) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasWedding(Wedding wedding) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteWedding(Wedding target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setWedding(Wedding target, Wedding editedWedding) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Wedding> getFilteredWeddingList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredWeddingList(Predicate<Wedding> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAllWeddingNotOwnWedding() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredWeddingListWithOwnWedding(Predicate<Wedding> predicate) {
            throw new AssertionError("This method should not be called.");
        }

    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithWedding extends AddwCommandTest.ModelStub {
        private final Wedding wedding;

        private final AddressBook addressBook;

        ModelStubWithWedding(Wedding wedding, AddressBook addressBook) {
            requireNonNull(wedding);
            this.wedding = wedding;
            this.addressBook = addressBook;
        }

        @Override
        public boolean hasWedding(Wedding wedding) {
            return this.wedding.isSameWedding(wedding);
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return addressBook.getPersonList();
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingWeddingAdded extends AddwCommandTest.ModelStub {
        final ArrayList<Wedding> weddingsAdded = new ArrayList<>();
        final AddressBook addressBook;

        public ModelStubAcceptingWeddingAdded(AddressBook addressBook) {
            this.addressBook = addressBook;
        }

        @Override
        public boolean hasWedding(Wedding wedding) {
            requireNonNull(wedding);
            return weddingsAdded.stream().anyMatch(wedding::isSameWedding);
        }

        @Override
        public void addWedding(Wedding wedding) {
            requireNonNull(wedding);
            weddingsAdded.add(wedding);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return addressBook;
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return addressBook.getPersonList();
        }
    }
}
