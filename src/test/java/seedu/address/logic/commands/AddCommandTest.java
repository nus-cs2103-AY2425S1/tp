package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalInternshipApplications.GOOGLE;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.internshipapplication.InternshipApplication;
import seedu.address.model.internshipapplication.Person;
import seedu.address.testutil.InternshipApplicationBuilder;
import seedu.address.testutil.PersonBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullApplication_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_applicationAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingInternshipApplicationAdded modelStub = new ModelStubAcceptingInternshipApplicationAdded();
        InternshipApplication validApplication = new InternshipApplicationBuilder().build();

        CommandResult commandResult = new AddCommand(validApplication).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validApplication)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validApplication), modelStub.internshipApplicationsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        InternshipApplication validApplication = new InternshipApplicationBuilder().build();
        AddCommand addCommand = new AddCommand(validApplication);
        ModelStub modelStub = new ModelStubWithInternshipApplication(validApplication);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_APPLICATION, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        InternshipApplication alice = new InternshipApplicationBuilder().withName("Alice").build();
        InternshipApplication bob = new InternshipApplicationBuilder().withName("Bob").build();
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
        AddCommand addCommand = new AddCommand(GOOGLE);
        String expected = AddCommand.class.getCanonicalName() + "{toAdd=" + GOOGLE + "}";
        assertEquals(expected, addCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model<InternshipApplication> {
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
        public Path getHireMeFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setHireMeFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addItem(InternshipApplication application) {
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
        public boolean hasItem(InternshipApplication application) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteItem(InternshipApplication target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setItem(InternshipApplication target, InternshipApplication editedApplication) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<InternshipApplication> getFilteredList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredList(Predicate<InternshipApplication> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithInternshipApplication extends ModelStub {
        private final InternshipApplication application;

        ModelStubWithInternshipApplication(InternshipApplication application) {
            requireNonNull(application);
            this.application = application;
        }

        @Override
        public boolean hasItem(InternshipApplication application) {
            requireNonNull(application);
            return this.application.isSame(application);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingInternshipApplicationAdded extends ModelStub {
        final ArrayList<InternshipApplication> internshipApplicationsAdded = new ArrayList<>();

        @Override
        public boolean hasItem(InternshipApplication application) {
            requireNonNull(application);
            return internshipApplicationsAdded.stream().anyMatch(application::isSame);
        }

        @Override
        public void addItem(InternshipApplication application) {
            requireNonNull(application);
            internshipApplicationsAdded.add(application);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook<InternshipApplication>();
        }
    }

}
