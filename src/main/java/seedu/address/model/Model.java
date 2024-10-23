package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.link.Link;
import seedu.address.model.owner.Owner;
import seedu.address.model.person.Person;
import seedu.address.model.pet.Pet;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    Predicate<Owner> PREDICATE_SHOW_ALL_OWNERS = unused -> true;

    Predicate<Pet> PREDICATE_SHOW_ALL_PETS = unused -> true;

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
     * Returns the user prefs' PawPatrol file path.
     */
    Path getPawPatrolFilePath();

    /**
     * Sets the user prefs' PawPatrol file path.
     */
    void setPawPatrolPath(Path pawPatrolFilePath);

    /**
     * Replaces PawPatrol data with the data in {@code pawPatrol}.
     */
    void setPawPatrol(ReadOnlyPawPatrol pawPatrol);

    /** Returns the PawPatrol */
    ReadOnlyPawPatrol getPawPatrol();

    /**
     * Returns true if a person with the same identity as {@code person} exists in PawPatrol.
     */
    boolean hasPerson(Person person);

    /**
     * Returns true if an owner with the same identity as {@code person} exists in PawPatrol.
     */
    boolean hasOwner(Owner owner);

    /***
     * Returns true if a pet with the same identity as {@code pet} exists in PawPatrol.
     */
    boolean hasPet(Pet pet);

    /***
     * Returns true if a pet with the same identity as {@code pet} exists in PawPatrol.
     */
    boolean hasLink(Link pet);

    /**
     * Deletes the given person.
     * The person must exist in PawPatrol.
     */
    void deletePerson(Person target);

    /**
     * Deletes the given owner.
     * The owner must exist in PawPatrol.
     */
    void deleteOwner(Owner target);

    /**
     * Deletes the given pet.
     * The pet must exist in PawPatrol.
     */
    void deletePet(Pet target);

    /**
     * Deletes the given link.
     * The link must exist in PawPatrol.
     */
    void deleteLink(Link target);

    /**
     * Deletes links with unique ID.
     * @param id
     */
    void deleteLinksWithId(String id);

    /**
     * Adds the given person.
     * {@code person} must not already exist in PawPatrol.
     */
    void addPerson(Person person);

    /**
     * Adds the given owner.
     * {@code owner} must not already exist in PawPatrol.
     */
    void addOwner(Owner owner);

    /**
     * Adds the given pet.
     * {@code pet} must not already exist in PawPatrol.
     */
    void addPet(Pet pet);

    /**
     * Adds the given link.
     * {@code link} must not already exist in PawPatrol.
     */
    void addLink(Link link);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in PawPatrol.
     * The person identity of {@code editedPerson} must not be the same as another existing person in PawPatrol.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Replaces the given person {@code target} with {@code editedOwner}.
     * {@code target} must exist in PawPatrol.
     * The person identity of {@code editedOwner} must not be the same as another existing owner in PawPatrol.
     */
    void setOwner(Owner target, Owner editedOwner);

    /**
     * Replaces the given pet {@code target} with {@code editedPet}.
     * {@code target} must exist in the PawPatrol.
     * The pet identity of {@code editedPet} must not be the same as another existing pet in PawPatrol.
     */
    void setPet(Pet target, Pet editedPet);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered owner list */
    ObservableList<Owner> getFilteredOwnerList();

    /** Returns an unmodifiable view of the filtered pet list */
    ObservableList<Pet> getFilteredPetList();

    /** Returns an unmodifiable view of the filtered link list */
    public ObservableList<Link> getFilteredLinkList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Updates the filter of the filtered owner list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredOwnerList(Predicate<Owner> predicate);

    /**
     * Updates the filter of the filtered pet list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPetList(Predicate<Pet> predicate);
}
