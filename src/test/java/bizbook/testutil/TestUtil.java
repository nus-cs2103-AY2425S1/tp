package bizbook.testutil;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import bizbook.commons.core.index.Index;
import bizbook.commons.util.FileUtil;
import bizbook.model.Model;
import bizbook.model.person.Person;


/**
 * A utility class for test cases.
 */
public class TestUtil {

    /**
     * Folder used for temp files created during testing. Ignored by Git.
     */
    private static final Path SANDBOX_FOLDER = Paths.get("src", "test", "data", "sandbox");

    /**
     * Appends {@code fileName} to the sandbox folder path and returns the resulting path.
     * Creates the sandbox folder if it doesn't exist.
     */
    public static Path getFilePathInSandboxFolder(String fileName) {
        try {
            Files.createDirectories(SANDBOX_FOLDER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return SANDBOX_FOLDER.resolve(fileName);
    }

    /**
     * Returns the middle index of the person in the {@code model}'s person list.
     */
    public static Index getMidIndex(Model model) {
        return Index.fromOneBased(model.getFilteredPersonList().size() / 2);
    }

    /**
     * Returns the last index of the person in the {@code model}'s person list.
     */
    public static Index getLastIndex(Model model) {
        return Index.fromOneBased(model.getFilteredPersonList().size());
    }

    /**
     * Returns the person in the {@code model}'s person list at {@code index}.
     */
    public static Person getPerson(Model model, Index index) {
        return model.getFilteredPersonList().get(index.getZeroBased());
    }

    /**
     * Returns the {@link Path} to the resource in the class' package.
     * For example, for a class {@code X} in package {@code a.b.c},
     * {@code getResourceFilePath(X.class, "subDir/myFile.txt");} will return the
     * path to the file {@code resources/a/b/c/subDir/myFile.txt}
     *
     * @param klass the relative class to retrieve the resource from
     * @param filePath the path to the file to retrieve
     * @return path to the file
     */
    public static Path getResourceFilePath(Class<?> klass, String filePath) {
        try {
            URL url = klass.getResource(filePath);
            assertNotNull(url, filePath + " does not exist.");
            return Path.of(url.toURI());
        } catch (URISyntaxException e) {
            fail("Failed to get URI for resource: " + filePath);
            throw new AssertionError("Failed to get URI for resource");
        }
    }

    /**
     * Returns the contents of the resource file in the class' package.
     * For example, for a class {@code X} in package {@code a.b.c},
     * {@code readResourceFile(X.class, "subDir/myFile.txt");} will return the
     * contents in the file {@code resources/a/b/c/subDir/myFile.txt}
     *
     * @param klass the relative class to retrieve the resource from
     * @param filePath the path to the file to retrieve
     * @return contents of the resource file
     * @throws IOException when reading the file fails
     */
    public static String readResourceFile(Class<?> klass, String filePath) throws IOException {
        Path path = getResourceFilePath(klass, filePath);
        return FileUtil.readFromFile(path);
    }
}
