package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Function;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.note.Note;
import seedu.address.model.person.Address;
import seedu.address.model.person.Age;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Sex;
import seedu.address.model.person.StarredStatus;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new ImportCommand object.
 */
public class ImportCommandParser implements Parser<ImportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ImportCommand
     * and returns an ImportCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public ImportCommand parse(String args) throws ParseException {
        String fileName = args.trim();
        Path filePath = Path.of(".", fileName);

        if (fileName.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
        } else {
            // Check that the file exists in the same directory
            if (!Files.exists(filePath)) {
                throw new ParseException(String.format(ImportCommand.MESSAGE_FILE_NOT_FOUND, fileName));
            }

            // Check that the file is a JSON file
            if (!fileName.endsWith(".json")) {
                throw new ParseException(String.format(ImportCommand.MESSAGE_FILE_NOT_JSON, fileName));
            }

            // Validate the JSON file format and overwrite addressbook.json if valid.
            try {
                String fileContent = Files.readString(filePath);
                validateDataFormat(fileContent);
                return new ImportCommand(fileContent);
            } catch (IOException ioe) {
                throw new ParseException(String.format(ImportCommand.MESSAGE_IMPORT_FAIL, fileName));
            } catch (CommandException e) {
                throw new ParseException(e.getMessage());
            }
        }
    }

    /**
     * Validates the data format of the given json file.
     *
     * @param fileContent The content of json file.
     * @throws CommandException If the format is not as expected.
     */
    private void validateDataFormat(String fileContent) throws CommandException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readTree(fileContent);
            JsonNode personsNode = rootNode.get("persons");

            if (personsNode == null || !personsNode.isArray()) {
                throw new CommandException(ImportCommand.MESSAGE_FILE_FORMAT_FAIL_NO_PERSONS);
            }

            for (JsonNode person : personsNode) {
                validatePerson(person);
            }
        } catch (IOException e) {
            throw new CommandException(ImportCommand.MESSAGE_FILE_FORMAT_FAIL_INVALID_JSON);
        }
    }

    /**
     * Validates each person Node in the Json file. Ensures that all fields follow
     * the constraints of {@code Person} object.
     *
     * @param person The current person.
     * @throws CommandException If {@code person} is not a valid {@code Person} object.
     */
    private void validatePerson(JsonNode person) throws CommandException {
        if (!Name.isValidName(person.get("name").asText())
                || !Phone.isValidPhone(person.get("phone").asText())
                || !Email.isValidEmail(person.get("email").asText())
                || !Address.isValidAddress(person.get("address").asText())
                || !Age.isValidAge(person.get("age").asText())
                || !Sex.isValidSex(person.get("sex").asText())
                || !StarredStatus.isValidStarredStatus(person.get("starredStatus").asText())) {
            throw new CommandException(ImportCommand.MESSAGE_FILE_FORMAT_FAIL_INVALID_FORMAT);
        }

        validateOptionalFields(person);
    }

    /**
     * Validates optional {@code Person} fields such as appointments and tags.
     *
     * @param person The current person.
     * @throws CommandException If the optional fields don't follow the expected format.
     */
    private void validateOptionalFields(JsonNode person) throws CommandException {
        JsonNode appointmentsNode = person.get("appointments");
        if (appointmentsNode != null) {
            if (!appointmentsNode.isArray()) {
                throw new CommandException(String.format(
                        ImportCommand.MESSAGE_FILE_FORMAT_FAIL_INVALID_OPTIONAL, "appointments"));
            }

            for (JsonNode appointment : appointmentsNode) {
                if (!Appointment.isValidAppointment(appointment.asText())) {
                    throw new CommandException(ImportCommand.MESSAGE_FILE_FORMAT_FAIL_INVALID_OPTIONAL);
                }
            }
        }

        JsonNode tagsNode = person.get("tags");
        if (tagsNode != null) {
            if (!tagsNode.isArray()) {
                throw new CommandException(String.format(
                        ImportCommand.MESSAGE_FILE_FORMAT_FAIL_INVALID_OPTIONAL, "tags"));
            }

            for (JsonNode tag : tagsNode) {
                if (!Tag.isValidTagName(tag.asText())) {
                    throw new CommandException(ImportCommand.MESSAGE_FILE_FORMAT_FAIL_INVALID_OPTIONAL);
                }
            }
        }

        JsonNode noteNode = person.get("note");
        if (noteNode != null) {
            validateNoteField(noteNode);
        }
    }

    /**
     * Validates Note fields medication, appointments, and remark.
     *
     * @param noteNode The Note attribute of {@code Person} object.
     * @throws CommandException If the Note fields do not follow the expected format.
     */
    private void validateNoteField(JsonNode noteNode) throws CommandException {
        // Validate each optional array in note: appointments, remarks, medication
        validateNoteArray(noteNode, "appointments", Note::isValidAppointment);
        validateNoteArray(noteNode, "remark", Note::isValidString);
        validateNoteArray(noteNode, "medication", Note::isValidString);
    }

    private void validateNoteArray(JsonNode noteNode, String fieldName, Function<String, Boolean> validationFunction)
            throws CommandException {
        JsonNode arrayNode = noteNode.get(fieldName);
        if (arrayNode != null) {
            if (!arrayNode.isArray()) {
                throw new CommandException(String.format(
                        ImportCommand.MESSAGE_FILE_FORMAT_FAIL_INVALID_OPTIONAL, fieldName));
            }
            for (JsonNode element : arrayNode) {
                if (!validationFunction.apply(element.asText())) {
                    throw new CommandException(ImportCommand.MESSAGE_FILE_FORMAT_FAIL_INVALID_FORMAT);
                }
            }
        }
    }
}
