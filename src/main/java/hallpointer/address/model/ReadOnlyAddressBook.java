package hallpointer.address.model;

import hallpointer.address.model.member.Member;
import hallpointer.address.model.session.Session;
import javafx.collections.ObservableList;

/**
 * Unmodifiable view of an address book.
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the members list.
     * This list will not contain any duplicate members.
     */
    ObservableList<Member> getMemberList();

    ObservableList<Session> getSessionList();

}
