package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

import seedu.address.model.goods.Goods;
import seedu.address.model.goods.GoodsCategories;
import seedu.address.model.goods.GoodsName;
import seedu.address.model.goodsreceipt.Date;
import seedu.address.model.person.Name;


public class CsvConvertersTest {

    @Test
    public void goodsDateConverter_validDate_success() {
        CsvConverters.GoodsDateConverter converter = new CsvConverters.GoodsDateConverter();
        String csvDate = "2021-01-01T00:00";
        Date expectedDate = new Date("2021-01-01 00:00");
        Date actualDate = (Date) converter.convert(csvDate);
        assertEquals(expectedDate, actualDate);
    }

    @Test
    public void goodsDateConverter_invalidDate_throwDateTimeParseException() {
        CsvConverters.GoodsDateConverter converter = new CsvConverters.GoodsDateConverter();
        String invalidDate = "invalid-date";
        assertThrows(DateTimeParseException.class, () -> {
            converter.convert(invalidDate);
        });
    }

    @Test
    public void goodsDateConverter_nullDate_throwNullPointerException() {
        CsvConverters.GoodsDateConverter converter = new CsvConverters.GoodsDateConverter();
        assertThrows(NullPointerException.class, () -> {
            converter.convert(null);
        });
    }

    @Test
    public void goodsDateConverter_incorrectDateFormat_throwIllegalArgumentException() {
        CsvConverters.GoodsDateConverter converter = new CsvConverters.GoodsDateConverter();
        String incorrectDateFormat = "2021-01-01";
        assertThrows(IllegalArgumentException.class, () -> {
            converter.convert(incorrectDateFormat);
        });
    }

    @Test
    public void personNameConverter_validName_success() {
        CsvConverters.PersonNameConverter converter = new CsvConverters.PersonNameConverter();
        String csvName = "John Doe";
        Name expectedName = new Name(csvName);
        Name actualName = (Name) converter.convert(csvName);
        assertEquals(expectedName, actualName);
    }

    @Test
    public void personNameConverter_nullName_throwNullPointerException() {
        CsvConverters.PersonNameConverter converter = new CsvConverters.PersonNameConverter();
        assertThrows(NullPointerException.class, () -> {
            converter.convert(null);
        });
    }

    @Test
    public void personNameConverter_invalidName_throwIllegalArgumentException() {
        CsvConverters.PersonNameConverter converter = new CsvConverters.PersonNameConverter();
        String invalidName = "John@Doe";
        assertThrows(IllegalArgumentException.class, () -> {
            converter.convert(invalidName);
        });
    }

    @Test
    public void personNameConverter_blankName_throwIllegalArgumentException() {
        CsvConverters.PersonNameConverter converter = new CsvConverters.PersonNameConverter();
        String invalidName = " ";
        assertThrows(IllegalArgumentException.class, () -> {
            converter.convert(invalidName);
        });
    }

    @Test
    public void goodsConverter_validGoods_success() {
        CsvConverters.GoodsConverter converter = new CsvConverters.GoodsConverter();
        String csvGoods = "Apple,CONSUMABLES";
        GoodsName goodsName = new GoodsName("Apple");
        GoodsCategories category = GoodsCategories.CONSUMABLES;
        Goods expectedGoods = new Goods(goodsName, category);
        Goods actualGoods = (Goods) converter.convert(csvGoods);
        assertEquals(expectedGoods.convertToCsvWrite(), actualGoods.convertToCsvWrite());
    }

    @Test
    public void goodsConverter_missingGoodsCategory_throwIllegalArgumentException() {
        CsvConverters.GoodsConverter converter = new CsvConverters.GoodsConverter();
        String invalidGoods = "Apple,";
        assertThrows(IllegalArgumentException.class, () -> {
            converter.convert(invalidGoods);
        });
    }

    @Test
    public void goodsConverter_missingGoodsName_throwIllegalArgumentException() {
        CsvConverters.GoodsConverter converter = new CsvConverters.GoodsConverter();
        String invalidGoods = ",CONSUMABLES";
        assertThrows(IllegalArgumentException.class, () -> {
            converter.convert(invalidGoods);
        });
    }

    @Test
    public void goodsConverter_invalidGoodsCategory_throwIllegalArgumentException() {
        CsvConverters.GoodsConverter converter = new CsvConverters.GoodsConverter();
        String invalidGoods = "Apple,consume";
        assertThrows(IllegalArgumentException.class, () -> {
            converter.convert(invalidGoods);
        });
    }

    @Test
    public void goodsConverter_missingSeparator_throwIllegalArgumentException() {
        CsvConverters.GoodsConverter converter = new CsvConverters.GoodsConverter();
        String invalidGoods = "Appleconsume";
        assertThrows(IllegalArgumentException.class, () -> {
            converter.convert(invalidGoods);
        });
    }

    @Test
    public void goodsConverter_convertToWrite_success() {
        CsvConverters.GoodsConverter converter = new CsvConverters.GoodsConverter();
        GoodsName goodsName = new GoodsName("Apple");
        GoodsCategories category = GoodsCategories.CONSUMABLES;
        Goods goods = new Goods(goodsName, category);
        String expectedCsv = "Apple,CONSUMABLES";
        String actualCsv = converter.convertToWrite(goods);
        assertEquals(expectedCsv, actualCsv);
    }
}
