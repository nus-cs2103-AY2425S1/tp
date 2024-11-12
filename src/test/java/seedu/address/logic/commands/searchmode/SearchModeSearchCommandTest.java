package seedu.address.logic.commands.searchmode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_VENDOR;
import static seedu.address.testutil.TypicalEvents.getTypicalEventManager;

import java.util.Collections;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;
import seedu.address.model.person.predicates.PersonIsRolePredicate;
import seedu.address.model.role.RoleHandler;
import seedu.address.model.role.exceptions.InvalidRoleException;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalPersons;

public class SearchModeSearchCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
        expectedModel = new ModelManager();
    }

    @Test
    public void execute_validPredicate_success() {
        model = new ModelManager(TypicalPersons.getTypicalAddressBook(), getTypicalEventManager(), new UserPrefs());
        expectedModel = new ModelManager(TypicalPersons.getTypicalAddressBook(), getTypicalEventManager(),
                new UserPrefs());
        Predicate<Person> predicate = new NameContainsKeywordsPredicate(Collections.singletonList("Alice"));

        SearchModeSearchCommand command = new SearchModeSearchCommand(predicate);
        model.updateFilteredPersonList(person -> false);
        CommandResult result = command.execute(model, model.getEventManager());

        expectedModel.updateFilteredPersonList(predicate);

        assertEquals(String.format(SearchModeSearchCommand.MESSAGE_SUCCESS, "1"), result.getFeedbackToUser());
        assertEquals(model.getFilteredPersonList(), expectedModel.getFilteredPersonList());
    }

    @Test
    public void equals_samePredicate_returnsTrue() {
        Predicate<Person> predicate = new NameContainsKeywordsPredicate(Collections.singletonList("Amy"));

        SearchModeSearchCommand command1 = new SearchModeSearchCommand(predicate);
        SearchModeSearchCommand command2 = new SearchModeSearchCommand(predicate);

        assertEquals(command1, command2);
    }



    @Test
    public void equals_differentPredicate_returnsFalse() {
        Predicate<Person> predicate1 = new NameContainsKeywordsPredicate(Collections.singletonList("Amy"));
        Predicate<Person> predicate2 = new NameContainsKeywordsPredicate(Collections.singletonList("Bob"));
        SearchModeSearchCommand command1 = new SearchModeSearchCommand(predicate1);
        SearchModeSearchCommand command2 = new SearchModeSearchCommand(predicate2);

        assertNotEquals(command1, command2);
    }

    @Test
    public void equals_differentObject_returnsFalse() {
        Predicate<Person> predicate = new NameContainsKeywordsPredicate(Collections.singletonList("Amy"));
        SearchModeSearchCommand command = new SearchModeSearchCommand(predicate);

        assertNotEquals(command, new Object());
    }

    @Test
    public void equals_null_returnsFalse() {
        Predicate<Person> predicate = new NameContainsKeywordsPredicate(Collections.singletonList("Amy"));
        SearchModeSearchCommand command = new SearchModeSearchCommand(predicate);

        assertNotEquals(command, null);
    }

    @Test
    public void execute_combinedPredicate_success() throws InvalidRoleException {
        Predicate<Person> namePredicate = new NameContainsKeywordsPredicate(Collections.singletonList("Amy"));
        Predicate<Person> rolePredicate = new PersonIsRolePredicate(
                Collections.singletonList(RoleHandler.getRole(VALID_ROLE_VENDOR)));
        Predicate<Person> combinedPredicate = namePredicate.and(rolePredicate);

        SearchModeSearchCommand command = new SearchModeSearchCommand(combinedPredicate);

        model.updateFilteredPersonList(combinedPredicate);
        CommandResult result = command.execute(model, model.getEventManager());

        assertEquals(SearchModeSearchCommand.MESSAGE_NO_PERSONS_FOUND, result.getFeedbackToUser());
        assertEquals(model.getFilteredPersonList(), expectedModel.getFilteredPersonList());
    }


    @Test
    public void execute_chainedPredicates_success() throws InvalidRoleException {
        Predicate<Person> namePredicate = new NameContainsKeywordsPredicate(Collections.singletonList("Amy"));
        Predicate<Person> rolePredicate = new PersonIsRolePredicate(
                Collections.singletonList(RoleHandler.getRole(VALID_ROLE_VENDOR)));
        Predicate<Person> combinedPredicate = namePredicate.and(rolePredicate);

        PersonBuilder personBuilder = new PersonBuilder().withName("Amy").withRoles(VALID_ROLE_VENDOR);
        model.addPerson(personBuilder.build());
        model.updateFilteredPersonList(person -> false);
        SearchModeSearchCommand command = new SearchModeSearchCommand(combinedPredicate);

        expectedModel.addPerson(personBuilder.build());
        expectedModel.updateFilteredPersonList(combinedPredicate);
        CommandResult result = command.execute(model, model.getEventManager());

        assertEquals(String.format(SearchModeSearchCommand.MESSAGE_SUCCESS, 1), result.getFeedbackToUser());
        assertEquals(model.getFilteredPersonList(), expectedModel.getFilteredPersonList());
    }

    @Test
    public void execute_orPredicates_success() throws InvalidRoleException {
        model = new ModelManager(TypicalPersons.getTypicalAddressBook(), getTypicalEventManager(), new UserPrefs());
        expectedModel = new ModelManager(TypicalPersons.getTypicalAddressBook(), getTypicalEventManager(),
                new UserPrefs());
        Predicate<Person> namePredicate = new NameContainsKeywordsPredicate(Collections.singletonList("Alice"));
        Predicate<Person> rolePredicate = new PersonIsRolePredicate(
                Collections.singletonList(RoleHandler.getRole(VALID_ROLE_VENDOR)));
        Predicate<Person> combinedPredicate = namePredicate.or(rolePredicate);

        SearchModeSearchCommand command = new SearchModeSearchCommand(combinedPredicate);
        model.updateFilteredPersonList(person -> false);
        expectedModel.updateFilteredPersonList(combinedPredicate);
        CommandResult result = command.execute(model, model.getEventManager());

        assertEquals(String.format(SearchModeSearchCommand.MESSAGE_SUCCESS, "3"), result.getFeedbackToUser());
        assertEquals(model.getFilteredPersonList(), expectedModel.getFilteredPersonList());
    }

    @Test
    public void execute_negatedPredicate_success() {
        Predicate<Person> namePredicate = new NameContainsKeywordsPredicate(Collections.singletonList("Amy"));
        Predicate<Person> negatedPredicate = namePredicate.negate();

        SearchModeSearchCommand command = new SearchModeSearchCommand(negatedPredicate);
        model = new ModelManager(TypicalPersons.getTypicalAddressBook(), getTypicalEventManager(), new UserPrefs());
        expectedModel = new ModelManager(TypicalPersons.getTypicalAddressBook(), getTypicalEventManager(),
                new UserPrefs());

        model.updateFilteredPersonList(person -> false);
        expectedModel.updateFilteredPersonList(negatedPredicate);
        CommandResult result = command.execute(model, model.getEventManager());
        //everyone except amy
        assertEquals(String.format(SearchModeSearchCommand.MESSAGE_SUCCESS, "7"), result.getFeedbackToUser());
        assertEquals(model.getFilteredPersonList(), expectedModel.getFilteredPersonList());
    }

    @Test
    public void execute_noPersonsFound_success() {
        Predicate<Person> namePredicate = new NameContainsKeywordsPredicate(Collections.singletonList("Obama"));
        SearchModeSearchCommand command = new SearchModeSearchCommand(namePredicate);
        model = new ModelManager(new AddressBook(), getTypicalEventManager(), new UserPrefs());
        expectedModel = new ModelManager(new AddressBook(), getTypicalEventManager(), new UserPrefs());

        model.updateFilteredPersonList(person -> false);
        expectedModel.updateFilteredPersonList(namePredicate);
        CommandResult result = command.execute(model, model.getEventManager());

        assertEquals(SearchModeSearchCommand.MESSAGE_NO_PERSONS_FOUND, result.getFeedbackToUser());
        assertEquals(model.getFilteredPersonList(), expectedModel.getFilteredPersonList());
    }


}
