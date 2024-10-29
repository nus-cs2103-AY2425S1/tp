package seedu.address.logic.commands.searchmode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Collections;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.FieldContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonIsRolePredicate;
import seedu.address.model.role.RoleHandler;
import seedu.address.model.role.exceptions.InvalidRoleException;

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
        Predicate<Person> predicate = new FieldContainsKeywordsPredicate<>(
                Collections.singletonList("Amy"), Person::getName);
        SearchModeSearchCommand command = new SearchModeSearchCommand(predicate);

        model.updateFilteredPersonList(predicate);
        CommandResult result = command.execute(model, null);

        assertEquals(SearchModeSearchCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertEquals(model.getFilteredPersonList(), expectedModel.getFilteredPersonList());
    }

    @Test
    public void equals_samePredicate_returnsTrue() {
        Predicate<Person> predicate = new FieldContainsKeywordsPredicate<>(
                Collections.singletonList("Amy"), Person::getName);
        SearchModeSearchCommand command1 = new SearchModeSearchCommand(predicate);
        SearchModeSearchCommand command2 = new SearchModeSearchCommand(predicate);

        assertEquals(command1, command2);
    }



    @Test
    public void equals_differentPredicate_returnsFalse() {
        Predicate<Person> predicate1 = new FieldContainsKeywordsPredicate<>(
                Collections.singletonList("Amy"), Person::getName);
        Predicate<Person> predicate2 = new FieldContainsKeywordsPredicate<>(
                Collections.singletonList("Bob"), Person::getName);
        SearchModeSearchCommand command1 = new SearchModeSearchCommand(predicate1);
        SearchModeSearchCommand command2 = new SearchModeSearchCommand(predicate2);

        assertNotEquals(command1, command2);
    }

    @Test
    public void equals_differentObject_returnsFalse() {
        Predicate<Person> predicate = new FieldContainsKeywordsPredicate<>(
                Collections.singletonList("Amy"), Person::getName);
        SearchModeSearchCommand command = new SearchModeSearchCommand(predicate);

        assertNotEquals(command, new Object());
    }

    @Test
    public void equals_null_returnsFalse() {
        Predicate<Person> predicate = new FieldContainsKeywordsPredicate<>(
                Collections.singletonList("Amy"), Person::getName);
        SearchModeSearchCommand command = new SearchModeSearchCommand(predicate);

        assertNotEquals(command, null);
    }

    @Test
    public void execute_combinedPredicate_success() throws InvalidRoleException {
        Predicate<Person> namePredicate = new FieldContainsKeywordsPredicate<>(
                Collections.singletonList("Amy"), Person::getName);
        Predicate<Person> rolePredicate = new PersonIsRolePredicate(
                Collections.singletonList(RoleHandler.getRole("vendor")));
        Predicate<Person> combinedPredicate = namePredicate.and(rolePredicate);

        SearchModeSearchCommand command = new SearchModeSearchCommand(combinedPredicate);

        model.updateFilteredPersonList(combinedPredicate);
        CommandResult result = command.execute(model, null);

        assertEquals(SearchModeSearchCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertEquals(model.getFilteredPersonList(), expectedModel.getFilteredPersonList());
    }


    @Test
    public void execute_chainedPredicates_success() throws InvalidRoleException {
        Predicate<Person> namePredicate = new FieldContainsKeywordsPredicate<>(
                Collections.singletonList("Amy"), Person::getName);
        Predicate<Person> rolePredicate = new PersonIsRolePredicate(
                Collections.singletonList(RoleHandler.getRole("vendor")));
        Predicate<Person> combinedPredicate = namePredicate.and(rolePredicate);

        SearchModeSearchCommand command = new SearchModeSearchCommand(combinedPredicate);

        model.updateFilteredPersonList(combinedPredicate);
        CommandResult result = command.execute(model, null);

        assertEquals(SearchModeSearchCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertEquals(model.getFilteredPersonList(), expectedModel.getFilteredPersonList());
    }

    @Test
    public void execute_orPredicates_success() throws InvalidRoleException {
        Predicate<Person> namePredicate = new FieldContainsKeywordsPredicate<>(
                Collections.singletonList("Amy"), Person::getName);
        Predicate<Person> rolePredicate = new PersonIsRolePredicate(
                Collections.singletonList(RoleHandler.getRole("vendor")));
        Predicate<Person> combinedPredicate = namePredicate.or(rolePredicate);

        SearchModeSearchCommand command = new SearchModeSearchCommand(combinedPredicate);

        model.updateFilteredPersonList(combinedPredicate);
        CommandResult result = command.execute(model, null);

        assertEquals(SearchModeSearchCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertEquals(model.getFilteredPersonList(), expectedModel.getFilteredPersonList());
    }

    @Test
    public void execute_negatedPredicate_success() {
        Predicate<Person> namePredicate = new FieldContainsKeywordsPredicate<>(
                Collections.singletonList("Amy"), Person::getName);
        Predicate<Person> negatedPredicate = namePredicate.negate();

        SearchModeSearchCommand command = new SearchModeSearchCommand(negatedPredicate);

        model.updateFilteredPersonList(negatedPredicate);
        CommandResult result = command.execute(model, null);

        assertEquals(SearchModeSearchCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertEquals(model.getFilteredPersonList(), expectedModel.getFilteredPersonList());
    }


}
