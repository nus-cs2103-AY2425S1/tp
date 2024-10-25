package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;

    private final FilteredList<Group> groups;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        this.groups = new FilteredList<>(this.addressBook.getGroupList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    // =========== UserPrefs
    // ==================================================================================

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
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
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    // =========== AddressBook
    // ================================================================================

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);
        addressBook.setPerson(target, editedPerson);
    }

    @Override
    public boolean tagExists(Person target, Set<Tag> tags) {
        return target.tagExists(tags);
    }

    @Override
    public void addTag(Person target, Set<Tag> newTags) {
        Person updatedPerson = target.addTags(newTags);
        setPerson(target, updatedPerson);
    }

    @Override
    public void deleteTag(Person target, Set<Tag> tagsToDelete) {
        Person updatedPerson = target.deleteTags(tagsToDelete);
        setPerson(target, updatedPerson);
    }

    // =========== Filtered Person List Accessors
    // =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the
     * internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    // =========== Group Logic
    // =============================================================

    /**
     * Adds a group to the Model
     *
     * @param group Group to be added
     */
    @Override
    public void addGroup(Group group) {
        addressBook.addGroup(group);
        updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
    }

    /**
     * Deletes the specified group from the list of groups.
     *
     * @param groupToDelete The group to be deleted.
     * @throws NullPointerException if {@code groupToDelete} is null.
     * @throws IllegalArgumentException if the group does not exist in the list.
     */
    @Override
    public void deleteGroup(Group groupToDelete) {
        requireNonNull(groupToDelete);

        boolean isRemoved = groups.remove(groupToDelete);

        if (!isRemoved) {
            throw new IllegalArgumentException("Group not found: " + groupToDelete.getGroupName());
        }
    }

    /**
     * Adds a group to the Model
     */
    @Override
    public String groupsString() {
        return groups.toString();
    }

    /**
     * Checks if a group with the same name already exists in the Model
     *
     * @param group Group to be checked
     * @return true if the group exists, false otherwise
     */
    @Override
    public boolean hasGroupName(Group group) {
        requireNonNull(group);
        return addressBook.hasGroup(group);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Group} backed by the
     * internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Group> getFilteredGroupList() {
        return groups;
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
                && filteredPersons.equals(otherModelManager.filteredPersons);
    }

    @Override
    public void updateFilteredGroupList(Predicate<Group> groupPredicate) {
        requireNonNull(groupPredicate);

        groups.setPredicate(groupPredicate);

    }

}
