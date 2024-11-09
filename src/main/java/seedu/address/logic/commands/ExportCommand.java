package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

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
    public static final int DISTANCE_TO_TAG_FRONT = 6;
    public static final int DISTANCE_TO_TAG_BACK = 3;

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Exports the address book in CSV format.\n"
            + "Example: " + COMMAND_WORD + " "
            + "format\\csv";
    public static final String SUCCESS_MESSAGE = "The address book has been exported to "
            + "./data/addressbook.%1$s in the specified format.";

    public static final String FAILURE_MESSAGE = "Error exporting address book to %1$s";
    public static final String BACKWARD_SLASH_REGEX = "\\\\";
    public static final String INVERTED_COMMA_REGEX = "\"";
    public static final String EMPTY_STRING = "";

    public static final String DEFAULT_FILEPATH = "data/";
    public static final String DEFAULT_FILENAME = "addressbook";

    /**
     * Class that handles Format enum type used in ExportCommand
     */
    public static enum Format {
        CSV("csv"),
        TXT("txt"),
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
        String jsonFilePath = DEFAULT_FILEPATH + DEFAULT_FILENAME + ".json";
        try {
            List<Map<String, String>> jsonData = readAndParseJson(jsonFilePath);
            Set<String> headers = extractHeaders(jsonData);
            writeFile(jsonData,
                    headers,
                    DEFAULT_FILEPATH + DEFAULT_FILENAME,
                    this.format);
        } catch (IOException e) {
            return new CommandResult(String.format(FAILURE_MESSAGE, this.format.getKeyword()));
        }
        return new CommandResult(String.format(SUCCESS_MESSAGE, this.format.getKeyword()));
    }

    /**
     * Parses a single tag from its JSON representation to its CSV representation.
     * e.g. {\n \"friends\" : null\n} -> friends
     * @param tagString
     * @return a single parsed tag
     */
    static String parseTags(String tagString) {
        // Remove leading and trailing whitespace
        tagString = tagString.trim();

        // Check if the string starts with { and ends with }
        if (tagString.startsWith("\"{") && tagString.endsWith("}\"")) {
            // Remove the outer braces
            tagString = tagString.substring(DISTANCE_TO_TAG_FRONT, tagString.length() - DISTANCE_TO_TAG_BACK);

            // Split by : and take the first part
            String[] parts = tagString.split(":");
            if (parts.length > 0) {
                // Trim and remove quotes from the tag name
                String trimmedKey = parts[0].trim()
                        .replaceAll(INVERTED_COMMA_REGEX, EMPTY_STRING)
                        .replaceAll(BACKWARD_SLASH_REGEX, EMPTY_STRING);
                String trimmedValue = parts[1].trim()
                        .replaceAll(INVERTED_COMMA_REGEX, EMPTY_STRING)
                        .replaceAll(BACKWARD_SLASH_REGEX, EMPTY_STRING);
                return trimmedKey + " : " + trimmedValue;
            }
        }
        // If the format doesn't match, return the original string
        return tagString;
    }

    static List<Map<String, String>> readAndParseJson(String filePath) throws IOException {
        List<Map<String, String>> jsonData = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode rootNode = objectMapper.readTree(new File(filePath));
        JsonNode persons = rootNode.get("persons");

        for (JsonNode person : persons) {
            Map<String, String> personInfo = new LinkedHashMap<>();

            person.fields().forEachRemaining(entry -> {
                String header = entry.getKey(); // e.g. name, email, tags, etc.
                JsonNode value = entry.getValue(); // e.g. the value associated with the header

                // isTextual checks if a JsonNode represents a basic JSON String value.
                if (value.isTextual()) {
                    personInfo.put(header, value.asText());
                    // Note that the tags in AddressBook.json are stored in an array literal.
                    // Therefore, we can't process tags in the same way as we do other variables
                    // (e.g. name, phone, etc.)
                } else if (value.isArray() && header.equals("tags")) {
                    List<String> tags = new ArrayList<>();
                    for (JsonNode tag : value) {
                        tags.add(parseTags(tag.toString()));
                    }
                    personInfo.put(header, String.join(", ", tags));
                } else {
                    personInfo.put(header, value.toString());
                }
            });

            jsonData.add(personInfo);
        }

        return jsonData;
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
     * Calls methods that write to csv or txt format depending on the format specified
     * @param jsonData
     * @param headers
     * @throws IOException
     */
    static void writeFile(List<Map<String, String>> jsonData, Set<String> headers,
                          String filePathAndName, Format format) throws IOException {
        String formattedFilePath = filePathAndName + "." + format.getKeyword();
        switch (format) {
        case CSV -> {
            writeCsvFile(jsonData, headers, formattedFilePath);
            break;
        }
        case TXT -> {
            writeTxtFile(jsonData, headers, formattedFilePath);
            break;
        }
        default -> throw new IllegalArgumentException("Unknown/Unsupported file format");
        };
    }

    static void writeCsvFile(List<Map<String, String>> jsonData, Set<String> headers, String csvFilePath)
            throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(csvFilePath)) {
            writer.println(String.join(",", headers));
            for (Map<String, String> row : jsonData) {
                List<String> rowData = new ArrayList<>();
                for (String header : headers) {
                    String cellValue = row.getOrDefault(header, "").replace("\"", "\"\"");
                    rowData.add("\"" + cellValue + "\"");
                }
                writer.println(String.join(",", rowData));
            }
        }
    }

    static void writeTxtFile(List<Map<String, String>> jsonData, Set<String> headers, String txtFilePath)
            throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(txtFilePath)) {
            for (Map<String, String> row : jsonData) {
                writer.println("{");
                for (String header : headers) {
                    String cellValue = row.getOrDefault(header, "");

                    // Check if the header is "tags" and format as a comma-separated list in square brackets
                    if ("tags".equals(header) && !cellValue.isEmpty()) {
                        // Assuming the tags come in as a set represented by a JSON-like structure
                        // Parse the cell value if it's formatted as JSON-like, i.e., "{key1 : value1, key2 : value2}"
                        cellValue = cellValue.replaceAll("[{}\"]", ""); // Remove curly braces and quotes
                        String[] tagsArray = cellValue.split(","); // Split tags by comma if needed
                        cellValue = "[ " + String.join(", ", tagsArray) + " ]"; // Join with comma and wrap in brackets
                    } else {
                        // For other fields, clean up quotes, commas, newline characters, and backslashes
                        cellValue = cellValue
                                .replace("\"", "")
                                .replace(",", "")
                                .replace("\\n", "")
                                .replace("\\", "");
                    }

                    writer.printf("  %s | %s%n", header, cellValue);
                }
                writer.println("}");
                writer.println(); // Blank line between records for readability
            }
        }
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
