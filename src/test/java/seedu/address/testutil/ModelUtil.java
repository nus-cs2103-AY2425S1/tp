package seedu.address.testutil;

import static java.util.Collections.unmodifiableList;
import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.concert.Concert;
import seedu.address.model.concert.ConcertContact;
import seedu.address.model.person.Person;

/**
 * ModelUtil to create stubs for tests.
 */
public class ModelUtil {
    /**
     * A default model stub that have all methods failing.
     */
    public class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
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
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteConcertContact(ConcertContact cc) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteConcertContact(Concert targetConcert) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteConcertContact(Person targetPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteConcert(Concert target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setConcert(Concert target, Concert editedConcert) {
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
        public void addConcert(Concert concert) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Concert> getFilteredConcertList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasConcert(Concert concert) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredConcertList(Predicate<Concert> concert) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addConcertContact(ConcertContact concertContact) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<ConcertContact> getFilteredConcertContactList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasConcertContact(ConcertContact concertContact) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasConcertContact(Person person, Concert concert) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setConcertContact(ConcertContact target, ConcertContact editedConcertContact) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredConcertContactList(Predicate<ConcertContact> concertContact) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    public class ModelStubWithPerson extends ModelStub {
        private final Person person;

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.person = person;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.person.isSamePerson(person);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    public class ModelStubAcceptingPersonAdded extends ModelStub {
        private final ArrayList<Person> personsAdded = new ArrayList<>();

        public List<Person> getPersons() {
            return unmodifiableList(personsAdded);
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that contains a single concert.
     */
    public class ModelStubWithConcert extends ModelStub {
        private final Concert concert;

        ModelStubWithConcert(Concert concert) {
            requireNonNull(concert);
            this.concert = concert;
        }

        @Override
        public boolean hasConcert(Concert concert) {
            requireNonNull(concert);
            return this.concert.isSameConcert(concert);
        }
    }

    /**
     * A Model stub that always accept the concert being added.
     */
    public class ModelStubAcceptingConcertAdded extends ModelStub {
        private final ArrayList<Concert> concertsAdded = new ArrayList<>();

        public List<Concert> getConcerts() {
            return unmodifiableList(concertsAdded);
        }

        @Override
        public boolean hasConcert(Concert concert) {
            requireNonNull(concert);
            return concertsAdded.stream().anyMatch(concert::isSameConcert);
        }

        @Override
        public void addConcert(Concert concert) {
            requireNonNull(concert);
            concertsAdded.add(concert);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    public ModelStubWithPerson ofWithPerson(Person person) {
        return new ModelStubWithPerson(person);
    }

    public ModelStubAcceptingPersonAdded ofAcceptingPersonAdded() {
        return new ModelStubAcceptingPersonAdded();
    }

    public ModelStubAcceptingConcertAdded ofAcceptingConcertAdded() {
        return new ModelStubAcceptingConcertAdded();
    }

    public ModelStubWithConcert ofStubWithConcert(Concert concert) {
        return new ModelStubWithConcert(concert);
    }

    public ModelStub of() {
        return new ModelStub();
    }
}
