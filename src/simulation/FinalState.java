package simulation;

import output.ConsumerOutput;
import output.DistributorOutput;
import output.Output;
import output.ProducerOutput;

import java.util.Map;
import java.util.TreeMap;

/**
 * baza de date in care sunt adaugati consumatorii si distribuitorii pe parcurs.
 * fie dupa ce dau faliment, fie dupa ce se termina simularea curenta
 */
public final class FinalState {

    // am folosit TreeMap pentru a pastra entitatile ordonate in ordinea id-urilor
    private TreeMap<Integer, CurrentStateConsumer> finalConsumersMap = new TreeMap<>();
    private TreeMap<Integer, CurrentStateDistributor> finalDistributorsMap = new TreeMap<>();
    private TreeMap<Integer, CurrentStateProducer> finalProducersMap = new TreeMap<>();

    private static FinalState finalState = null;

    /**
     * returneaza instanta curenta pentru Singleton
     * @return
     */
    public static FinalState getInstance() {
        if (finalState == null) {
            finalState = new FinalState();
        }

        return finalState;
    }

    private FinalState() {
    }

    /**
     * curata continutul Singleton-ului pentru urmatoarea rulare
     */
    public static void reset() {
        finalState = null;
    }

    public TreeMap<Integer, CurrentStateConsumer> getFinalConsumersMap() {
        return finalConsumersMap;
    }

    public void setFinalConsumersMap(final TreeMap<Integer,
            CurrentStateConsumer> finalConsumersMap) {
        this.finalConsumersMap = finalConsumersMap;
    }

    public TreeMap<Integer, CurrentStateDistributor> getFinalDistributorsMap() {
        return finalDistributorsMap;
    }

    public void setFinalDistributorsMap(final TreeMap<Integer,
            CurrentStateDistributor> finalDistributorsMap) {
        this.finalDistributorsMap = finalDistributorsMap;
    }

    public TreeMap<Integer, CurrentStateProducer> getFinalProducersMap() {
        return finalProducersMap;
    }

    public void setFinalProducersMap(final TreeMap<Integer,
            CurrentStateProducer> finalProducersMap) {
        this.finalProducersMap = finalProducersMap;
    }

    /**
     * se genereaza output-ul prin selectarea campurilor relevante din FinalState
     * @param output
     */
    public void createOutput(final Output output) {
        for (Map.Entry<Integer, CurrentStateConsumer> entry
                : getFinalConsumersMap().entrySet()) {
            Integer id = entry.getKey();
            CurrentStateConsumer consumer = entry.getValue();

            ConsumerOutput consumerOutput = new ConsumerOutput(id, consumer.isBankrupt(),
                                                consumer.getBudget());
            output.getConsumers().add(consumerOutput);
        }

        for (Map.Entry<Integer, CurrentStateDistributor> entry
                : getFinalDistributorsMap().entrySet()) {
            Integer id = entry.getKey();
            CurrentStateDistributor distributor = entry.getValue();

            DistributorOutput distributorOutput = new DistributorOutput(distributor.getId(),
                    distributor.getEnergyNeededKW(), distributor.getContractCost(),
                    distributor.getBudget(), distributor.getProducerStrategy(),
                    distributor.isBankrupt(), distributor.getContracts());
            output.getDistributors().add(distributorOutput);
        }

        for (Map.Entry<Integer, CurrentStateProducer> entry
                : getFinalProducersMap().entrySet()) {
            Integer id = entry.getKey();
            CurrentStateProducer producer = entry.getValue();

            ProducerOutput producerOutput = new ProducerOutput(producer.getId(),
                    producer.getMaxDistributors(), producer.getPriceKW(),
                    producer.getEnergyType(), producer.getEnergyPerDistributor(),
                    producer.getMonthlyStats());
            output.getEnergyProducers().add(producerOutput);
        }
    }
}
