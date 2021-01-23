package simulation;

import input.Consumer;
import output.Contract;

/**
 * consumatorul in timpul unei runde din simularea curenta
 */
public class CurrentStateConsumer {
    private int id;
    private int monthlyIncome;
    private boolean isBankrupt;
    private int budget;
    private Contract contract;
    private boolean isRestant;
    private int currentDistributorId;
    private int restantDistributorId;

    public CurrentStateConsumer(final Consumer consumer) {
        this.id = consumer.getId();
        this.monthlyIncome = consumer.getMonthlyIncome();
        this.budget = consumer.getInitialBudget();
        this.currentDistributorId = -1;
        this.restantDistributorId = -1;
    }

    public final int getId() {
        return id;
    }

    public final void setId(final int id) {
        this.id = id;
    }

    public final int getMonthlyIncome() {
        return monthlyIncome;
    }

    public final void setMonthlyIncome(final int monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public final boolean isBankrupt() {
        return isBankrupt;
    }

    public final void setBankrupt(final boolean bankrupt) {
        isBankrupt = bankrupt;
    }

    public final int getBudget() {
        return budget;
    }

    public final void setBudget(final int budget) {
        this.budget = budget;
    }

    public final Contract getContract() {
        return contract;
    }

    public final void setContract(final Contract contract) {
        this.contract = contract;
    }

    public final boolean isRestant() {
        return isRestant;
    }

    public final void setRestant(final boolean restant) {
        isRestant = restant;
    }

    public final int getCurrentDistributorId() {
        return currentDistributorId;
    }

    public final void setCurrentDistributorId(final int currentDistributorId) {
        this.currentDistributorId = currentDistributorId;
    }

    public final int getRestantDistributorId() {
        return restantDistributorId;
    }

    public final void setRestantDistributorId(final int restantDistributorId) {
        this.restantDistributorId = restantDistributorId;
    }

    /**
     * consumatorul primeste salariu
     */
    public final void getPaid() {
        this.budget += this.getMonthlyIncome();
    }

    /**
     * consumatorul plateste valoarea contractului actual catre distributor,
     * iar aceste primeste banii
     * @param currentDistributorId idul
     */
    public final void payContract(final int currentDistributorId) {
        this.budget -= this.contract.getPrice();

        Simulation.getInstance().getDistributorsMap().get(currentDistributorId).
                acceptMoney(this.contract.getPrice());
    }

    /**
     * @return suma pe care consumatorul restant o datoreaza distributorului
     */
    public final int computeRestantAndCurrentContract() {
        // suma contractului neplatit plus penalitatile se adauga la pretul contractului actual

        int oldId = this.getRestantDistributorId();
        int oldPrice = 0;
        for (Contract contract: Simulation.getInstance().getDistributorsMap().
                get(oldId).getContracts()) {
            if (contract.getConsumerId() == this.getId()) {
                oldPrice = contract.getPrice();
            }
        }

        final double restantFactor = 1.2;

        return (int) (Math.round(Math.floor(restantFactor * oldPrice)) + this.contract.getPrice());
    }

    /**
     * consumatorul plateste valoarea contractului actual si a contractului unde este restant
     * catre distributor, iar aceste primeste banii
     */
    public final void payRestantAndCurrentContract() {
        this.budget -= computeRestantAndCurrentContract();

        Simulation.getInstance().getDistributorsMap().get(currentDistributorId).
                acceptMoney(computeRestantAndCurrentContract());
    }
}
