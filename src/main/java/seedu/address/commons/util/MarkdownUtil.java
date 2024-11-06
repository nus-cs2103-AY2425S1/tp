package seedu.address.commons.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.ObservableList;

/**
 * Utility class for handling Markdown operations.
 */
public class MarkdownUtil {

    private static final String VARIABLES_FILE_PATH = "docs/_markbind/variables.md";
    private static final String TABLE_TITLE = "### Command Summary\n";
    private static final String TABLE_HEADER =
            "| Action | Format | Examples |\n"
                    + "|--------|--------|----------|\n";

    /**
     * Main method to update the command summary in the Markdown file.
     *
     * @param args Command line arguments.
     * @throws IOException If an I/O error occurs.
     */
    public static void main(String[] args) throws IOException {
        updateCommandSummary(VARIABLES_FILE_PATH);
    }

    /**
     * Updates the command summary in the specified Markdown file.
     *
     * @param path The path to the Markdown file.
     * @throws IOException If an I/O error occurs.
     */
    public static void updateCommandSummary(String path) throws IOException {
        final ObservableList<String[]> data = CommandSummaryUtil.getCommandSummaryData();
        String markdownTable = generateMarkdownTable(data);

        String variablesContent = new String(Files.readAllBytes(Paths.get(path)));
        Pattern pattern = Pattern.compile("(<variable name=\"commandSummary\">)(.*?)(</variable>)", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(variablesContent);

        if (matcher.find()) {
            String newVariablesContent = matcher.replaceFirst("$1\n\n" + markdownTable + "\n\n$3");
            Files.write(Paths.get(path), newVariablesContent.getBytes());
        } else {
            // Variable not found, append to the end of the file
            String newVariablesContent = variablesContent
                    + "\n\n<variable name=\"commandSummary\">\n\n"
                    + markdownTable
                    + "\n\n</variable>\n";
            Files.write(Paths.get(path), newVariablesContent.getBytes());
        }
    }

    /**
     * Generates a Markdown table from the given command summary data.
     *
     * @param data The command summary data.
     * @return The generated Markdown table as a string.
     */
    public static String generateMarkdownTable(ObservableList<String[]> data) {
        StringBuilder markdownTable = new StringBuilder();
        markdownTable.append(TABLE_TITLE);
        markdownTable.append(TABLE_HEADER);

        for (String[] command : data) {
            String[] action = command[0].split("\n");
            StringBuilder actionString = Arrays.stream(action).collect(StringBuilder::new, (sb, s) ->
                    sb.append(s).append("<br>"), StringBuilder::append);

            String[] format = command[1].split("\n");
            StringBuilder formatString = Arrays.stream(format).collect(StringBuilder::new, (sb, s) ->
                    sb.append("`").append(s).append("`<br>"), StringBuilder::append);

            String[] examples = command[2].split("\n");
            StringBuilder examplesString = Arrays.stream(examples).collect(StringBuilder::new, (sb, s) ->
                    sb.append("`").append(s).append("`<br>"), StringBuilder::append);

            markdownTable.append(String.format("| **%s** | %s | %s |\n",
                    actionString, formatString, examplesString));
        }

        return markdownTable.toString();
    }
}
