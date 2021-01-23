package input;

import java.util.ArrayList;
import java.util.List;

/**
 * informatiile initiale despre consumatori si distribuitori
 */
public class InitialData {
    private final List<Consumer> consumers;
    private final List<Distributor> distributors;
    private final List<Producer> producers;

    public InitialData() {
        consumers = new ArrayList<>();
        distributors = new ArrayList<>();
        producers = new ArrayList<>();
    }

    public final List<Consumer> getConsumers() {
        return consumers;
    }

    public final List<Distributor> getDistributors() {
        return distributors;
    }

    public final  List<Producer> getProducers() {
        return producers;
    }
}
