package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.GitHubCommand.MISSING_PERSON_EXCEPTION;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Github;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.testutil.NonFunctionalBrowser;
import seedu.address.testutil.PersonBuilder;


public class GitHubCommandTest {
    private NonFunctionalBrowser nonFunctionalBrowser = NonFunctionalBrowser.getDesktop();
    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new GitHubCommand(null, nonFunctionalBrowser));
    }

    @Test
    public void toStringMethod() {
        GitHubCommand githubCommand = new GitHubCommand(new Name("Alice Pauline"), nonFunctionalBrowser);
        String expected = GitHubCommand.class.getCanonicalName() + "{toLaunch=" + ALICE.getName() + "}";
        assertEquals(expected, githubCommand.toString());
    }

    @Test
    public void equals() {
        GitHubCommand aliceGithubCommand = new GitHubCommand(new Name("Alice"), nonFunctionalBrowser);
        GitHubCommand bobGithubCommand = new GitHubCommand(new Name("Bob"), nonFunctionalBrowser);

        // same object -> returns true
        assertTrue(aliceGithubCommand.equals(aliceGithubCommand));

        // same values -> returns true
        GitHubCommand addAliceCommandCopy = new GitHubCommand(new Name("Alice"), nonFunctionalBrowser);
        assertTrue(aliceGithubCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(aliceGithubCommand.equals(1));

        // null -> returns false
        assertFalse(aliceGithubCommand.equals(null));

        // different person -> returns false
        assertFalse(aliceGithubCommand.equals(bobGithubCommand));
    }

    @Test
    public void execute_nameAcceptedByModel_browserLaunchSuccessful() throws Exception {
        Person validPerson = new PersonBuilder().build();
        ModelStubWithPerson modelStub = new ModelStubWithPerson(validPerson);

        CommandResult commandResult = new GitHubCommand(validPerson.getName(),
                NonFunctionalBrowser.getDesktop()).execute(modelStub);
        CommandResult expectedCommandResult = new CommandResult(GitHubCommand.MESSAGE_SUCCESS);

        assertEquals(commandResult, expectedCommandResult);
    }

    @Test
    public void execute_nameNotInModel_throwsCommandException() throws Exception {
        Person validPerson = new PersonBuilder().build();
        ModelStubWithPerson modelStub = new ModelStubWithPerson(validPerson);
        Name invalidName = new Name("invalidChad");
        GitHubCommand gitHubCommand = new GitHubCommand(invalidName, nonFunctionalBrowser);

        String expectedErrorMessage = String.format(MISSING_PERSON_EXCEPTION, "invalidChad");

        assertThrows(CommandException.class, expectedErrorMessage, () -> gitHubCommand.execute(modelStub));
    }

    /**
     * A default model stub that have all the methods failing.
     */
    private class ModelStub implements Model {

        @Override
        public void replaceAllPersons(List<Person> persons) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
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
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
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
        public void setPerson(Person target, Person editedPerson) {
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
        public void updateSortedPersonList(Comparator<Person> comparator) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasAssignment(String name) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public float maxScore(String assignment) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public String getAssignmentName(String name) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public boolean hasName(Name name) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public Github getGitHubUsername(Name name) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public Optional<Person> getPerson(Name name) {
            throw new AssertionError("This method should not be called");
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
        public boolean hasName(Name name) {
            return person.getName().equals(name);
        }

        @Override
        public Github getGitHubUsername(Name name) {
            requireNonNull(name);
            return this.person.getGithub();
        }
    }

}
