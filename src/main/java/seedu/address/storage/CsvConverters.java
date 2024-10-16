package seedu.address.storage;

import com.opencsv.bean.AbstractBeanField;

import seedu.address.model.goods.Date;
import seedu.address.model.goods.GoodsName;
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
            return new Date(value);
        }
    }

    /**
     * Custom converter for GoodsName type for Goods.
     */
    public static class GoodsNameConverter extends AbstractBeanField<GoodsName, String> {
        @Override
        protected Object convert(String value) {
            return new GoodsName(value);
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
}
