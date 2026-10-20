import java.util.ArrayList;

public class MiniBank {
    public static void main(String[] args) {
        Repository<Account> accountRepo = new Repository<>();

        Account a1 = new Account("AC0001", "Alice", 5000);
        Account a2 = new Account("AC0002", "Bob", 2000);
        Account a3 = new Account("AC0003", "Charlie", 9000);

        accountRepo.saveAll(a1, a2, a3);

        System.out.println("Find account by ID AC0002:");
        Account fetched = accountRepo.findById("AC0002");
        System.out.println(fetched);

        System.out.println("\nAll accounts in repository:");
        ArrayList<Account> allAccounts = accountRepo.findAll();
        for (Account a : allAccounts) {
            System.out.println(a);
        }

        System.out.println("\nDemonstrating same Repository with Customer objects:");
        Repository<Customer> customerRepo = new Repository<>();
        Customer c1 = new Customer("CUST101", "Riya", "riya@bank.com");
        Customer c2 = new Customer("CUST102", "Amit", "amit@bank.com");

        customerRepo.save(c1);
        customerRepo.save(c2);

        System.out.println("Find customer CUST101: " + customerRepo.findById("CUST101"));
        System.out.println("All customers: " + customerRepo.findAll());

        Double[] sampleNumbers = {1000.0, 2000.0, 3000.0};
        System.out.println("\nBounded generic average: " + Repository.average(sampleNumbers));
    }
}
