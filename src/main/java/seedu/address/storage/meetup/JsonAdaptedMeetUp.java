package seedu.address.storage.meetup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.meetup.AddedBuyer;
import seedu.address.model.meetup.From;
import seedu.address.model.meetup.Info;
import seedu.address.model.meetup.MeetUp;
import seedu.address.model.meetup.Name;
import seedu.address.model.meetup.To;

/**
 * Jackson-friendly version of {@link MeetUp}.
 */
public class JsonAdaptedMeetUp {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Meet up's %s field is missing!";

    private final String name;
    private final String info;
    private final String from;
    private final String to;
    private final List<JsonAdaptedAddedBuyer> addedBuyers = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedMeetUp} with the given meet up details.
     */
    @JsonCreator
    public JsonAdaptedMeetUp(@JsonProperty("name") String name, @JsonProperty("info") String info,
                             @JsonProperty("from") String from, @JsonProperty("to") String to,
                             @JsonProperty("addedBuyers") List<JsonAdaptedAddedBuyer> addedBuyers) {

        this.name = name;
        this.info = info;
        this.from = from;
        this.to = to;
        if (addedBuyers != null) {
            this.addedBuyers.addAll(addedBuyers);
        }
    }

    /**
     * Converts a given {@code MeetUp} into this class for Jackson use.
     */
    public JsonAdaptedMeetUp(MeetUp source) {
        name = source.getName().toString();
        info = source.getInfo().toString();
        from = source.getFrom().toString();
        to = source.getTo().toString();
        addedBuyers.addAll(source.getAddedBuyers().stream()
                .map(JsonAdaptedAddedBuyer::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted buyer object into the model's {@code MeetUp} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted meet up.
     */
    public MeetUp toModelType() throws IllegalValueException {
        final List<AddedBuyer> meetUpAddedBuyers = new ArrayList<>();
        for (JsonAdaptedAddedBuyer addedBuyer : addedBuyers) {
            meetUpAddedBuyers.add(addedBuyer.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (info == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Info.class.getSimpleName()));
        }
        if (!Info.isValidInfo(info)) {
            throw new IllegalValueException(Info.MESSAGE_CONSTRAINTS);
        }
        final Info modelInfo = new Info(info);

        if (from == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    From.class.getSimpleName()));
        }
        if (!From.isValidFrom(from)) {
            throw new IllegalValueException(From.MESSAGE_CONSTRAINTS);
        }
        final From modelFrom = new From(from);

        if (to == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    To.class.getSimpleName()));
        }
        if (!To.isValidTo(to)) {
            throw new IllegalValueException(To.MESSAGE_CONSTRAINTS);
        }
        final To modelTo = new To(to);

        final Set<AddedBuyer> modelAddedBuyers = new HashSet<>(meetUpAddedBuyers);
        return new MeetUp(modelName, modelInfo, modelFrom, modelTo, modelAddedBuyers);
    }
}
