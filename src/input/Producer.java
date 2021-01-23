package input;

import entities.EnergyType;

public class Producer {
    private final int id;
    private final EnergyType energyType;
    private final int maxDistributors;
    private final double priceKW;
    private final int energyPerDistributor;

    public Producer() {
        id = -1;
        energyType = null;
        maxDistributors = 0;
        priceKW = 0;
        energyPerDistributor = 0;
    }

    public final int getId() {
        return id;
    }

    public final EnergyType getEnergyType() {
        return energyType;
    }

    public final int getMaxDistributors() {
        return maxDistributors;
    }

    public final double getPriceKW() {
        return priceKW;
    }

    public final int getEnergyPerDistributor() {
        return energyPerDistributor;
    }
}
