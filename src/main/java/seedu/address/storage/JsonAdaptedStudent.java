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
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Student;
import seedu.address.model.tag.Grade;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Student}.
 */
public class JsonAdaptedStudent extends JsonAdaptedPerson {

    public static final String MISSING_PARENT_FIELD_MESSAGE_FORMAT = "Person's parent %s field is missing!";

    private final String parentName;
    private final String parentPhone;
    private final String parentEmail;
    private final String grade;

    /**
     * Constructs a {@code JsonAdaptedStudent} with the given details.
     */
    @JsonCreator
    public JsonAdaptedStudent(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("address") String address,
            @JsonProperty("parentName") String parentName, @JsonProperty("parentPhone") String parentPhone,
            @JsonProperty("parentEmail") String parentEmail, @JsonProperty("grade") String grade,
            @JsonProperty("tags") List<JsonAdaptedTag> tags, @JsonProperty("isPinned") boolean isPinned) {
        super(name, phone, email, address, tags, isPinned);
        this.parentName = parentName;
        this.parentEmail = parentEmail;
        this.parentPhone = parentPhone;
        this.grade = grade;
    }

    /**
     * Constructs a {@code JsonAdaptedStudent} with the given {@code Student}.
     */
    public JsonAdaptedStudent(Student source) {
        super(source);
        parentName = source.getParentName() == null ? null : source.getParentName().fullName;
        parentPhone = source.getParentPhone() == null ? null : source.getParentPhone().value;
        parentEmail = source.getParentEmail() == null ? null : source.getParentEmail().value;
        grade = source.getGrade() == null ? null : source.getGrade().gradeIndex;
    }

    @Override
    public Student toModelType() throws IllegalValueException {

        String name = this.getName();
        String phone = this.getPhone();
        String email = this.getEmail();
        String address = this.getAddress();
        List<JsonAdaptedTag> tags = this.getTags();
        boolean isPinned = this.getPinned();

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

        Name modelParentName = null;
        if (parentName != null) {
            if (!Name.isValidName(parentName)) {
                throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
            }
            modelParentName = new Name(parentName);
        }

        Phone modelParentPhone = null;
        if (parentPhone != null) {
            if (!Phone.isValidPhone(parentPhone)) {
                throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
            }
            modelParentPhone = new Phone(parentPhone);
        }

        Email modelParentEmail = null;
        if (parentEmail != null) {
            if (!Email.isValidEmail(parentEmail)) {
                throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
            }
            modelParentEmail = new Email(parentEmail);
        }

        Grade modelGrade = null;
        if (grade != null) {
            if (!Grade.isValidGradeIndex(grade)) {
                throw new IllegalValueException(Grade.MESSAGE_CONSTRAINTS);
            }
            modelGrade = new Grade(grade);
        }

        final Set<Tag> modelTags = new HashSet<>(personTags);
        return new Student(modelName, modelPhone, modelEmail, modelAddress, modelParentName, modelParentPhone,
                modelParentEmail, modelGrade, modelTags, isPinned);
    }
}
