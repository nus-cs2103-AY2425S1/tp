package seedu.address.testutil;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.company.Company;
import seedu.address.model.job.Job;
import seedu.address.model.person.Person;

/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {
    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public GuiSettings getGuiSettings() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getAddressBookFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setAddressBook(ReadOnlyAddressBook newData) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasPerson(Person person) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasCompany(Company company) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasJob(Job job) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deletePerson(Person target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteCompany(Company target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteJob(Job target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addPerson(Person person) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addCompany(Company company) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addJob(Job job) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setJob(Job target, Job editedJob) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setCompany(Company target, Company editedCompany) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Job> getFilteredJobList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Company> getFilteredCompanyList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredJobList(Predicate<Job> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredCompanyList(Predicate<Company> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void showLinkedJobsAndPersonsByCompany(Company company) {
        throw new AssertionError("This method should not be called.");
    }
}
