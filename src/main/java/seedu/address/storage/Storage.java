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

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, UserPrefsStorage, IngredientCatalogueStorage, PastryCatalogueStorage {

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
}
