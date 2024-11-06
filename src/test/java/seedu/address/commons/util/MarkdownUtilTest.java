// MarkdownUtilTest.java
package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MarkdownUtilTest {

    @Test
    public void generateMarkdownTable_validData_correctFormat() {
        ObservableList<String[]> data = FXCollections.observableArrayList(
                new String[]{"Action1", "Format1", "Example1"},
                new String[]{"Action2", "Format2", "Example2"}
        );

        String markdownTable = MarkdownUtil.generateMarkdownTable(data);
        String expectedTable = "### Command Summary\n"
                + "| Action | Format | Examples |\n"
                + "|--------|--------|----------|\n"
                + "| **Action1<br>** | `Format1`<br> | `Example1`<br> |\n"
                + "| **Action2<br>** | `Format2`<br> | `Example2`<br> |\n";


        assertEquals(markdownTable, expectedTable);
    }

    @Test
    public void main_validData_fileUpdated() throws IOException {
        // Create a temporary file
        Path tempFile = Files.createTempFile("variables", ".md");
        String initialContent = "<variable name=\"commandSummary\"></variable>";
        Files.write(tempFile, initialContent.getBytes());

        // Run the main method with the temporary file path
        MarkdownUtil.updateCommandSummary(tempFile.toString());

        // Check if the file contains the updated content
        String updatedContent = new String(Files.readAllBytes(tempFile));
        assertTrue(updatedContent.contains("### Command Summary"));

        // Delete the temporary file
        Files.delete(tempFile);
    }
}
