package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_SUPPLIERS_FOUND_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalSuppliers.ALICE;
import static seedu.address.testutil.TypicalSuppliers.GEORGE;
import static seedu.address.testutil.TypicalSuppliers.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.supplier.Supplier;
import seedu.address.model.supplier.predicates.CompanyContainsKeywordPredicate;
import seedu.address.model.supplier.predicates.NameContainsPredicate;


public class FindSupplierCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        List<Predicate<Supplier>> namePred = List.of(new NameContainsPredicate("Linkes"));
        List<Predicate<Supplier>> companyPred = List.of(new CompanyContainsKeywordPredicate("NUS"));

        FindSupplierCommand findUsingNameCommand = new FindSupplierCommand(namePred);
        FindSupplierCommand findUsingCompanyCommand = new FindSupplierCommand(companyPred);

        // same object -> returns true
        assertTrue(findUsingNameCommand.equals(findUsingNameCommand));

        // same values -> returns true
        FindSupplierCommand findUsingNameCommandCopy = new FindSupplierCommand(namePred);
        assertTrue(findUsingNameCommand.equals(findUsingNameCommandCopy));

        // different types -> returns false
        assertFalse(findUsingNameCommand.equals(1));

        // null -> returns false
        assertFalse(findUsingNameCommand.equals(null));

        // different predicates -> returns false
        assertFalse(findUsingNameCommand.equals(findUsingCompanyCommand));
    }

    @Test
    public void execute_noSuppliersFound() {
        String expectedMessage = String.format(MESSAGE_SUPPLIERS_FOUND_OVERVIEW, 0);

        Predicate<Supplier> predicates = new NameContainsPredicate("$$$$$$$$$$$$$$$$$");

        FindSupplierCommand command = new FindSupplierCommand(List.of(predicates));
        expectedModel.updateFilteredSupplierList(predicates);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredSupplierList());
    }

    @Test
    public void execute_multipleKeywords_multipleSuppliersFound() {
        String expectedMessage = String.format(MESSAGE_SUPPLIERS_FOUND_OVERVIEW, 2);

        Predicate<Supplier> namePredicate = new NameContainsPredicate("Pauline");
        Predicate<Supplier> companyPredicate = new CompanyContainsKeywordPredicate("company A");

        List<Predicate<Supplier>> predicates = Arrays.asList(namePredicate, companyPredicate);

        FindSupplierCommand command = new FindSupplierCommand(predicates);
        expectedModel.updateFilteredSupplierList(namePredicate.and(companyPredicate));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, GEORGE), model.getFilteredSupplierList());
    }
    @Test
    public void toStringMethod() {
        List<Predicate<Supplier>> predicates = Arrays.asList(new NameContainsPredicate("testing 1 2 3"));
        FindSupplierCommand findCommand = new FindSupplierCommand(predicates);

        String expected = FindSupplierCommand.class.getCanonicalName() + "{predicates=" + predicates + "}";
        assertEquals(expected, findCommand.toString());
    }


}
