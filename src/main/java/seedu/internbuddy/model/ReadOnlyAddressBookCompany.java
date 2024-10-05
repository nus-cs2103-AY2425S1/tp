package seedu.internbuddy.model;

import javafx.collections.ObservableList;
import seedu.internbuddy.model.company.Company;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBookCompany {

    /**
     * Returns an unmodifiable view of the companies list.
     * This list will not contain any duplicate companies.
     */
    ObservableList<Company> getCompanyList();

}
