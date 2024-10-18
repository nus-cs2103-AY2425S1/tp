package seedu.address.storage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.opencsv.bean.AbstractBeanField;

public class JsonAdaptedSubjectConverter extends AbstractBeanField<List<JsonAdaptedSubject>, String> {
    @Override
    protected List<JsonAdaptedSubject> convert(String value) {
        if (value == null || value.trim().isEmpty()) {
            return new ArrayList<>();
        }
        List<String> stringList = Arrays.asList(value.split(";"));
        return stringList.stream().map(JsonAdaptedSubject::new).toList();
    }
}
