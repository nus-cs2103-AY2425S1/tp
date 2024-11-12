package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.person.Email;
import seedu.address.model.person.Github;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Telegram;
import seedu.address.model.tag.Tag;


/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String email;
    private final String telegram;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final String github;
    private final Map<String, Assignment> assignments = new HashMap<>();
    private final List<Integer> attendance = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdapte dPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name,
                             @JsonProperty("email") String email,
                             @JsonProperty("telegram") String telegram, @JsonProperty("tags") List<JsonAdaptedTag> tags,
                             @JsonProperty("github") String github,
                             @JsonProperty("assignment") Map<String, Assignment> assignments,
                             @JsonProperty("attendance") List<Integer> attendance) {
        this.name = name;
        this.email = email;
        this.telegram = telegram;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        if (assignments != null) {
            this.assignments.putAll(assignments);
        }
        this.github = github;
        if (attendance != null) {
            this.attendance.addAll(attendance); // Add attendance data
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        email = source.getEmail().value;
        telegram = source.getTelegram().value;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        github = source.getGithub().username;
        assignments.putAll(source.getAssignment());
        attendance.addAll(source.getWeeksPresent());

    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (telegram == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Telegram.class.getSimpleName()));
        }
        if (!Telegram.isValidTelegram(telegram)) {
            throw new IllegalValueException(Telegram.MESSAGE_CONSTRAINTS);
        }
        final Telegram modelTelegram = new Telegram(telegram);

        final Set<Tag> modelTags = new HashSet<>(personTags);

        if (github == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Github.class.getSimpleName()));
        }
        if (!Github.isValidGithubUsername(github)) {
            throw new IllegalValueException(Github.MESSAGE_CONSTRAINTS);
        }
        final Github modelGithub = new Github(github);

        Map<String, Assignment> modelAssignment = new HashMap<>(assignments);
        Set<Integer> modelAttendance = new HashSet<>(attendance); // Convert List to Set for the model

        return new Person(
                modelName, modelEmail,
                modelTelegram, modelGithub, modelAssignment, modelAttendance, modelTags);
    }

}
