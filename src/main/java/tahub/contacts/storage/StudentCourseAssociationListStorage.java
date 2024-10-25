package tahub.contacts.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import tahub.contacts.commons.exceptions.DataLoadingException;
import tahub.contacts.model.ReadOnlyAddressBook;
import tahub.contacts.model.course.UniqueCourseList;
import tahub.contacts.model.studentcourseassociation.StudentCourseAssociationList;

/**
 * Interface of the StudentCourseAssociationListStorage component
 */
public interface StudentCourseAssociationListStorage {
    Optional<StudentCourseAssociationList> readScaList() throws DataLoadingException;

    Optional<StudentCourseAssociationList>
        readScaList(Path scaListFilePath, ReadOnlyAddressBook initialData, UniqueCourseList initialCourseList)
            throws DataLoadingException;

    void saveScaList(StudentCourseAssociationList scaList) throws IOException;

    void saveScaList(StudentCourseAssociationList scaList, Path filePath) throws IOException;

    Path getScaListFilePath();

}
