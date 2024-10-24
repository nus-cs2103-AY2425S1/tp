package tahub.contacts.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import tahub.contacts.commons.exceptions.IllegalValueException;
import tahub.contacts.model.studentcourseassociation.StudentCourseAssociation;
import tahub.contacts.model.studentcourseassociation.StudentCourseAssociationList;

/**
 * An Immutable StudentCourseAssociationList that is serializable to JSON format.
 */
@JsonRootName(value = "scalist")
class JsonSerializableStudentCourseAssociationList {

    public static final String MESSAGE_DUPLICATE_SCA = "SCA list contains duplicate item(s).";

    private final List<JsonAdaptedStudentCourseAssociation> scas = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableUniqueCourseList} with the given sca.
     */
    @JsonCreator
    public JsonSerializableStudentCourseAssociationList(@JsonProperty("sca")
                                                            List<JsonAdaptedStudentCourseAssociation> sca) {
        this.scas.addAll(sca);
    }

    /**
     * Converts a given {@code StudentCourseAssociationList} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableUniqueCourseList}.
     */
    public JsonSerializableStudentCourseAssociationList(StudentCourseAssociationList source) {
        scas.addAll(source.get().stream().map(JsonAdaptedStudentCourseAssociation::new).collect(Collectors.toList()));
    }

    /**
     * Converts this into the model's {@code StudentCourseAssociationList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public StudentCourseAssociationList toModelType() throws IllegalValueException {
        StudentCourseAssociationList scaList = new StudentCourseAssociationList();
        for (JsonAdaptedStudentCourseAssociation jsonAdaptedCourse : scas) {
            StudentCourseAssociation sca = jsonAdaptedCourse.toModelType();




            if (scaList.has(sca)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_SCA);
            }
            scaList.add(sca);
        }
        return scaList;
    }

}
