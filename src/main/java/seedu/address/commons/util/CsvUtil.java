package seedu.address.commons.util;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvException;


import seedu.address.storage.CsvAdaptedPerson;

import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CsvUtil {

    public static void exportCsvFile (List<CsvAdaptedPerson> personsList, Path filePath) {
        try (Writer writer = Files.newBufferedWriter(filePath)) {

            StatefulBeanToCsv<CsvAdaptedPerson> beanToCsv = new StatefulBeanToCsvBuilder<CsvAdaptedPerson>(writer)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER)
                    .build();

            beanToCsv.write(personsList);
        } catch (CsvException e) {
            System.err.println("Error mapping Bean to CSV");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
