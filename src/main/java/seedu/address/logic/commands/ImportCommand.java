package seedu.address.logic.commands;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.util.Objects.requireNonNull;

public class ImportCommand extends Command {
    public static final String COMMAND_WORD = "import";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports a json file with patient data if"
            + "json file exists in the same directory as MediContact.jar and the data "
            + "is in the correct format (refer to user guide linked in help window for more information).\n"
            + "Parameters: FILE_NAME.json\n"
            + "Example: " + COMMAND_WORD + " addressbook.json ";
    public static final String MESSAGE_IMPORT_SUCCESS = "Data successfully imported into MediContact";
    public static final String MESSAGE_IMPORT_FAIL = "Error reading or writing '%1$s'";
    public static final String MESSAGE_FILE_NOT_FOUND = "'%1$s' not found in the same directory as the " +
            "application JAR file";
    public static final String MESSAGE_FILE_NOT_JSON = "'%1$s' is not a JSON file";
    public static final String MESSAGE_FILE_FORMAT_FAIL_NO_PERSONS = "Invalid file format: 'persons' array is missing";
    public static final String MESSAGE_FILE_FORMAT_FAIL_INVALID_JSON = "Invalid file format: invalid JSON format";
    public static final String MESSAGE_FILE_FORMAT_FAIL_INVALID_FORMAT = "Invalid file format: data does not follow "
            + "the correct format";
    public static final String MESSAGE_FILE_FORMAT_FAIL_INVALID_OPTIONAL = "Invalid file format: '%1$s' should be an "
            + "array or null";
    private static final String MESSAGE_FILE_FORMAT_FAIL_INVALID_NOTE = "Invalid file format: 'note' should be an "
            + "object or null";
    private static final String ADDRESSBOOK_FILE_PATH = "data/addressbook.json";
    private final String fileName;

    public ImportCommand(String fileName) {
        this.fileName = fileName;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Path filePath = Path.of(".", fileName);

        // Check that the file exists in the same directory
        if (!Files.exists(filePath)) {
            throw new CommandException(String.format(MESSAGE_FILE_NOT_FOUND, fileName));
        }

        // Check that the file is a JSON file
        if (!fileName.endsWith(".json")) {
            throw new CommandException(String.format(MESSAGE_FILE_NOT_JSON, fileName));
        }

        // Validate the JSON file format and overwrite addressbook.json if valid.
        try {
            String fileContent = Files.readString(filePath);
            validateDataFormat(fileContent);

            File file = new File(ADDRESSBOOK_FILE_PATH);
            File dir = file.getParentFile();

            // if directory does not exist, make it
            if (dir != null && !dir.exists()) {
                dir.mkdirs();
            }

            // if file doesn't exist, make it
            if (!file.exists()) {
                file.createNewFile();
            }

            Path path = Path.of(System.getProperty("user.dir"),"data","addressbook.json");
            System.out.println("Writing to file:" + path.toAbsolutePath());
            Files.writeString(path, fileContent);
            String verifyContent = Files.readString(path);
            System.out.println("File written successfully. Content: \n" + verifyContent);
            return new CommandResult(MESSAGE_IMPORT_SUCCESS);
        } catch (IOException e) {
            throw new CommandException(String.format(MESSAGE_IMPORT_FAIL, fileName), e);
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
                throw new CommandException(MESSAGE_FILE_FORMAT_FAIL_NO_PERSONS);
            }

            for (JsonNode person : personsNode) {
                validatePerson(person);
            }
        } catch (IOException e) {
            throw new CommandException(MESSAGE_FILE_FORMAT_FAIL_INVALID_JSON);
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
        if (!Name.isValidName(person.get("name").asText()) ||
            !Phone.isValidPhone(person.get("phone").asText()) ||
            !Email.isValidEmail(person.get("email").asText()) ||
            !Address.isValidAddress(person.get("address").asText()) ||
            !Age.isValidAge(person.get("age").asText()) ||
            !Sex.isValidSex(person.get("sex").asText()) ||
            !StarredStatus.isValidStarredStatus(person.get("starredStatus").asText())) {
            throw new CommandException(MESSAGE_FILE_FORMAT_FAIL_INVALID_FORMAT);
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
                throw new CommandException(String.format(MESSAGE_FILE_FORMAT_FAIL_INVALID_OPTIONAL, "appointments"));
            }

            for (JsonNode appointment : appointmentsNode) {
                if (!Appointment.isValidAppointment(appointment.asText())) {
                    throw new CommandException(MESSAGE_FILE_FORMAT_FAIL_INVALID_OPTIONAL);
                }
            }
        }

        JsonNode tagsNode = person.get("tags");
        if (tagsNode != null) {
            if (!tagsNode.isArray()) {
                throw new CommandException(String.format(MESSAGE_FILE_FORMAT_FAIL_INVALID_OPTIONAL, "tags"));
            }

            for (JsonNode tag : tagsNode) {
                if (!Tag.isValidTagName(tag.asText())) {
                    throw new CommandException(MESSAGE_FILE_FORMAT_FAIL_INVALID_OPTIONAL);
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
        if (!noteNode.isObject()) {
            throw new CommandException(MESSAGE_FILE_FORMAT_FAIL_INVALID_NOTE);
        }

        // Validates Note medication
        JsonNode medicationNode = noteNode.get("medication");
        if (medicationNode != null) {
            if (!medicationNode.isArray()) {
                throw new CommandException(String.format(MESSAGE_FILE_FORMAT_FAIL_INVALID_OPTIONAL, "medication"));
            }

            for (JsonNode medication : medicationNode) {
                if (!Note.isValidString(medication.asText())) {
                    throw new CommandException(MESSAGE_FILE_FORMAT_FAIL_INVALID_OPTIONAL);
                }
            }
        }

        // Validates Note appointments
        JsonNode appointmentsNode = noteNode.get("appointments");
        if (appointmentsNode != null) {
            if (!appointmentsNode.isArray()) {
                throw new CommandException(String.format(MESSAGE_FILE_FORMAT_FAIL_INVALID_OPTIONAL, "appointments"));
            }

            for (JsonNode appointment : appointmentsNode) {
                if (!Note.isValidAppointment(new Appointment(appointment.asText()))) {
                    throw new CommandException(MESSAGE_FILE_FORMAT_FAIL_INVALID_OPTIONAL);
                }
            }
        }

        // Validates Note remark
        JsonNode remarkNode = noteNode.get("remark");
        if (remarkNode != null) {
            if (!remarkNode.isArray()) {
                throw new CommandException(String.format(MESSAGE_FILE_FORMAT_FAIL_INVALID_OPTIONAL, "remark"));
            }

            for (JsonNode remark : remarkNode) {
                if (!Note.isValidString(remark.asText())) {
                    throw new CommandException(MESSAGE_FILE_FORMAT_FAIL_INVALID_OPTIONAL);
                }
            }
        }
    }
}
