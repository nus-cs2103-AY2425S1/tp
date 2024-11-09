package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Vendor;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Event;
import seedu.address.model.task.Task;
import seedu.address.model.task.Todo;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingName;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final boolean isVendor;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final List<WeddingName> weddings = new ArrayList<>();
    private final List<JsonAdaptedTask> tasks = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("address") String address,
            @JsonProperty("tags") List<JsonAdaptedTag> tags,
            @JsonProperty("weddings") List<WeddingName> weddings,
            @JsonProperty("tasks") List<JsonAdaptedTask> tasks,
            @JsonProperty("isVendor") boolean isVendor) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        if (tasks != null) {
            this.tasks.addAll(tasks);
        }
        if (weddings != null) {
            this.weddings.addAll(weddings);
        }
        this.isVendor = isVendor;

    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        weddings.addAll(source.getWeddings().stream()
                .map(Wedding::getWeddingName)
                .collect(Collectors.toList()));
        tasks.addAll(source.getTasks().stream()
                .map(this::mapToJsonAdaptedTask)
                .collect(Collectors.toList()));
        isVendor = source.isVendor();
    }

    protected String getName() {
        return name;
    }

    protected String getPhone() {
        return phone;
    }

    protected String getEmail() {
        return email;
    }

    protected String getAddress() {
        return address;
    }

    protected List<JsonAdaptedTag> getTags() {
        return tags;
    }

    protected List<WeddingName> getWeddings() {
        return weddings;
    }

    protected List<JsonAdaptedTask> getTasks() {
        return tasks;
    }

    protected boolean isVendor() {
        return this.isVendor;
    }

    protected boolean hasTasks() {
        return !this.tasks.isEmpty();
    }

    /**
     * Helper function to map a Task to its corresponding JsonAdaptedTask subclass.
     */
    private JsonAdaptedTask mapToJsonAdaptedTask(Task task) {
        if (task instanceof Todo) {
            return new JsonAdaptedTodo((Todo) task);
        } else if (task instanceof Deadline) {
            return new JsonAdaptedDeadline((Deadline) task);
        } else if (task instanceof Event) {
            return new JsonAdaptedEvent((Event) task);
        } else {
            throw new IllegalArgumentException("Unknown task type");
        }
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     * Stores blank weddings with only the wedding name.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        final List<Wedding> personWeddings = new ArrayList<>();
        final List<Task> personTasks = new ArrayList<>();

        for (JsonAdaptedTag tag : tags) {
            Tag toAdd = tag.toModelType();
            personTags.add(toAdd);
        }

        for (JsonAdaptedTask task : tasks) {
            personTasks.add(task.toModelType());
        }

        for (WeddingName weddingName : weddings) {
            if (!Wedding.isValidWeddingName(weddingName.toString())) {
                throw new IllegalValueException(WeddingName.MESSAGE_CONSTRAINTS);
            }
            personWeddings.add(new Wedding(weddingName));
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
        final Address modelAddress = new Address(address);

        final Set<Tag> modelTags = new HashSet<>(personTags);
        final Set<Wedding> modelWeddings = new HashSet<>(personWeddings);
        final Set<Task> modelTasks = new HashSet<>(personTasks);

        if (isVendor) {
            return new Vendor(modelName, modelPhone, modelEmail, modelAddress, modelTags, modelWeddings, modelTasks);
        }
        return new Person(modelName, modelPhone, modelEmail, modelAddress, modelTags, modelWeddings, modelTasks);
    }

}
