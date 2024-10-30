package seedu.address.testutil;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.wedding.Wedding;

/**
 * A Model stub that always accept the wedding being added.
 */
public class ModelStubAcceptingWeddingAdded extends ModelStub {
    private final ArrayList<Wedding> weddingsAdded = new ArrayList<>();

    @Override
    public boolean hasWedding(Wedding wedding) {
        return weddingsAdded.stream().anyMatch(wedding::isSameWedding);
    }

    @Override
    public void addWedding(Wedding wedding) {
        weddingsAdded.add(wedding);
    }

    @Override
    public ObservableList<Wedding> getFilteredWeddingList() {
        return FXCollections.observableArrayList(weddingsAdded);
    }

    @Override
    public void setWedding(Wedding target, Wedding editedWedding) {
        int index = weddingsAdded.indexOf(target);
        if (index != -1) {
            weddingsAdded.set(index, editedWedding);
        }
    }
}

