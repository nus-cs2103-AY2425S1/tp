package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;


/**
 * Exports the address book in a specified format.
 * Currently, BA€ can export the address book as a CSV file.
 */
public class ExportCommand extends Command {
    public static final String MESSAGE_ARGUMENTS = "Export: %1$s";

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Exports the address book in CSV format. "
            + "Example: " + COMMAND_WORD + " "
            + "format/csv";
    public static final String SUCCESS_MESSAGE = "The address book has been exported in the specified format.";
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

    private static List<Map<String, String>> readAndParseJson(String filePath) throws IOException {
        List<Map<String, String>> jsonData = new ArrayList<>();
        String jsonString = new String(Files.readAllBytes(Paths.get(filePath)));

        Pattern personPattern = Pattern.compile("\\{([^{}]+)}");
        Matcher personMatcher = personPattern.matcher(jsonString);

        while (personMatcher.find()) {
            String personString = personMatcher.group(1);
            Map<String, String> personData = new LinkedHashMap<>();
            Pattern kvPattern = Pattern.compile("\"(\\w+)\"\\s*:\\s*\"([^\"]*)\"");
            Matcher kvMatcher = kvPattern.matcher(personString);
            while (kvMatcher.find()) {
                String key = kvMatcher.group(1);
                String value = kvMatcher.group(2);
                personData.put(key, value);
            }

            if (!personData.isEmpty()) {
                jsonData.add(personData);
            }
        }

        return jsonData;
    }

    private static Set<String> extractHeaders(List<Map<String, String>> jsonData) {
        Set<String> headers = new LinkedHashSet<>();
        for (Map<String, String> row : jsonData) {
            headers.addAll(row.keySet());
        }
        return headers;
    }

    private static void writeCsvFile(List<Map<String, String>> jsonData, Set<String> headers, String csvFilePath)
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
