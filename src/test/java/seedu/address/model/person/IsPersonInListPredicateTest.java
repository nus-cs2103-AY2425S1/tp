package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.predicates.IsPersonInListPredicate;


public class IsPersonInListPredicateTest {

    @Test
    public void test_validList() {
        List<Person> filteredList = new ArrayList<>();
        filteredList.add(ALICE);
        filteredList.add(CARL);
        IsPersonInListPredicate isPersonInListPredicate = new IsPersonInListPredicate(filteredList);
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        model.updateFilteredPersonList(isPersonInListPredicate);
        List<Person> actualFilteredList = model.getFilteredPersonList();
        assertEquals(filteredList, actualFilteredList);
    }



    @Test
    public void test_invalidList() {
        List<Person> filteredList = new ArrayList<>();
        filteredList.add(AMY);
        IsPersonInListPredicate isPersonInListPredicate = new IsPersonInListPredicate(filteredList);
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        model.updateFilteredPersonList(isPersonInListPredicate);
        List<Person> actualFilteredList = model.getFilteredPersonList();
        List<Person> emptyList = new ArrayList<>();
        assertEquals(emptyList, actualFilteredList);
    }
}
