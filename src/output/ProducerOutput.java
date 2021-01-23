package output;

import entities.EnergyType;

import java.util.List;

public class ProducerOutput {
    private final int id;
    private final int maxDistributors;
    private final double priceKW;
    private final EnergyType energyType;
    private final int energyPerDistributor;
    private final List<MonthlyStat> monthlyStats;

    public final int getId() {
        return id;
    }

    public final int getMaxDistributors() {
        return maxDistributors;
    }

    public final double getPriceKW() {
        return priceKW;
    }

    public final EnergyType getEnergyType() {
        return energyType;
    }

    public final List<MonthlyStat> getMonthlyStats() {
        return monthlyStats;
    }

    public final int getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    public ProducerOutput(int id, int maxDistributors, double priceKW, EnergyType energyType,
                          int energyPerDistributor, List<MonthlyStat> monthlyStats) {
        this.id = id;
        this.maxDistributors = maxDistributors;
        this.priceKW = priceKW;
        this.energyType = energyType;
        this.energyPerDistributor = energyPerDistributor;
        this.monthlyStats = monthlyStats;
    }
}
