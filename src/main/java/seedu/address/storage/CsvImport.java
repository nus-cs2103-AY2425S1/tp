package seedu.address.storage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.bean.CsvToBeanBuilder;

import seedu.address.model.Model;

/**
 * Handles the import of CSV data into the application.
 * This class reads a specified CSV file and converts its contents into
 * PersonBean objects, which can then be added to a Model.
 */
public class CsvImport {
    private final String importFilePath;
    private final ArrayList<Integer> failed;

    /**
     * Constructs a CsvImport instance with the specified file path.
     *
     * @param importFilePath The path of the CSV file to be imported.
     */
    public CsvImport(String importFilePath) {
        this.importFilePath = importFilePath;
        this.failed = new ArrayList<>();
    }

    /**
     * Reads the CSV file and imports the data into the provided model.
     *
     * @param model The model to which the imported PersonBean objects will be added.
     * @return The number of successful imports.
     */
    public int readCsv(Model model) {
        FileReader reader = null;
        try {
            reader = new FileReader(importFilePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        assert reader != null;
        List<PersonBean> personList = new CsvToBeanBuilder<PersonBean>(reader)
                .withType(PersonBean.class).build().parse();

        int success = 0;
        for (PersonBean p : personList) {
            if (model.hasPerson(p.toPerson())) {
                failed.add(personList.indexOf(p));
            } else {
                model.addPerson(p.toPerson());
                success++;
            }
        }
        return success;
    }

    public ArrayList<Integer> getFailed() {
        return failed;
    }

    public boolean hasFailures() {
        return !failed.isEmpty();
    }
}
