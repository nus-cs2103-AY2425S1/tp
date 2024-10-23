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
    public void testGoodsDateConverter_validDate() {
        CsvConverters.GoodsDateConverter converter = new CsvConverters.GoodsDateConverter();
        String csvDate = "2021-01-01T00:00";
        Date expectedDate = new Date("2021-01-01 00:00");
        Date actualDate = (Date) converter.convert(csvDate);
        assertEquals(expectedDate, actualDate);
    }

    @Test
    public void testGoodsDateConverter_invalidDate() {
        CsvConverters.GoodsDateConverter converter = new CsvConverters.GoodsDateConverter();
        String invalidDate = "invalid-date";
        assertThrows(DateTimeParseException.class, () -> {
            converter.convert(invalidDate);
        });
    }

    @Test
    public void testPersonNameConverter_validName() {
        CsvConverters.PersonNameConverter converter = new CsvConverters.PersonNameConverter();
        String csvName = "John Doe";
        Name expectedName = new Name(csvName);
        Name actualName = (Name) converter.convert(csvName);
        assertEquals(expectedName, actualName);
    }

    @Test
    public void testPersonNameConverter_nullName() {
        CsvConverters.PersonNameConverter converter = new CsvConverters.PersonNameConverter();
        assertThrows(NullPointerException.class, () -> {
            converter.convert(null);
        });
    }

    @Test
    public void testGoodsConverter_validGoods() {
        CsvConverters.GoodsConverter converter = new CsvConverters.GoodsConverter();
        String csvGoods = "Apple,CONSUMABLES";
        GoodsName goodsName = new GoodsName("Apple");
        GoodsCategories category = GoodsCategories.CONSUMABLES;
        Goods expectedGoods = new Goods(goodsName, category);
        Goods actualGoods = (Goods) converter.convert(csvGoods);
        assertEquals(expectedGoods.convertToCsvWrite(), actualGoods.convertToCsvWrite());
    }

    @Test
    public void testGoodsConverter_invalidGoods() {
        CsvConverters.GoodsConverter converter = new CsvConverters.GoodsConverter();
        String invalidGoods = "Apple";
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            converter.convert(invalidGoods);
        });
    }

    @Test
    public void testGoodsConverter_convertToWrite() {
        CsvConverters.GoodsConverter converter = new CsvConverters.GoodsConverter();
        GoodsName goodsName = new GoodsName("Apple");
        GoodsCategories category = GoodsCategories.CONSUMABLES;
        Goods goods = new Goods(goodsName, category);
        String expectedCsv = "Apple,CONSUMABLES";
        String actualCsv = converter.convertToWrite(goods);
        assertEquals(expectedCsv, actualCsv);
    }
}
