package hallpointer.address.storage;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import hallpointer.address.commons.exceptions.IllegalValueException;
import hallpointer.address.model.member.Member;
import hallpointer.address.model.session.Date;
import hallpointer.address.model.session.Session;
import hallpointer.address.model.session.SessionName;

/**
 * Jackson-friendly version of {@link Session}.
 */
public class JsonAdaptedSession {
    private final String sessionName;
    private final int points;
    private final String date;
    private final Set<JsonAdaptedMember> members = new HashSet<>();

    /**
     * Constructs a {@code JsonAdaptedSession} with the given session details.
     *
     * @param sessionName The name of the session.
     * @param points The points associated with the session.
     * @param date The date of the session.
     * @param members The members attending the session.
     */
    @JsonCreator
    public JsonAdaptedSession(@JsonProperty("sessionName") String sessionName,
                              @JsonProperty("points") int points,
                              @JsonProperty("date") String date,
                              @JsonProperty("members") Set<JsonAdaptedMember> members) {
        this.sessionName = sessionName;
        this.points = points;
        this.date = date;
        this.members.addAll(members);
    }

    /**
     * Converts a given {@code Session} into this class for Jackson use.
     *
     * @param source The session to be converted into a {@code JsonAdaptedSession}.
     */
    public JsonAdaptedSession(Session source) {
        sessionName = source.getSessionName().toString();
        points = source.getPoints();
        date = source.getDate().toString();
        source.getMembers().forEach(member -> members.add(new JsonAdaptedMember(member)));
    }

    /**
     * Converts this Jackson-friendly adapted session object into the model's {@code Session} object.
     *
     * @return The model-type session corresponding to this JSON-adapted session.
     * @throws IllegalValueException If any data constraints are violated in the adapted session.
     */
    public Session toModelType() throws IllegalValueException {
        Set<Member> modelMembers = new HashSet<>();
        for (JsonAdaptedMember member : members) {
            modelMembers.add(member.toModelType());
        }
        return new Session(new SessionName(sessionName), new Date(date), points, modelMembers);
    }
}
