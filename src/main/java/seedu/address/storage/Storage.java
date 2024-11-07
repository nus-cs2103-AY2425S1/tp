package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.CustomerOrderList;
import seedu.address.model.order.SupplyOrderList;
import seedu.address.model.product.IngredientCatalogue;
import seedu.address.model.product.PastryCatalogue;

/**
 * API of the Storage component.
 * Provides methods to save and retrieve data from local storage.
 */
public interface Storage extends AddressBookStorage, UserPrefsStorage, CustomerOrderListStorage,
        SupplyOrderListStorage, IngredientCatalogueStorage, PastryCatalogueStorage {

    /**
     * Reads user preferences from the storage.
     *
     * @return An {@code Optional} containing {@code UserPrefs}, or empty if not found.
     * @throws DataLoadingException if the data cannot be read or is in an invalid format.
     */
    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    /**
     * Saves the user preferences to the storage.
     *
     * @param userPrefs The user preferences to be saved.
     * @throws IOException if an error occurs during writing.
     */
    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    /**
     * Returns the file path of the AddressBook data file.
     *
     * @return Path to the AddressBook data file.
     */
    @Override
    Path getAddressBookFilePath();

    /**
     * Reads the AddressBook from the storage.
     *
     * @return An {@code Optional} containing the AddressBook data, or empty if not found.
     * @throws DataLoadingException if the data cannot be read or is in an invalid format.
     */
    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException;

    /**
     * Saves the AddressBook to the storage.
     *
     * @param addressBook The AddressBook to be saved.
     * @throws IOException if an error occurs during writing.
     */
    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    /**
     * Reads the IngredientCatalogue from the storage.
     *
     * @return An {@code Optional} containing the IngredientCatalogue, or empty if not found.
     * @throws DataLoadingException if the data cannot be read or is in an invalid format.
     */
    @Override
    Optional<IngredientCatalogue> readIngredientCatalogue() throws DataLoadingException;

    /**
     * Saves the IngredientCatalogue to the storage.
     *
     * @param ingredientCatalogue The IngredientCatalogue to be saved.
     * @throws IOException if an error occurs during writing.
     */
    @Override
    void saveIngredientCatalogue(IngredientCatalogue ingredientCatalogue) throws IOException;

    /**
     * Reads the PastryCatalogue from the storage.
     *
     * @return An {@code Optional} containing the PastryCatalogue, or empty if not found.
     * @throws DataLoadingException if the data cannot be read or is in an invalid format.
     */
    @Override
    Optional<PastryCatalogue> readPastryCatalogue() throws DataLoadingException;

    /**
     * Saves the PastryCatalogue to the storage.
     *
     * @param pastryCatalogue The PastryCatalogue to be saved.
     * @throws IOException if an error occurs during writing.
     */
    @Override
    void savePastryCatalogue(PastryCatalogue pastryCatalogue) throws IOException;

    /**
     * Returns the file path of the CustomerOrderList data file.
     *
     * @return Path to the CustomerOrderList data file.
     */
    @Override
    Path getCustomerOrderListFilePath();

    /**
     * Reads the CustomerOrderList from the storage.
     *
     * @return An {@code Optional} containing the CustomerOrderList, or empty if not found.
     * @throws DataLoadingException if the data cannot be read or is in an invalid format.
     */
    @Override
    Optional<CustomerOrderList> readCustomerOrderList() throws DataLoadingException;

    /**
     * Saves the CustomerOrderList to the storage.
     *
     * @param customerOrderList The CustomerOrderList to be saved.
     * @throws IOException if an error occurs during writing.
     */
    @Override
    void saveCustomerOrderList(CustomerOrderList customerOrderList) throws IOException;

    /**
     * Returns the file path of the SupplyOrderList data file.
     *
     * @return Path to the SupplyOrderList data file.
     */
    @Override
    Path getSupplyOrderListFilePath();

    /**
     * Reads the SupplyOrderList from the storage.
     *
     * @return An {@code Optional} containing the SupplyOrderList, or empty if not found.
     * @throws DataLoadingException if the data cannot be read or is in an invalid format.
     */
    @Override
    Optional<SupplyOrderList> readSupplyOrderList() throws DataLoadingException;

    /**
     * Saves the SupplyOrderList to the storage.
     *
     * @param supplyOrderList The SupplyOrderList to be saved.
     * @throws IOException if an error occurs during writing.
     */
    @Override
    void saveSupplyOrderList(SupplyOrderList supplyOrderList) throws IOException;
}