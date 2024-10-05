package seedu.internbuddy.model;

import static java.util.Objects.requireNonNull;
import static seedu.internbuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.internbuddy.commons.core.GuiSettings;
import seedu.internbuddy.commons.core.LogsCenter;
import seedu.internbuddy.model.company.Company;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManagerCompany implements ModelCompany {
    private static final Logger logger = LogsCenter.getLogger(ModelManagerCompany.class);

    private final AddressBookCompany addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Company> filteredCompanies;

    /**
     * Initializes a ModelManagerCompany with the given addressBook and userPrefs.
     */
    public ModelManagerCompany(ReadOnlyAddressBookCompany addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBookCompany(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredCompanies = new FilteredList<>(this.addressBook.getCompanyList());
    }

    public ModelManagerCompany() {
        this(new AddressBookCompany(), new UserPrefs());
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
    public void setAddressBook(ReadOnlyAddressBookCompany addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBookCompany getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasCompany(Company company) {
        requireNonNull(company);
        return addressBook.hasCompany(company);
    }

    @Override
    public void deleteCompany(Company target) {
        addressBook.removeCompany(target);
    }

    @Override
    public void addCompany(Company company) {
        addressBook.addCompany(company);
        updateFilteredCompanyList(PREDICATE_SHOW_ALL_COMPANIES);
    }

    @Override
    public void setCompany(Company target, Company editedCompany) {
        requireAllNonNull(target, editedCompany);

        addressBook.setCompany(target, editedCompany);
    }

    //=========== Filtered Company List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Company} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Company> getFilteredCompanyList() {
        return filteredCompanies;
    }

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
        if (!(other instanceof ModelManagerCompany)) {
            return false;
        }

        ModelManagerCompany otherModelManagerCompany = (ModelManagerCompany) other;
        return addressBook.equals(otherModelManagerCompany.addressBook)
                && userPrefs.equals(otherModelManagerCompany.userPrefs)
                && filteredCompanies.equals(otherModelManagerCompany.filteredCompanies);
    }

}
