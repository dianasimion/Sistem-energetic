package input;

import strategies.EnergyChoiceStrategyType;

/**
 * distribuitorul in formatul primit de la input
 */
public class Distributor {
    private final int id;
    private final int contractLength;
    private final int initialBudget;
    private final int initialInfrastructureCost;
    private final int initialProductionCost;
    private final int energyNeededKW;
    private final EnergyChoiceStrategyType producerStrategy;

    public Distributor() {
        id = -1;
        contractLength = 0;
        initialBudget = 0;
        initialInfrastructureCost = 0;
        initialProductionCost = 0;
        energyNeededKW = 0;
        producerStrategy = null;
    }

    public final int getId() {
        return id;
    }

    public final int getContractLength() {
        return contractLength;
    }

    public final int getInitialBudget() {
        return initialBudget;
    }

    public final int getInitialInfrastructureCost() {
        return initialInfrastructureCost;
    }

    public final int getInitialProductionCost() {
        return initialProductionCost;
    }

    public final int getEnergyNeededKW() {
        return energyNeededKW;
    }

    public final EnergyChoiceStrategyType getProducerStrategy() {
        return producerStrategy;
    }
}

