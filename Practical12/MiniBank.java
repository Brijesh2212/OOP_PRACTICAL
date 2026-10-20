import java.util.ArrayList;

public class MiniBank {
    public static void main(String[] args) {
        AccountStore store = new AccountStore();

        Account a1 = new Account("AC0001", "Alice", 5000);
        Account a2 = new Account("AC0002", "Bob", 2000);
        Account a3 = new Account("AC0003", "Charlie", 9000);

        store.add(a1);
        store.add(a2);
        store.add(a3);

        System.out.println("Lookup by account number AC0002:");
        Account retrieved = store.get("AC0002");
        System.out.println(retrieved);

        System.out.println("\nAccounts sorted by balance (descending):");
        ArrayList<Account> sorted = store.sortedByBalance();
        for (Account acc : sorted) {
            System.out.println(acc);
        }

        System.out.println("\nTransaction history:");
        for (String record : store.getTransactionHistory()) {
            System.out.println(record);
        }

        TransactionProcessor processor = new TransactionProcessor();
        processor.addAccount(a1);
        processor.addAccount(a2);
        processor.addAccount(a3);

        for (int i = 0; i < 5; i++) {
            processor.submit(new Runnable() {
                @Override
                public void run() {
                    Account acc = processor.getAccount("AC0001");
                    if (acc != null) {
                        acc.deposit(100);
                    }
                }
            });
        }

        processor.stop();
        System.out.println("\nAfter concurrent deposits on AC0001, balance: " + processor.getAccount("AC0001").getBalance());
    }
}
