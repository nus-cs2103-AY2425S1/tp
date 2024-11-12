package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.group.Group;
import seedu.address.model.list.GroupList;
import seedu.address.model.person.Comment;
import seedu.address.model.person.Email;
import seedu.address.model.person.Major;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;
import seedu.address.model.person.Year;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String studentId;
    private final String email;
    private final String major;
    private final String year;
    private final List<JsonAdaptedGroup> groups = new ArrayList<>();
    private final String comment;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("studentId") String studentId,
            @JsonProperty("email") String email, @JsonProperty("major") String major, @JsonProperty("year") String year,
            @JsonProperty("groups") List<JsonAdaptedGroup> groups, @JsonProperty("comment") String comment) {
        this.name = name;
        this.studentId = studentId;
        this.email = email;
        this.major = major;
        this.year = year;
        this.comment = comment;
        if (groups != null) {
            this.groups.addAll(groups);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        studentId = source.getStudentId().value;
        email = source.getEmail().value;
        major = source.getMajor().value;
        year = source.getYear().value;
        groups.addAll(source.getGroups().stream()
                .map(JsonAdaptedGroup::new)
                .collect(Collectors.toList()));
        comment = source.getComment().value;
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {

        final Name modelName = convertStringToName();
        final StudentId modelStudentId = convertStringToStudentId();
        final Email modelEmail = convertStringToEmail();
        final Major modelMajor = convertStringToMajor();
        final Year modelYear = convertStringToYear();
        final Comment modelComment = convertStringToComment();
        final GroupList modelGroups = convertStringToGroups();

        return new Person(modelName, modelStudentId, modelEmail, modelMajor, modelGroups, modelYear, modelComment);
    }

    private Name convertStringToName() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }

        return new Name(name);
    }

    private StudentId convertStringToStudentId() throws IllegalValueException {
        if (studentId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    StudentId.class.getSimpleName()));
        }
        if (!StudentId.isValidStudentId(studentId)) {
            throw new IllegalValueException(StudentId.MESSAGE_CONSTRAINTS);
        }
        return new StudentId(studentId);
    }

    private Email convertStringToEmail() throws IllegalValueException {
        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }

        if (!Email.isValidEmail(email) && !email.isEmpty()) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        return Email.makeEmail(email);
    }

    private Major convertStringToMajor() throws IllegalValueException {
        if (major == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Major.class.getSimpleName()));
        }

        if (!Major.isValidMajor(major) && !major.isEmpty()) {
            throw new IllegalValueException(Major.MESSAGE_CONSTRAINTS);
        }
        return Major.makeMajor(major);
    }

    private Year convertStringToYear() throws IllegalValueException {
        if (year == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Year.class.getSimpleName()));
        }

        if (!Year.isValidYear(year) && !year.isEmpty()) {
            throw new IllegalValueException(Year.MESSAGE_CONSTRAINTS);
        }
        return Year.makeYear(year);
    }

    private Comment convertStringToComment() throws IllegalValueException {
        if (comment == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Comment.class.getSimpleName()));
        }
        return new Comment(comment);
    }

    private GroupList convertStringToGroups() throws IllegalValueException {
        GroupList modelGroups = new GroupList();

        for (JsonAdaptedGroup group : groups) {
            modelGroups.addGroup(group.toModelType());
        }

        List<Group> invalidGroups = modelGroups.getUnmodifiableGroups().stream()
                .filter(groupName -> !Group.isValidGroupName(groupName.getGroupName()))
                .collect(Collectors.toList());

        if (!invalidGroups.isEmpty()) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Group.class.getSimpleName()));
        }

        return modelGroups;
    }

}
