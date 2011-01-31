package com.cogito.bukkit.bob;

import java.util.concurrent.BlockingQueue;

public class Teller implements Runnable{

    private boolean stopRequested;
    private Account customer;
    
    private BlockingQueue<Transaction> transactions;
    private BlockingQueue<Boolean> responses;
    public Teller(Account customer) {
        super();
        this.stopRequested = false;
        this.customer = customer;
    }

    public void run() {
        while(!stopRequested()){
            try {
                Transaction currentTransaction = transactions.take();
                if(customer.equals(currentTransaction.debtor)){
                    customer.sendMessage("Do you want to "+currentTransaction+"?");
                    Boolean response = responses.take();
                    if(response){
                        process(currentTransaction);
                    }
                }
            } catch (InterruptedException e) {
                continue;
            } catch (InvalidTransactionException e) {
                customer.sendMessage(e.getMessage());
            }
        }
    }

    private void process(Transaction transaction) throws InvalidTransactionException {
        Account.transfer(transaction.amount, transaction.creditor, transaction.debtor);
    }

    /** Request that this thread stop running.  */
    public synchronized void requestStop(){
      stopRequested = true;
    }

    private synchronized boolean stopRequested() {
        return stopRequested;
    }

    public void recieveResponse(){
        
    }
    
    public void newTransaction(Transaction transaction){
        this.transactions.add(transaction);
    }
}
