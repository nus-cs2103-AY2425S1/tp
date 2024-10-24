package seedu.address.model.util;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.student.Email;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentNumber;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Status;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskName;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    private static final Logger logger = LogsCenter.getLogger(SampleDataUtil.class);

    public static Student[] getSamplePersons() {
        return new Student[]{
            new Student(new Name("Alex Yeoh"), new Email("alexyeoh@u.nus.edu"),
                getTagSet("TD7"), new StudentNumber("A0737935G"),
                Optional.of(new GroupName("CS2103-F12-1"))),
            new Student(new Name("Bernice Yu"), new Email("berniceyu@u.nus.edu"),
                getTagSet("TD7", "friends"), new StudentNumber("A0597991H"),
                Optional.of(new GroupName("CS2103-F12-1"))),
            new Student(new Name("Charlotte Oliveiro"), new Email("charlotte@u.nus.edu"),
                getTagSet("TD7"), new StudentNumber("A0632228J"),
                Optional.of(new GroupName("CS2103-F12-1"))),
            new Student(new Name("David Li"), new Email("lidavid@u.nus.edu"),
                getTagSet("TD8"), new StudentNumber("A0965321G"),
                Optional.of(new GroupName("CS2103-F11-1"))),
            new Student(new Name("Irfan Ibrahim"), new Email("irfan@u.nus.edu"),
                getTagSet("TD8"), new StudentNumber("A0467953H"),
                Optional.of(new GroupName("CS2103-F11-1"))),
            new Student(new Name("Roy Balakrishnan"), new Email("royb@u.nus.edu"),
                getTagSet("TD8"), new StudentNumber("A0122764H"),
                Optional.of(new GroupName("CS2103-F11-1")))
        };
    }

    public static Task[] getSampleTasks() {
        return new Task[]{
            new Task(new TaskName("Add postmortem to team docs"),
                new Deadline(LocalDateTime.of(2024, 10, 24, 23, 59)), Status.PENDING,
                1),
            new Task(new TaskName("Consultation slot"),
                new Deadline(LocalDateTime.of(2024, 10, 25, 12, 0)), Status.PENDING,
                0)
        };
    }

    public static Group[] getSampleGroups() {
        return new Group[]{
            new Group(new GroupName("CS2103-F12-1"),
                Set.<Student>of(
                    new Student(new Name("Alex Yeoh"), new Email(
                        "alexyeoh@u.nus.edu"),
                        getTagSet("TD7"), new StudentNumber("A0737935G"),
                        Optional.of(new GroupName("CS2103-F12-1"))),
                    new Student(new Name("Bernice Yu"), new Email("berniceyu@u.nus.edu"),
                        getTagSet("TD7", "friends"), new StudentNumber("A0597991H"),
                        Optional.of(new GroupName("CS2103-F12-1"))),
                    new Student(new Name("Charlotte Oliveiro"), new Email("charlotte@u.nus.edu"),
                        getTagSet("TD7"), new StudentNumber("A0632228J"),
                        Optional.of(new GroupName("CS2103-F12-1")))),
                Set.<Task>of(
                    new Task(new TaskName("Add postmortem to team docs"),
                        new Deadline(LocalDateTime.of(2024, 10, 24, 23, 59)),
                        Status.PENDING, 1))),
            new Group(new GroupName("CS2103-F11-1"),
                Set.<Student>of(
                    new Student(new Name("David Li"), new Email(
                        "lidavid@u.nus.edu"),
                        getTagSet("TD8"), new StudentNumber("A0965321G"),
                        Optional.of(new GroupName("CS2103-F11-1"))),
                    new Student(new Name("Irfan Ibrahim"), new Email("irfan@u.nus.edu"),
                        getTagSet("TD8"), new StudentNumber("A0467953H"),
                        Optional.of(new GroupName("CS2103-F11-1"))),
                    new Student(new Name("Roy Balakrishnan"), new Email("royb@u.nus.edu"),
                        getTagSet("TD8"), new StudentNumber("A0122764H"),
                        Optional.of(new GroupName("CS2103-F11-1")))),
                Set.<Task>of(
                    new Task(new TaskName("Add postmortem to team docs"),
                        new Deadline(LocalDateTime.of(2024, 10, 24, 23, 59)),
                        Status.PENDING, 1),
                    new Task(new TaskName("Consultation slot"),
                        new Deadline(LocalDateTime.of(2024, 10, 25, 12, 0)),
                        Status.PENDING, 0)))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Student sampleStudent : getSamplePersons()) {
            sampleAb.addStudent(sampleStudent);
        }
        for (Group sampleGroup : getSampleGroups()) {
            sampleAb.addGroup(sampleGroup);
        }
        for (Task sampleTask : getSampleTasks()) {
            sampleAb.addTask(sampleTask);
        }
        logger.info("Sample data loaded.");
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
            .map(Tag::new)
            .collect(Collectors.toSet());
    }

}
