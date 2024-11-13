package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalGoods.getTypicalGoodsReceipts;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.goods.GoodsCategories;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalGoodsReceipts());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalGoodsReceipts());

    @Test
    public void equals() {

        List<String> firstKeywords = List.of("Hello", "World");
        Set<GoodsCategories> firstCategories = Set.of(GoodsCategories.CONSUMABLES, GoodsCategories.SPECIALTY);
        List<String> secondKeywords = List.of("World", "Hello");
        Set<GoodsCategories> secondCategories = Set.of(GoodsCategories.CONSUMABLES);

        FindCommand firstFindCommand = new FindCommand(firstKeywords, firstCategories);

        // same object -> returns true
        assertTrue(firstFindCommand.equals(firstFindCommand));

        // different types -> returns false
        assertFalse(firstFindCommand.equals(1));

        // null -> returns false
        assertFalse(firstFindCommand.equals(null));

        // same values -> returns true
        assertTrue(new FindCommand(firstKeywords, firstCategories)
                .equals(new FindCommand(firstKeywords, firstCategories)));

        // different values -> returns false
        assertFalse(new FindCommand(firstKeywords, firstCategories)
                .equals(new FindCommand(firstKeywords, secondCategories)));
        assertFalse(new FindCommand(firstKeywords, firstCategories)
                .equals(new FindCommand(secondKeywords, firstCategories)));
    }

    @Test
    public void execute_zeroKeywordsNoCategories_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FindCommand command = new FindCommand(List.of(), Set.of());
        expectedModel.updateFilteredPersonList(p -> false);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywordsNoCategories_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        List<String> keywords = List.of("Kurz", "Elle", "Kunz");
        FindCommand command = new FindCommand(keywords, Set.of());
        expectedModel.updateFilteredPersonList(new NameContainsKeywordsPredicate(keywords));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void execute_withCategory_multiplePersonsFound() {
        NameContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");

        Function<GoodsCategories, Predicate<Person>> hasCategory = goodsCategory -> person -> model
                .getGoods()
                .getReceiptList()
                .stream()
                .filter(goodsReceipt -> goodsReceipt.isFromSupplier(person.getName()))
                .anyMatch(goodsReceipt -> goodsReceipt
                        .getGoods()
                        .category()
                        .equals(goodsCategory));

        for (GoodsCategories category : GoodsCategories.values()) {
            FindCommand command = new FindCommand(List.of(), Set.of(category));
            expectedModel.updateFilteredPersonList(hasCategory.apply(category));
            String expectedMessage = String.format(
                    MESSAGE_PERSONS_LISTED_OVERVIEW, expectedModel.getFilteredPersonList().size());
            assertCommandSuccess(command, model, expectedMessage, expectedModel);
            assertEquals(model.getFilteredPersonList(), expectedModel.getFilteredPersonList());
        }
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword");
        FindCommand findCommand = new FindCommand(keywords);
        String expected = FindCommand.class.getCanonicalName()
                + "{keywords=" + keywords + ", categoriesSet=" + Set.of() + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
