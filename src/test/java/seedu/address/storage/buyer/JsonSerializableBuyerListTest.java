package seedu.address.storage.buyer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.BuyerList;
import seedu.address.testutil.buyer.TypicalBuyers;

public class JsonSerializableBuyerListTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableBuyerListTest");
    private static final Path TYPICAL_BUYERS_FILE = TEST_DATA_FOLDER.resolve("typicalBuyersBuyerList.json");
    private static final Path INVALID_BUYER_FILE = TEST_DATA_FOLDER.resolve("invalidBuyerBuyerList.json");
    private static final Path DUPLICATE_BUYER_FILE = TEST_DATA_FOLDER.resolve("duplicateBuyerBuyerList.json");

    @Test
    public void toModelType_typicalBuyersFile_success() throws Exception {
        JsonSerializableBuyerList dataFromFile = JsonUtil.readJsonFile(TYPICAL_BUYERS_FILE,
                JsonSerializableBuyerList.class).get();
        BuyerList buyerListFromFile = dataFromFile.toModelType();
        BuyerList typicalBuyersBuyerList = TypicalBuyers.getTypicalBuyerList();
        assertEquals(buyerListFromFile, typicalBuyersBuyerList);
    }

    @Test
    public void toModelType_invalidBuyerFile_throwsIllegalValueException() throws Exception {
        JsonSerializableBuyerList dataFromFile = JsonUtil.readJsonFile(INVALID_BUYER_FILE,
                JsonSerializableBuyerList.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateBuyers_throwsIllegalValueException() throws Exception {
        JsonSerializableBuyerList dataFromFile = JsonUtil.readJsonFile(DUPLICATE_BUYER_FILE,
                JsonSerializableBuyerList.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableBuyerList.MESSAGE_DUPLICATE_BUYER,
                dataFromFile::toModelType);
    }

}
