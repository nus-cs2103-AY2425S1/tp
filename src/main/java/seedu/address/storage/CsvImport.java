package seedu.address.storage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.exceptions.CsvChainedException;
import com.opencsv.exceptions.CsvFieldAssignmentException;
import com.opencsv.exceptions.CsvValidationException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.exceptions.ImproperFormatException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Handles the import of CSV data into the application.
 * This class reads a specified CSV file and converts its contents into
 * JsonAdaptedPerson objects, which can then be added to a Model.
 */
public class CsvImport {
    public static final String INCORRECT_HEADERS = "Your file is missing or has extra headers. "
            + "Please use the following headers: name,phone,email,address,hours,role,subjects ";
    public static final String INCORRECT_ROWS = "Some rows have an incorrect number of entries "
            + "or have an incorrect formatting. "
            + "The expected number of entries is %d. The rows that failed are: %s";
    public static final int HEADER_COUNT = 7;
    private final String importFilePath;
    private final ArrayList<Integer> duplicates;
    private final Map<Integer, String> failedRows;

    /**
     * Constructs a CsvImport instance with the specified file path.
     *
     * @param importFilePath The path of the CSV file to be imported.
     */
    public CsvImport(String importFilePath) {
        this.importFilePath = importFilePath;
        this.duplicates = new ArrayList<>();
        this.failedRows = new HashMap<>();
    }

    /**
     * Reads the CSV file and imports the data into the provided model.
     *
     * @param model The model to which the imported JsonAdaptedPerson objects will be added.
     * @return The number of successful imports.
     */
    public int readCsv(Model model) throws ImproperFormatException, FileNotFoundException {
        FileReader reader = null;
        FileReader headerReader = null;
        FileReader rowReader = null;
        try {
            reader = new FileReader(importFilePath);
            headerReader = new FileReader(importFilePath);
            rowReader = new FileReader(importFilePath);
        } catch (FileNotFoundException e) {
            throw e;
        }

        assert reader != null;
        if (!validateHeaders(headerReader)) {
            throw new ImproperFormatException(INCORRECT_HEADERS);
        } else if (!validateCsv(rowReader)) {
            throw new ImproperFormatException(String.format(INCORRECT_ROWS, HEADER_COUNT, duplicates));
        }

        MappingStrategy<JsonAdaptedPerson> map = createMappingStrategy();
        map.setType(JsonAdaptedPerson.class);
        List<JsonAdaptedPerson> personList = new CsvToBeanBuilder<JsonAdaptedPerson>(reader)
                .withMappingStrategy(map)
                .build()
                .parse();

        int success = 0;
        for (JsonAdaptedPerson p : personList) {
            try {
                if (model.hasPerson(p.toModelType())) {
                    duplicates.add(personList.indexOf(p) + 1);
                } else {
                    p.setId(Person.getNextIndex());
                    model.addPerson(p.toModelType());
                    success++;
                }
            } catch (IllegalValueException e) {
                failedRows.put(personList.indexOf(p) + 1, e.getMessage());
            }
        }
        return success;
    }

    public ArrayList<Integer> getDuplicates() {
        return duplicates;
    }

    public Map<Integer, String> getFailed() {
        return failedRows;
    }

    public boolean hasDuplicates() {
        return !duplicates.isEmpty();
    }

    public boolean hasFailed() {
        return !failedRows.isEmpty();
    }

    private boolean validateHeaders(FileReader reader) {
        CSVReader csvReader = new CSVReader(reader);

        ArrayList<String> expectedHeaders = new ArrayList<>();
        expectedHeaders.add("name");
        expectedHeaders.add("phone");
        expectedHeaders.add("email");
        expectedHeaders.add("address");
        expectedHeaders.add("hours");
        expectedHeaders.add("role");
        expectedHeaders.add("subjects");

        try {
            ArrayList<String> actualHeaders = new ArrayList<>(List.of(csvReader.peek()));
            if (actualHeaders.isEmpty()) {
                return false;
            }

            actualHeaders.replaceAll(String::trim);
            // Check for missing headers
            for (String expectedHeader : expectedHeaders) {
                if (!actualHeaders.contains(expectedHeader)) {
                    return false;
                }
            }

            // Check for extra headers
            for (String actualHeader : actualHeaders) {
                if (!expectedHeaders.remove(actualHeader)) {
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
        return true;
    }

    private boolean validateCsv(FileReader reader) {
        CSVReader csvReader = new CSVReader(reader);
        try {
            csvReader.skip(1);
            int lineCount = 1;
            String[] row = csvReader.readNext();
            while (row != null) {
                if (row.length != HEADER_COUNT) {
                    duplicates.add(lineCount);
                }
                lineCount++;
                row = csvReader.readNext();
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return duplicates.isEmpty();
    }

    private MappingStrategy<JsonAdaptedPerson> createMappingStrategy() {
        return new HeaderColumnNameMappingStrategy<>() {
            @Override
            public JsonAdaptedPerson populateNewBean(String[] line) throws CsvFieldAssignmentException,
                    CsvChainedException {
                Arrays.setAll(line, index -> line[index].trim());
                return super.populateNewBean(line);
            }
        };
    }
}
