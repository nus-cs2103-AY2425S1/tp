package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.util.CsvUtil;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.goodsReceipt.GoodsReceipt;

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
    public Optional<List<GoodsReceipt>> readGoods() throws DataLoadingException {
        return readGoods(filePath);
    }

    @Override
    public Optional<List<GoodsReceipt>> readGoods(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);
        return CsvUtil.readCsvFile(filePath, GoodsReceipt.class);
    }

    @Override
    public void saveGoods(List<GoodsReceipt> goods) throws IOException {
        saveGoods(goods, filePath);
    }

    @Override
    public void saveGoods(List<GoodsReceipt> goods, Path filePath) throws IOException {
        requireNonNull(goods);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        CsvUtil.writeCsvFile(filePath, goods);
    }
}
