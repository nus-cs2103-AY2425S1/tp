package seedu.hiredfiredpro.model;

import static java.util.Objects.requireNonNull;
import static seedu.hiredfiredpro.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.hiredfiredpro.commons.core.GuiSettings;
import seedu.hiredfiredpro.commons.core.LogsCenter;
import seedu.hiredfiredpro.model.person.Job;
import seedu.hiredfiredpro.model.person.Name;
import seedu.hiredfiredpro.model.person.Person;

/**
 * Represents the in-memory model of the hiredfiredpro data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final HiredFiredPro hiredFiredPro;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final SortedList<Person> sortedFilteredPersons;


    /**
     * Initializes a ModelManager with the given hiredFiredPro and userPrefs.
     */
    public ModelManager(ReadOnlyHiredFiredPro hiredFiredPro, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(hiredFiredPro, userPrefs);

        logger.fine("Initializing with hiredfiredpro: " + hiredFiredPro + " and user prefs " + userPrefs);

        this.hiredFiredPro = new HiredFiredPro(hiredFiredPro);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.hiredFiredPro.getPersonList());
        sortedFilteredPersons = new SortedList<>(filteredPersons);
    }

    public ModelManager() {
        this(new HiredFiredPro(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public Person findPersonByNameAndJob(Name name, Job job) {
        for (Person person : hiredFiredPro.getPersonList()) {
            if (person.getName().isSameName(name) && person.getJob().isSameJob(job)) {
                return person;
            }
        }
        return null;
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
    public Path getHiredFiredProFilePath() {
        return userPrefs.getHiredFiredProFilePath();
    }

    @Override
    public void setHiredFiredProFilePath(Path hiredFiredProFilePath) {
        requireNonNull(hiredFiredProFilePath);
        userPrefs.setHiredFiredProFilePath(hiredFiredProFilePath);
    }

    //=========== HiredFiredPro ================================================================================

    @Override
    public void setHiredFiredPro(ReadOnlyHiredFiredPro hiredFiredPro) {
        this.hiredFiredPro.resetData(hiredFiredPro);
    }

    @Override
    public ReadOnlyHiredFiredPro getHiredFiredPro() {
        return hiredFiredPro;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return hiredFiredPro.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        hiredFiredPro.removePerson(target);
    }

    @Override
    public ObservableList<Person> getSortedPersonList() {
        return sortedFilteredPersons;
    }

    @Override
    public void updateSortedPersonList(Comparator<Person> comparator) {
        requireNonNull(comparator);
        sortedFilteredPersons.setComparator(comparator);
    }

    @Override
    public void addPerson(Person person) {
        hiredFiredPro.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        hiredFiredPro.setPerson(target, editedPerson);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedHiredFiredPro}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return sortedFilteredPersons; // Changed to return sorted list instead of filtered list
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public ObservableList<Person> getSortedFilteredPersonList() {
        return sortedFilteredPersons;
    }


    @Override
    public void markAsHired(Person person) {
        // Implementation to mark the person as hired
        // For example, setting a hired flag or updating the person's status
        person.markAsHired();
    }

    @Override
    public boolean isJobPresent(Job job) {
        Objects.requireNonNull(job);
        return hiredFiredPro.getPersonList().stream()
                .anyMatch(person -> person.getJob().equals(job));
    }

    @Override
    public void setPersons(List<Person> persons) {
        this.hiredFiredPro.setPersons(persons);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
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
        return hiredFiredPro.equals(otherModelManager.hiredFiredPro)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons);
    }

}
