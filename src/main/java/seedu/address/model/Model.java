package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.company.Company;
import seedu.address.model.job.Job;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<Job> PREDICATE_SHOW_ALL_JOBS = unused -> true;
    Predicate<Company> PREDICATE_SHOW_ALL_COMPANIES = unused -> true;

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Returns true if a job with the same identity as {@code job} exists in the address book.
     */
    boolean hasJob(Job job);

    /**
     * Returns true if the same company is in the address book.
     *
     * @param company Company to be checked.
     * @return true if in address book.
     */
    boolean hasCompany(Company company);

    /**
     * Deletes the given person as well as any existing association with a job.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Deletes the given job as well as every existing association with a person.
     * The job must exist in the address book.
     */
    void deleteJob(Job target);

    /**
     * Deletes a company same as the target from the address book.
     *
     * @param target Company to be deleted.
     */
    void deleteCompany(Company target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Adds the given job.
     * {@code job} must not already exist in the address book.
     */
    void addJob(Job job);

    /**
     * Adds a company to the address book.
     *
     * @param company Company to be added.
     */
    void addCompany(Company company);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Replaces the given job {@code target} with {@code editedJob}.
     * {@code target} must exist in the address book.
     * The job identity of {@code editedJob} must not be the same as another existing job in the address book.
     */
    void setJob(Job target, Job editedJob);

    /**
     * Replaces the target company with an edited version of itself.
     *
     * @param target Company to be replaced.
     * @param editedCompany Company to replace the other.
     */
    void setCompany(Company target, Company editedCompany);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered job list */
    ObservableList<Job> getFilteredJobList();

    /** Returns an unmodifiable view of the filtered company list */
    ObservableList<Company> getFilteredCompanyList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /** Updates the filter of the filtered job list to filter by the given {@code predicate}. */
    void updateFilteredJobList(Predicate<Job> predicate);

    /** Updates the filter of the filtered company list to filter by the given {@code predicate}. */
    void updateFilteredCompanyList(Predicate<Company> predicate);

    /**
     * Updates the filtered job and person lists to show only the jobs and persons
     * that are linked to the specified company.
     */
    void showLinkedJobsAndPersonsByCompany(Company company);
}
