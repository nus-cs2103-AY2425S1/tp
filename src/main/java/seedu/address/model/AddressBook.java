package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.wedding.UniqueWeddingList;
import seedu.address.model.wedding.Wedding;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueTagList tags;
    private final UniqueWeddingList weddings;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     * among constructors.
     */
    {
        persons = new UniquePersonList();
        tags = new UniqueTagList();
        weddings = new UniqueWeddingList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
        initialiseTags();
        initialiseWeddings();
    }

    //// list overwrite operations

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setTags(newData.getTagList());
        setWeddings(newData.getWeddingList());
    }

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Replaces the contents of the wedding list with {@code weddings}.
     * {@code weddings} must not contain duplicate tags.
     */
    public void setWeddings(List<Wedding> weddings) {
        this.weddings.setWeddings(weddings);
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
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    //// tag-level operations

    /**
     * Adds a tag to the Wedlinker.
     * The tag must not already exist in the Wedlinker.
     * @param tag A {@code Tag} object to be added.
     */
    public void addTag(Tag tag) {
        tags.add(tag);
    }

    /**
     * Returns true if a tag with the same name as {@code tag} exists in the Wedlinker.
     */
    public boolean hasTag(Tag tag) {
        requireNonNull(tag);
        return tags.contains(tag);
    }

    /**
     * Adds a wedding to the Wedlinker
     * The wedding must not already exist in the Wedlinker
     * @param wedding A {@code Wedding} object to be added.
     */
    public void addWedding(Wedding wedding) {
        weddings.add(wedding);
    }

    /**
     * Replaces the given wedding {@code target} in the list with {@code editedWedding}.
     * {@code target} must exist in the address book.
     * The wedding identity of {@code editedWedding} must not be the same as another existing wedding in the Wedlinker.
     */
    public void setWedding(Wedding target, Wedding editedWedding) {
        requireNonNull(editedWedding);

        weddings.setWedding(target, editedWedding);
    }

    /**
     * Returns true if a wedding with the same name as the {@code wedding} exists in the Wedlinker.
     */
    public boolean hasWedding(Wedding wedding) {
        requireNonNull(wedding);
        return weddings.contains(wedding);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeWedding(Wedding key) {
        weddings.remove(key);
    }

    /**
     * Replaces the given tag {@code target} in the list with {@code editedTag}.
     * {@code target} must exist in the address book.
     * The tag identity of {@code editedTag} must not be the same as another existing tag in the address book.
     */
    public void setTag(Tag target, Tag editedTag) {
        requireNonNull(editedTag);

        tags.setTag(target, editedTag);
    }

    /**
     * Replaces the contents of the tag list with {@code tags}.
     * {@code tags} must not contain duplicate tags.
     */
    public void setTags(List<Tag> tags) {
        this.tags.setTags(tags);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeTag(Tag key) {
        tags.remove(key);
    }

    /**
     * Creates any tags that is attached to a person but not initialised.
     * This function is only to be used when loading from Storage.
     */
    public void initialiseTags() {
        for (Person person : persons) {
            Set<Tag> tagForPerson = person.getTags();
            for (Tag tag : tagForPerson) {
                tag.increaseTaggedCount();
                if (!this.hasTag(tag)) {
                    this.addTag(tag);
                } else {
                    this.setTag(tag, tag);
                }
            }
        }
    }

    /**
     * Creates any weddings attached to a person but not initialised.
     * This function is only to be used when loading from Storage.
     */
    public void initialiseWeddings() {
        for (Person person : persons) {
            Set<Wedding> weddingForPerson = person.getWeddings();
            for (Wedding wedding : weddingForPerson) {
                if (!this.hasWedding(wedding)) {
                    this.addWedding(wedding);
                }
            }
        }
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
    public ObservableList<Tag> getTagList() {
        return tags.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Wedding> getWeddingList() {
        return weddings.asUnmodifiableObservableList();
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
