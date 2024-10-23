package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.link.Link;
import seedu.address.model.link.UniqueLinkList;
import seedu.address.model.owner.Owner;
import seedu.address.model.owner.UniqueOwnerList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.pet.Pet;
import seedu.address.model.pet.UniquePetList;

/**
 * Wraps all data at PawPatrol level
 * Duplicates are not allowed (by .isSameOwner and .isSamePet comparison)
 */
public class PawPatrol implements ReadOnlyPawPatrol {

    private final UniquePersonList persons;
    private final UniqueOwnerList owners;
    private final UniquePetList pets;
    private final UniqueLinkList links;

    /*
    * The 'unusual' code block below is a non-static initialization block,
    * sometimes used to avoid duplication
    * between constructors. See
    * https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
    *
    * Note that non-static init blocks are not recommended to use. There are other
    * ways to avoid duplication
    * among constructors.
    */

    {
        persons = new UniquePersonList();
        owners = new UniqueOwnerList();
        pets = new UniquePetList();
        links = new UniqueLinkList();
    }

    public PawPatrol() {
    }

    /**
     * Creates an PawPatrol using the Persons in the {@code toBeCopied}
     */
    public PawPatrol(ReadOnlyPawPatrol toBeCopied) {
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
     * Replaces the contents of the owner list with {@code owners}.
     * {@code owners} must not contain duplicate owners.
     */
    public void setOwners(List<Owner> owners) {
        this.owners.setOwners(owners);
    }

    /**
     * Replaces the contents of the pet list with {@code pets}.
     * {@code pets} must not contain duplicate pets.
     */
    public void setPets(List<Pet> pets) {
        this.pets.setPets(pets);
    }

    /**
     * Replaces the contents of the link list with {@code links}.
     * {@code links} must not contain duplicate links.
     */
    public void setLinks(List<Link> links) {
        this.links.setLinks(links);
    }

    /**
     * Resets the existing data of this {@code PawPatrol} with {@code newData}.
     */
    public void resetData(ReadOnlyPawPatrol newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setOwners(newData.getOwnerList());
        setPets(newData.getPetList());
        setLinks(newData.getLinkList());
    }

    // person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in
     * PawPatrol.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to PawPatrol.
     * The person must not already exist in PawPatrol.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with
     * {@code editedPerson}.
     * {@code target} must exist in PawPatrol.
     * The person identity of {@code editedPerson} must not be the same as another
     * existing person in PawPatrol.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code PawPatrol}.
     * {@code key} must exist in PawPatrol.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    // owner-level operations

    /**
     * Returns true if an owner with the same identity as {@code owner} exists in
     * PawPatrol.
     */
    public boolean hasOwner(Owner owner) {
        requireNonNull(owner);
        return owners.contains(owner);
    }

    /**
     * Adds an owner to PawPatrol.
     * The owner must not already exist in PawPatrol.
     */
    public void addOwner(Owner o) {
        owners.add(o);
    }

    /**
     * Replaces the given owner {@code target} in the list with {@code editedOwner}.
     * {@code target} must exist in PawPatrol.
     * The owner identity of {@code editedOwner} must not be the same as another
     * existing owner in PawPatrol.
     */
    public void setOwner(Owner target, Owner editedOwner) {
        requireNonNull(editedOwner);

        owners.setOwner(target, editedOwner);
    }

    /**
     * Removes {@code key} from this {@code PawPatrol}.
     * {@code key} must exist in PawPatrol.
     */
    public void removeOwner(Owner key) {
        owners.remove(key);
    }

    // pet-level operations

    /**
     * Returns true if a pet with the same identity as {@code pet} exits in PawPatrol.
     */
    public boolean hasPet(Pet pet) {
        requireNonNull(pet);
        return pets.contains(pet);
    }

    /**
     * Adds a pet to PawPatrol.
     * The pet must not already exist in PawPatrol.
     */
    public void addPet(Pet p) {
        pets.add(p);
    }

    /**
     * Replaces the given pet {@code target} in the list with {@code editedPet}.
     * {@code target} must exist in PawPatrol.
     * The pet identity of {@code editedPet} must not be the same as another
     * existing pet in PawPatrol.
     */
    public void setPet(Pet target, Pet editedPet) {
        requireNonNull(editedPet);

        pets.setPet(target, editedPet);
    }

    /**
     * Removes {@code key} from this {@code PawPatrol}.
     * {@code key} must exist in PawPatrol.
     */
    public void removePet(Pet key) {
        pets.remove(key);
    }

    // link-level operations

    /**
     * Returns true if a link with the same identity as {@code link} exists in PawPatrol.
     */
    public boolean hasLink(Link link) {
        requireNonNull(link);
        return links.contains(link);
    }

    /**
     * Adds a link to PawPatrol.
     * The link must not already exist in PawPatrol.
     */
    public void addLink(Link link) {
        requireNonNull(link);
        links.add(link);
    }

    /**
     * Removes {@code key} from this {@code PawPatrol}.
     * {@code key} must exist in PawPatrol.
     */
    public void removeLink(Link key) {
        links.remove(key);
    }

    /**
     * Remove {@code Link} that involve the specific ID
     * taken from {@code Linkable}
     */
    public void deleteLinksWithId(String id) {
        links.deleteLinksWithId(id);
    }

    // util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("persons", persons)
            .add("owners", owners)
            .add("pets", pets)
            .add("links", links)
            .toString();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Owner> getOwnerList() {
        return owners.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Pet> getPetList() {
        return pets.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Link> getLinkList() {
        return links.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PawPatrol)) {
            return false;
        }

        PawPatrol otherPawPatrol = (PawPatrol) other;

        return persons.equals(otherPawPatrol.persons) && owners.equals(otherPawPatrol.owners)
            && pets.equals(otherPawPatrol.pets);
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
