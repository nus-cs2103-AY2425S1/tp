package hallpointer.address.storage;

import static hallpointer.address.storage.JsonAdaptedMember.MISSING_FIELD_MESSAGE_FORMAT;
import static hallpointer.address.testutil.Assert.assertThrows;
import static hallpointer.address.testutil.TypicalMembers.GEORGE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import hallpointer.address.commons.exceptions.IllegalValueException;
import hallpointer.address.model.member.Name;
import hallpointer.address.model.member.Room;
import hallpointer.address.model.member.Telegram;

public class JsonAdaptedMemberTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_TELEGRAM = "+651234";
    private static final String INVALID_ROOM = " 222";
    private static final String INVALID_TAG = "#friend";

    // Valid values
    private static final String VALID_NAME = GEORGE.getName().toString();
    private static final String VALID_TELEGRAM = GEORGE.getTelegram().toString();
    private static final String VALID_ROOM = GEORGE.getRoom().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = GEORGE.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedSession> VALID_SESSIONS = GEORGE.getSessions().stream()
            .map(JsonAdaptedSession::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validMemberDetails_returnsMember() throws Exception {
        JsonAdaptedMember member = new JsonAdaptedMember(GEORGE);
        assertEquals(GEORGE, member.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedMember member =
                new JsonAdaptedMember(INVALID_NAME, VALID_TELEGRAM,
                    VALID_ROOM, VALID_TAGS, VALID_SESSIONS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, member::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedMember member = new JsonAdaptedMember(null, VALID_TELEGRAM,
                                        VALID_ROOM, VALID_TAGS, VALID_SESSIONS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, member::toModelType);
    }

    @Test
    public void toModelType_invalidTelegram_throwsIllegalValueException() {
        JsonAdaptedMember member =
                new JsonAdaptedMember(VALID_NAME, INVALID_TELEGRAM,
                        VALID_ROOM, VALID_TAGS, VALID_SESSIONS);
        String expectedMessage = Telegram.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, member::toModelType);
    }

    @Test
    public void toModelType_nullTelegram_throwsIllegalValueException() {
        JsonAdaptedMember member = new JsonAdaptedMember(VALID_NAME, null,
                                        VALID_ROOM, VALID_TAGS, VALID_SESSIONS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Telegram.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, member::toModelType);
    }

    @Test
    public void toModelType_invalidRoom_throwsIllegalValueException() {
        JsonAdaptedMember member =
                new JsonAdaptedMember(VALID_NAME, VALID_TELEGRAM,
                    INVALID_ROOM, VALID_TAGS, VALID_SESSIONS);
        String expectedMessage = Room.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, member::toModelType);
    }

    @Test
    public void toModelType_nullRoom_throwsIllegalValueException() {
        JsonAdaptedMember member = new JsonAdaptedMember(VALID_NAME, VALID_TELEGRAM,
                                    null, VALID_TAGS, VALID_SESSIONS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Room.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, member::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedMember member =
                new JsonAdaptedMember(VALID_NAME, VALID_TELEGRAM,
                    VALID_ROOM, invalidTags, VALID_SESSIONS);
        assertThrows(IllegalValueException.class, member::toModelType);
    }

    @Test
    public void toModelType_validSessions_returnsSessions() throws Exception {
        JsonAdaptedMember member =
                new JsonAdaptedMember(VALID_NAME, VALID_TELEGRAM,
                    VALID_ROOM, VALID_TAGS, VALID_SESSIONS);
        assertEquals(VALID_SESSIONS.size(), member.toModelType().getSessions().size());
    }
}
