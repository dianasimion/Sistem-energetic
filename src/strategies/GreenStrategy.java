package strategies;

import simulation.CurrentStateProducer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class GreenStrategy implements Strategy {

    /**
     * returneaza lista de producatori corespunzatoare strategiei
     * @param producers
     * @return
     */
    @Override
    public List<CurrentStateProducer> getProducers(List<CurrentStateProducer> producers) {
        List<CurrentStateProducer> tmpList = new ArrayList<>(producers);

        tmpList.sort(Comparator.comparing(CurrentStateProducer::producerHasRenewable,
                Comparator.reverseOrder()).thenComparing(CurrentStateProducer::getPriceKW).
                        thenComparing(CurrentStateProducer::getEnergyPerDistributor,
                                Comparator.reverseOrder()).
                        thenComparing(CurrentStateProducer::getId));

        List<CurrentStateProducer> list = new ArrayList<>(tmpList);

        // exclud producatorii care deja au atins limita maxima de distribuitori
        list.removeIf(producer -> producer.getAllDistributors().size()
                >= producer.getMaxDistributors());

//        for (CurrentStateProducer prod : list) {
//            System.out.println(prod.producerHasRenewable());
//            System.out.println(prod.getPriceKW());
//            System.out.println(prod.getEnergyPerDistributor());
//            System.out.println(prod.getId());
//        }

        return list;
    }
}
