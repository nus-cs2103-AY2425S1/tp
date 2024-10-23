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
import seedu.address.model.company.Company;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobCompany;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Job> filteredJobs;
    private final FilteredList<Company> filteredCompanies;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredJobs = new FilteredList<>(this.addressBook.getJobList());
        filteredCompanies = new FilteredList<>(this.addressBook.getCompanyList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

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

    //=========== AddressBook ================================================================================

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

    /**
     * Returns true if the same company is in the address book.
     *
     * @param company Company to be checked.
     * @return true if in address book.
     */
    @Override
    public boolean hasCompany(Company company) {
        requireNonNull(company);
        return addressBook.hasCompany(company);
    }

    @Override
    public boolean hasJob(Job job) {
        requireNonNull(job);
        return addressBook.hasJob(job);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    /**
     * Deletes a company same as the target from the address book.
     *
     * @param target Company to be deleted.
     */
    @Override
    public void deleteCompany(Company target) {
        addressBook.removeCompany(target);
    }

    @Override
    public void deleteJob(Job target) {
        addressBook.removeJob(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    /**
     * Adds a company to the address book.
     *
     * @param company Company to be added.
     */
    @Override
    public void addCompany(Company company) {
        addressBook.addCompany(company);
        updateFilteredCompanyList(PREDICATE_SHOW_ALL_COMPANIES);
    }

    @Override
    public void addJob(Job job) {
        addressBook.addJob(job);
        updateFilteredJobList(PREDICATE_SHOW_ALL_JOBS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    @Override
    public void setJob(Job target, Job editedJob) {
        requireAllNonNull(target, editedJob);

        addressBook.setJob(target, editedJob);
    }

    /**
     * Replaces the target company with an edited version of itself.
     *
     * @param target        Company to be replaced.
     * @param editedCompany Company to replace the other.
     */
    @Override
    public void setCompany(Company target, Company editedCompany) {
        requireAllNonNull(target, editedCompany);

        addressBook.setCompany(target, editedCompany);
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
    public ObservableList<Job> getFilteredJobList() {
        return filteredJobs;
    }

    /**
     * Returns the list of filtered companies.
     *
     * @return List of filtered companies.
     */
    @Override
    public ObservableList<Company> getFilteredCompanyList() {
        return filteredCompanies;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public void updateFilteredJobList(Predicate<Job> predicate) {
        requireNonNull(predicate);
        filteredJobs.setPredicate(predicate);
    }

    /**
     * Updates the filtered company list with the given predicate.
     *
     * @param predicate Predicate for the filter.
     */
    @Override
    public void updateFilteredCompanyList(Predicate<Company> predicate) {
        requireNonNull(predicate);
        filteredCompanies.setPredicate(predicate);
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
        return addressBook.equals(otherModelManager.addressBook) && userPrefs.equals(otherModelManager.userPrefs)
               && filteredPersons.equals(otherModelManager.filteredPersons)
               && filteredJobs.equals(otherModelManager.filteredJobs)
               && filteredCompanies.equals(otherModelManager.filteredCompanies);
    }

    /**
     * Returns a predicate that tests whether a {@code Person} is linked to the specified company.
     * @param targetCompany The company to match against.
     * @return A predicate that returns {@code true} if the person is linked to the specified company.
     */
    public Predicate<Person> getPersonLinkedToCompanyPredicate(Company targetCompany) {
        return person -> person.isMatchPresent()
                && person.getMatch().startsWith(targetCompany.getName().toString() + "::");
    }

    /**
     * Returns a predicate that tests whether a {@code Job} is linked to the specified company.
     * @param targetCompany The company to match against.
     * @return A predicate that returns {@code true} if the job is linked to the specified company.
     */
    public Predicate<Job> getJobLinkedToCompanyPredicate(Company targetCompany) {
        return job -> job.getCompany().equals(new JobCompany(targetCompany.getName().toString()));
    }

    /**
     * Filters the lists of jobs, persons, and companies to show only those
     * linked to the specified company.
     * @param targetCompany The company whose linked jobs and persons will be shown.
     */
    @Override
    public void showLinkedJobsAndPersonsByCompany(Company targetCompany) {
        updateFilteredJobList(getJobLinkedToCompanyPredicate(targetCompany));
        updateFilteredPersonList(getPersonLinkedToCompanyPredicate(targetCompany));
        updateFilteredCompanyList(company -> company.equals(targetCompany));
    }

}
