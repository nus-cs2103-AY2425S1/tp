package hallpointer.address.testutil;

import hallpointer.address.model.point.Point;
import hallpointer.address.model.session.Session;
import hallpointer.address.model.session.SessionDate;
import hallpointer.address.model.session.SessionName;

/**
 * A utility class to help with building Session objects.
 */
public class SessionBuilder {
    public static final String DEFAULT_SESSION_NAME = "Swimming Lesson";
    public static final String DEFAULT_DATE = "10 Oct 2023";
    public static final String DEFAULT_POINTS = "3";

    private SessionName sessionName;
    private SessionDate date;
    private Point points;

    /**
     * Creates a {@code SessionBuilder} with the default details.
     */
    public SessionBuilder() {
        sessionName = new SessionName(DEFAULT_SESSION_NAME);
        date = new SessionDate(DEFAULT_DATE);
        points = new Point(DEFAULT_POINTS);
    }

    /**
     * Initializes the SessionBuilder with the data of {@code sessionToCopy}.
     */
    public SessionBuilder(Session sessionToCopy) {
        sessionName = sessionToCopy.getSessionName();
        date = sessionToCopy.getDate();
        points = sessionToCopy.getPoints();
    }

    /**
     * Sets the {@code SessionName} of the {@code Session} that we are building.
     */
    public SessionBuilder withSessionName(String name) {
        this.sessionName = new SessionName(name);
        return this;
    }

    /**
     * Sets the {@code SessionDate} of the {@code Session} that we are building.
     */
    public SessionBuilder withDate(String date) {
        this.date = new SessionDate(date);
        return this;
    }

    /**
     * Sets the {@code Point} of the {@code Session} that we are building.
     */
    public SessionBuilder withPoints(String points) {
        this.points = new Point(points);
        return this;
    }

    public Session build() {
        return new Session(sessionName, date, points);
    }
}
