package factories;

import input.Distributor;
import simulation.CurrentStateDistributor;

/**
 * creaza distribuitori in formatul complex CurrentStateConsumer
 */
public final class DistributorFactory {
    private static DistributorFactory factory = null;

    /**
     * returneaza instanta curenta pentru Singleton
     * @return
     */
    public static DistributorFactory getInstance() {
        if (factory == null) {
            factory = new DistributorFactory();
        }

        return factory;
    }

    private DistributorFactory() {
    }

    /**
     * curata continutul Singleton-ului pentru urmatoarea rulare
     */
    public static void reset() {
        factory = null;
    }

    /**
     * creeaza un distribuitor
     * @param distributor
     * @return
     */
    public CurrentStateDistributor createDistributor(final Distributor distributor) {
        return new CurrentStateDistributor(distributor);
    }
}
