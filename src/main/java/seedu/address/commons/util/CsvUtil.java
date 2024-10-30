package seedu.address.commons.util;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvException;

import javafx.collections.ObservableList;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;

import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CsvUtil {

    public static void exportCsvFile (ReadOnlyAddressBook addressBook, Path filePath) {
        ObservableList<Person> personList = addressBook.getPersonList();

        try (Writer writer = Files.newBufferedWriter(filePath)) {

            // Create StatefulBeanToCsv object
            StatefulBeanToCsv<Person> beanToCsv = new StatefulBeanToCsvBuilder<Person>(writer)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER)
                    .build();

            // Write the list to CSV
            beanToCsv.write(personList);

            System.out.println("Data written to CSV file successfully.");

        } catch (CsvException e) {
            System.err.println("Error mapping Bean to CSV");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
