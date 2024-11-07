package seedu.internbuddy.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.internbuddy.commons.util.ToStringBuilder;
import seedu.internbuddy.model.company.Company;
import seedu.internbuddy.model.company.UniqueCompanyList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamecompany comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueCompanyList companies;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        companies = new UniqueCompanyList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the companies in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the company list with {@code companies}.
     * {@code companies} must not contain duplicate companies.
     */
    public void setCompanies(List<Company> companies) {
        this.companies.setCompanies(companies);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setCompanies(newData.getCompanyList());
    }

    /**
     * Hides application details for all companies in the address book.
     */
    public void hideAppDetailsForAll() {
        for (Company companyToHide : companies) {
            Company hiddenCompany = new Company(companyToHide.getName(), companyToHide.getPhone(),
                    companyToHide.getEmail(), companyToHide.getAddress(), companyToHide.getTags(),
                    companyToHide.getStatus(), companyToHide.getApplications(), companyToHide.getIsFavourite(),
                    false);
            setCompany(companyToHide, hiddenCompany);
        };
    }

    //// company-level operations

    /**
     * Returns true if a company with the same identity as {@code company} exists in the address book.
     */
    public boolean hasCompany(Company company) {
        requireNonNull(company);
        return companies.contains(company);
    }

    /**
     * Adds a company to the address book.
     * The company must not already exist in the address book.
     */
    public void addCompany(Company c) {
        companies.add(c);
    }

    /**
     * Replaces the given company {@code target} in the list with {@code editedCompany}.
     * {@code target} must exist in the address book.
     * The company identity of {@code editedCompany}
     * must not be the same as another existing company in the address book.
     */
    public void setCompany(Company target, Company editedCompany) {
        requireNonNull(editedCompany);

        companies.setCompany(target, editedCompany);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeCompany(Company key) {
        companies.remove(key);
    }

    /**
     * Views application details of this {@code companyToView} in the UI.
     * {@code companyToView} is the company whose application is to be viewed.
     */

    public void viewAppDetails(Company companyToView) {
        Company viewedCompany = new Company(companyToView.getName(), companyToView.getPhone(), companyToView.getEmail(),
                companyToView.getAddress(), companyToView.getTags(), companyToView.getStatus(),
                companyToView.getApplications(), companyToView.getIsFavourite(), true);
        setCompany(companyToView, viewedCompany);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("companies", companies)
                .toString();
    }

    @Override
    public ObservableList<Company> getCompanyList() {
        return companies.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return companies.equals(otherAddressBook.companies);
    }

    @Override
    public int hashCode() {
        return companies.hashCode();
    }
}
