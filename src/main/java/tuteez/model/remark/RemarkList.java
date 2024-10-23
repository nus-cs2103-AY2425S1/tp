package tuteez.model.remark;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a RemarkList in the address book.
 */
public class RemarkList {
    private final List<Remark> remarks;

    /**
     * Constructs an empty {@code RemarkList}.
     */
    public RemarkList() {
        remarks = new ArrayList<>();
    }

    /**
     * Constructs a {@code RemarkList}.
     *
     * @param remarks A list of remarks.
     */
    public RemarkList(List<Remark> remarks) {
        this.remarks = new ArrayList<>(remarks);
    }

    /**
     * Adds a {@code Remark} to {@code remarks}.
     *
     * @param remark The remark to be added
     */
    public void addRemark(Remark remark) {
        remarks.add(remark);
    }

    /**
     * Deletes the remark to from the specified {@code index}.
     *
     * @param index The specified index of the remark to be deleted.
     */
    public void deleteRemark(int index) {
        remarks.remove(index);
    }

    /**
     * Returns the number of remarks in the list.
     */
    public int getSize() {
        return remarks.size();
    }

    /**
     * Returns the list of remarks.
     */
    public List<Remark> getRemarks() {
        return Collections.unmodifiableList(remarks);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemarkList // instanceof handles nulls
                && remarks.equals(((RemarkList) other).remarks));
    }

    @Override
    public int hashCode() {
        return remarks.hashCode();
    }
}
