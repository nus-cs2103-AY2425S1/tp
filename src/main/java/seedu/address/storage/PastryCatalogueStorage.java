package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.product.PastryCatalogue;

/**
 * Represents a storage for {@link PastryCatalogue}.
 */
public interface PastryCatalogueStorage {

    Path getPastryCatalogueFilePath();

    Optional<PastryCatalogue> readPastryCatalogue() throws DataLoadingException;

    Optional<PastryCatalogue> readPastryCatalogue(Path filePath) throws DataLoadingException;

    void savePastryCatalogue(PastryCatalogue pastryCatalogue) throws IOException;

    void savePastryCatalogue(PastryCatalogue pastryCatalogue, Path filePath) throws IOException;
}
