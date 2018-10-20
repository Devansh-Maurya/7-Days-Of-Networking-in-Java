package ItemQuoteTransferProtocol;

public class ItemQuote {

    private long itemNumber;
    private String itemDescription;
    private int quantity;
    private int unitPrice;
    private boolean discounted;
    private boolean inStock;

    public ItemQuote(long itemNumber, String itemDescription,
                     int quantity, int unitPrice, boolean discounted, boolean inStock) {
        this.itemNumber = itemNumber;
        this.itemDescription = itemDescription;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.discounted = discounted;
        this.inStock = inStock;
    }

    @Override
    public String toString() {
        final String EOLN = System.getProperty("line.separator");
        String value = "Item#= " + itemNumber + EOLN +
                "Description= " + itemDescription + EOLN +
                "Quantity= " + quantity + EOLN +
                "Price(each)= " + unitPrice + EOLN +
                "Total= " + (quantity + unitPrice);

        if (discounted)
            value += " (discounted)";
        if (inStock)
            value += EOLN + "In Stock" + EOLN;
        else
            value += EOLN + "Out of Stock" + EOLN;
        return value;
    }

    public long getItemNumber() {
        return itemNumber;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public boolean isDiscounted() {
        return discounted;
    }

    public boolean isInStock() {
        return inStock;
    }
}
