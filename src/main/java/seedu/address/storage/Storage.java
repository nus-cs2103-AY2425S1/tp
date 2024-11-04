package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.product.IngredientCatalogue;
import seedu.address.model.product.PastryCatalogue;
import seedu.address.model.order.CustomerOrderList;
import seedu.address.model.order.SupplyOrderList;

/**
 * API of the Storage component
 */

public interface Storage extends AddressBookStorage, UserPrefsStorage, CustomerOrderListStorage, SupplyOrderListStorage
        , IngredientCatalogueStorage, PastryCatalogueStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getAddressBookFilePath();

    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException;

    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    @Override
    Optional<IngredientCatalogue> readIngredientCatalogue() throws DataLoadingException;

    @Override
    void saveIngredientCatalogue(IngredientCatalogue ingredientCatalogue) throws IOException;

    @Override
    Optional<PastryCatalogue> readPastryCatalogue() throws DataLoadingException;

    @Override
    void savePastryCatalogue(PastryCatalogue pastryCatalogue) throws IOException;
    Path getCustomerOrderListFilePath();

    @Override
    Optional<CustomerOrderList> readCustomerOrderList() throws DataLoadingException;

    @Override
    void saveCustomerOrderList(CustomerOrderList customerOrderList) throws IOException;

    @Override
    Path getSupplyOrderListFilePath();

    @Override
    Optional<SupplyOrderList> readSupplyOrderList() throws DataLoadingException;

    @Override
    void saveSupplyOrderList(SupplyOrderList supplyOrderList) throws IOException;
}
