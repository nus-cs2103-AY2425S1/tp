package seedu.hireme.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.hireme.logic.commands.CommandTestUtil.VALID_COMPANY_EMAIL_APPLE;
import static seedu.hireme.logic.commands.CommandTestUtil.VALID_ROLE_APPLE;
import static seedu.hireme.testutil.Assert.assertThrows;
import static seedu.hireme.testutil.TypicalInternshipApplications.GOOGLE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.hireme.commons.core.GuiSettings;
import seedu.hireme.logic.Messages;
import seedu.hireme.logic.commands.exceptions.CommandException;
import seedu.hireme.model.AddressBook;
import seedu.hireme.model.Model;
import seedu.hireme.model.ReadOnlyAddressBook;
import seedu.hireme.model.ReadOnlyUserPrefs;
import seedu.hireme.model.internshipapplication.InternshipApplication;
import seedu.hireme.testutil.InternshipApplicationBuilder;

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
    public void execute_sameCompanyDifferentEmail_addSuccessful() throws Exception {
        ModelStubAcceptingInternshipApplicationAdded modelStub = new ModelStubAcceptingInternshipApplicationAdded();
        InternshipApplication application = new InternshipApplicationBuilder().build();
        modelStub.addItem(application);
        InternshipApplication diffApplication = new InternshipApplicationBuilder()
                                            .withEmail(VALID_COMPANY_EMAIL_APPLE).build();
        CommandResult commandResult = new AddCommand(diffApplication).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(diffApplication)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(application, diffApplication), modelStub.internshipApplicationsAdded);
    }

    @Test
    public void execute_sameCompanyDifferentRole_addSuccessful() throws Exception {
        ModelStubAcceptingInternshipApplicationAdded modelStub = new ModelStubAcceptingInternshipApplicationAdded();
        InternshipApplication application = new InternshipApplicationBuilder().build();
        modelStub.addItem(application);
        InternshipApplication diffApplication = new InternshipApplicationBuilder()
                .withRole(VALID_ROLE_APPLE).build();
        CommandResult commandResult = new AddCommand(diffApplication).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(diffApplication)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(application, diffApplication), modelStub.internshipApplicationsAdded);
    }

    @Test
    public void execute_sameCompanyDifferentDate_addSuccessful() throws Exception {
        ModelStubAcceptingInternshipApplicationAdded modelStub = new ModelStubAcceptingInternshipApplicationAdded();
        InternshipApplication application = new InternshipApplicationBuilder().build();
        modelStub.addItem(application);
        InternshipApplication diffApplication = new InternshipApplicationBuilder()
                .withDate("01/01/01").build();
        CommandResult commandResult = new AddCommand(diffApplication).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(diffApplication)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(application, diffApplication), modelStub.internshipApplicationsAdded);
    }

    @Test
    public void execute_duplicateInternshipApplication_throwsCommandException() {
        InternshipApplication validApplication = new InternshipApplicationBuilder().build();
        AddCommand addCommand = new AddCommand(validApplication);
        ModelStub modelStub = new ModelStubWithInternshipApplication(validApplication);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_APPLICATION, () -> addCommand
                .execute(modelStub));
    }

    @Test
    public void equals() {
        InternshipApplication apple = new InternshipApplicationBuilder().withName("Apple").build();
        InternshipApplication bofa = new InternshipApplicationBuilder().withName("Bofa").build();
        AddCommand addAppleCommand = new AddCommand(apple);
        AddCommand addBofaCommand = new AddCommand(bofa);

        // same object -> returns true
        assertTrue(addAppleCommand.equals(addAppleCommand));

        // same values -> returns true
        AddCommand addAppleCommandCopy = new AddCommand(apple);
        assertTrue(addAppleCommand.equals(addAppleCommandCopy));

        // different types -> returns false
        assertFalse(addAppleCommand.equals(1));

        // null -> returns false
        assertFalse(addAppleCommand.equals(null));

        // different person -> returns false
        assertFalse(addAppleCommand.equals(addBofaCommand));
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
        public void setAddressBook(ReadOnlyAddressBook<InternshipApplication> newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook<InternshipApplication> getAddressBook() {
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
        public ReadOnlyAddressBook<InternshipApplication> getAddressBook() {
            return new AddressBook<>();
        }
    }

}
