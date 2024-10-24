package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.participation.Participation;
import seedu.address.model.participation.UniqueParticipationList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tutorial.Tutorial;
import seedu.address.model.tutorial.UniqueTutorialList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson and isSameTutorial comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;

    private final UniqueTutorialList tutorials;
    private final UniqueParticipationList participations;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        tutorials = new UniqueTutorialList();
        participations = new UniqueParticipationList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Replaces the contents of the tutorial list with {@code tutorials}.
     * {@code tutorials} must not contain duplicate tutorials.
     */
    public void setTutorials(List<Tutorial> tutorials) {
        this.tutorials.setTutorials(tutorials);
    }

    /**
     * Replaces the contents of the participation list with {@code participations}.
     * {@code participations} must not contain duplicate tutorials.
     */
    public void setParticipations(List<Participation> participation) {
        this.participations.setParticipation(participation);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setTutorials(newData.getTutorialList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes a person, denoted by {@code key}, from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    //// tutorial-level operations

    /**
     * Returns true if a tutorial with the same identity as {@code tutorial} exists in the address book.
     */
    public boolean hasTutorial(Tutorial tutorial) {
        requireNonNull(tutorial);
        return tutorials.contains(tutorial);
    }

    /**
     * Adds a tutorial to the address book.
     * The tutorial must not already exist in the address book.
     */
    public void addTutorial(Tutorial t) {
        tutorials.add(t);
    }


    //// participation-level operation
    /**
     * Returns true if a participation with the same identity as {@code participation} exists in the address book.
     */
    public boolean hasParticipation(Participation participation) {
        requireNonNull(participation);
        return participations.contains(participation);
    }

    /**
     * Adds a participation to the address book.
     * The participation must not already exist in the address book.
     */
    public void addParticipation(Participation p) {
        participations.add(p);
    }

    /**
     * Replaces the given participation {@code target} in the list with {@code editedParticipation}.
     * {@code target} must exist in the address book.
     * The participation identity of {@code editedParticipation} must not be the same as another existing
     * participation in the address book.
     */
    public void setParticipation(Participation target, Participation editedParticipation) {
        requireNonNull(editedParticipation);

        participations.setParticipation(target, editedParticipation);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeParticipation(Participation key) {
        participations.remove(key);
    }


    /**
     * Removes a tutorial, denoted by {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void deleteTutorial(Tutorial key) {
        requireNonNull(key);
        tutorials.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .add("tutorials", tutorials)
                .toString();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Tutorial> getTutorialList() {
        return tutorials.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return persons.equals(otherAddressBook.persons)
                && tutorials.equals(otherAddressBook.tutorials);
    }

    @Override
    public int hashCode() {
        return Objects.hash(persons, tutorials);
    }
}
