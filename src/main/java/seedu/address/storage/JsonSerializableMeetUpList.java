package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.MeetUpList;
import seedu.address.model.ReadOnlyMeetUpList;

/**
 * An Immutable MeetUpList that is serializable to JSON format.
 */
@JsonRootName(value = "meetuplist")
class JsonSerializableMeetUpList {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableMeetUpList} with the given persons.
     */
    @JsonCreator
    public JsonSerializableMeetUpList(@JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyMeetUpList} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableMeetUpList}.
     */
    public JsonSerializableMeetUpList(ReadOnlyMeetUpList source) {
        // TODO
        // persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code MeetUpList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public MeetUpList toModelType() throws IllegalValueException {
        return null;
        // TODO
        // MeetUpList addressBook = new MeetUpList();
        // for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
        //     Person person = jsonAdaptedPerson.toModelType();
        //     if (addressBook.hasPerson(person)) {
        //         throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
        //     }
        //     addressBook.addPerson(person);
        // }
        // return addressBook;
    }

}
