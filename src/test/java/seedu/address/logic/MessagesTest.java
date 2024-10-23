package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_DUPLICATE_FIELDS;

import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagName;
import seedu.address.model.task.Task;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalTasks;

public class MessagesTest {

    @Test
    public void getErrorMessageForDuplicatePrefixes_singlePrefix_success() {
        Prefix prefix = new Prefix("t/");
        String expectedMessage = MESSAGE_DUPLICATE_FIELDS + "t/";

        String actualMessage = Messages.getErrorMessageForDuplicatePrefixes(prefix);
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void getErrorMessageForDuplicatePrefixes_multiplePrefixes_success() {
        Prefix prefix1 = new Prefix("t/");
        Prefix prefix2 = new Prefix("p/");
        String expectedMessage = MESSAGE_DUPLICATE_FIELDS + "t/ p/";

        String actualMessage = Messages.getErrorMessageForDuplicatePrefixes(prefix1, prefix2);
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void format_person_success() {
        Person person = new PersonBuilder()
                .withName("John Doe")
                .withPhone("12345678")
                .withEmail("john@example.com")
                .withAddress("123 Main St")
                .withTags("friend", "colleague")
                .build();

        String expectedOutput = "John Doe; Phone: 12345678; Email: john@example.com; Address: 123 Main St; Tags: "
                + person.getTags().stream()
                .map(Tag::toString)
                .collect(Collectors.joining())
                + "; Weddings: ";

        String actualOutput = Messages.format(person);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void format_tag_success() {
        Tag tag = new Tag(new TagName("friend"));
        String expectedOutput = "friend";

        String actualOutput = Messages.format(tag);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void format_task_success() {
        Task task = TypicalTasks.TODO_TASK;
        String expectedOutput = task.getDescription();

        String actualOutput = Messages.format(task);
        assertEquals(expectedOutput, actualOutput);
    }
}
