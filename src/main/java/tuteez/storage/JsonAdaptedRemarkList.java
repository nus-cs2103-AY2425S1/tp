package tuteez.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import tuteez.commons.exceptions.IllegalValueException;
import tuteez.model.remark.Remark;
import tuteez.model.remark.RemarkList;

/**
 * Jackson-friendly version of {@link RemarkList}.
 */
public class JsonAdaptedRemarkList {
    private final List<String> remarks;

    /**
     * Constructs a {@code JsonAdaptedRemarkList} with the given {@code remarks}.
     */
    @JsonCreator
    public JsonAdaptedRemarkList(@JsonProperty("remarks") List<String> remarks) {
        this.remarks = remarks != null ? remarks : new ArrayList<>();
    }

    /**
     * Constructs a {@code JsonAdaptedRemarkList} from the model's {@code RemarkList}.
     */
    public JsonAdaptedRemarkList(RemarkList remarkList) {
        this.remarks = remarkList.getRemarks().stream()
                .map(Remark::getRemark)
                .collect(Collectors.toList());
    }

    /**
     * Converts this Jackson-friendly adapted remark list object into the model's {@code RemarkList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted remark list.
     */
    public RemarkList toModelType() throws IllegalValueException {
        List<Remark> modelRemarks = new ArrayList<>();
        for (String remark : remarks) {
            if (remark == null || remark.trim().isEmpty()) {
                throw new IllegalValueException("Remark text cannot be empty");
            }
            modelRemarks.add(new Remark(remark));
        }
        return new RemarkList(modelRemarks);
    }
}
