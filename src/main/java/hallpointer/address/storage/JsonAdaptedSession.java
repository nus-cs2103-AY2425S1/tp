package hallpointer.address.storage;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import hallpointer.address.commons.exceptions.IllegalValueException;
import hallpointer.address.model.session.Date;
import hallpointer.address.model.session.Session;
import hallpointer.address.model.member.Member;
import hallpointer.address.model.session.SessionName;

public class JsonAdaptedSession {
    private final String sessionName;
    private final int points;
    private final String date;
    private final Set<JsonAdaptedMember> members = new HashSet<>();

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

    public JsonAdaptedSession(Session source) {
        sessionName = source.getSessionName().toString();
        points = source.getPoints();
        date = source.getDate().toString();
        source.getMembers().forEach(member -> members.add(new JsonAdaptedMember(member)));
    }

    public Session toModelType() throws IllegalValueException {
        Set<Member> modelMembers = new HashSet<>();
        for (JsonAdaptedMember member : members) {
            modelMembers.add(member.toModelType());
        }
        return new Session(new SessionName(sessionName), new Date(date), points, modelMembers);
    }
}
