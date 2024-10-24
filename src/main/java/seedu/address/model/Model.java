package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.concert.Concert;
import seedu.address.model.concert.ConcertContact;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<Concert> PREDICATE_SHOW_ALL_CONCERTS = unused -> true;
    Predicate<ConcertContact> PREDICATE_SHOW_ALL_CONCERT_CONTACTS = unused -> true;


    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Returns true if a concert with the same identity as {@code concert} exists in the concert
     * phonebook.
     *
     * @param concert
     * @return
     */
    boolean hasConcert(Concert concert);

    /**
     * Checks whether a concertContact with the same identity as {@code concertContact} exists in the concertContact
     * phonebook.
     *
     * @param concertContact ConcertContact to check
     * @return true if the concertContact exists
     */
    boolean hasConcertContact(ConcertContact concertContact);

    /**
     * Checks whether a concertContact with the same person and concert as {@code person}  and {@code concert} exists
     * in the concertContact phonebook.
     *
     * @param person person to check for
     * @param concert concert to check for
     * @return true if address-book contains concertContact
     */
    boolean hasConcertContact(Person person, Concert concert);

    /**
     * Deletes the given person. The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Deletes the concertConcert associated to the given concert and person.
     * The concertContact must exist in the address book.
     */
    void deleteConcertContact(Person targetPerson, Concert targetConcert);

    /**
     * Deletes all concertContacts associated to the given concert.
     */
    void deleteConcertContact(Concert targetConcert);

    /**
     * Deletes all concertContacts associated to the given person.
     */
    void deleteConcertContact(Person targetPerson);

    /**
     * Deletes the given person. The person must exist in the address book.
     */
    void deleteConcert(Concert target);

    /**
     * Adds the given person. {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Adds the given concert. {@code concert} must not already exist in the concert phonebook.
     */
    void addConcert(Concert concert);

    /**
     * Adds the given concertContact. {@code concertContact} must not already exist in the concertContact phonebook.
     */
    void addConcertContact(ConcertContact concertContact);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}. {@code target} must exist
     * in the address book. The person identity of {@code editedPerson} must not be the same as
     * another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Replaces the given concert {@code target} with {@code editedConcert}. {@code target} must exist
     * in the address book. The {@code editedConcert} must not be equals to another existing concert
     * in the address book.
     * @param target The concert to be edited.
     * @param editedConcert The concert to replace the target with.
     */
    void setConcert(Concert target, Concert editedConcert);

    /**
     * Replaces the given concertContact {@code target} with {@code editedConcertContact}. {@code target} must exist
     * in the address book. The concertContact identity of {@code editedConcertContact} must not be the same as
     * another existing concertContact in the address book.
     */
    void setConcertContact(ConcertContact target, ConcertContact editedConcertContact);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered concert list */
    ObservableList<Concert> getFilteredConcertList();

    /** Returns an unmodifiable view of the filtered concertContact list */
    ObservableList<ConcertContact> getFilteredConcertContactList();
    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Updates the filter of the filtered concert list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredConcertList(Predicate<Concert> predicate);

    /**
     * Updates the filter of the filtered concertContact list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredConcertContactList(Predicate<ConcertContact> predicate);
}
