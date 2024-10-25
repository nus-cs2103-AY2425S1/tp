package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_EMPLOYEES_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEmployees.ALICE;
import static seedu.address.testutil.TypicalEmployees.BENSON;
import static seedu.address.testutil.TypicalEmployees.CARL;
import static seedu.address.testutil.TypicalEmployees.DANIEL;
import static seedu.address.testutil.TypicalEmployees.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.skill.SkillsContainsKeywordsPredicate;
import seedu.address.model.tag.TagsContainsKeywordsPredicate;
import seedu.address.ui.DisplayType;

/**
 * Contains integration tests (interaction with the Model) for {@code FilterCommand}.
 */
public class FilterCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        SkillsContainsKeywordsPredicate firstSkillPredicate = new SkillsContainsKeywordsPredicate(
                Collections.singletonList("firstSkill"));
        SkillsContainsKeywordsPredicate secondSkillPredicate = new SkillsContainsKeywordsPredicate(
                Collections.singletonList("secondSkill"));
        TagsContainsKeywordsPredicate firstTagPredicate = new TagsContainsKeywordsPredicate(
                Collections.singletonList("firstTag"));
        TagsContainsKeywordsPredicate secondTagPredicate = new TagsContainsKeywordsPredicate(
                Collections.singletonList("secondTag"));

        FilterCommand filterFirstCommand = new FilterCommand(firstSkillPredicate, firstTagPredicate);
        FilterCommand filterSecondCommand = new FilterCommand(secondSkillPredicate, secondTagPredicate);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> returns true
        FilterCommand filterFirstCommandCopy = new FilterCommand(firstSkillPredicate, firstTagPredicate);
        assertTrue(filterFirstCommand.equals(filterFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // different employee -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noEmployeeFound() {
        String expectedMessage = String.format(MESSAGE_EMPLOYEES_LISTED_OVERVIEW, 0);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage,
                DisplayType.EMPLOYEE_LIST, false, false);
        SkillsContainsKeywordsPredicate skillsPredicate = prepareSkillsPredicate(" ");
        TagsContainsKeywordsPredicate tagsPredicate = prepareTagsPredicate(" ");
        FilterCommand command = new FilterCommand(skillsPredicate, tagsPredicate);
        expectedModel.updateFilteredEmployeeList(skillsPredicate.or(tagsPredicate));
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredEmployeeList());
    }

    @Test
    public void execute_multipleKeywords_multipleEmployeesFound() {
        String expectedMessage = String.format(MESSAGE_EMPLOYEES_LISTED_OVERVIEW, 4);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage,
                DisplayType.EMPLOYEE_LIST, false, false);

        SkillsContainsKeywordsPredicate skillsPredicate = prepareSkillsPredicate("thievery");
        TagsContainsKeywordsPredicate tagsPredicate = prepareTagsPredicate("friends");

        FilterCommand command = new FilterCommand(skillsPredicate, tagsPredicate);
        expectedModel.updateFilteredEmployeeList(skillsPredicate.or(tagsPredicate));
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, CARL, DANIEL), model.getFilteredEmployeeList());
    }

    @Test
    public void execute_multipleKeywordsMutuallyExclusive_multipleEmployeesFound() {
        String expectedMessage = String.format(MESSAGE_EMPLOYEES_LISTED_OVERVIEW, 2);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage,
                DisplayType.EMPLOYEE_LIST, false, false);

        SkillsContainsKeywordsPredicate skillsPredicate = prepareSkillsPredicate("moneyManagement");
        TagsContainsKeywordsPredicate tagsPredicate = prepareTagsPredicate("owesMoney");

        FilterCommand command = new FilterCommand(skillsPredicate, tagsPredicate);
        expectedModel.updateFilteredEmployeeList(skillsPredicate.or(tagsPredicate));
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON), model.getFilteredEmployeeList());
    }

    @Test
    public void execute_onlySkills_multipleEmployeesFound() {
        String expectedMessage = String.format(MESSAGE_EMPLOYEES_LISTED_OVERVIEW, 2);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage,
                DisplayType.EMPLOYEE_LIST, false, false);

        SkillsContainsKeywordsPredicate skillsPredicate = prepareSkillsPredicate("thievery");
        TagsContainsKeywordsPredicate tagsPredicate = prepareTagsPredicate(" ");

        FilterCommand command = new FilterCommand(skillsPredicate, tagsPredicate);
        expectedModel.updateFilteredEmployeeList(skillsPredicate.or(tagsPredicate));
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
        assertEquals(Arrays.asList(BENSON, CARL), model.getFilteredEmployeeList());
    }

    @Test
    public void execute_onlyTags_multipleEmployeesFound() {
        String expectedMessage = String.format(MESSAGE_EMPLOYEES_LISTED_OVERVIEW, 3);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage,
                DisplayType.EMPLOYEE_LIST, false, false);

        SkillsContainsKeywordsPredicate skillsPredicate = prepareSkillsPredicate(" ");
        TagsContainsKeywordsPredicate tagsPredicate = prepareTagsPredicate("friends");

        FilterCommand command = new FilterCommand(skillsPredicate, tagsPredicate);
        expectedModel.updateFilteredEmployeeList(skillsPredicate.or(tagsPredicate));
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL), model.getFilteredEmployeeList());
    }

    @Test
    public void toStringMethod() {
        SkillsContainsKeywordsPredicate skillsPredicate = new SkillsContainsKeywordsPredicate(Arrays.asList("keyword"));
        TagsContainsKeywordsPredicate tagsPredicate = new TagsContainsKeywordsPredicate(Arrays.asList("keyword"));
        FilterCommand filterCommand = new FilterCommand(skillsPredicate, tagsPredicate);
        String expected = FilterCommand.class.getCanonicalName() + "{skillsPredicate=" + skillsPredicate
                + ", tagsPredicate=" + tagsPredicate + "}";
        assertEquals(expected, filterCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code SkillsContainsKeywordsPredicate}.
     */
    private SkillsContainsKeywordsPredicate prepareSkillsPredicate(String userInput) {
        return new SkillsContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code TagsContainsKeywordsPredicate}.
     */
    private TagsContainsKeywordsPredicate prepareTagsPredicate(String userInput) {
        return new TagsContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
