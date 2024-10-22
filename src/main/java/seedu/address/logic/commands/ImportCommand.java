package seedu.address.logic.commands;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

/**
 * Imports person data from a CSV file and writes it to a JSON file.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports person data from a CSV file and writes to a JSON file.\n"
        + "Parameters: FILE_PATH\n"
        + "Example: " + COMMAND_WORD + " data/persons.csv";

    private final String csvFilePath;
    private final String jsonFilePath = "data/addressbook.json";

    public ImportCommand(String csvFilePath) {
        this.csvFilePath = csvFilePath;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            ObjectMapper objectMapper = new ObjectMapper();

            // Create the root object node that will contain the persons array
            ObjectNode rootNode = objectMapper.createObjectNode();
            ArrayNode personsNode = objectMapper.createArrayNode();

            // Skip header line
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length < 9) {
                    throw new CommandException("Invalid CSV format.");
                }

                // Create JSON node for each person
                ObjectNode personNode = objectMapper.createObjectNode();
                String name = fields[0].trim().replaceAll("\"", "");
                String phone = fields[1].trim().replaceAll("\"", "");
                String email = fields[2].trim().replaceAll("\"", "");
                String address = fields[3].trim().replaceAll("\"", "");
                String tele = fields[4].trim().replaceAll("\"", "");
                personNode.put("name", name);
                personNode.put("phone", phone);
                personNode.put("email", email);
                personNode.put("address", address);
                personNode.put("telegram", tele);

                Set<Tag> tags = new HashSet<>();

                if (!fields[5].trim().isEmpty() && !fields[5].equals("\"\"")) {
                    String[] tagArray = fields[5].trim().split(",");
                    for (String tag : tagArray) {
                        tag = tag.trim();  // Trim whitespace
                        if (!tag.isEmpty()) {  // Ensure we only process non-empty tags
                            tags.add(new Tag(tag));
                        }
                    }
                }

                personNode.set("tags", objectMapper.valueToTree(tags));

                personNode.put("github", fields[6].trim().replaceAll("\"", ""));

                String assignmentName = fields[7].trim().replaceAll("\"", "");
                if (assignmentName.equals("N/A") || assignmentName.isEmpty()) {
                    personNode.put("assignmentName", (String) null); // Set to null if "N/A" or empty
                } else {
                    personNode.put("assignmentName", assignmentName);
                }

                String assignmentScore = fields[8].trim().replaceAll("\"", "");  // Remove any double quotes
                if (assignmentScore.isEmpty()) {
                    personNode.put("assignmentScore", (Double) null); // Set to null if empty
                } else {
                    personNode.put("assignmentScore", Double.parseDouble(assignmentScore)); // Convert to Double
                }

                personsNode.add(personNode);
            }

            // Add the persons array to the root node
            rootNode.set("persons", personsNode);

            // Write to JSON file
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new FileWriter(jsonFilePath), rootNode);

            return new CommandResult("Imported data from CSV and saved to JSON.");
        } catch (IOException e) {
            throw new CommandException("Error reading from the CSV file or writing to JSON file: " + e.getMessage());
        }
    }
}



