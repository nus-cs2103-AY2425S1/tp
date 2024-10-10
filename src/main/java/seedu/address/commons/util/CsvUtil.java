package seedu.address.commons.util;

import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Utility class for converting an ObservableList of Persons to a CSV string.
 */
public class CsvUtil {

    /**
     * Converts an ObservableList of Persons to a CSV string.
     * @param personList The ObservableList of Persons to be converted.
     * @return The CSV string.
     */
    public static String convertObservableListToCsv(ObservableList<Person> personList) {
        StringBuilder csvBuilder = new StringBuilder();

        csvBuilder.append("Name,Phone,Email,Address,Tags\n");

        for (Person person : personList) {
            String name = escapeSpecialCharacters(String.valueOf(person.getName()));
            String phone = escapeSpecialCharacters(String.valueOf(person.getPhone()));
            String email = escapeSpecialCharacters(String.valueOf(person.getEmail()));
            String address = escapeSpecialCharacters(String.valueOf(person.getAddress()));

            Set<Tag> tags = person.getTags();
            String tagsString = tags.stream()
                    .map(tag -> escapeSpecialCharacters(tag.getTagName()))
                    .collect(Collectors.joining(";"));

            csvBuilder.append(name).append(",");
            csvBuilder.append(phone).append(",");
            csvBuilder.append(email).append(",");
            csvBuilder.append(address).append(",");
            csvBuilder.append(tagsString).append("\n");
        }

        return csvBuilder.toString();
    }

    /**
     * Escapes special characters such as commas, newlines, and double quotes in CSV data.
     */
    private static String escapeSpecialCharacters(String data) {
        String escapedData = data;
        if (data.contains(",") || data.contains("\"") || data.contains("\n")) {
            escapedData = data.replace("\"", "\"\"");
            escapedData = "\"" + escapedData + "\"";
        }
        return escapedData;
    }
}
