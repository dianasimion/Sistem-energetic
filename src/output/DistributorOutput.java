package output;

import strategies.EnergyChoiceStrategyType;

import java.util.List;

/**
 * distribuitorul in formatul de output
 */
public class DistributorOutput {
    private final int id;
    private final int energyNeededKW;
    private final int contractCost;
    private final int budget;
    private final EnergyChoiceStrategyType producerStrategy;
    private final boolean isBankrupt;
    private final List<Contract> contracts;


    public final int getId() {
        return id;
    }

    public final int getBudget() {
        return budget;
    }

    public final boolean getIsBankrupt() {
        return isBankrupt;
    }

    public final List<Contract> getContracts() {
        return contracts;
    }

    public final int getEnergyNeededKW() {
        return energyNeededKW;
    }

    public final int getContractCost() {
        return contractCost;
    }

    public final EnergyChoiceStrategyType getProducerStrategy() {
        return producerStrategy;
    }

    public DistributorOutput(final int id, final int energyNeededKW,
                             final int contractCost, final int budget,
                             final EnergyChoiceStrategyType producerStrategy,
                             final boolean isBankrupt,
                             final List<Contract> contracts) {
        this.id = id;
        this.energyNeededKW = energyNeededKW;
        this.contractCost = contractCost;
        this.budget = budget;
        this.producerStrategy = producerStrategy;
        this.isBankrupt = isBankrupt;
        this.contracts = contracts;
    }
}
