package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.link.Link;
import seedu.address.model.owner.Owner;
import seedu.address.model.person.Person;
import seedu.address.model.pet.Pet;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Owner> filteredOwners;
    private final FilteredList<Pet> filteredPets;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredOwners = new FilteredList<>(this.addressBook.getOwnerList());
        filteredPets = new FilteredList<>(this.addressBook.getPetList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getPawPatrolFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setPawPatrolPath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public boolean hasOwner(Owner owner) {
        requireNonNull(owner);
        return addressBook.hasOwner(owner);
    }

    @Override
    public boolean hasPet(Pet pet) {
        requireNonNull(pet);
        return addressBook.hasPet(pet);
    }

    @Override
    public boolean hasLink(Link link) {
        requireNonNull(link);
        return addressBook.hasLink(link);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void deleteOwner(Owner target) {
        addressBook.removeOwner(target);
    }

    @Override
    public void deletePet(Pet target) {
        addressBook.removePet(target);
    }

    @Override
    public void deleteLink(Link link) {
        addressBook.removeLink(link);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void addOwner(Owner owner) {
        addressBook.addOwner(owner);
        updateFilteredOwnerList(PREDICATE_SHOW_ALL_OWNERS);
    }

    @Override
    public void addPet(Pet pet) {
        addressBook.addPet(pet);
        updateFilteredPetList(PREDICATE_SHOW_ALL_PETS);
    }

    @Override
    public void addLink(Link link) {
        addressBook.addLink(link);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    @Override
    public void setOwner(Owner target, Owner editedOwner) {
        requireAllNonNull(target, editedOwner);

        addressBook.setOwner(target, editedOwner);
    }

    @Override
    public void setPet(Pet target, Pet editedPet) {
        requireAllNonNull(target, editedPet);

        addressBook.setPet(target, editedPet);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    /**
     * Returns an unmodifiable view of the list of {@code Owner} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Owner> getFilteredOwnerList() {
        return filteredOwners;
    }

    /**
     * Returns an unmodifiable view of the list of {@code Pet} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Pet> getFilteredPetList() {
        return filteredPets;
    }

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Owner} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public void updateFilteredOwnerList(Predicate<Owner> predicate) {
        requireNonNull(predicate);
        filteredOwners.setPredicate(predicate);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Pet} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public void updateFilteredPetList(Predicate<Pet> predicate) {
        requireNonNull(predicate);
        filteredPets.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;

        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons)
                && filteredOwners.equals(otherModelManager.filteredOwners)
                && filteredPets.equals(otherModelManager.filteredPets);
    }

}
