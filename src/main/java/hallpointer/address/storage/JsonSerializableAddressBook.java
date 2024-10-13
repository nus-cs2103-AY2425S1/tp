package hallpointer.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import hallpointer.address.commons.exceptions.IllegalValueException;
import hallpointer.address.model.AddressBook;
import hallpointer.address.model.ReadOnlyAddressBook;
import hallpointer.address.model.member.Member;
import hallpointer.address.model.session.Session;


/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_MEMBER = "Members list contains duplicate member(s).";
    private static final String MESSAGE_DUPLICATE_SESSION = "Session list contains duplicate session(s).";

    private final List<JsonAdaptedMember> members = new ArrayList<>();
    private final List<JsonAdaptedSession> sessions = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given members.
     */
    @JsonCreator
    public JsonSerializableAddressBook(
            @JsonProperty("members") List<JsonAdaptedMember> members,
            @JsonProperty("sessions") List<JsonAdaptedSession> sessions) {
        if (members != null) {
            this.members.addAll(members);
        }
        if (sessions != null) {
            this.sessions.addAll(sessions);
        }
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        members.addAll(source.getMemberList().stream().map(JsonAdaptedMember::new).collect(Collectors.toList()));
        sessions.addAll(source.getSessionList().stream().map(JsonAdaptedSession::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedMember jsonAdaptedMember : members) {
            Member member = jsonAdaptedMember.toModelType();
            if (addressBook.hasMembers(member)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MEMBER);
            }
            addressBook.addMember(member);
        }
        for (JsonAdaptedSession jsonAdaptedSession : sessions) {
            Session session = jsonAdaptedSession.toModelType();
            if (addressBook.hasSessions(session)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_SESSION);
            }
            addressBook.addSession(session);
        }
        return addressBook;
    }

}
