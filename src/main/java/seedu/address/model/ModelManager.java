package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.commands.TagAddCommand.MESSAGE_EMPTY;
import static seedu.address.logic.commands.TagAddCommand.MESSAGE_WEDDING_DOESNT_EXIST;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.JobContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Tag;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingName;
import seedu.address.model.wedding.WeddingNameContainsKeywordsPredicate;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final WeddingBook weddingBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Wedding> filteredWeddings;

    /**
     * Initializes a ModelManager with the given addressBook, weddingBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs, ReadOnlyWeddingBook weddingBook) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.weddingBook = new WeddingBook(weddingBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredWeddings = new FilteredList<>(this.weddingBook.getWeddingList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs(), new WeddingBook());
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

    @Override
    public Path getWeddingBookFilePath() {
        return userPrefs.getWeddingBookFilePath();
    }

    @Override
    public void setWeddingBookFilePath(Path weddingBookFilePath) {
        requireNonNull(weddingBookFilePath);
        userPrefs.setWeddingBookFilePath(weddingBookFilePath);
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

    /**
     * Checks if the address book contains the exact instance of the specified person.
     * This method verifies whether the provided {@code person} object is equal to another
     * person in the address book
     *
     * @param person The person instance to check for in the address book.
     * @return {@code true} if the address book contains the exact instance of {@code person};
     *         {@code false} otherwise.
     * @throws NullPointerException if {@code person} is null.
     */
    public boolean hasExactPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasExactPerson(person);
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

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
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

    @Override
    public void updateFilteredPersonList(JobContainsKeywordsPredicate predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== WeddingBook ================================================================================

    @Override
    public void setWeddingBook(ReadOnlyWeddingBook weddingBook) {
        this.weddingBook.resetData(weddingBook);
    }

    @Override
    public ReadOnlyWeddingBook getWeddingBook() {
        return weddingBook;
    }

    @Override
    public boolean hasWedding(Wedding wedding) {
        requireNonNull(wedding);
        return weddingBook.hasWedding(wedding);
    }

    @Override
    public void deleteWedding(Wedding target) {
        weddingBook.removeWedding(target);
    }

    @Override
    public void addWedding(Wedding wedding) {
        weddingBook.addWedding(wedding);
        updateFilteredWeddingList(PREDICATE_SHOW_ALL_WEDDINGS);
    }

    @Override
    public void setWedding(Wedding target, Wedding editedWedding) {
        requireAllNonNull(target, editedWedding);

        weddingBook.setWedding(target, editedWedding);
    }

    //=========== Filtered Wedding List Accessors ============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Wedding} backed by the internal list of
     * {@code versionedWeddingBook}
     */
    @Override
    public ObservableList<Wedding> getFilteredWeddingList() {
        return filteredWeddings;
    }

    @Override
    public void updateFilteredWeddingList(Predicate<Wedding> predicate) {
        requireNonNull(predicate);
        filteredWeddings.setPredicate(predicate);
    }

    @Override
    public void updateFilteredWeddingList(WeddingNameContainsKeywordsPredicate predicate) {
        requireNonNull(predicate);
        filteredWeddings.setPredicate(predicate);
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
                && weddingBook.equals(otherModelManager.weddingBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons)
                && filteredWeddings.equals(otherModelManager.filteredWeddings);
    }

    //=========== Person, Wedding and Tag related accessors ============================================================

    @Override
    public void setPersonInWedding(Person editedPerson, Person personToEdit) {
        List<Wedding> weddingList = getWeddingFromTags(editedPerson.getTags());

        List<Set<Person>> weddingParticipantsSet = weddingList.stream().map(Wedding::getParticipants)
                .toList();

        for (Set<Person> set : weddingParticipantsSet) {
            set.remove(personToEdit);
            set.add(editedPerson);
        }
    }

    @Override
    public void deleteTagsWithWedding(Wedding weddingToDelete) {
        Set<Person> weddingParticipants = weddingToDelete.getParticipants();
        for (Person participant : weddingParticipants) {
            try {
                if (addressBook.hasExactPerson(participant)) {
                    participant.getTags()
                            .removeIf(tag -> tag.getTagName().equals(weddingToDelete.getWeddingName().toString()));
                    Person newPerson = new Person(
                            participant.getName(), participant.getPhone(), participant.getEmail(),
                            participant.getAddress(), participant.getJob(),
                            participant.getTags());
                    setPerson(participant, newPerson);
                }
            } catch (PersonNotFoundException e) {
                // Handle the case where the person does not exist in the address book
                System.out.println("Person not found: " + participant);
            }
        }
    }

    @Override
    public void updatePersonInWedding(Person personToEdit, Person editedPerson) {
        List<Wedding> weddingList = getFilteredWeddingList();

        List<Set<Person>> weddingParticipantsSet = weddingList.stream().map(Wedding::getParticipants)
                .toList();

        for (Set<Person> set : weddingParticipantsSet) {
            if (set.contains(personToEdit)) {
                set.remove(personToEdit);
                set.add(editedPerson);
            }
        }
    }

    @Override
    public List<Wedding> getWeddingFromTags(Set<Tag> tags) {
        List<String> predicate = tags
                .stream().map(Tag::getTagName).collect(Collectors.toList());

        List<Wedding> list = new ArrayList<>();

        for (Wedding wedding : getFilteredWeddingList()) {
            for (String tagName : predicate) {
                if (wedding.getWeddingName().toString().equals(tagName)) {
                    list.add(wedding);
                }
            }
        }

        return list;
    }

    @Override
    public void deletePersonInWedding(Person editedPerson, Set<Tag> editedTags) {
        List<Wedding> weddingList = getWeddingFromTags(editedTags);

        List<Set<Person>> weddingParticipantsSet = weddingList.stream().map(Wedding::getParticipants)
                .toList();

        for (Set<Person> set : weddingParticipantsSet) {
            set.remove(editedPerson);
        }
    }

    @Override
    public Person personWithAllTagsRemoved(Person personToEdit) {
        Set<Tag> currentTags = new HashSet<>(personToEdit.getTags());

        deletePersonInWedding(personToEdit, currentTags);

        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(),
                personToEdit.getEmail(), personToEdit.getAddress(), personToEdit.getJob(), Collections.emptySet());
        setPerson(personToEdit, editedPerson);

        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return editedPerson;
    }

    @Override
    public String messageWeddingDoesNotExist(Set<Tag> editedTags) throws CommandException {
        List<Wedding> weddingList = getWeddingFromTags(editedTags);

        if (weddingList.isEmpty() && !editedTags.isEmpty()) {
            Set<Tag> tagsDontExist = new HashSet<>(editedTags);

            editedTags.removeAll(editedTags);

            throw new CommandException(String.format(MESSAGE_WEDDING_DOESNT_EXIST,
                    Messages.tagSetToString(tagsDontExist), Messages.tagSetToString(tagsDontExist)));
        } else if (weddingList.size() < editedTags.size()) {
            Set<Tag> weddingSet = weddingList.stream().map(Wedding::getWeddingName)
                    .map(WeddingName::toString).map(Tag::new).collect(Collectors.toSet());
            Set<Tag> tagsDontExist = new HashSet<>(editedTags);

            editedTags.retainAll(weddingSet);
            tagsDontExist.removeAll(weddingSet);

            return String.format(MESSAGE_WEDDING_DOESNT_EXIST, Messages.tagSetToString(tagsDontExist),
                    Messages.tagSetToString(tagsDontExist));
        }

        return MESSAGE_EMPTY;
    }

    @Override
    public void clearAllPersonTags() {
        for (Person person : addressBook.getPersonList()) {
            Set<Tag> currentTags = new HashSet<>(person.getTags());

            deletePersonInWedding(person, currentTags);

            Person personToDelete = new Person(person.getName(), person.getPhone(),
                    person.getEmail(), person.getAddress(), person.getJob(), Collections.emptySet());
            setPerson(person, personToDelete);
        }
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void clearAllWeddingParticipants() {
        for (Wedding wedding : weddingBook.getWeddingList()) {
            deleteTagsWithWedding(wedding);
        }
        updateFilteredWeddingList(PREDICATE_SHOW_ALL_WEDDINGS);
    }
}
