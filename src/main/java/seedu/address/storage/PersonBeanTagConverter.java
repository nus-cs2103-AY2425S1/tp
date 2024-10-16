package seedu.address.storage;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.opencsv.bean.AbstractBeanField;

/**
 * Custom converter for converting between a CSV string representation of tags
 * and a Set of Strings for the PersonBean class.
 * This class extends AbstractBeanField from OpenCSV to handle conversion
 * of tag data.
 */
public class PersonBeanTagConverter extends AbstractBeanField<Set<String>, String> {
    @Override
    protected Set<String> convert(String value) {
        if (value == null || value.trim().isEmpty()) {
            return new HashSet<>();
        }
        return new HashSet<>(Arrays.asList(value.split(";")));
    }
}
