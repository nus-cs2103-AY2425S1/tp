package tahub.contacts.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import tahub.contacts.commons.exceptions.IllegalValueException;
import tahub.contacts.model.ReadOnlyAddressBook;
import tahub.contacts.model.course.UniqueCourseList;
import tahub.contacts.model.studentcourseassociation.StudentCourseAssociation;
import tahub.contacts.model.studentcourseassociation.StudentCourseAssociationList;

/**
 * An Immutable StudentCourseAssociationList that is serializable to JSON format.
 */
@JsonRootName(value = "scalist")
class JsonSerializableStudentCourseAssociationList {

    public static final String MESSAGE_DUPLICATE_SCA = "SCA list contains duplicate item(s).";

    private List<JsonAdaptedStudentCourseAssociation> scas = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableStudentCourseAssociationList} with the given SCAs.
     */
    @JsonCreator
    public JsonSerializableStudentCourseAssociationList(@JsonProperty("scas")
                                                            List<JsonAdaptedStudentCourseAssociation> scas) {
        this.scas = (scas != null) ? scas : new ArrayList<>();
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
     * Converts this SCA list into the model's {@code StudentCourseAssociationList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public StudentCourseAssociationList toModelType(ReadOnlyAddressBook addressBook,
                                                    UniqueCourseList courseList) throws IllegalValueException {
        StudentCourseAssociationList scaList = new StudentCourseAssociationList();
        for (JsonAdaptedStudentCourseAssociation jsonAdaptedSca : scas) {
            StudentCourseAssociation sca = jsonAdaptedSca.toModelType(addressBook, courseList);
            if (scaList.has(sca)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_SCA);
            }
            scaList.add(sca);
        }
        return scaList;
    }

}
