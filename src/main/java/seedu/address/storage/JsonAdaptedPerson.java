package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Age;
import seedu.address.model.person.Detail;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.tag.StudyGroupTag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String email;
    private final String gender;
    private final String age;
    private final List<JsonAdaptedStudyGroupTag> studyGroupTags = new ArrayList<>();
    private final String detail;

    /**
     * Constructs a {@code JsonAdaptedStudyGroupTag} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("email") String email,
                             @JsonProperty("gender") String gender, @JsonProperty("age") String age,
                             @JsonProperty("detail") String detail,
                             @JsonProperty("study groups") List<JsonAdaptedStudyGroupTag> studyGroupTags) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.age = age;
        if (studyGroupTags != null) {
            this.studyGroupTags.addAll(studyGroupTags);
        }
        this.detail = detail;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        email = source.getEmail().value;
        gender = source.getGender().value;
        age = source.getAge().value;
        studyGroupTags.addAll(source.getStudyGroupTags().stream()
                .map(JsonAdaptedStudyGroupTag::new)
                .collect(Collectors.toList()));
        detail = source.getDetail().value;
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's
     * {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in
     *                               the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<StudyGroupTag> personStudyGroupTags = new ArrayList<>();
        for (JsonAdaptedStudyGroupTag tag : studyGroupTags) {
            personStudyGroupTags.add(tag.toModelType());
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

        if (gender == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Gender.class.getSimpleName()));
        }
        if (!Gender.isValidGender(gender)) {
            throw new IllegalValueException(Gender.MESSAGE_CONSTRAINTS);
        }
        final Gender modelGender = new Gender(gender);

        if (age == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Age.class.getSimpleName()));
        }
        if (!Age.isValidAge(age)) {
            throw new IllegalValueException(Age.MESSAGE_CONSTRAINTS);
        }
        final Age modelAge = new Age(age);

        final Detail modelDetail = new Detail(detail);

        final Set<StudyGroupTag> modelStudyGroupTags = new HashSet<>(personStudyGroupTags);

        return new Person(modelName, modelEmail, modelGender, modelAge, modelDetail, modelStudyGroupTags);
    }

}
