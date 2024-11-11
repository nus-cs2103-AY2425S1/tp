package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.ExportCommand.Format;
import static seedu.address.logic.commands.ExportCommand.parseTags;
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
    public void equals() {
        final ExportCommand standardCommand = new ExportCommand(Format.CSV);

        // same values -> returns true
        ExportCommand commandWithSameValues = new ExportCommand(Format.CSV);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
    }
    @Test
    public void toStringMethod() {
        ExportCommand testCommand = new ExportCommand(Format.CSV);
        assertEquals("seedu.address.logic.commands.ExportCommand{format=CSV}", testCommand.toString());
    }
    @Test
    public void parseValidTagMacOs() {
        String input = "\"{\\n  \\\"neighbours\\\" : null\\n}\"";
        String expectedOutput = "neighbours";
        String result = parseTags(input);
        assertEquals(expectedOutput, result);
    }

    @Test
    public void parseValidTagWindows() {
        String input = "\"{\\r\\n  \\\"neighbours\\\" : null\\r\\n}\"";
        String expectedOutput = "neighbours";
        String result = parseTags(input);
        assertEquals(expectedOutput, result);
    }

    @Test
    public void parseEmptyString() {
        String input = "";
        String expectedOutput = "";
        String result = parseTags(input);
        assertEquals(expectedOutput, result);
    }

    @Test
    public void parseMalformedTag() {
        String input = "{\"friends\":}";
        String expectedOutput = "{\"friends\" : }";
        String result = parseTags(input);
        assertEquals(expectedOutput, result);
    }

    @Test
    public void parseNoColonInTag() {
        String input = "{\"friends\"}";
        String expectedOutput = "{\"friends\"}";
        String result = parseTags(input);
        assertEquals(expectedOutput, result);
    }

    @Test
    public void readAndParseJson() throws IOException {
        String jsonContent = "{\n"
                + "  \"persons\" : [ {\n"
                + "    \"name\" : \"Alex Yeoh\",\n"
                + "    \"phone\" : \"87438807\",\n"
                + "    \"email\" : \"alexyeoh@example.com\",\n"
                + "    \"address\" : \"Blk 30 Geylang Street 29, #06-40\",\n"
                + "    \"tags\" : [ \"{\\n  \\\"friends\\\" : null\\n}\" ],\n"
                + "    \"financialInfo\" : \"Annual life insurance premium: $1,200\",\n"
                + "    \"socialMediaHandle\" : \"alex_yeoh\"\n"
                + "  }, {\n"
                + "    \"name\" : \"Bernice Yu\",\n"
                + "    \"phone\" : \"99272758\",\n"
                + "    \"email\" : \"berniceyu@example.com\",\n"
                + "    \"address\" : \"Blk 30 Lorong 3 Serangoon Gardens, #07-18\",\n"
                + "    \"tags\" : [ \"{\\n  \\\"colleagues\\\" : null\\n}\", \"{\\n  \\\"friends\\\" : null\\n}\" ],\n"
                + "    \"financialInfo\" : \"Income category: $60,000 - $80,000\",\n"
                + "    \"socialMediaHandle\" : \"bernice_yu\"\n"
                + "  }]\n"
                + "}";
        Path jsonPath = tempDir.resolve("test.json");
        Files.write(jsonPath, jsonContent.getBytes());
        List<Map<String, String>> result = ExportCommand.readAndParseJson(jsonPath.toString());

        assertEquals(2, result.size());
        assertEquals("Alex Yeoh", result.get(0).get("name"));
        assertEquals("87438807", result.get(0).get("phone"));
        assertEquals("alexyeoh@example.com", result.get(0).get("email"));
        assertEquals("Blk 30 Geylang Street 29, #06-40", result.get(0).get("address"));
        assertEquals("friends", result.get(0).get("tags"));
        assertEquals("Annual life insurance premium: $1,200", result.get(0).get("financialInfo"));
        assertEquals("alex_yeoh", result.get(0).get("socialMediaHandle"));

        assertEquals("Bernice Yu", result.get(1).get("name"));
        assertEquals("99272758", result.get(1).get("phone"));
        assertEquals("berniceyu@example.com", result.get(1).get("email"));
        assertEquals("Blk 30 Lorong 3 Serangoon Gardens, #07-18", result.get(1).get("address"));
        assertEquals("colleagues, friends", result.get(1).get("tags"));
        assertEquals("Income category: $60,000 - $80,000", result.get(1).get("financialInfo"));
        assertEquals("bernice_yu", result.get(1).get("socialMediaHandle"));
    }

    @Test
    public void extractHeaders() {
        List<Map<String, String>> jsonData = new ArrayList<>();
        jsonData.add(new LinkedHashMap<>(Map.of("name", "Siti", "phone", "65432109")));
        jsonData.add(new LinkedHashMap<>(Map.of("name", "Kumar",
                "email", "kumar@kgoomail.com",
                "tags", "[ \"{\\n  \\\"colleagues\\\" : null\\n}\", \"{\\n  \\\"friends\\\" : null\\n}\" ]")));
        Set<String> headers = ExportCommand.extractHeaders(jsonData);

        assertEquals(4, headers.size());
        assertTrue(headers.containsAll(Arrays.asList("name", "phone", "email", "tags")));
    }

    @Test
    public void matchFormat_success() {
        assertEquals(ExportCommand.matchFormat("csv"), Format.CSV);
        assertEquals(ExportCommand.matchFormat("txt"), Format.TXT);
    }

    @Test
    public void matchFormat_failure() {
        assertEquals(ExportCommand.matchFormat("invalidFormat"), Format.UNSUPPORTED);
        // Case sensitivity test
        assertEquals(ExportCommand.matchFormat("cSv"), Format.UNSUPPORTED);
        // Null input
        assertEquals(ExportCommand.matchFormat(null), Format.UNSUPPORTED);
    }

    @Test
    public void writeFileTest_csv() throws IOException {
        List<Map<String, String>> jsonData = new ArrayList<>();
        jsonData.add(new LinkedHashMap<>(Map.of("name", "Siti", "phone", "65432109")));
        jsonData.add(new LinkedHashMap<>(Map.of("name", "Kumar", "email", "kumar@kgoomail.com")));
        jsonData.add(new LinkedHashMap<>(
                Map.of("name", "Ahmad", "phone", "32109876", "email", "kumar@kgoomail.com")));
        Set<String> headers = new LinkedHashSet<>(Arrays.asList("name", "phone", "email"));

        String fileName = "test";
        Path csvPath = tempDir.resolve(fileName + ".csv");
        ExportCommand.writeFile(jsonData, headers, csvPath.toString().replace(".csv", ""), Format.CSV);

        List<String> lines = Files.readAllLines(csvPath);
        assertEquals(4, lines.size());
        assertEquals("name,phone,email", lines.get(0));
        assertEquals("\"Siti\",\"65432109\",\"\"", lines.get(1));
        assertEquals("\"Kumar\",\"\",\"kumar@kgoomail.com\"", lines.get(2));
        assertEquals("\"Ahmad\",\"32109876\",\"kumar@kgoomail.com\"", lines.get(3));
    }

    // Test case written by ChatGPT and adjusted to match output in txt file
    @Test
    public void writeFileTest_txtFormatted() throws IOException {
        List<Map<String, String>> jsonData = new ArrayList<>();
        jsonData.add(new LinkedHashMap<>(Map.of("name", "Johnny Appleseed",
                "phone", "93321121",
                "email", "johnnya@example.com",
                "address", "Malaysia",
                "tags", "\"LifeInsurance\" : \"Yes\"",
                "financialInfo", "noIncome",
                "socialMediaHandle", "@johnA")));
        jsonData.add(new LinkedHashMap<>(Map.of("name", "Kumar",
                "email", "kumar@kgoomail.com")));
        Set<String> headers = new LinkedHashSet<>(Arrays.asList("name", "phone", "email", "address",
                "tags", "financialInfo", "socialMediaHandle"));

        String fileName = "test";
        Path txtPath = tempDir.resolve(fileName + ".txt");
        ExportCommand.writeFile(jsonData, headers, txtPath.toString().replace(".txt", ""), Format.TXT);

        List<String> lines = Files.readAllLines(txtPath);

        // Check first entry
        assertEquals("{", lines.get(0));
        assertEquals("  name | Johnny Appleseed", lines.get(1));
        assertEquals("  phone | 93321121", lines.get(2));
        assertEquals("  email | johnnya@example.com", lines.get(3));
        assertEquals("  address | Malaysia", lines.get(4));
        assertEquals("  tags | LifeInsurance : Yes", lines.get(5));
        assertEquals("  financialInfo | noIncome", lines.get(6));
        assertEquals("  socialMediaHandle | @johnA", lines.get(7));
        assertEquals("}", lines.get(8));
        assertEquals("", lines.get(9)); // Blank line between records

        // Check second entry
        assertEquals("{", lines.get(10));
        assertEquals("  name | Kumar", lines.get(11));
        assertEquals("  phone | ", lines.get(12));
        assertEquals("  email | kumar@kgoomail.com", lines.get(13));
        assertEquals("  address | ", lines.get(14));
        assertEquals("  tags | ", lines.get(15));
        assertEquals("  financialInfo | ", lines.get(16));
        assertEquals("  socialMediaHandle | ", lines.get(17));
        assertEquals("}", lines.get(18));
    }

    // Test case written by ChatGPT and adjusted to check for unsupported format
    @Test
    public void writeFile_unsupportedFormat_throwsIllegalArgumentException() {
        List<Map<String, String>> jsonData = new ArrayList<>();
        jsonData.add(new LinkedHashMap<>(Map.of("name", "Siti", "phone", "65432109")));
        Set<String> headers = new LinkedHashSet<>(Arrays.asList("name", "phone"));

        String filePathAndName = "test";
        Format unsupportedFormat = ExportCommand.matchFormat("invalid");
        // Check format is unsupported
        assertEquals(unsupportedFormat, Format.UNSUPPORTED);
        // Check that IllegalArgumentException is thrown when calling writeFile with an unsupported format
        assertThrows(IllegalArgumentException.class, () -> {
            ExportCommand.writeFile(jsonData, headers, filePathAndName, unsupportedFormat);
        });
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
