package hallpointer.address.model;

import hallpointer.address.model.member.Member;
import javafx.collections.ObservableList;

/**
 * Unmodifiable view of an hall pointer.
 */
public interface ReadOnlyHallPointer {

    /**
     * Returns an unmodifiable view of the members list.
     * This list will not contain any duplicate members.
     */
    ObservableList<Member> getMemberList();
}
