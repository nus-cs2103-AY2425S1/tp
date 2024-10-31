package seedu.address.testutil;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A utility class containing a list of {@code Path} objects to be used in tests.
 */
public class TypicalPaths {

    public static final Path PROJECT_ROOT_PATH = Paths.get(System.getProperty("user.dir"));
    public static final Path TEST_DATA_FOLDER = PROJECT_ROOT_PATH.resolve("src").resolve("test").resolve("data");

    // Paths for ImportCommandTest
    public static final Path DUPLICATE_IMPORT_FILE = TEST_DATA_FOLDER.resolve("ImportCommandTest")
            .resolve("dups.csv");
    public static final Path EMPTY_IMPORT_FILE = TEST_DATA_FOLDER.resolve("ImportCommandTest")
            .resolve("empty.csv");
    public static final Path INVALID_MISSING_NAME_FILE = TEST_DATA_FOLDER.resolve("ImportCommandTest")
            .resolve("invalid_missing_name.csv");
    public static final Path INVALID_IMPORT_FILE = TEST_DATA_FOLDER.resolve("ImportCommandTest")
            .resolve("invalid.csv");
    public static final Path VALID_NO_DUPS_IMPORT_FILE = TEST_DATA_FOLDER.resolve("ImportCommandTest")
            .resolve("valid_noDups_importFile.csv");
    public static final Path VALID_MISSING_CLASS_FILE = TEST_DATA_FOLDER.resolve("ImportCommandTest")
            .resolve("valid_missing_class.csv");
    public static final Path VALID_MISSING_DATA_IMPORT_FILE = TEST_DATA_FOLDER.resolve("ImportCommandTest")
            .resolve("valid_missing_data.csv");
    public static final Path VALID_MISSING_PHONE_FILE = TEST_DATA_FOLDER.resolve("ImportCommandTest")
            .resolve("valid_missing_phone.csv");
    public static final Path VALID_MISSING_TAGS_FILE = TEST_DATA_FOLDER.resolve("ImportCommandTest")
            .resolve("valid_missing_tags.csv");

    // Paths for ExportCommandTest
    public static final Path EXPORT_FILE_PATH = TEST_DATA_FOLDER.resolve("JsonExportTest")
            .resolve("exported_data.csv");
    public static final Path TYPICAL_PERSONS_ADDRESS_BOOK = TEST_DATA_FOLDER.resolve("JsonExportTest")
            .resolve("typicalPersonsAddressBook.json");

    private TypicalPaths() {} // prevents instantiation

    public static Path getTypicalPath() {
        return PROJECT_ROOT_PATH;
    }
}
