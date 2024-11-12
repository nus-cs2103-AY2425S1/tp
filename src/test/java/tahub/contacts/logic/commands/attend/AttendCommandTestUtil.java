package tahub.contacts.logic.commands.attend;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import tahub.contacts.commons.core.GuiSettings;
import tahub.contacts.model.Model;
import tahub.contacts.model.ReadOnlyAddressBook;
import tahub.contacts.model.ReadOnlyUserPrefs;
import tahub.contacts.model.course.Course;
import tahub.contacts.model.course.UniqueCourseList;
import tahub.contacts.model.person.Person;
import tahub.contacts.model.studentcourseassociation.StudentCourseAssociation;
import tahub.contacts.model.studentcourseassociation.StudentCourseAssociationList;
import tahub.contacts.model.studentcourseassociation.exceptions.ScaNotFoundException;
import tahub.contacts.model.tutorial.Tutorial;
import tahub.contacts.testutil.ScaBuilder;

/**
 * Helpers used to test Attendance Commands
 */
public class AttendCommandTestUtil {
    /**
     * Creates a stubbed ScaList with no SCA match.
     *
     * @return Stubbed ScaList.
     */
    public ScaListNoMatch createNoMatchScaList() {
        return new ScaListNoMatch();
    }

    /**
     * Creates a stubbed ScaList with an SCA match.
     *
     * @param sca SCA to return.
     * @return Stubbed ScaList.
     */
    public static ScaListWithMatch createMatchScaList(StudentCourseAssociation sca) {
        return new ScaListWithMatch(sca);
    }

    /**
     * Gets a new standard SCA to be used for Attendance command testing.
     * This SCA will have an empty {@code Attendance}.
     *
     * @return SCA.
     */
    public static StudentCourseAssociation getNewScaToTestAttendance() {
        StudentCourseAssociation sca = ScaBuilder.createDefault();
        sca.getAttendance().clear();
        return sca;
    }

    /**
     * Creates a new {@code ModelStub} with an ScaList.
     *
     * @param scaList ScaList to be used.
     * @return new {@link ModelStubWithScaList}.
     */
    public static ModelStubWithScaList createModelStubWithScaList(StudentCourseAssociationList scaList) {
        return new ModelStubWithScaList(scaList);
    }

    // ==================== STUB CLASSES ======================

    // @@author miloaisdino
    /**
     * A default model stub that has all the unnecessary methods failing.
     */
    private static class ModelStub implements Model {

        @Override
        public void notifyEnrollmentChanged() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setStudentCourseAssociation(StudentCourseAssociation target, StudentCourseAssociation editedSca) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public StudentCourseAssociationList getStudentScaList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(java.nio.file.Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public java.nio.file.Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCourseListFilePath(java.nio.file.Path courseListFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setScaListFilePath(Path scaListFilePath) {

        }

        @Override
        public java.nio.file.Path getCourseListFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getScaListFilePath() {
            return null;
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCourseList(UniqueCourseList courseList) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addSca(StudentCourseAssociation sca) {

        }

        @Override
        public UniqueCourseList getCourseList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public StudentCourseAssociationList getScaList() {
            return null;
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteSca(StudentCourseAssociation target) {

        }

        @Override
        public void addCourse(Course course) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCourse(Course target, Course editedCourse) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasSca(StudentCourseAssociation sca) {
            return false;
        }

        @Override
        public void deleteCourse(Course course) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasCourse(Course course) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public StudentCourseAssociationList getStudentScas(Person student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public UniqueCourseList getStudentCourses(Person student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Tutorial> getStudentTutorials(Person student) {
            throw new AssertionError("This method should not be called.");
        }
    }
    // @@author

    /**
     * Model stub that takes in and has a {@link StudentCourseAssociationList}.
     */
    public static class ModelStubWithScaList extends ModelStub {
        private StudentCourseAssociationList scaList;
        ModelStubWithScaList(StudentCourseAssociationList scaList) {
            this.scaList = scaList;
        }

        @Override
        public StudentCourseAssociationList getScaList() {
            return scaList;
        }
    }

    /**
     * ScaList stub class with no match.
     */
    public static class ScaListNoMatch extends StudentCourseAssociationList {
        /**
         * Throws a guaranteed {@link ScaNotFoundException}.
         *
         * @param toFind Ignored query SCA.
         * @return NIL
         */
        @Override
        public StudentCourseAssociation findMatch(StudentCourseAssociation toFind) {
            throw new ScaNotFoundException("STUB: SCA not found.");
        }
    }

    /**
     * ScaList stub class with a match.
     */
    public static class ScaListWithMatch extends StudentCourseAssociationList {
        private StudentCourseAssociation scaToMatch;

        /**
         * Creates a stubbed ScaList with a guaranteed SCA return for the method {@code findMatch}.
         *
         * @param scaToMatch SCA to be returned on every {@code findMatch} call.
         */
        ScaListWithMatch(StudentCourseAssociation scaToMatch) {
            this.scaToMatch = scaToMatch;
        }

        /**
         * Returns the SCA associated with this stub SCAList class.
         *
         * @param toFind Ignored query SCA.
         * @return NIL
         */
        @Override
        public StudentCourseAssociation findMatch(StudentCourseAssociation toFind) {
            return scaToMatch;
        }
    }
}
