package input;

public class ProducerChange {
    private final int id;
    private final int energyPerDistributor;

    public ProducerChange() {
        id = -1;
        energyPerDistributor = 0;
    }

    public final int getId() {
        return id;
    }

    public final int getEnergyPerDistributor() {
        return energyPerDistributor;
    }
}

