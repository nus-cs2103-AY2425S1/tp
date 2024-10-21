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
    public static final int DISTANCE_TO_TAG = 6;

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Exports the address book in CSV format. "
            + "Example: " + COMMAND_WORD + " "
            + "format/csv";
    public static final String SUCCESS_MESSAGE = "The address book has been exported to "
            + "/data/addressbook.csv in the specified format.";
    private final String format;

    /**
     * Constructs a ExportCommand instance (TODO: supplement JavaDoc stub)
     * @param format the file format of the file to be exported (this should be "csv")
     */
    public ExportCommand(String format) {
        requireAllNonNull(format);
        this.format = format;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        String jsonFilePath = "data/addressbook.json";
        String csvFilePath = "data/addressbook.csv";

        try {
            List<Map<String, String>> jsonData = readAndParseJson(jsonFilePath);
            Set<String> headers = extractHeaders(jsonData);
            writeCsvFile(jsonData, headers, csvFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new CommandResult(SUCCESS_MESSAGE);
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
            tagString = tagString.substring(DISTANCE_TO_TAG, tagString.length() - 1);

            // Split by : and take the first part
            String[] parts = tagString.split(":");
            if (parts.length > 0) {
                // Trim and remove quotes from the tag name
                String trimmed = parts[0].trim()
                        .replaceAll("\"", "")
                        .replaceAll("\\\\", "");
                return trimmed;
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
                // Therefore, we can't process tags in the same way as we do other variables (e.g. name, phone, etc.)
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

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ExportCommand)) {
            return false;
        }

        ExportCommand e = (ExportCommand) other;
        return format.equals(e.format);
    }
}
