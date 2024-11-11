package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;


/**
 * Exports the address book in a specified format.
 * Currently, BA€ can export the address book as a CSV file.
 * The process of converting the JSON file to a CSV file is as follows:
 * 1. Data is read from the JSON file and parsed into a List of Maps (readAndParseJSON).
 * 2. The headers of the JSON file (e.g. name, phone) are extracted (extractHeaders).
 * 3. The data and headers are then written to the CSV file (writeCsvFile).
 */
public class ExportCommand extends Command {
    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Exports the address book in CSV or TXT format.\n"
            + "Example: " + COMMAND_WORD + " format\\csv or " + COMMAND_WORD + " format\\txt";
    public static final String SUCCESS_MESSAGE = "The address book has been exported to "
            + "./data/bae_addressbook.%1$s in the specified format.";

    public static final String FAILURE_MESSAGE = "Error exporting address book to %1$s";
    public static final String BACKWARD_SLASH = "\\";
    public static final String COMMA = ",";
    public static final String QUOTES = "\"";
    public static final String EMPTY_STRING = "";
    public static final String COLON = ":";
    public static final String SPACE = " ";

    public static final String DEFAULT_FILEPATH = "data/";
    public static final String DEFAULT_FILENAME = "bae_addressbook";
    public static final String NULL_VALUE = "\\b : null\\b";
    public static final String LEFT_CURLY_BRACKET = "\\{";
    public static final String RIGHT_CURLY_BRACKET = "}";
    public static final String NEWLINE = "\\n";
    public static final String CARRIAGE_RETURN = "\\r";
    public static final String NEWLINE_OR_CARRIAGE_RETURN_REGEX = "\\\\r|\\\\n";
    public static final String CURLY_BRACE_AND_QUOTES_REGEX = "[{}\"]";

    /**
     * Class that handles Format enum type used in ExportCommand
     */
    public static enum Format {
        CSV("csv"),
        TXT("txt"),
        JSON("json"),
        UNSUPPORTED("unsupported");

        private final String format;

        Format(String format) {
            this.format = format;
        }

        public String getKeyword() {
            return this.format;
        }
    }
    private final Format format;

    /**
     * Class that handles ExportCommand
     */
    public ExportCommand(Format format) {
        requireAllNonNull(format);
        this.format = format;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        String jsonFilePath = buildJsonFilePath();
        try {
            List<Map<String, String>> jsonData = readAndParseJson(jsonFilePath);
            Set<String> headers = extractHeaders(jsonData);
            writeFile(jsonData, headers, getFilePath(), this.format);
        } catch (IOException e) {
            return new CommandResult(String.format(FAILURE_MESSAGE, this.format.getKeyword()));
        }
        return new CommandResult(String.format(SUCCESS_MESSAGE, this.format.getKeyword()));
    }

    private static String getFilePath() {
        return DEFAULT_FILEPATH + DEFAULT_FILENAME;
    }

    private static String buildJsonFilePath() {
        return getFilePath() + "." + Format.JSON;
    }

    /**
     * Parses a single tag from its JSON representation to its CSV representation.
     * e.g. {\n \"friends\" : null\n} -> friends
     * @param tagString
     * @return a single parsed tag
     */
    static String parseTags(String tagString) {
        // Remove leading and trailing whitespace
        tagString = tagString.replaceAll(NEWLINE_OR_CARRIAGE_RETURN_REGEX, EMPTY_STRING)
                .replaceAll(BACKWARD_SLASH + BACKWARD_SLASH + QUOTES, EMPTY_STRING)
                .replaceAll(NULL_VALUE, EMPTY_STRING)
                .replaceAll(QUOTES + LEFT_CURLY_BRACKET, EMPTY_STRING)
                .replaceAll(RIGHT_CURLY_BRACKET + QUOTES, EMPTY_STRING)
                .trim();
        // Split the tagString; the first part represents the tag's key.
        // If there's a second part, it represents the tag's value.
        String[] parts = tagString.split(COLON);
        boolean tagHasValue = parts.length == 2;
        String trimmedKey = parts[0].trim();
        if (tagHasValue) {
            String trimmedValue = parts[1].trim();
            return trimmedKey + SPACE + COLON + SPACE + trimmedValue;
        }
        // If the format doesn't match, return the original string
        return tagString;
    }

    static List<Map<String, String>> readAndParseJson(String filePath) throws IOException {
        JsonNode rootNode = readJsonFromFile(filePath);
        JsonNode persons = rootNode.get("persons");
        return addPersonsToJson(persons);
    }

    private static JsonNode readJsonFromFile(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(new File(filePath));
    }

    private static List<Map<String, String>> addPersonsToJson(JsonNode persons) {
        List<Map<String, String>> jsonData = new ArrayList<>();
        for (JsonNode person : persons) {
            Map<String, String> personInfo = new LinkedHashMap<>();
            person.fields().forEachRemaining(entry -> parsePerson(entry, personInfo));
            jsonData.add(personInfo);
        }
        return jsonData;
    }

    private static void parsePerson(Map.Entry<String, JsonNode> entry, Map<String, String> personInfo) {
        String header = entry.getKey(); // e.g. name, email, tags, etc.
        JsonNode value = entry.getValue(); // e.g. the value associated with the header

        // isTextual checks if a JsonNode represents a basic JSON String value.
        if (value.isTextual()) {
            personInfo.put(header, value.asText());
            // Note that the tags in bae_addressbook.json are stored in an array literal.
            // Therefore, we can't process tags in the same way as we do other variables
            // (e.g. name, phone, etc.)
        } else if (value.isArray() && header.equals("tags")) {
            List<String> tags = new ArrayList<>();
            for (JsonNode tag : value) {
                tags.add(parseTags(tag.toString()));
            }
            personInfo.put(header, String.join(COMMA + SPACE, tags));
        } else {
            personInfo.put(header, value.toString());
        }
    }

    static Set<String> extractHeaders(List<Map<String, String>> jsonData) {
        Set<String> headers = new LinkedHashSet<>();
        for (Map<String, String> row : jsonData) {
            headers.addAll(row.keySet());
        }
        return headers;
    }

    /**
     * Checks if user's command input contains a valid format.
     * @param string The format in the form of a String
     * @return matching Format object if valid, null otherwise.
     */
    public static Format matchFormat(String string) {
        Format formatType = Format.UNSUPPORTED;
        if (string == null) {
            return formatType;
        }
        for (Format format : Format.values()) {
            if (string.equals(format.getKeyword())) {
                formatType = format;
                break;
            }
        }
        return formatType;
    }

    /**
     * Writes the address book data to a file in the specified format (CSV or TXT).
     * (Credit: this JavaDoc comment was generated with the assistance of ChatGPT.)
     * @param jsonData        The data parsed from the JSON file, represented as a list of maps,
     *                        where each map corresponds to a person's details (key-value pairs).
     * @param headers         The set of headers extracted from the JSON data, which are used
     *                        as column headers in the CSV file or as keys in the TXT file.
     * @param filePathAndName The file path and file name (without its file format) where the file will be written.
     * @param format          The format to write the file in (CSV or TXT).
     * @throws IOException    If an I/O error occurs while writing to the file.
     */

    static void writeFile(List<Map<String, String>> jsonData, Set<String> headers,
                          String filePathAndName, Format format) throws IOException {
        String formattedFilePath = filePathAndName + "." + format.getKeyword();
        switch (format) {
        case CSV -> writeCsvFile(jsonData, headers, formattedFilePath);
        case TXT -> writeTxtFile(jsonData, headers, formattedFilePath);
        default -> throw new IllegalArgumentException("Unknown/Unsupported file format");
        }
    }

    static void writeCsvFile(List<Map<String, String>> jsonData, Set<String> headers, String csvFilePath)
            throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(csvFilePath)) {
            writer.println(String.join(COMMA, headers));
            for (Map<String, String> row : jsonData) {
                List<String> rowData = new ArrayList<>();
                for (String header : headers) {
                    String cellValue = row.getOrDefault(header, EMPTY_STRING)
                            .replace(QUOTES, QUOTES + QUOTES);
                    rowData.add(QUOTES + cellValue + QUOTES);
                }
                writer.println(String.join(COMMA, rowData));
            }
        }
    }

    static void writeTxtFile(List<Map<String, String>> jsonData, Set<String> headers, String txtFilePath)
            throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(txtFilePath)) {
            for (Map<String, String> row : jsonData) {
                writer.println("{");
                for (String header : headers) {
                    String cellValue = formatCellValue(row, header);
                    writer.printf("  %s | %s%n", header, cellValue);
                }
                writer.println("}");
                writer.println(); // Blank line between records for readability
            }
        }
    }

    private static String formatCellValue(Map<String, String> row, String header) {
        String cellValue = row.getOrDefault(header, EMPTY_STRING);
        // Check if the header is "tags" and format as a comma-separated list in square brackets
        if ("tags".equals(header) && !cellValue.isEmpty()) {
            cellValue = formatTags(cellValue);
        } else {
            // For other fields, clean up quotes, commas, newline characters, and backslashes
            cellValue = cleanCellValue(cellValue);
        }
        return cellValue;
    }

    private static String formatTags(String cellValue) {
        // Assuming the tags come in as a set represented by a JSON-like structure
        // Parse the cell value if it's formatted as JSON-like, i.e., "{key1 : value1, key2 : value2}"
        cellValue = cellValue.replaceAll(CURLY_BRACE_AND_QUOTES_REGEX, EMPTY_STRING); // Remove curly braces and quotes
        String[] tagsArray = cellValue.split(COMMA); // Split tags by comma if needed
        cellValue = String.join(COMMA, tagsArray); // Join with comma and wrap in brackets
        return cellValue;
    }

    private static String cleanCellValue(String cellValue) {
        cellValue = cellValue
                .replace(QUOTES, EMPTY_STRING)
                .replace(COMMA, EMPTY_STRING)
                .replace(NEWLINE, EMPTY_STRING)
                .replace(BACKWARD_SLASH, EMPTY_STRING)
                .replace(CARRIAGE_RETURN, EMPTY_STRING);
        return cellValue;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ExportCommand)) {
            return false;
        }
        ExportCommand otherCommand = (ExportCommand) other;
        return this.format.equals(otherCommand.format);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("format", this.format)
                .toString();
    }
}
