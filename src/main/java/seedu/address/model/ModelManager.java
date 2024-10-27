package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Module;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of EduContacts data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final EduContacts eduContacts;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private Person personToDisplay;

    /**
     * Initializes a ModelManager with the given eduContacts and userPrefs.
     */
    public ModelManager(ReadOnlyEduContacts eduContacts, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(eduContacts, userPrefs);

        logger.fine("Initializing with EduContacts: " + eduContacts + " and user prefs " + userPrefs);

        this.eduContacts = new EduContacts(eduContacts);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.eduContacts.getPersonList());
    }

    /**
     * Initializes a ModelManager with the given eduContacts, userPrefs and personToDisplay.
     */
    public ModelManager(ReadOnlyEduContacts eduContacts, ReadOnlyUserPrefs userPrefs, Person personToDisplay) {
        requireAllNonNull(eduContacts, userPrefs);

        logger.fine("Initializing with EduContacts: " + eduContacts + " and user prefs " + userPrefs);

        this.eduContacts = new EduContacts(eduContacts);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.eduContacts.getPersonList());
        setPersonToDisplay(personToDisplay);
    }

    public ModelManager() {
        this(new EduContacts(), new UserPrefs());
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
    public Path getEduContactsFilePath() {
        return userPrefs.getEduContactsFilePath();
    }

    @Override
    public void setEduContactsFilePath(Path eduContactsFilePath) {
        requireNonNull(eduContactsFilePath);
        userPrefs.setEduContactsFilePath(eduContactsFilePath);
    }

    //=========== EduContacts ================================================================================

    @Override
    public void setEduContacts(ReadOnlyEduContacts eduContacts) {
        this.eduContacts.resetData(eduContacts);
        setPersonToDisplay(null);
    }

    @Override
    public ReadOnlyEduContacts getEduContacts() {
        return eduContacts;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return eduContacts.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        eduContacts.removePerson(target);
        if (target.isSamePerson(personToDisplay)) {
            setPersonToDisplay(null);
        }
    }
    @Override
    public void deleteModule(Person target, Module module) {
        eduContacts.removeModule(target, module);
    }

    @Override
    public void addPerson(Person person) {
        eduContacts.addPerson(person);

        setPersonToDisplay(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void addModule(Person person, Module module) {
        eduContacts.addModule(person, module);

        setPersonToDisplay(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        eduContacts.setPerson(target, editedPerson);
        setPersonToDisplay(editedPerson);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedEduContacts}
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

    //=========== Person To Display =========================================================================

    @Override
    public void setPersonToDisplay(Person personToDisplay) {
        if (personToDisplay == null || eduContacts.hasPerson(personToDisplay)) {
            this.personToDisplay = personToDisplay;
        }
    }

    @Override
    public Person getPersonToDisplay() {
        return personToDisplay;
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

        return eduContacts.equals(otherModelManager.eduContacts)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons)
                && Objects.equals(personToDisplay, otherModelManager.personToDisplay);
    }

}
