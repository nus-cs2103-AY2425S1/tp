package tuteez.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.collections.ObservableList;
import tuteez.commons.util.ToStringBuilder;
import tuteez.model.person.Name;
import tuteez.model.person.Person;
import tuteez.model.person.UniquePersonList;
import tuteez.model.person.lesson.Lesson;
import tuteez.model.person.lesson.LessonManager;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final LessonManager lessonManager;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        lessonManager = new LessonManager();
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

    public void setLessons(LessonManager dataLessonManager) {
        this.lessonManager.setLessonsFromStorage(dataLessonManager);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setLessons(newData.getLessonManager());
        setPersons(newData.getPersonList());
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
     * The person's lessons are also added (clashing lesson are checked earlier)
     */
    public void addPerson(Person p) {
        p.getLessons().forEach(lessonManager::addLesson);
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);
        target.getLessons().forEach(lessonManager::deleteLesson);
        editedPerson.getLessons().forEach(lessonManager::addLesson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        key.getLessons().forEach(lessonManager::deleteLesson);
        persons.remove(key);
    }

    /**
     * Displays {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void displayPerson(Person key) {
        persons.display(key);
    }

    /**
     * Finds and returns the {@code person} from this {@code AddressBook} with the specified {@code Name}.
     * {@code Name} must exist in the address book.
     */
    public Person findPersonByName(Name targetName) {
        requireNonNull(targetName);
        return getPersonList().stream()
                .filter(person -> person.getName().fullName.equalsIgnoreCase(targetName.fullName))
                .findFirst()
                .orElse(null);
    }

    public Map<Person, ArrayList<Lesson>> getClashingLessons(Lesson lesson) {
        return lessonManager.getClashingLessons(persons, lesson);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .toString();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public LessonManager getLessonManager() {
        return lessonManager;
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
        return persons.equals(otherAddressBook.persons);
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
