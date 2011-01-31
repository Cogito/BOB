package com.cogito.bukkit.bob;

public class Transaction {
    /**
     * amount to transfer
     */
    public final double amount;

    /**
     *  Account to be paid.
     */
    public final Account creditor;

    /**
     *  Account to draw money from.
     */
    public final Account debtor;

    /**
     * An optional reason for the transaction.
     * 
     * Can be null. Example: "10 wood"
     * The reason, if supplied, will be added to the end of transaction messages as "for \<reason\>"
     */
    public final String reason;

    public Transaction(double amount, Account creditor, Account debtor) {
        this(amount, creditor, debtor, null);
        
    }
    
    public Transaction(double amount, Account creditor, Account debtor, String reason) {
        super();
        this.amount = amount;
        this.creditor = creditor;
        this.debtor = debtor;
        this.reason = reason;
    }

    public String toString(){
        return "transfer "+amount+" from "+debtor+" to "+creditor+((reason != null)?" for "+reason:"");
    }
}
