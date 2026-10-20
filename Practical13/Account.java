import java.io.Serializable;
import java.util.Objects;

public class Account implements Comparable<Account>, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private final String accountNumber;
    private String ownerName;
    private long balance;
    private boolean active;

    public Account(String accountNumber, String ownerName, long balance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = balance;
        this.active = true;
    }

    public Account(String ownerName, long balance) {
        this("AC" + System.currentTimeMillis() % 10000, ownerName, balance);
    }

    public Account(String ownerName) {
        this(ownerName, 0);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public long getBalance() {
        return balance;
    }

    public boolean isActive() {
        return active;
    }

    public synchronized void deposit(long amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public synchronized boolean withdraw(long amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(Account other) {
        return this.accountNumber.compareTo(other.accountNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(accountNumber, account.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }

    @Override
    public String toString() {
        return accountNumber + " | " + ownerName + " | " + balance;
    }
}
