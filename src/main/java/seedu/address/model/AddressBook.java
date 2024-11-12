package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.Vendor;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.wedding.UniqueWeddingList;
import seedu.address.model.wedding.Wedding;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueTagList tags;
    private final UniqueTaskList tasks;
    private final UniqueWeddingList weddings;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     * among constructors.
     */
    {
        persons = new UniquePersonList();
        tags = new UniqueTagList();
        tasks = new UniqueTaskList();
        weddings = new UniqueWeddingList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setTags(newData.getTagList());
        setWeddings(newData.getWeddingList());
        setTasks(newData.getTaskList());
    }

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Replaces the contents of the wedding list with {@code weddings}.
     * {@code weddings} must not contain duplicate tags.
     */
    public void setWeddings(List<Wedding> weddings) {
        this.weddings.setWeddings(weddings);
    }

    /**
     * Replaces the contents of the task list with {@code tasks}.
     * {@code tasks} must not contain duplicate tasks.
     */
    public void setTasks(List<Task> tasks) {
        this.tasks.setTasks(tasks);
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);
        persons.setPerson(target, editedPerson);
    }

    /**
     * Returns a person in the model that has the same name
     */
    public Person getPerson(Person person) {
        requireNonNull(person);
        return persons.asUnmodifiableObservableList().stream().filter(person::isSamePerson).findFirst().orElse(null);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    //// vendor-level operations
    /**
     * Returns true if a vendor with the same identity as {@code person} exists in the address book.
     */
    public boolean hasVendor(Person person) {
        requireNonNull(person);
        return persons.containsVendor(person);
    }

    /**
     * Adds a person as a vendor to the vendor list.
     * The person must not already exist in the vendor list.
     */
    public void addVendor(Person p) {
        setPerson(p, new Vendor(p));
    }

    /**
     * Removes {@code key} from this {@code vendors}.
     * {@code key} must exist in the address book.
     */
    public void removeVendor(Person key) {
        setPerson(key, new Person(key));
    }

    //// tag-level operations

    /**
     * Adds a tag to the Wedlinker.
     * The tag must not already exist in the Wedlinker.
     * @param tag A {@code Tag} object to be added.
     */
    public void addTag(Tag tag) {
        tags.add(tag);
    }

    /**
     * Returns true if a tag with the same name as {@code tag} exists in the Wedlinker.
     */
    public boolean hasTag(Tag tag) {
        requireNonNull(tag);
        return tags.contains(tag);
    }


    /**
     * Adds a task to the Wedlinker.
     * The task must not already exist in the Wedlinker.
     * @param task A {@code Task} object to be added.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Updates the completion status of a task in the Wedlinker.
     * The task must already exist in the Wedlinker.
     *
     * @param task A {@code Task} object to update.
     * @param markAsCompleted True to mark the task as completed, false to unmark it as completed.
     */
    private void updateTaskCompletionStatus(Task task, boolean markAsCompleted) {
        requireNonNull(task);
        if (!tasks.contains(task)) {
            throw new NoSuchElementException(Messages.MESSAGE_TASK_NOT_FOUND_IN_AB);
        }
        Task taskToUpdate = tasks.getTask(task);
        if (markAsCompleted) {
            taskToUpdate.markAsDone();
        } else {
            taskToUpdate.markAsUndone();
        }
        tasks.setTask(task, taskToUpdate);
    }
    /**
     * Marks a task in the Wedlinker.
     * The task must already exist in the Wedlinker.
     * @param task A {@code Task} object to be marked.
     */
    public void markTask(Task task) {
        updateTaskCompletionStatus(task, true);
    }

    /**
     * Unmarks a task in the Wedlinker.
     * The task must already exist in the Wedlinker.
     * @param task A {@code Task} object to be Unmarked.
     */
    public void unmarkTask(Task task) {
        updateTaskCompletionStatus(task, false);
    }

    /**
     * Returns true if a task with the same description as {@code task} exists in the Wedlinker.
     */
    public boolean hasTask(Task task) {
        requireNonNull(task);
        return tasks.contains(task);
    }

    /**
     * Replaces the given task {@code target} in the list with {@code editedTask}.
     * {@code target} must exist in the address book.
     * The task identity of {@code editedTask} must not be the same as another existing task in the address book.
     */
    public void setTask(Task target, Task editedTask) {
        requireNonNull(editedTask);
        tasks.setTask(target, editedTask);
    }


    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeTask(Task key) {
        tasks.remove(key);
    }

    /**
     * Adds a wedding to the Wedlinker
     * The wedding must not already exist in the Wedlinker
     * @param wedding A {@code Wedding} object to be added.
     */
    public void addWedding(Wedding wedding) {
        weddings.add(wedding);
    }


    /**
     * Replaces the given wedding {@code target} in the list with {@code editedWedding}.
     * {@code target} must exist in the address book.
     * The wedding identity of {@code editedWedding} must not be the same as another existing wedding in the Wedlinker.
     */
    public void setWedding(Wedding target, Wedding editedWedding) {
        requireNonNull(editedWedding);

        weddings.setWedding(target, editedWedding);
    }

    /**
     * Returns true if a wedding with the same name as the {@code wedding} exists in the Wedlinker.
     */
    public boolean hasWedding(Wedding wedding) {
        requireNonNull(wedding);
        return weddings.contains(wedding);
    }

    /**
     * Returns wedding object with the same name
     */
    public Wedding getWedding(Wedding wedding) {
        requireNonNull(wedding);
        return weddings.getWedding(wedding);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeWedding(Wedding key) {
        weddings.remove(key);
    }

    /**
     * Replaces the given tag {@code target} in the list with {@code editedTag}.
     * {@code target} must exist in the address book.
     * The tag identity of {@code editedTag} must not be the same as another existing tag in the address book.
     */
    public void setTag(Tag target, Tag editedTag) {
        requireNonNull(editedTag);

        tags.setTag(target, editedTag);
    }

    /**
     * Replaces the contents of the tag list with {@code tags}.
     * {@code tags} must not contain duplicate tags.
     */
    public void setTags(List<Tag> tags) {
        this.tags.setTags(tags);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeTag(Tag key) {
        tags.remove(key);
    }

    /**
     * Returns {@code Tag} object with the same {@code TagName}.
     */
    public Tag getTag(Tag target) {
        requireNonNull(target);
        return tags.getTag(target);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .add("tags", tags)
                .add("tasks", tasks)
                .add("weddings", weddings)
                .toString();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Task> getTaskList() {
        return tasks.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Wedding> getWeddingList() {
        return weddings.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return persons.equals(otherAddressBook.persons)
                && tags.equals(otherAddressBook.tags)
                && tasks.equals(otherAddressBook.tasks)
                && weddings.equals(otherAddressBook.weddings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(persons, tags, tasks, weddings);
    }
}
