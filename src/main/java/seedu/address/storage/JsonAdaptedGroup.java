package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.student.Student;
import seedu.address.model.task.Task;

/**
 * Jackson-friendly version of {@link Group}.
 */
class JsonAdaptedGroup {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Group's %s field is missing!";

    private final String groupName;
    private final List<JsonAdaptedPerson> students = new ArrayList<>();
    private final List<JsonAdaptedTask> tasks = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedGroup} with the given group details.
     */
    @JsonCreator
    public JsonAdaptedGroup(@JsonProperty("groupname") String groupName,
                             @JsonProperty("students") List<JsonAdaptedPerson> students,
                            @JsonProperty("tasks") List<JsonAdaptedTask> tasks) {
        this.groupName = groupName;
        if (students != null) {
            this.students.addAll(students);
        }
        if (tasks != null) {
            this.tasks.addAll(tasks);
        }
    }

    /**
     * Converts a given {@code Group} into this class for Jackson use.
     */
    public JsonAdaptedGroup(Group source) {
        groupName = source.getGroupName().fullName;
        students.addAll(source.getStudents().stream()
                .map(JsonAdaptedPerson::new)
                .collect(Collectors.toList()));
        tasks.addAll(source.getTasks().stream()
                .map(JsonAdaptedTask::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted student object into the model's {@code Group} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted student.
     */
    public Group toModelType() throws IllegalValueException {
        final List<Student> allStudents = new ArrayList<>();
        for (JsonAdaptedPerson student : this.students) {
            allStudents.add(student.toModelType());
        }

        final List<Task> allTasks = new ArrayList<>();
        for (JsonAdaptedTask task : this.tasks) {
            allTasks.add(task.toModelType());
        }
        if (groupName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    GroupName.class.getSimpleName()));
        }
        if (!GroupName.isValidName(groupName)) {
            throw new IllegalValueException(GroupName.MESSAGE_CONSTRAINTS);
        }
        final GroupName modelGroupName = new GroupName(groupName);

        final Set<Student> modelStudents = new HashSet<>(allStudents);

        final Set<Task> modelTasks = new HashSet<>(allTasks);
        return new Group(modelGroupName, modelStudents, modelTasks);
    }

}
