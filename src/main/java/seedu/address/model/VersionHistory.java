package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class VersionHistory {
    private static final int MAXIMUM_INDEX = 19;
    public List<ReadOnlyAddressBook> versions = new ArrayList<>();
    public int currentVersionIndex = -1;

    public VersionHistory(VersionHistory versionHistory) {
        this.versions = versionHistory.versions;
        this.currentVersionIndex = versionHistory.currentVersionIndex;
    }

    public VersionHistory() {
        versions = new ArrayList<>();
    }

    public VersionHistory addVersion(Model model) {
        ReadOnlyAddressBook dataToSave = new AddressBook().duplicateCopy(model.getAddressBook());
        if (currentVersionIndex == -1) {
            versions.add(dataToSave);
            currentVersionIndex++;
            return this;
        }

        if (currentVersionIndex == MAXIMUM_INDEX) {
            versions.remove(0);
            versions.add(dataToSave);
            return this;
        }

        if (currentVersionIndex < versions.size() - 1) {
            for (int i = currentVersionIndex + 1; i < versions.size(); i++) {
                versions.remove(i);
            }
            // AtomicInteger versionIndex = new AtomicInteger(0);
            // versions.stream().filter(x -> versionIndex.getAndIncrement() <= currentVersionIndex)
            //         .collect(Collectors.toCollection(ArrayList::new));
            versions.add(dataToSave);
            currentVersionIndex++;
            return this;
        }

        versions.add(dataToSave);
        currentVersionIndex++;
        return this;
    }

    public VersionHistory undoVersion() {
        if (currentVersionIndex > 0) {
            currentVersionIndex--;
        }
        return this;
    }

    public VersionHistory redoVersion() {
        if (currentVersionIndex < MAXIMUM_INDEX) {
            currentVersionIndex++;
        }
        return this;
    }
}