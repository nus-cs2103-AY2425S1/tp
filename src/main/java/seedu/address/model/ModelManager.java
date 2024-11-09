package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.model.person.Person;
import seedu.address.model.person.RsvpStatus;
import seedu.address.model.tag.Tag;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private FilteredList<Person> filteredPersons;
    private ObservableList<Tag> tagList;
    private Command previousCommand;
    private ObservableList<Tag> tagFilters = FXCollections.observableArrayList();
    private ObservableList<RsvpStatus> statusFilters = FXCollections.observableArrayList();

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        tagList = this.addressBook.getTagList();
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
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
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
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void addPerson(int index, Person person) {
        addressBook.addPerson(index, person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
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
     * Returns an unmodifiable view of the full list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    public ObservableList<Person> getFullPersonList() {
        return addressBook.getPersonList();
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);

        @SuppressWarnings("unchecked")
        Predicate<Person> currentPredicate = (Predicate<Person>) filteredPersons.getPredicate();

        if (currentPredicate == null || predicate.equals(PREDICATE_SHOW_ALL_PERSONS)) {
            filteredPersons.setPredicate(predicate);
        } else {
            filteredPersons.setPredicate(currentPredicate.and(predicate));
        }
    }

    //=========== Tags ================================================================================

    @Override
    public boolean addTag(Tag tag) {
        return addressBook.addTag(tag);
    }

    @Override
    public Set<Tag> addTags(List<Tag> tags) {
        Set<Tag> tagsSuccessfullyAdded = new HashSet<>();
        for (Tag tag : tags) {
            boolean isSuccessful = addTag(tag);
            if (!isSuccessful) {
                continue;
            }
            tagsSuccessfullyAdded.add(tag);
        }
        return tagsSuccessfullyAdded;
    }

    @Override public boolean deleteTag(Tag tag) {
        return addressBook.deleteTag(tag);
    }

    @Override
    public Set<Tag> deleteTags(List<Tag> tags) {
        Set<Tag> tagsSuccessfullyDeleted = new HashSet<>();
        for (Tag tag : tags) {
            boolean isSuccessful = deleteTag(tag);
            if (!isSuccessful) {
                continue;
            }
            tagsSuccessfullyDeleted.add(tag);
        }
        return tagsSuccessfullyDeleted;
    }

    @Override
    public boolean renameTag(Tag existingTag, String newTagName) {
        boolean isSuccessful = addressBook.renameTag(existingTag, newTagName);
        tagFilters.remove(existingTag);
        return isSuccessful;
    }

    @Override
    public boolean hasTag(Tag tag) {
        return addressBook.hasTag(tag);
    }

    @Override
    public Set<Tag> getTagsInUse() {
        Set<Tag> tagsInUse = new HashSet<>();
        List<Person> persons = getFullPersonList();
        for (Person person : persons) {
            Set<Tag> personTags = person.getTags();
            tagsInUse.addAll(personTags);
        }
        return tagsInUse;
    }

    @Override
    public Set<Person> removeTagFromPersons(Tag tag) {
        List<Person> persons = getFullPersonList();
        Set<Person> updatedPersons = new HashSet<>();
        for (Person person : persons) {
            if (!person.hasTag(tag)) {
                continue;
            }
            Set<Tag> newTags = new HashSet<>(person.getTags());
            newTags.remove(tag);

            Person updatedPerson = new Person(person.getName(), person.getPhone(),
                    person.getEmail(), person.getRsvpStatus(), newTags);
            setPerson(person, updatedPerson);
            updatedPersons.add(updatedPerson);
            tagFilters.remove(tag);
        }
        return updatedPersons;
    }

    @Override
    public void editTagInPersons(Tag existingTag, String newTagName) {
        List<Person> persons = getFullPersonList();
        for (Person person : persons) {
            Set<Tag> updatedTags = new HashSet<>();
            Set<Tag> tags = new HashSet<>(person.getTags());
            for (Tag tag : tags) {
                if (!tag.equals(existingTag)) {
                    updatedTags.add(tag);
                    continue;
                }
                tag.setTagName(newTagName);
                updatedTags.add(tag);
            }
            Person newPerson = new Person(person.getName(), person.getPhone(), person.getEmail(),
                    person.getRsvpStatus(), updatedTags);
            setPerson(person, newPerson);
        }
    }

    @Override
    public boolean checkAcceptableTagListSize(int additionalTags) {
        return addressBook.checkAcceptableTagListSize(additionalTags);
    }

    @Override
    public ObservableList<Tag> getTagListAsObservableList() {
        return addressBook.getTagList();
    }

    @Override
    public void updateTagList() {
        ObservableList<Tag> tagList = this.addressBook.getTagList();
        tagList.setAll(FXCollections.observableArrayList(tagList));
    }

    @Override
    public void updatePreviousCommand(Command nextCommand) {
        this.previousCommand = nextCommand;
    }

    @Override
    public Command getPreviousCommand() {
        return this.previousCommand;
    }

    @Override
    public Predicate<Person> getCurrentPredicate() {
        @SuppressWarnings("unchecked")
        Predicate<Person> result = (Predicate<Person>) filteredPersons.getPredicate();
        return result;
    }

    @Override
    public ObservableList<Tag> getTagFiltersList() {
        return tagFilters;
    }

    @Override
    public ObservableList<RsvpStatus> getStatusFiltersList() {
        return statusFilters;
    }

    @Override
    public void addTagFilters(Set<Tag> tagFilters) {
        for (Tag tag : tagFilters) {
            if (!this.tagFilters.contains(tag)) {
                this.tagFilters.add(tag);
            }
        }
    }

    @Override
    public void addStatusFilters(Set<RsvpStatus> statusFilters) {
        for (RsvpStatus status : statusFilters) {
            if (!this.statusFilters.contains(status)) {
                this.statusFilters.add(status);
            }
        }
    }

    @Override
    public boolean checkTagFilterAlreadyExists(Tag tagToCheck) {
        if (this.tagFilters.contains(tagToCheck)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkStatusFilterAlreadyExists(RsvpStatus statusToCheck) {
        return (!statusFilters.isEmpty());
    }

    @Override
    public void removeFilters(Set<Tag> tagFilters, Set<RsvpStatus> statusFilters) {
        this.tagFilters.removeAll(tagFilters);
        this.statusFilters.removeAll(statusFilters);

    }

    @Override
    public void clearFilterSet() {
        this.tagFilters.clear();
        this.statusFilters.clear();
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
}
