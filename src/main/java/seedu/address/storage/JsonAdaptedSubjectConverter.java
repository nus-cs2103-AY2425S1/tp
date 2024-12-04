package seedu.address.storage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.opencsv.bean.AbstractBeanField;

/**
 * Custom converter for converting between a CSV string representation of subjects
 * and a Set of Strings for the PersonBean class.
 * This class extends AbstractBeanField from OpenCSV to handle conversion
 * of tag data.
 */
public class JsonAdaptedSubjectConverter extends AbstractBeanField<List<JsonAdaptedSubject>, String> {
    @Override
    protected List<JsonAdaptedSubject> convert(String value) {
        if (value == null || value.trim().isEmpty()) {
            return new ArrayList<>();
        }
        List<String> stringList = Arrays.asList(value.split(";"));
        return stringList.stream().map(String::trim).map(JsonAdaptedSubject::new).toList();
    }
}
