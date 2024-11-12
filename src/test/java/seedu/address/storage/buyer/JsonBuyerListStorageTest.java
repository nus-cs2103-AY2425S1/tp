package seedu.address.storage.buyer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.buyer.TypicalBuyers.ALICE;
import static seedu.address.testutil.buyer.TypicalBuyers.HOON;
import static seedu.address.testutil.buyer.TypicalBuyers.IDA;
import static seedu.address.testutil.buyer.TypicalBuyers.getTypicalBuyerList;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.BuyerList;
import seedu.address.model.ReadOnlyBuyerList;

public class JsonBuyerListStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonBuyerListStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readBuyerList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readBuyerList(null));
    }

    private java.util.Optional<ReadOnlyBuyerList> readBuyerList(String filePath) throws Exception {
        return new JsonBuyerListStorage(Paths.get(filePath)).readBuyerList(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readBuyerList("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readBuyerList("notJsonFormatBuyerList.json"));
    }

    @Test
    public void readBuyerList_invalidBuyerList_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readBuyerList("invalidBuyerList.json"));
    }

    @Test
    public void readBuyerList_invalidAndValidBuyerList_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readBuyerList("invalidAndValidBuyerList.json"));
    }

    @Test
    public void readAndSaveBuyerList_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempBuyerList.json");
        BuyerList original = getTypicalBuyerList();
        JsonBuyerListStorage jsonBuyerListStorage = new JsonBuyerListStorage(filePath);

        // Save in new file and read back
        jsonBuyerListStorage.saveBuyerList(original, filePath);
        ReadOnlyBuyerList readBack = jsonBuyerListStorage.readBuyerList(filePath).get();
        assertEquals(original, new BuyerList(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addBuyer(HOON);
        original.removeBuyer(ALICE);
        jsonBuyerListStorage.saveBuyerList(original, filePath);
        readBack = jsonBuyerListStorage.readBuyerList(filePath).get();
        assertEquals(original, new BuyerList(readBack));

        // Save and read without specifying file path
        original.addBuyer(IDA);
        jsonBuyerListStorage.saveBuyerList(original); // file path not specified
        readBack = jsonBuyerListStorage.readBuyerList().get(); // file path not specified
        assertEquals(original, new BuyerList(readBack));

    }

    @Test
    public void saveBuyerList_nullBuyerList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveBuyerList(null, "SomeFile.json"));
    }

    /**
     * Saves {@code buyerList} at the specified {@code filePath}.
     */
    private void saveBuyerList(ReadOnlyBuyerList buyerList, String filePath) {
        try {
            new JsonBuyerListStorage(Paths.get(filePath))
                    .saveBuyerList(buyerList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveBuyerList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveBuyerList(new BuyerList(), null));
    }
}
