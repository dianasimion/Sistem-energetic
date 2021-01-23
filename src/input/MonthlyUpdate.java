package input;

import java.util.ArrayList;
import java.util.List;

/**
 * update-uri lunare
 */
public class MonthlyUpdate {
    private final List<Consumer> newConsumers;
    private final List<DistributorChange> distributorChanges;
    private final List<ProducerChange> producerChanges;

    public MonthlyUpdate() {
        newConsumers = new ArrayList<>();
        distributorChanges = new ArrayList<>();
        producerChanges = new ArrayList<>();
    }

    public final List<Consumer> getNewConsumers() {
        return newConsumers;
    }

    public final List<DistributorChange> getDistributorChanges() {
        return distributorChanges;
    }

    public final List<ProducerChange> getProducerChanges() {
        return producerChanges;
    }
}
