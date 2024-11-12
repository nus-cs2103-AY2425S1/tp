package tuteez.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tuteez.testutil.Assert.assertThrows;
import static tuteez.testutil.TypicalPersons.ALICE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import tuteez.commons.core.GuiSettings;
import tuteez.logic.Messages;
import tuteez.logic.commands.exceptions.CommandException;
import tuteez.model.AddressBook;
import tuteez.model.Model;
import tuteez.model.ReadOnlyAddressBook;
import tuteez.model.ReadOnlyUserPrefs;
import tuteez.model.person.Name;
import tuteez.model.person.Person;
import tuteez.model.person.lesson.Lesson;
import tuteez.testutil.PersonBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person validPerson = new PersonBuilder().build();

        CommandResult commandResult = new AddCommand(validPerson).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validPerson)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person validPerson = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validPerson);
        ModelStub modelStub = new ModelStubWithPerson(validPerson);

        assertThrows(CommandException.class, Messages.MESSAGE_DUPLICATE_PERSON, () -> addCommand.execute(modelStub));
    }

    @Test
    public void execute_clashingWithAnotherStudentLesson_throwsCommandException() {
        Person alice = new PersonBuilder().withName("Alice").withLessons("friday 1800-2000").build();
        Person bob = new PersonBuilder().withName("Bob").withLessons("friday 1730-1830").build();
        Person charlie = new PersonBuilder().withName("Charlie").withLessons("friday 1730-1830").build();
        ModelStub modelStub = new ModelStubPersonAcceptedButClashingLesson();
        modelStub.addPerson(bob);
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addCharlieCommand = new AddCommand(charlie);

        // EP: Same day, overlapping lesson time
        assertThrows(CommandException.class, () -> addAliceCommand.execute(modelStub));

        //EP: Exact same lesson time
        assertThrows(CommandException.class, () -> addCharlieCommand.execute(modelStub));
    }

    @Test
    public void execute_studentNoClashingLesson_addSuccessful() throws CommandException {
        Person alice = new PersonBuilder().withName("Alice").withLessons("friday 1800-2000").build();
        Person bob = new PersonBuilder().withName("Bob").withLessons("friday 1700-1800").build();
        Person charlie = new PersonBuilder().withName("Charlie").withLessons("monday 1800-1900").build();
        ModelStub modelStub = new ModelStubPersonAcceptedButClashingLesson();
        modelStub.addPerson(bob);
        CommandResult addAliceResult = new AddCommand(alice).execute(modelStub);


        // EP: Lesson on same day, start and end time coincide
        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(alice)),
                addAliceResult.getFeedbackToUser());

        CommandResult charlieResult = new AddCommand(charlie).execute(modelStub);
        // EP: Lesson on different day
        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(charlie)),
                charlieResult.getFeedbackToUser());
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddCommand addCommand = new AddCommand(ALICE);
        String expected = AddCommand.class.getCanonicalName() + "{toAdd=" + ALICE + "}";
        assertEquals(expected, addCommand.toString());
    }


    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
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
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
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
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void displayPerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Map<Person, ArrayList<Lesson>> getClashingLessons(Lesson lesson) {
            return Map.of();
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
        public ObjectProperty<Optional<Person>> getLastViewedPerson() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void updateLastViewedPerson(Person personOnDisplay) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeLastViewedPerson() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public boolean isLastViewPersonAvailable() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public boolean isSamePersonAsPersonOnDisplay(Person person) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public Person findPersonByName(Name name) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Person person;

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.person = person;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.person.isSamePerson(person);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     * This implies there are no lesson clashes
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }

        @Override
        public Map<Person, ArrayList<Lesson>> getClashingLessons(Lesson lesson) {
            return Collections.emptyMap();
        }
    }

    private class ModelStubPersonAcceptedButClashingLesson extends ModelStubAcceptingPersonAdded {

        @Override
        public Map<Person, ArrayList<Lesson>> getClashingLessons(Lesson lesson) {
            Map<Person, ArrayList<Lesson>> clashingLessonMap = new HashMap<>();
            Iterator<Person> students = personsAdded.iterator();
            while (students.hasNext()) {
                Person studentToCheck = students.next();
                ArrayList<Lesson> clashedLessons = studentToCheck.findStudentClashingLessons(lesson);
                if (!clashedLessons.isEmpty()) {
                    clashingLessonMap.put(studentToCheck, clashedLessons);
                }
            }
            return clashingLessonMap;
        }

    }
}
