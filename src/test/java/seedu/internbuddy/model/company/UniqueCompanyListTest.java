package seedu.internbuddy.model.company;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.internbuddy.logic.commands.CommandTestUtilCompany.VALID_ADDRESS_GOOGLE;
import static seedu.internbuddy.logic.commands.CommandTestUtilCompany.VALID_TAG_TECH;
import static seedu.internbuddy.testutil.Assert.assertThrows;
import static seedu.internbuddy.testutil.TypicalCompanies.GOOGLE;
import static seedu.internbuddy.testutil.TypicalCompanies.MICROSOFT;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.internbuddy.model.company.exceptions.CompanyNotFoundException;
import seedu.internbuddy.model.company.exceptions.DuplicateCompanyException;
import seedu.internbuddy.testutil.CompanyBuilder;

public class UniqueCompanyListTest {

    private final UniqueCompanyList uniqueCompanyList = new UniqueCompanyList();

    @Test
    public void contains_nullCompany_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCompanyList.contains(null));
    }

    @Test
    public void contains_companyNotInList_returnsFalse() {
        assertFalse(uniqueCompanyList.contains(GOOGLE));
    }

    @Test
    public void contains_companyInList_returnsTrue() {
        uniqueCompanyList.add(GOOGLE);
        assertTrue(uniqueCompanyList.contains(GOOGLE));
    }

    @Test
    public void contains_companyWithSameIdentityFieldsInList_returnsTrue() {
        uniqueCompanyList.add(GOOGLE);
        Company editedGoogle = new CompanyBuilder(GOOGLE).withAddress(VALID_ADDRESS_GOOGLE).withTags(VALID_TAG_TECH)
                .build();
        assertTrue(uniqueCompanyList.contains(editedGoogle));
    }

    @Test
    public void add_nullCompany_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCompanyList.add(null));
    }

    @Test
    public void add_duplicateCompany_throwsDuplicateCompanyException() {
        uniqueCompanyList.add(GOOGLE);
        assertThrows(DuplicateCompanyException.class, () -> uniqueCompanyList.add(GOOGLE));
    }

    @Test
    public void setCompany_nullTargetCompany_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCompanyList.setCompany(null, GOOGLE));
    }

    @Test
    public void setCompany_nullEditedCompany_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCompanyList.setCompany(GOOGLE, null));
    }

    @Test
    public void setCompany_targetCompanyNotInList_throwsCompanyNotFoundException() {
        assertThrows(CompanyNotFoundException.class, () -> uniqueCompanyList.setCompany(GOOGLE, GOOGLE));
    }

    @Test
    public void setCompany_editedCompanyIsSameCompany_success() {
        uniqueCompanyList.add(GOOGLE);
        uniqueCompanyList.setCompany(GOOGLE, GOOGLE);
        UniqueCompanyList expectedUniqueCompanyList = new UniqueCompanyList();
        expectedUniqueCompanyList.add(GOOGLE);
        assertEquals(expectedUniqueCompanyList, uniqueCompanyList);
    }

    @Test
    public void setCompany_editedCompanyHasSameIdentity_success() {
        uniqueCompanyList.add(GOOGLE);
        Company editedGoogle = new CompanyBuilder(GOOGLE).withAddress(VALID_ADDRESS_GOOGLE).withTags(VALID_TAG_TECH)
                .build();
        uniqueCompanyList.setCompany(GOOGLE, editedGoogle);
        UniqueCompanyList expectedUniqueCompanyList = new UniqueCompanyList();
        expectedUniqueCompanyList.add(editedGoogle);
        assertEquals(expectedUniqueCompanyList, uniqueCompanyList);
    }

    @Test
    public void setCompany_editedCompanyHasDifferentIdentity_success() {
        uniqueCompanyList.add(GOOGLE);
        uniqueCompanyList.setCompany(GOOGLE, MICROSOFT);
        UniqueCompanyList expectedUniqueCompanyList = new UniqueCompanyList();
        expectedUniqueCompanyList.add(MICROSOFT);
        assertEquals(expectedUniqueCompanyList, uniqueCompanyList);
    }

    @Test
    public void setCompany_editedCompanyHasNonUniqueIdentity_throwsDuplicateCompanyException() {
        uniqueCompanyList.add(GOOGLE);
        uniqueCompanyList.add(MICROSOFT);
        assertThrows(DuplicateCompanyException.class, () -> uniqueCompanyList.setCompany(GOOGLE, MICROSOFT));
    }

    @Test
    public void remove_nullCompany_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCompanyList.remove(null));
    }

    @Test
    public void remove_companyDoesNotExist_throwsCompanyNotFoundException() {
        assertThrows(CompanyNotFoundException.class, () -> uniqueCompanyList.remove(GOOGLE));
    }

    @Test
    public void remove_existingCompany_removesCompany() {
        uniqueCompanyList.add(GOOGLE);
        uniqueCompanyList.remove(GOOGLE);
        UniqueCompanyList expectedUniqueCompanyList = new UniqueCompanyList();
        assertEquals(expectedUniqueCompanyList, uniqueCompanyList);
    }

    @Test
    public void setCompanies_nullUniqueCompanyList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCompanyList.setCompanies((UniqueCompanyList) null));
    }

    @Test
    public void setCompanies_uniqueCompanyList_replacesOwnListWithProvidedUniqueCompanyList() {
        uniqueCompanyList.add(GOOGLE);
        UniqueCompanyList expectedUniqueCompanyList = new UniqueCompanyList();
        expectedUniqueCompanyList.add(MICROSOFT);
        uniqueCompanyList.setCompanies(expectedUniqueCompanyList);
        assertEquals(expectedUniqueCompanyList, uniqueCompanyList);
    }

    @Test
    public void setCompanies_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCompanyList.setCompanies((List<Company>) null));
    }

    @Test
    public void setCompanies_list_replacesOwnListWithProvidedList() {
        uniqueCompanyList.add(GOOGLE);
        List<Company> companyList = Collections.singletonList(MICROSOFT);
        uniqueCompanyList.setCompanies(companyList);
        UniqueCompanyList expectedUniqueCompanyList = new UniqueCompanyList();
        expectedUniqueCompanyList.add(MICROSOFT);
        assertEquals(expectedUniqueCompanyList, uniqueCompanyList);
    }

    @Test
    public void setCompanies_listWithDuplicateCompanies_throwsDuplicateCompanyException() {
        List<Company> listWithDuplicateCompanies = Arrays.asList(GOOGLE, GOOGLE);
        assertThrows(DuplicateCompanyException.class, () -> uniqueCompanyList.setCompanies(listWithDuplicateCompanies));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueCompanyList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueCompanyList.asUnmodifiableObservableList().toString(), uniqueCompanyList.toString());
    }
}
