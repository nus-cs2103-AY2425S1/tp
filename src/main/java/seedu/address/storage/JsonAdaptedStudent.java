package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.LessonTime;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Student;
import seedu.address.model.tag.Education;
import seedu.address.model.tag.Grade;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Student}.
 */
public class JsonAdaptedStudent extends JsonAdaptedPerson {

    public static final String MISSING_PARENT_FIELD_MESSAGE_FORMAT = "Person's parent %s field is missing!";

    private final String lessonTime;
    private final String education;
    private final String grade;
    private final String parentName;

    /**
     * Constructs a {@code JsonAdaptedStudent} with the given details.
     */
    @JsonCreator
    public JsonAdaptedStudent(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("address") String address,
            @JsonProperty("lessonTime") String lessonTime, @JsonProperty("education") String education,
            @JsonProperty("grade") String grade, @JsonProperty("parentName") String parentName,
            @JsonProperty("tags") List<JsonAdaptedTag> tags, @JsonProperty("isPinned") boolean isPinned,
            @JsonProperty("isArchived") boolean isArchived) {
        super("Student", name, phone, email, address, tags, isPinned, isArchived);
        this.lessonTime = lessonTime;
        this.education = education;
        this.grade = grade;
        this.parentName = parentName;
    }

    /**
     * Constructs a {@code JsonAdaptedStudent} with the given {@code Student}.
     */
    public JsonAdaptedStudent(Student source) {
        super(source);
        lessonTime = source.getLessonTime() == null ? null : source.getLessonTime().value;
        education = source.getEducation() == null ? null : source.getEducation().educationLevel;
        grade = source.getGrade() == null ? null : source.getGrade().gradeIndex;
        parentName = source.getParentName() == null ? null : source.getParentName().fullName;
    }

    @Override
    public Student toModelType() throws IllegalValueException {

        String name = this.getName();
        String phone = this.getPhone();
        String email = this.getEmail();
        String address = this.getAddress();
        List<JsonAdaptedTag> tags = this.getTags();
        boolean isPinned = this.getPinned();
        boolean isArchived = this.isArchived();

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

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        if (lessonTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    LessonTime.class.getSimpleName()));
        }
        if (!LessonTime.isValidLessonTime(lessonTime)) {
            throw new IllegalValueException(LessonTime.MESSAGE_CONSTRAINTS);
        }
        final LessonTime modelLessonTime = new LessonTime(lessonTime);

        if (education == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Education.class.getSimpleName()));
        }
        if (!Education.isValidEducationLevel(education)) {
            throw new IllegalValueException(Education.MESSAGE_CONSTRAINTS);
        }
        final Education modelEducation = new Education(education);

        if (grade == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Grade.class.getSimpleName()));
        }
        if (!Grade.isValidGradeIndex(grade)) {
            throw new IllegalValueException(Grade.MESSAGE_CONSTRAINTS);
        }
        final Grade modelGrade = new Grade(grade);

        final Name modelParentName;
        if (parentName != null) {
            if (!Name.isValidName(parentName)) {
                throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
            }
            modelParentName = new Name(parentName);
        } else {
            modelParentName = null;
        }

        final Set<Tag> modelTags = new HashSet<>(personTags);
        return new Student(modelName, modelPhone, modelEmail, modelAddress, modelLessonTime, modelEducation, modelGrade,
                modelParentName, modelTags, isPinned, isArchived);
    }
}
