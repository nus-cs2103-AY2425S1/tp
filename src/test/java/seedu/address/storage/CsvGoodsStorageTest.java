package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyReceiptLog;
import seedu.address.model.ReceiptLog;
import seedu.address.model.goodsreceipt.GoodsReceipt;
import seedu.address.testutil.GoodsReceiptBuilder;



public class CsvGoodsStorageTest {

    @TempDir
    public Path testFolder;

    private CsvGoodsStorage csvGoodsStorage;
    private Path filePath;

    @BeforeEach
    public void setUp() {
        filePath = testFolder.resolve("TempGoods.csv");
        csvGoodsStorage = new CsvGoodsStorage(filePath);
    }

    @Test
    public void saveGoods_nullGoods_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> csvGoodsStorage.saveGoods(null, filePath));
    }

    @Test
    public void saveGoods_nullFilePath_throwsNullPointerException() {
        GoodsReceipt goodsReceipt = new GoodsReceiptBuilder().build();
        ReceiptLog receiptLog = new ReceiptLog();
        receiptLog.addReceipt(goodsReceipt);
        assertThrows(NullPointerException.class, () -> csvGoodsStorage.saveGoods(receiptLog, null));
    }

    @Test
    public void saveGoods_validGoods_success() throws IOException {
        GoodsReceipt goodsReceipt = new GoodsReceiptBuilder().build();
        ReceiptLog receiptLog = new ReceiptLog();
        receiptLog.addReceipt(goodsReceipt);

        csvGoodsStorage.saveGoods(receiptLog, filePath);

        List<String> lines = Files.readAllLines(filePath);
        String expectedLine = "\"2021-01-01T00:00\","
                + "\"Apple,CONSUMABLES\","
                + "\"false\","
                + "\"10.00\","
                + "\"2021-01-01T00:00\","
                + "\"1\","
                + "\"Supplier\"";
        assertTrue(lines.contains(expectedLine));
    }

    @Test
    public void readGoods_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> csvGoodsStorage.readGoods(null));
    }

    @Test
    public void readGoods_missingFile_returnOptionalReadOnlyReceiptLogWithNoReceipt() throws DataLoadingException {
        Path nonExistentFile = Paths.get("nonExistentFile.csv");
        Optional<ReadOnlyReceiptLog> optionalReceiptLog = csvGoodsStorage.readGoods(nonExistentFile);

        assertTrue(optionalReceiptLog.isPresent());
        ReadOnlyReceiptLog receiptLog = optionalReceiptLog.get();
        assertTrue(receiptLog.getReceiptList().isEmpty());
    }

    @Test
    public void readGoods_invalidCsvFormat_throwsException() throws IOException {
        Files.write(filePath, "invalid csv format".getBytes());
        assertThrows(DataLoadingException.class, () -> csvGoodsStorage.readGoods(filePath));
    }

    @Test
    public void readGoods_duplicateData_removesDuplicateData() throws IOException, DataLoadingException {
        String headerLine = "\"arrival date\","
                + "\"goods\","
                + "\"is delivered\","
                + "\"price\","
                + "\"procurement date\","
                + "\"quantity\","
                + "\"supplier name\"\n";
        String content = "\"2021-01-01T00:00\","
                + "\"Apple,CONSUMABLES\","
                + "\"false\","
                + "\"10.00\","
                + "\"2021-01-01T00:00\","
                + "\"1\","
                + "\"Supplier\"";
        Files.write(filePath, headerLine.getBytes());
        Files.write(filePath, content.getBytes(), java.nio.file.StandardOpenOption.APPEND);
        Files.write(filePath, content.getBytes(), java.nio.file.StandardOpenOption.APPEND);

        Optional<ReadOnlyReceiptLog> optionalReceiptLog = csvGoodsStorage.readGoods(filePath);
        assertTrue(optionalReceiptLog.isPresent());
        ReadOnlyReceiptLog receiptLog = optionalReceiptLog.get();
        List<GoodsReceipt> goodsReceiptList = receiptLog.getReceiptList();
        assertEquals(1, goodsReceiptList.size());
        GoodsReceipt goodsReceipt = goodsReceiptList.get(0);
        GoodsReceipt expected = new GoodsReceiptBuilder().build();
        assertTrue(expected.isSameReceipt(goodsReceipt));
    }

    @Test
    public void readGoods_missingData_removesInvalidData() throws IOException, DataLoadingException {
        String headerLine = "\"arrival date\","
            + "\"goods\","
            + "\"is delivered\","
            + "\"price\","
            + "\"procurement date\","
            + "\"quantity\","
            + "\"supplier name\"\n";
        String content = "\"2021-01-01T00:00\","
                + "\"Apple,CONSUMABLES\","
                + "\"false\","
                + "\"10.00\","
                + "\"2021-01-01T00:00\","
                + "\"1\","
                + "\"Supplier\"\n";
        String missingContent = "\"2022-02-02T00:00\","
                + "\"Banana\","
                + "\"true\","
                + "\"20.00\","
                + "\"2022-02-02T00:00\","
                + "\"2\","
                + "\"New Supplier\"\n";
        Files.write(filePath, headerLine.getBytes());
        Files.write(filePath, content.getBytes(), java.nio.file.StandardOpenOption.APPEND);
        Files.write(filePath, missingContent.getBytes(), java.nio.file.StandardOpenOption.APPEND);

        Optional<ReadOnlyReceiptLog> optionalReceiptLog = csvGoodsStorage.readGoods(filePath);
        assertTrue(optionalReceiptLog.isPresent());
        ReadOnlyReceiptLog receiptLog = optionalReceiptLog.get();
        List<GoodsReceipt> goodsReceiptList = receiptLog.getReceiptList();
        assertEquals(1, goodsReceiptList.size());
        GoodsReceipt goodsReceipt = goodsReceiptList.get(0);
        GoodsReceipt expected = new GoodsReceiptBuilder().build();
        assertTrue(expected.isSameReceipt(goodsReceipt));
    }

    @Test
    public void readGoods_validCsv_success() throws IOException, DataLoadingException {
        String headerLine = "\"arrival date\","
                + "\"goods\","
                + "\"is delivered\","
                + "\"price\","
                + "\"procurement date\","
                + "\"quantity\","
                + "\"supplier name\"\n";
        String content = "\"2021-01-01T00:00\","
                + "\"Apple,CONSUMABLES\","
                + "\"false\","
                + "\"10.00\","
                + "\"2021-01-01T00:00\","
                + "\"1\","
                + "\"Supplier\"\n";
        Files.write(filePath, headerLine.getBytes());
        Files.write(filePath, content.getBytes(), java.nio.file.StandardOpenOption.APPEND);

        Optional<ReadOnlyReceiptLog> optionalReceiptLog = csvGoodsStorage.readGoods(filePath);
        assertTrue(optionalReceiptLog.isPresent());
        ReadOnlyReceiptLog receiptLog = optionalReceiptLog.get();
        List<GoodsReceipt> goodsReceiptList = receiptLog.getReceiptList();
        assertEquals(1, goodsReceiptList.size());
        GoodsReceipt goodsReceipt = goodsReceiptList.get(0);
        GoodsReceipt expected = new GoodsReceiptBuilder().build();
        assertTrue(expected.isSameReceipt(goodsReceipt));
    }
}
