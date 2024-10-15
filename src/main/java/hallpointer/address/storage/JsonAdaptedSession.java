package hallpointer.address.storage;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import hallpointer.address.commons.exceptions.IllegalValueException;
import hallpointer.address.model.point.Point;
import hallpointer.address.model.session.Session;
import hallpointer.address.model.session.SessionDate;
import hallpointer.address.model.session.SessionName;

/**
 * Jackson-friendly version of {@link Session}.
 */
public class JsonAdaptedSession {
    private final String sessionName;
    private final int points;
    private final String date;

    /**
     * Constructs a {@code JsonAdaptedSession} with the given session details.
     *
     * @param sessionName The name of the session.
     * @param points The points associated with the session.
     * @param date The date of the session.
     */
    @JsonCreator
    public JsonAdaptedSession(@JsonProperty("sessionName") String sessionName,
                              @JsonProperty("points") int points,
                              @JsonProperty("date") String date) {
        this.sessionName = sessionName;
        this.points = points;
        this.date = date;
    }

    /**
     * Converts a given {@code Session} into this class for Jackson use.
     *
     * @param source The session to be converted into a {@code JsonAdaptedSession}.
     */
    public JsonAdaptedSession(Session source) {
        sessionName = source.getSessionName().toString();
        points = source.getPoints().getValue();
        date = source.getDate().toString();
    }

    /**
     * Converts this Jackson-friendly adapted session object into the model's {@code Session} object.
     *
     * @return The model-type session corresponding to this JSON-adapted session.
     * @throws IllegalValueException If any data constraints are violated in the adapted session.
     */
    public Session toModelType() throws IllegalValueException {
        return new Session(new SessionName(sessionName), new SessionDate(date), new Point(points));
    }
}
