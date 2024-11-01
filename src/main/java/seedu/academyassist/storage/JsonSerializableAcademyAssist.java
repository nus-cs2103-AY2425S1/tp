package seedu.academyassist.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.academyassist.commons.exceptions.IllegalValueException;
import seedu.academyassist.model.AcademyAssist;
import seedu.academyassist.model.ReadOnlyAcademyAssist;
import seedu.academyassist.model.person.Person;

/**
 * An Immutable AcademyAssist that is serializable to JSON format.
 */
@JsonRootName(value = "academyassist")
class JsonSerializableAcademyAssist {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final int idGeneratedCount;

    /**
     * Constructs a {@code JsonSerializableAcademyAssist} with the given persons.
     */
    @JsonCreator
    public JsonSerializableAcademyAssist(@JsonProperty("persons") List<JsonAdaptedPerson> persons,
                                         @JsonProperty("id_generated_count") int idGeneratedCount) {
        this.persons.addAll(persons);
        this.idGeneratedCount = idGeneratedCount;
    }

    /**
     * Converts a given {@code ReadOnlyAcademyAssist} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAcademyAssist}.
     */
    public JsonSerializableAcademyAssist(ReadOnlyAcademyAssist source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
        idGeneratedCount = source.getIdGeneratedCount();
    }

    /**
     * Converts this academy assist into the model's {@code AcademyAssist} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AcademyAssist toModelType() throws IllegalValueException {
        AcademyAssist academyAssist = new AcademyAssist();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (academyAssist.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            academyAssist.addPerson(person);
        }
        academyAssist.setIdGeneratedCount(idGeneratedCount);
        return academyAssist;
    }

}
