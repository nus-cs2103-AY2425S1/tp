package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_SUPPLIER_SORTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.SortOrder;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.SupplierSortComparator;
import seedu.address.model.person.SupplierSortNameComparator;
import seedu.address.testutil.TypicalPersons;


public class SortSupplierCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    @Test
    public void equals() {
        SupplierSortComparator firstComparator = new SupplierSortNameComparator(new SortOrder("a"));
        SupplierSortComparator secondComparator = new SupplierSortNameComparator(new SortOrder("d"));
        SortSupplierCommand sortFirstSupplierCommand = new SortSupplierCommand(firstComparator);
        SortSupplierCommand sortSecondSupplierCommand = new SortSupplierCommand(secondComparator);
        // same object -> returns true
        assertTrue(sortFirstSupplierCommand.equals(sortFirstSupplierCommand));
        // same values -> returns true
        SortSupplierCommand sortFirstSupplierCommandCopy = new SortSupplierCommand(firstComparator);
        assertTrue(sortFirstSupplierCommand.equals(sortFirstSupplierCommandCopy));
        // different types -> returns false
        assertFalse(sortFirstSupplierCommand.equals(1));
        // null -> returns false
        assertFalse(sortFirstSupplierCommand.equals(null));
        // different command -> returns false
        assertFalse(sortFirstSupplierCommand.equals(sortSecondSupplierCommand));
    }
    @Test
    public void execute_ascendingName_sortedByAscendingName() {
        String expectedMessage = String.format(MESSAGE_SUPPLIER_SORTED_OVERVIEW, 7, "name", "ascending");
        SupplierSortComparator comparator = new SupplierSortNameComparator(new SortOrder("a"));
        SortSupplierCommand command = new SortSupplierCommand(comparator);
        expectedModel.updateSortedSupplierList(comparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(TypicalPersons.getTypicalPersons(), model.getSortedSupplierList());
    }
    @Test
    public void execute_descendingName_sortedByDescendingName() {
        String expectedMessage = String.format(MESSAGE_SUPPLIER_SORTED_OVERVIEW, 7, "name", "descending");
        SupplierSortComparator comparator = new SupplierSortNameComparator(new SortOrder("d"));
        SortSupplierCommand command = new SortSupplierCommand(comparator);
        expectedModel.updateSortedSupplierList(comparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(new ArrayList<>(Arrays.asList(GEORGE, FIONA, ELLE, DANIEL, CARL, BENSON, ALICE)),
                model.getSortedSupplierList());
    }

}

