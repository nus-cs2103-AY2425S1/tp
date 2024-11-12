package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.product.PastryCatalogue;
import seedu.address.model.util.SampleDataUtil;

/**
 * A class to manage the storage of PastryCatalogue data in JSON format.
 * Provides functionality to read from and write to a JSON file.
 */
public class JsonPastryCatalogueStorage implements PastryCatalogueStorage {

    private final Path filePath;

    public JsonPastryCatalogueStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getPastryCatalogueFilePath() {
        return filePath;
    }

    @Override
    public Optional<PastryCatalogue> readPastryCatalogue() throws DataLoadingException {
        return readPastryCatalogue(filePath);
    }

    @Override
    public Optional<PastryCatalogue> readPastryCatalogue(Path filePath) throws DataLoadingException {
        if (!Files.exists(filePath)) {
            // If the file does not exist, create it and initialize with sample data
            PastryCatalogue sampleCatalogue = SampleDataUtil.getSamplePastryCatalogue();
            try {
                savePastryCatalogue(sampleCatalogue, filePath);
            } catch (IOException e) {
                throw new DataLoadingException(e);
            }
            return Optional.of(sampleCatalogue);
        }
        return JsonUtil.readJsonFile(filePath, PastryCatalogue.class);
    }

    @Override
    public void savePastryCatalogue(PastryCatalogue pastryCatalogue) throws IOException {
        savePastryCatalogue(pastryCatalogue, filePath);
    }

    @Override
    public void savePastryCatalogue(PastryCatalogue pastryCatalogue, Path filePath) throws IOException {
        JsonUtil.saveJsonFile(pastryCatalogue, filePath);
    }
}
