package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tags;

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
        requireNonNull(target);

        // Remove the person from the address book
        addressBook.removePerson(target);

        // Remove the person from all groups
        removePersonFromAllGroups(target);
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
        editPersonFromAllGroups(target, editedPerson);
    }

    @Override
    public boolean tagExists(Person target, Tags tags) {
        return target.tagExists(tags);
    }

    @Override
    public void addTag(Person target, Tags newTags) {
        Person updatedPerson = target.addTags(newTags);
        setPerson(target, updatedPerson);
    }

    @Override
    public void deleteTag(Person target, Tags tagsToDelete) {
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
     * @throws NullPointerException     if {@code groupToDelete} is null.
     * @throws IllegalArgumentException if the group does not exist in the list.
     */
    @Override
    public void deleteGroup(Group groupToDelete) {
        requireNonNull(groupToDelete);
        addressBook.removeGroup(groupToDelete);
    }

    /**
     * Adds a group to the Model
     */
    @Override
    public String groupsString() {
        if (groups.isEmpty()) {
            return "no groups found";
        } else {
            return groups.toString();
        }

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

    /**
     * Removes a person from all groups and deletes groups that become empty.
     *
     * @param person The person to remove from groups.
     */
    private void removePersonFromAllGroups(Person person) {
        // Create a list to collect groups that become empty
        List<Group> emptyGroups = new ArrayList<>();

        // Iterate over a copy of the group list to avoid
        // ConcurrentModificationException
        for (Group group : new ArrayList<>(addressBook.getGroupList())) {
            List<Person> members = new ArrayList<>(group.getMembers());

            // Check if the group contains the person
            if (members.contains(person)) {
                // Remove the person from the group's member list
                members.remove(person);

                if (members.isEmpty()) {
                    // If the group is empty after removal, mark it for deletion
                    emptyGroups.add(group);
                } else {
                    // Otherwise, update the group with the new member list
                    Group updatedGroup = new Group(group.getGroupName().toString(), members);
                    addressBook.setGroup(group, updatedGroup);
                }
            }
        }

        // Remove all empty groups from the address book
        for (Group emptyGroup : emptyGroups) {
            addressBook.removeGroup(emptyGroup);
        }

        // Update the filtered group list if necessary
        updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
    }

    /**
     * Updates the details of a person in all groups.
     *
     * @param oldPerson The person to be updated in the groups.
     * @param newPerson The updated person with new details to replace the old person in the groups.
     */
    private void editPersonFromAllGroups(Person oldPerson, Person newPerson) {
        // Iterate over a copy of the group list to avoid ConcurrentModificationException
        for (Group group : new ArrayList<>(addressBook.getGroupList())) {
            List<Person> members = new ArrayList<>(group.getMembers());
            boolean updated = false;

            // Check if the group contains the person with the old name
            for (int i = 0; i < members.size(); i++) {
                if (members.get(i).equals(oldPerson)) {
                    // Replace the old person with the new person
                    members.set(i, newPerson);
                    updated = true;
                    break;
                }
            }

            if (updated) {
                // Update the group with the modified member list
                Group updatedGroup = new Group(group.getGroupName().toString(), members);
                addressBook.setGroup(group, updatedGroup);
            }
        }

        // Update the filtered group list if necessary
        updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
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
