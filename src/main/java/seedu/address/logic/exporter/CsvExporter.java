package seedu.address.logic.exporter;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;

/**
 * Represents a class that can export to CSV
 */
public class CsvExporter implements Exporter {
    private static final String CSV_HEADERS = "Name,Phone No,Email,Address,Tags,Notes";

    private final Path exportSubPath = Paths.get("bizbook.csv");
    private final ReadOnlyUserPrefs userPrefs;

    /**
     * Constructs a {@code VcfExporter} class configured with {@code UserPrefs}
     */
    public CsvExporter(ReadOnlyUserPrefs userPrefs) {
        this.userPrefs = userPrefs;
    }

    @Override
    public void exportAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        Path exportPath = getExportPath();
        FileUtil.createIfMissing(exportPath);

        StringJoiner sj = new StringJoiner("\n");
        sj.add(CSV_HEADERS);

        addressBook.getPersonList()
                .forEach(person -> sj.add(convertToCsv(person)));

        FileUtil.writeToFile(exportPath, sj.toString());
    }

    /**
     * Converts a {@code Person} object into csv format.
     *
     * @param person to be encoded into a csv format.
     * @return A csv representation of the {@Code Person} object.
     */
    private String convertToCsv(Person person) {
        StringJoiner sj = new StringJoiner(",");

        sj.add(person.getName().fullName);
        sj.add(person.getPhone().value);
        sj.add(person.getEmail().value);

        // Prevent excel from separating entries due to commas
        String address = "\"" + person.getAddress().value + "\"";
        sj.add(address);

        String tags = person.getTags().stream()
                .map(tag -> tag.tagName).collect(Collectors.joining(","));

        String notes = person.getNotes().stream()
                .map(Note::getNote).collect(Collectors.joining(","));

        sj.add("\"" + tags + "\"");
        sj.add("\"" + notes + "\"");

        return sj.toString();
    }

    @Override
    public Path getExportPath() {
        return userPrefs.getExportDirectoryPath().resolve(exportSubPath);
    }
}
