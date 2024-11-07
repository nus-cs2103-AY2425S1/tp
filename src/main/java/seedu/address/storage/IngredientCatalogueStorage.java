package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.product.IngredientCatalogue;

/**
 * Represents a storage for {@link seedu.address.model.product.IngredientCatalogue}.
 */
public interface IngredientCatalogueStorage {

    Path getIngredientCatalogueFilePath();

    Optional<IngredientCatalogue> readIngredientCatalogue() throws DataLoadingException;

    Optional<IngredientCatalogue> readIngredientCatalogue(Path filePath) throws DataLoadingException;

    void saveIngredientCatalogue(IngredientCatalogue ingredientCatalogue) throws IOException;

    void saveIngredientCatalogue(IngredientCatalogue ingredientCatalogue, Path filePath) throws IOException;
}
