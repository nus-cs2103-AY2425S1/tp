package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import com.opencsv.bean.CsvToBeanFilter;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.util.CsvUtil;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyReceiptLog;
import seedu.address.model.ReceiptLog;
import seedu.address.model.goods.GoodsCategories;
import seedu.address.model.goods.GoodsName;
import seedu.address.model.goodsreceipt.Date;
import seedu.address.model.goodsreceipt.GoodsReceipt;
import seedu.address.model.person.Name;

/**
 * A class to access Goods data stored as a csv file on the hard disk.
 */
public class CsvGoodsStorage implements GoodsStorage {

    private static final Logger logger = LogsCenter.getLogger(CsvGoodsStorage.class);

    private Path filePath;

    public CsvGoodsStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getGoodsFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyReceiptLog> readGoods() throws DataLoadingException {
        return readGoods(filePath);
    }

    @Override
    public Optional<ReadOnlyReceiptLog> readGoods(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        ReceiptLog receiptLog = new ReceiptLog();

        CsvToBeanFilter filter = new CsvToBeanFilter() {
            private final HashSet<String> seenReceipts = new HashSet<>();

            @Override
            public boolean allowLine(String[] line) {
                String[] trimmedLine = Arrays.stream(line).map(String::trim).toArray(String[]::new);
                boolean isDuplicate = !seenReceipts.add(Arrays.toString(trimmedLine));
                boolean hasMissingFields = trimmedLine.length != 7;
                if (hasMissingFields || isDuplicate) {
                    return false;
                }

                String[] goodDetails = trimmedLine[1].split(",", 2);
                if (goodDetails.length != 2) {
                    return false;
                }

                try {
                    // TODO: Improve error handling
                    Date arrivalDate = new Date(trimmedLine[0].replace("T", " "));
                    GoodsName name = new GoodsName(goodDetails[0]);
                    GoodsCategories goodsCategory = GoodsCategories.valueOf(goodDetails[1]);
                    boolean isDelivered = Boolean.parseBoolean(trimmedLine[2]);
                    double price = Double.parseDouble(trimmedLine[3]);
                    Date procurementDate = new Date(trimmedLine[4].replace("T", " "));
                    int quantity = Integer.parseInt(trimmedLine[5]);
                    Name supplier = new Name(trimmedLine[6]);

                    return true;
                } catch (RuntimeException e) {
                    logger.warning("Error parsing line: " + e.getMessage());
                    return false;
                }
            }
        };
        Optional<List<GoodsReceipt>> goodsReceiptList = CsvUtil.readCsvFile(filePath, GoodsReceipt.class, filter);
        goodsReceiptList.ifPresent(receiptLog::setReceipts);
        return Optional.of(receiptLog);
    }

    @Override
    public void saveGoods(ReadOnlyReceiptLog goods) throws IOException {
        saveGoods(goods, filePath);
    }

    @Override
    public void saveGoods(ReadOnlyReceiptLog goods, Path filePath) throws IOException {
        requireNonNull(goods);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        CsvUtil.writeCsvFile(filePath, goods.getReceiptList());
    }
}
