package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ExportCommand.SUCCESS_MESSAGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ExportCommandTest {
    @TempDir
    // We need to save any temporary files created during tests to a temporary directory.
    // This directory will be deleted after the tests have finished running.
    Path tempDir;
    private Model expectedModel;
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_export_success() {
        CommandResult expectedCommandResult = new CommandResult(SUCCESS_MESSAGE);
        assertCommandSuccess(new ExportCommand("csv"), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void equals() {
        final ExportCommand standardCommand = new ExportCommand("csv");
        final String differentFormat = "pdf";

        // same values -> returns true
        ExportCommand commandWithSameValues = new ExportCommand("csv");
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different export format -> returns false
        assertFalse(standardCommand.equals(new ExportCommand(differentFormat)));
    }

    @Test
    public void readAndParseJson() throws IOException {
        String jsonContent = "{\"persons\":"
                + "[{\"name\":\"Tan Ah Kow\",\"phone\":\"98765432\"},"
                + "{\"name\":\"Chua Ah Lian\","
                + "\"address\":\"Blk 30 Lorong 3 Serangoon Gardens, #07-18\","
                + "\"tags\":[{\"colleagues\" : null},{\"friends\" : null}]}]}";
        Path jsonPath = tempDir.resolve("test.json");
        Files.write(jsonPath, jsonContent.getBytes());
        List<Map<String, String>> result = ExportCommand.readAndParseJson(jsonPath.toString());

        assertEquals(2, result.size());
        assertEquals("Tan Ah Kow", result.get(0).get("name"));
        assertEquals("98765432", result.get(0).get("phone"));
        assertEquals("Chua Ah Lian", result.get(1).get("name"));
        assertEquals("Blk 30 Lorong 3 Serangoon Gardens, #07-18", result.get(1).get("address"));
        assertEquals(
                "{\"colleagues\":null}, {\"friends\":null}",
                    result.get(1).get("tags"));
    }

    @Test
    public void extractHeaders() {
        List<Map<String, String>> jsonData = new ArrayList<>();
        jsonData.add(new LinkedHashMap<>(Map.of("name", "Siti", "phone", "65432109")));
        jsonData.add(new LinkedHashMap<>(Map.of("name", "Kumar",
                "email", "kumar@kgoomail.com",
                "tags", "[{\"colleagues\" : null},{\"friends\" : null}]")));
        Set<String> headers = ExportCommand.extractHeaders(jsonData);

        assertEquals(4, headers.size());
        assertTrue(headers.containsAll(Arrays.asList("name", "phone", "email", "tags")));
    }

    @Test
    public void writeCsvFile() throws IOException {
        List<Map<String, String>> jsonData = new ArrayList<>();
        jsonData.add(new LinkedHashMap<>(Map.of("name", "Siti", "phone", "65432109")));
        jsonData.add(new LinkedHashMap<>(Map.of("name", "Kumar", "email", "kumar@kgoomail.com")));
        jsonData.add(new LinkedHashMap<>(
                Map.of("name", "Ahmad", "phone", "32109876", "email", "kumar@kgoomail.com")));
        Set<String> headers = new LinkedHashSet<>(Arrays.asList("name", "phone", "email"));

        Path csvPath = tempDir.resolve("test.csv");
        ExportCommand.writeCsvFile(jsonData, headers, csvPath.toString());

        List<String> lines = Files.readAllLines(csvPath);
        assertEquals(4, lines.size());
        assertEquals("name,phone,email", lines.get(0));
        assertEquals("\"Siti\",\"65432109\",\"\"", lines.get(1));
        assertEquals("\"Kumar\",\"\",\"kumar@kgoomail.com\"", lines.get(2));
        assertEquals("\"Ahmad\",\"32109876\",\"kumar@kgoomail.com\"", lines.get(3));
    }
}
