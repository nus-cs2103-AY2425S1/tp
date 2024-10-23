package tuteez.model.remark;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tuteez.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class RemarkListTest {
    @Test
    public void constructor_emptyList_createsEmptyRemarkList() {
        RemarkList remarkList = new RemarkList();
        assertTrue(remarkList.getRemarks().isEmpty());
    }

    @Test
    public void constructor_withList_createsRemarkListWithRemarks() {
        List<Remark> remarks = Arrays.asList(new Remark("First"), new Remark("Second"));
        RemarkList remarkList = new RemarkList(remarks);
        assertEquals(remarks, remarkList.getRemarks());
    }

    @Test
    public void addRemark_validRemark_addsRemark() {
        RemarkList remarkList = new RemarkList();
        Remark remark = new Remark("Test remark");
        remarkList.addRemark(remark);
        assertTrue(remarkList.getRemarks().contains(remark));
    }

    @Test
    public void deleteRemark_validIndex_removesRemark() {
        RemarkList remarkList = new RemarkList(Arrays.asList(new Remark("First"), new Remark("Second")));
        remarkList.deleteRemark(1);
        assertEquals(1, remarkList.getSize());
    }

    @Test
    public void getSize_returnsCorrectSize() {
        RemarkList remarkList = new RemarkList(Arrays.asList(new Remark("First"), new Remark("Second")));
        assertEquals(2, remarkList.getSize());
    }

    @Test
    void deleteRemark_invalidIndex_throwsIndexOutOfBoundsException() {
        RemarkList remarkList = new RemarkList();
        assertThrows(IndexOutOfBoundsException.class, () -> remarkList.deleteRemark(0));
    }

    @Test
    void getSize_emptyList_returnsZero() {
        RemarkList remarkList = new RemarkList();
        assertTrue(remarkList.getSize() == 0);
    }

    @Test
    void getSize_nonEmptyList_returnsCorrectSize() {
        RemarkList remarkList = new RemarkList();
        remarkList.addRemark(new Remark("First remark"));
        remarkList.addRemark(new Remark("Second remark"));
        assertTrue(remarkList.getSize() == 2);
    }

    @Test
    void equals_sameRemarkList_returnsTrue() {
        RemarkList list1 = new RemarkList();
        list1.addRemark(new Remark("Remark 1"));

        RemarkList list2 = new RemarkList();
        list2.addRemark(new Remark("Remark 1"));

        assertTrue(list1.equals(list2));
    }

    @Test
    void equals_differentRemarkList_returnsFalse() {
        RemarkList list1 = new RemarkList();
        list1.addRemark(new Remark("Remark 1"));

        RemarkList list2 = new RemarkList();
        list2.addRemark(new Remark("Remark 2"));

        assertFalse(list1.equals(list2));
    }

    @Test
    void hashCode_sameRemarkList_returnsSameHashCode() {
        RemarkList list1 = new RemarkList();
        list1.addRemark(new Remark("Remark 1"));

        RemarkList list2 = new RemarkList();
        list2.addRemark(new Remark("Remark 1"));

        assertTrue(list1.hashCode() == list2.hashCode());
    }

    @Test
    void hashCode_differentRemarkList_returnsDifferentHashCode() {
        RemarkList list1 = new RemarkList();
        list1.addRemark(new Remark("Remark 1"));

        RemarkList list2 = new RemarkList();
        list2.addRemark(new Remark("Remark 2"));

        assertFalse(list1.hashCode() == list2.hashCode());
    }
}
