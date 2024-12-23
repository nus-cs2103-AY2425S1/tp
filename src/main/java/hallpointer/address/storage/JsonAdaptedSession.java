package hallpointer.address.storage;

import static hallpointer.address.storage.JsonAdaptedMember.MISSING_FIELD_MESSAGE_FORMAT;

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
    private final String date;
    private final JsonAdaptedPoint points;

    /**
     * Constructs a {@code JsonAdaptedSession} with the given session details.
     *
     * @param sessionName The name of the session.
     * @param points The points associated with the session.
     * @param date The date of the session.
     */
    @JsonCreator
    public JsonAdaptedSession(@JsonProperty("sessionName") String sessionName,
                              @JsonProperty("date") String date,
                              @JsonProperty("points") JsonAdaptedPoint points) {
        this.sessionName = sessionName;
        this.date = date;
        this.points = points;
    }

    /**
     * Converts a given {@code Session} into this class for Jackson use.
     *
     * @param source The session to be converted into a {@code JsonAdaptedSession}.
     */
    public JsonAdaptedSession(Session source) {
        sessionName = source.getSessionName().toString();
        date = source.getDate().toString();
        points = new JsonAdaptedPoint(source.getPoints());
    }

    /**
     * Converts this Jackson-friendly adapted session object into the model's {@code Session} object.
     *
     * @return The model-type session corresponding to this JSON-adapted session.
     * @throws IllegalValueException If any data constraints are violated in the adapted session.
     */
    public Session toModelType() throws IllegalValueException {
        if (sessionName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    SessionName.class.getSimpleName()));
        }
        if (!SessionName.isValidSessionName(sessionName)) {
            throw new IllegalValueException(SessionName.MESSAGE_CONSTRAINTS);
        }
        final SessionName modelName = new SessionName(sessionName);

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    SessionDate.class.getSimpleName()));
        }
        if (!SessionDate.isValidDate(date)) {
            throw new IllegalValueException(SessionDate.MESSAGE_CONSTRAINTS);
        }
        final SessionDate modelDate = new SessionDate(date);

        if (points == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Point.class.getSimpleName()));
        }
        final Point modelPoints = points.toModelType();

        Session session = new Session(modelName, modelDate, modelPoints);

        return session;
    }
}
