package seedu.address.storage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Person;
import seedu.address.model.person.Subject;
import seedu.address.model.person.Tutee;
import seedu.address.model.person.Tutor;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
@JsonInclude(JsonInclude.Include.NON_NULL)
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    public static final String MESSAGE_DUPLICATE_LESSON = "Lessons list contains duplicate lesson(s).";

    public static final String MESSAGE_PERSON_LESSON_MISMATCH = "Lessons list contains some lessons with "
            + "invalid subjects.";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    private final List<JsonAdaptedLesson> lessons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons,
                                       @JsonProperty("lessons") List<JsonAdaptedLesson> lessons) {
        this.persons.addAll(persons);
        if (lessons != null) {
            this.lessons.addAll(lessons);
        } else {
            this.lessons.addAll(Collections.emptyList());
        }

    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        persons.addAll(
                source.getPersonList().stream()
                        .map(person -> {
                            if (person instanceof Tutor) {
                                return new JsonAdaptedTutor((Tutor) person);
                            } else {
                                return new JsonAdaptedTutee((Tutee) person);
                            }
                        })
                        .collect(Collectors.toList())
        );
        lessons.addAll(
                source.getLessonList().stream()
                        .map(JsonAdaptedLesson::new)
                        .collect(Collectors.toList())
        );
    }

    /**
     * Validates if the lessons in the addressbook have valid subjects.
     *
     * @param addressBook the addressbook to validate
     */
    public boolean validateLessonSubject(AddressBook addressBook) {
        ObservableList<Lesson> lessonList = addressBook.getLessonList();
        for (Lesson lesson : lessonList) {
            Subject subject = lesson.getSubject();
            Tutor tutor = lesson.getTutor();
            Tutee tutee = lesson.getTutee();
            if (tutor == null || tutee == null) {
                return false;
            }

            if (!tutor.getSubjects().contains(subject) || !tutee.getSubjects().contains(subject)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        int highestId = 0;
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            highestId = Math.max(highestId, person.getId());
            addressBook.addPerson(person);
        }
        Person.initialiseIndex(highestId + 1);
        for (JsonAdaptedLesson jsonAdaptedLesson : lessons) {
            Lesson lesson = jsonAdaptedLesson.toModelType(addressBook);
            if (addressBook.hasLesson(lesson)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_LESSON);
            }
            addressBook.addLesson(lesson);
        }
        if (!validateLessonSubject(addressBook)) {
            throw new IllegalValueException(MESSAGE_PERSON_LESSON_MISMATCH);
        }
        return addressBook;
    }

}
