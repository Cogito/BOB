package com.cogito.bukkit.bob;

public class Account {

    private double balance;
    private boolean frozen;

    public double getBalance() {
        return balance;
    }

    public boolean isFrozen() {
        return frozen;
    }

    void freeze(boolean freeze){
        this.frozen = freeze;
    }

    public double credit(double amount, Account debtor) throws InvalidTransactionException {
        if (amount < 0){
            throw new InvalidTransactionException("Can not credit negative amounts.");
        }
        Account.transfer(amount, this, debtor);
        return this.balance;
    }

    public double debit(double amount, Account creditor) throws InvalidTransactionException {
        if (amount < 0){
            throw new InvalidTransactionException("Can not debit negative amounts.");
        }
        Account.transfer(amount, creditor, this);
        return this.balance;
    }
    
    static void transfer(double amount, Account creditor, Account debtor) throws InvalidTransactionException{
        if(creditor.isFrozen()){
            throw new InvalidTransactionException("Account \'"+creditor+"\' is frozen.");
        }
        if(debtor.isFrozen()){
            throw new InvalidTransactionException("Account \'"+debtor+"\' is frozen.");
        }
        if (debtor.getBalance() < amount) {
            throw new InvalidTransactionException("Not enough funds in account \'"+debtor+"\' to transfer.");
        }
        creditor.balance += amount;
        debtor.balance   -= amount;
    }

}
