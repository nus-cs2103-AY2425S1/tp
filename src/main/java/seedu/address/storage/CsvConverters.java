package seedu.address.storage;

import com.opencsv.bean.AbstractBeanField;

import seedu.address.model.goods.Goods;
import seedu.address.model.goods.GoodsCategories;
import seedu.address.model.goods.GoodsName;
import seedu.address.model.goodsreceipt.Date;
import seedu.address.model.person.Name;


/**
 * Custom converters for OpenCSV to convert between CSV strings and Java objects.
 */
public class CsvConverters {

    /**
     * Custom converter for Date type for Goods.
     */
    public static class GoodsDateConverter extends AbstractBeanField<Date, String> {
        @Override
        protected Object convert(String value) {
            return new Date(value.replace("T", " "));
        }
    }

    /**
     * Custom converter for Name type for Person.
     */
    public static class PersonNameConverter extends AbstractBeanField<Name, String> {
        @Override
        protected Object convert(String value) {
            return new Name(value);
        }
    }

    /**
     * Custom converter for Goods type for GoodsReceipt.
     */
    public static class GoodsConverter extends AbstractBeanField<Goods, String> {
        @Override
        protected Object convert(String value) {
            String[] goodsDetails = value.split(",", 2);

            if (goodsDetails.length != 2) {
                throw new IllegalArgumentException("Goods details must contain a name and a category");
            }
            GoodsName goodsName = new GoodsName(goodsDetails[0]);
            GoodsCategories category = GoodsCategories.valueOf(goodsDetails[1]);
            return new Goods(goodsName, category);
        }

        @Override
        protected String convertToWrite(Object value) {
            Goods goods = (Goods) value;
            return goods.convertToCsvWrite();
        }
    }
}
