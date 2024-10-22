package tuteez.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import tuteez.commons.exceptions.IllegalValueException;
import tuteez.model.remark.Remark;

public class JsonAdaptedRemark {

    private final String remark;

    @JsonCreator
    public JsonAdaptedRemark(@JsonProperty("remark") String remark) {
        this.remark = remark != null ? remark : "";
    }

    public JsonAdaptedRemark(Remark source) {
        this.remark = source.getRemark();
    }

    public Remark toModelType() throws IllegalValueException {
        if (remark == null || remark.isEmpty()) {
            throw new IllegalValueException("Remark text is missing");
        }
        return new Remark(remark);
    }
}
