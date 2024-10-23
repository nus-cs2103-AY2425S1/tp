package seedu.academyassist.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.academyassist.commons.core.GuiSettings;
import seedu.academyassist.commons.core.LogsCenter;
import seedu.academyassist.commons.util.CollectionUtil;
import seedu.academyassist.model.person.Ic;
import seedu.academyassist.model.person.Person;
import seedu.academyassist.model.person.StudentId;
import seedu.academyassist.model.person.Subject;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AcademyAssist academyAssist;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAcademyAssist academyAssist, ReadOnlyUserPrefs userPrefs) {
        CollectionUtil.requireAllNonNull(academyAssist, userPrefs);

        logger.fine("Initializing with address book: " + academyAssist + " and user prefs " + userPrefs);

        this.academyAssist = new AcademyAssist(academyAssist);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.academyAssist.getPersonList());
    }

    public ModelManager() {
        this(new AcademyAssist(), new UserPrefs());
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
    public Path getAcademyAssistFilePath() {
        return userPrefs.getAcademyAssistFilePath();
    }

    @Override
    public void setAcademyAssistFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAcademyAssistFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAcademyAssist(ReadOnlyAcademyAssist addressBook) {
        this.academyAssist.resetData(addressBook);
    }

    @Override
    public ReadOnlyAcademyAssist getAcademyAssist() {
        return academyAssist;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return academyAssist.hasPerson(person);
    }

    @Override
    public boolean hasPersonWithIc(Ic ic) {
        requireNonNull(ic);
        return academyAssist.hasPersonWithIc(ic);
    }

    @Override
    public boolean hasPersonWithStudentId(StudentId studentId) {
        requireNonNull(studentId);
        return academyAssist.hasPersonWithStudentId(studentId);
    }

    @Override
    public Person getPersonWithIc(Ic ic) {
        return academyAssist.getPersonWithIc(ic);
    }

    @Override
    public Person getPersonWithStudentId(StudentId studentId) {
        return academyAssist.getPersonWithStudentId(studentId);
    }

    @Override
    public boolean personDuplicateClass(Set<Subject> subjects, Person student) {
        return academyAssist.personDuplicateClass(subjects, student);
    }

    @Override
    public void addSubjectsToPerson(Set<Subject> subjects, Person person) {
        academyAssist.addSubjectsToPerson(subjects, person);
    }

    @Override
    public void deletePerson(Person target) {
        academyAssist.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        academyAssist.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        CollectionUtil.requireAllNonNull(target, editedPerson);

        academyAssist.setPerson(target, editedPerson);
    }

    @Override
    public void sortAcademyAssistByName() {
        academyAssist.sortPersonsByName();
    }

    @Override
    public void sortAcademyAssistByClass() {
        academyAssist.sortPersonsByClass();
    }

    @Override
    public void incrementStudentCount() {
        academyAssist.incrementStudentCount();
    }

    @Override
    public int getStudentCount() {
        return academyAssist.getStudentCount();
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
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return academyAssist.equals(otherModelManager.academyAssist)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons);
    }

}
