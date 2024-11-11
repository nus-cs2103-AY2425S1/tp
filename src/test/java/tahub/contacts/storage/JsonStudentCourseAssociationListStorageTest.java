package tahub.contacts.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tahub.contacts.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tahub.contacts.commons.exceptions.DataLoadingException;
import tahub.contacts.model.ReadOnlyAddressBook;
import tahub.contacts.model.course.Course;
import tahub.contacts.model.course.CourseCode;
import tahub.contacts.model.course.CourseName;
import tahub.contacts.model.course.UniqueCourseList;
import tahub.contacts.model.studentcourseassociation.StudentCourseAssociation;
import tahub.contacts.model.studentcourseassociation.StudentCourseAssociationList;
import tahub.contacts.model.tutorial.Tutorial;
import tahub.contacts.testutil.TypicalPersons;

public class JsonStudentCourseAssociationListStorageTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonStudentCourseAssociationListStorageTest");
    private static final Path VALID_SCA_LIST_FILE = TEST_DATA_FOLDER.resolve("validScaList.json");
    private static final Path INVALID_SCA_LIST_FILE = TEST_DATA_FOLDER.resolve("invalidScaList.json");

    private JsonStudentCourseAssociationListStorage storage;
    private ReadOnlyAddressBook addressBook;
    private UniqueCourseList courseList;
    private StudentCourseAssociation sca1;
    private StudentCourseAssociation sca2;

    @BeforeEach
    public void setUp() {
        addressBook = TypicalPersons.getTypicalAddressBook();
        Course course1 = new Course(new CourseCode("MA1521"), new CourseName("Calculus I"));
        Course course2 = new Course(new CourseCode("CS2103T"), new CourseName("Software Engineering"));
        courseList = new UniqueCourseList();
        courseList.add(course1);
        courseList.add(course2);
        storage = new JsonStudentCourseAssociationListStorage(VALID_SCA_LIST_FILE);
        sca1 = new StudentCourseAssociation(addressBook.getPersonByMatricNumber("A2345678Y"),
                course1, new Tutorial("T01", course1));
        sca2 = new StudentCourseAssociation(addressBook.getPersonByMatricNumber("A1234567X"),
                course2, new Tutorial("T02", course2));
    }

    @Test
    public void readScaList_validFile_success() throws Exception {
        Optional<StudentCourseAssociationList> actualList = storage.readScaList(VALID_SCA_LIST_FILE,
                addressBook, courseList);
        StudentCourseAssociationList expectedList = new StudentCourseAssociationList();
        expectedList.add(sca1);
        expectedList.add(sca2);
        assertEquals(Optional.of(expectedList), actualList);
    }

    @Test
    public void readScaList_invalidFile_throwsDataLoadingException() throws Exception {
        storage = new JsonStudentCourseAssociationListStorage(INVALID_SCA_LIST_FILE);
        assertThrows(
                DataLoadingException.class, () -> storage.readScaList(INVALID_SCA_LIST_FILE, addressBook, courseList));
    }
}
