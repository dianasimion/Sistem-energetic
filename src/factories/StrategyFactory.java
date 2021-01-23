package factories;

import strategies.GreenStrategy;
import strategies.PriceStrategy;
import strategies.QuantityStrategy;
import strategies.Strategy;
import strategies.EnergyChoiceStrategyType;

/**
 * creeaza strategii
 */
public final class StrategyFactory {
    private static StrategyFactory factory = null;

    /**
     * returneaza instanta curenta pentru Singleton
     * @return factory
     */
    public static StrategyFactory getInstance() {
        if (factory == null) {
            factory = new StrategyFactory();
        }

        return factory;
    }

    /**
     * curata continutul Singleton-ului pentru urmatoarea rulare
     */
    public static void reset() {
        factory = null;
    }

    private StrategyFactory() {
    }

    /**
     * creaza o noua strategie pe baza tipului
     * @param type erergyTypeStrategy
     * @return factory
     */
    public Strategy createStrategy(EnergyChoiceStrategyType type) {
        Strategy strategy = null;

        switch (type) {
            case GREEN:
                strategy = new GreenStrategy();
                break;

            case PRICE:
                strategy = new PriceStrategy();
                break;

            case QUANTITY:
                strategy = new QuantityStrategy();
                break;

            default:
                break;
        }

        return strategy;
    }
}
