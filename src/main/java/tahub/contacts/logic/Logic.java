package tahub.contacts.logic;

import java.nio.file.Path;
import java.util.List;

import javafx.collections.ObservableList;
import tahub.contacts.commons.core.GuiSettings;
import tahub.contacts.logic.commands.CommandResult;
import tahub.contacts.logic.commands.exceptions.CommandException;
import tahub.contacts.logic.parser.exceptions.ParseException;
import tahub.contacts.model.ReadOnlyAddressBook;
import tahub.contacts.model.course.UniqueCourseList;
import tahub.contacts.model.person.Person;
import tahub.contacts.model.studentcourseassociation.StudentCourseAssociationList;
import tahub.contacts.model.tutorial.Tutorial;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the AddressBook.
     *
     * @see tahub.contacts.model.Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Returns the SCA list that contains the given student.
     *
     * @param student the student to get the SCA list for
     * @return the SCA list that contains the given student
     */
    StudentCourseAssociationList getStudentScas(Person student);

    /**
     * Returns the course list taken by a given student.
     *
     * @param student the student to get the course list for
     * @return the course list that contains the given student
     */
    UniqueCourseList getStudentCourses(Person student);

    /**
     * Returns the tutorial list taken by a given student.
     *
     * @param student the student to get the tutorial list for
     * @return the tutorial list that contains the given student
     */
    List<Tutorial> getStudentTutorials(Person student);
    
    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
