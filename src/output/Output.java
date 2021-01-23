package output;

import simulation.CurrentStateConsumer;
import simulation.CurrentStateDistributor;
import simulation.CurrentStateProducer;
import simulation.FinalState;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Output {
    private final List<ConsumerOutput> consumers = new ArrayList<>();
    private final List<DistributorOutput> distributors = new ArrayList<>();
    private final List<ProducerOutput> energyProducers = new ArrayList<>();

    public final List<ConsumerOutput> getConsumers() {
        return consumers;
    }

    public final List<DistributorOutput> getDistributors() {
        return distributors;
    }

    public final List<ProducerOutput> getEnergyProducers() {
        return energyProducers;
    }

    /**
     * creeaza outputul pe baza informatiilor finale din "baza de date" FinalState
     */
    public void createOutput() {
        for (Map.Entry<Integer, CurrentStateConsumer> entry
                : FinalState.getInstance().getFinalConsumersMap().entrySet()) {
            Integer id = entry.getKey();
            CurrentStateConsumer consumer = entry.getValue();

            ConsumerOutput consumerOutput = new ConsumerOutput(id, consumer.isBankrupt(),
                                                                consumer.getBudget());
            this.getConsumers().add(consumerOutput);
        }

        for (Map.Entry<Integer, CurrentStateDistributor> entry
                : FinalState.getInstance().getFinalDistributorsMap().entrySet()) {
            Integer id = entry.getKey();
            CurrentStateDistributor distributor = entry.getValue();

            DistributorOutput distributorOutput = new DistributorOutput(distributor.getId(),
                    distributor.getEnergyNeededKW(), distributor.getContractCost(),
                    distributor.getBudget(), distributor.getProducerStrategy(),
                    distributor.isBankrupt(), distributor.getContracts());
            this.getDistributors().add(distributorOutput);
        }

        for (Map.Entry<Integer, CurrentStateProducer> entry
                : FinalState.getInstance().getFinalProducersMap().entrySet()) {
            Integer id = entry.getKey();
            CurrentStateProducer producer = entry.getValue();

            ProducerOutput producerOutput = new ProducerOutput(producer.getId(),
                    producer.getMaxDistributors(), producer.getPriceKW(),
                    producer.getEnergyType(), producer.getEnergyPerDistributor(),
                    producer.getMonthlyStats());
            this.getEnergyProducers().add(producerOutput);
        }
    }
}
