package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;
import seedu.address.model.wedding.Wedding;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {
    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final Set<Task> tasks = new HashSet<>();
    private final Set<Wedding> weddings = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address,
                  Set<Tag> tags, Set<Wedding> weddings, Set<Task> tasks) {
        requireAllNonNull(name, phone, email, address, tags, weddings, tasks);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.weddings.addAll(weddings);
        this.tasks.addAll(tasks);
    }

    /**
     * Create new person with the same details as an existing person
     */
    public Person(Person person) {
        this.name = person.getName();
        this.phone = person.getPhone();
        this.email = person.getEmail();
        this.address = person.getAddress();
        this.tags.addAll(person.getTags());
        this.weddings.addAll(person.getWeddings());
        this.tasks.addAll(person.getTasks());
    }

    /**
     * Creates a Person with only a name and all other fields blank
     */
    public static Person makePersonWithName(Name name) {
        return new Person(name, new Phone(""), new Email(""), new Address(""),
                new HashSet<>(), new HashSet<>(), new HashSet<>());
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns a task if it exists in this person's task list.
     */
    public Task getTask(Task task) throws NoSuchElementException {
        for (Task eachTask : tasks) {
            if (eachTask.isSameTask(task)) {
                return eachTask;
            }
        }
        throw new NoSuchElementException("Task not found in this person's assigned tasks.");
    }

    /**
     * Returns an immutable task set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Task> getTasks() {
        return Collections.unmodifiableSet(tasks);
    }

    /**
     * Returns false, as a Person object should not have tasks assigned
     */
    public boolean hasTasks() {
        return false;
    }

    /**
     * Removes all tasks from the Person's task list
     */
    public void clearTasks() {
        this.tasks.clear();
    }

    /**
     * Checks if this person has the specified task assigned.
     *
     * @param task The task to check.
     * @return true if the specified task is assigned to this person, false otherwise.
     */
    public boolean hasTask(Task task) {
        for (Task eachTask : tasks) {
            if (eachTask.isSameTask(task)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes the specified task from this person's assigned tasks.
     *
     * @param task The task to remove.
     */
    public void removeTask(Task task) {
        tasks.remove(task);
    }

    /**
     * Returns an immutable wedding set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Wedding> getWeddings() {
        return Collections.unmodifiableSet(weddings);
    }

    /**
     * Replaces the original set of weddings with a new specified one.
     */
    public void setWeddings(Set<Wedding> newWedding) {
        weddings.clear();
        weddings.addAll(newWedding);
    }

    /**
     * Replaces the old wedding with a specified new wedding. As weddings are internally stored in a set,
     * this can only be done by removing the old wedding and adding a new one.
     */
    public void setWedding(Wedding oldWedding, Wedding newWedding) {
        assert weddings.contains(oldWedding);
        weddings.remove(oldWedding);
        weddings.add(newWedding);
    }

    /**
     * Adds a wedding to a person's wedding list
     */
    public void addWedding(Wedding wedding) {
        requireNonNull(wedding);
        weddings.add(wedding);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    public boolean isVendor() {
        return false;
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags)
                && weddings.equals(otherPerson.weddings)
                && tasks.equals((otherPerson.tasks));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, weddings, tasks);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .add("weddings", weddings)
                .add("tasks", tasks)
                .toString();
    }

}
