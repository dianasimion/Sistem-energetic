package output;

public class Contract {
    private int consumerId;
    private int price;
    private int remainedContractMonths;

    public final int getConsumerId() {
        return consumerId;
    }

    public final void setConsumerId(final int consumerId) {
        this.consumerId = consumerId;
    }

    public final int getPrice() {
        return price;
    }

    public final void setPrice(final int price) {
        this.price = price;
    }

    public final int getRemainedContractMonths() {
        return remainedContractMonths;
    }

    public final void setRemainedContractMonths(final int remainedContractMonths) {
        this.remainedContractMonths = remainedContractMonths;
    }

    /**
     * constructor pentru contract
     * @param consumerId
     * @param price
     * @param remainedContractMonths
     */
    public Contract(final int consumerId, final int price, final int remainedContractMonths) {
        this.consumerId = consumerId;
        this.price = price;
        this.remainedContractMonths = remainedContractMonths;
    }

    /**
     * constructor care copiaza cel mai bun contract
     * @param contract
     */
    public Contract(final Contract contract) {
        this.consumerId = contract.getConsumerId();
        this.price = contract.getPrice();
        this.remainedContractMonths = contract.getRemainedContractMonths();
    }
}
