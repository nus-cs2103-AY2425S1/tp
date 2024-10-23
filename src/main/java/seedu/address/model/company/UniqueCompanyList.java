package seedu.address.model.company;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.company.exceptions.CompanyNotFoundException;
import seedu.address.model.company.exceptions.DuplicateCompanyException;

/**
 * A list of companies that enforces uniqueness between its elements and does not allow nulls.
 * A company is considered unique as deemed by the <Code>Company.isSameCompany(Company)</Code>.
 * Additions and deletions use this logic to determine what can be added and removed.
 */
public class UniqueCompanyList implements Iterable<Company> {
    private final ObservableList<Company> internalList = FXCollections.observableArrayList();
    private final ObservableList<Company> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains a company.
     *
     * @param toCheck Company to be checked.
     * @return true if list already contains company.
     */
    public boolean contains(Company toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameCompany);
    }

    /**
     * Adds a company to the list.
     * Company must not exist in the list.
     *
     * @param toAdd Company to be added.
     */
    public void add(Company toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateCompanyException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the specified company with an edited one.
     *
     * @param target Company to be replaced in list.
     * @param editedCompany Company to replace the other.
     */
    public void setCompany(Company target, Company editedCompany) {
        requireAllNonNull(target, editedCompany);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new CompanyNotFoundException();
        }

        if (!target.isSameCompany(editedCompany) && contains(editedCompany)) {
            throw new DuplicateCompanyException();
        }

        internalList.set(index, editedCompany);
    }

    /**
     * Removes the specified company from the list.
     *
     * @param toRemove Company to be removed.
     */
    public void remove(Company toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new CompanyNotFoundException();
        }
    }

    /**
     * Replaces the list with another list in the other <code>UniqueCompanyList</code>.
     *
     * @param replacement The replacement <code>UniqueCompanyList</code>.
     */
    public void setCompanies(UniqueCompanyList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the list with another <code>List</code>.
     *
     * @param companies The replacement <code>List</code>.
     */
    public void setCompanies(List<Company> companies) {
        requireAllNonNull(companies);
        if (!companiesAreUnique(companies)) {
            throw new DuplicateCompanyException();
        }

        internalList.setAll(companies);
    }

    /**
     * Returns the backing list as an unmodifiable <code>ObservableList</code>.
     */
    public ObservableList<Company> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Company> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueCompanyList)) {
            return false;
        }

        UniqueCompanyList otherUniqueCompanyList = (UniqueCompanyList) other;
        return internalList.equals(otherUniqueCompanyList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if all of the companies in the list are unique.
     *
     * @param companies List to check.
     * @return true if all companies in the list are unique.
     */
    private boolean companiesAreUnique(List<Company> companies) {
        for (int i = 0; i < companies.size() - 1; i++) {
            for (int j = i + 1; j < companies.size(); j++) {
                if (companies.get(i).isSameCompany(companies.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
