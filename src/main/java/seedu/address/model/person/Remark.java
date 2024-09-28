package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
public class Remark {

    public final String value;

    public Remark(String remark) {
        requireNonNull(remark);
        this.value = remark;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object otherObj) {
        if (otherObj == this) {
            return true;
        } else if (!(otherObj instanceof Remark)) {
            return false;
        }
        Remark typeCastedOtherObj = (Remark) otherObj;
        return value.equals((typeCastedOtherObj.value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
