import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class BankService {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1000);

    private final Repository<Account> repository;
    private final Path dataFile;
    private final Path logsDir;
    private final Path currentLogFile;

    public BankService() {
        this(Paths.get("data", "accounts.dat"), Paths.get("logs"));
    }

    public BankService(Path dataFile, Path logsDir) {
        this.repository = new Repository<>();
        this.dataFile = dataFile;
        this.logsDir = logsDir;
        this.currentLogFile = logsDir.resolve("transactions.log");
        loadSavedState();
    }

    public Repository<Account> getRepository() {
        return repository;
    }

    public Account openAccount(String ownerName, long initialBalance, String mobile, String email) throws BankException {
        if (ownerName == null || ownerName.trim().isEmpty()) {
            throw new BankException("Owner name cannot be empty");
        }
        if (mobile != null && !mobile.isEmpty() && !Validator.isValidMobile(mobile)) {
            throw new BankException("Invalid mobile number format: " + mobile);
        }
        if (email != null && !email.isEmpty() && !Validator.isValidEmail(email)) {
            throw new BankException("Invalid email format: " + email);
        }
        if (initialBalance < 0) {
            throw new InvalidAmountException("Initial balance cannot be negative: " + initialBalance);
        }

        String accNumber = "AC" + ID_GENERATOR.incrementAndGet();
        Account account = new Account(accNumber, ownerName.trim(), initialBalance);
        repository.save(account);

        logAction("OPEN_ACCOUNT " + accNumber + " " + initialBalance);
        return account;
    }

    public void deposit(String accountNumber, long amount) throws BankException {
        if (!Validator.isValidAmount(amount)) {
            throw new InvalidAmountException("Deposit amount must be positive: " + amount);
        }
        Account account = repository.findById(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException(accountNumber);
        }
        account.deposit(amount);
        logAction("DEPOSIT " + accountNumber + " " + amount);
    }

    public void withdraw(String accountNumber, long amount) throws BankException {
        if (!Validator.isValidAmount(amount)) {
            throw new InvalidAmountException("Withdrawal amount must be positive: " + amount);
        }
        Account account = repository.findById(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException(accountNumber);
        }
        account.withdraw(amount);
        logAction("WITHDRAW " + accountNumber + " " + amount);
    }

    public void transfer(String fromAccountNumber, String toAccountNumber, long amount) throws BankException {
        if (fromAccountNumber == null || toAccountNumber == null || fromAccountNumber.equals(toAccountNumber)) {
            throw new BankException("Invalid transfer accounts");
        }
        if (!Validator.isValidAmount(amount)) {
            throw new InvalidAmountException("Transfer amount must be positive: " + amount);
        }

        Account from = repository.findById(fromAccountNumber);
        if (from == null) {
            throw new AccountNotFoundException(fromAccountNumber);
        }
        Account to = repository.findById(toAccountNumber);
        if (to == null) {
            throw new AccountNotFoundException(toAccountNumber);
        }

        Account first = from.compareTo(to) < 0 ? from : to;
        Account second = from.compareTo(to) < 0 ? to : from;

        synchronized (first) {
            synchronized (second) {
                from.withdraw(amount);
                to.deposit(amount);
            }
        }

        logAction("WITHDRAW " + fromAccountNumber + " " + amount);
        logAction("DEPOSIT " + toAccountNumber + " " + amount);
    }

    public String statement(String accountNumber) throws BankException {
        Account account = repository.findById(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException(accountNumber);
        }
        return "------------------------------------\n"
             + "Account Statement: " + account.getAccountNumber() + "\n"
             + "Owner: " + account.getOwnerName() + "\n"
             + "Current Balance: " + account.getBalance() + "\n"
             + "Status: " + (account.isActive() ? "Active" : "Inactive") + "\n"
             + "------------------------------------";
    }

    public List<Account> listAccounts() {
        ArrayList<Account> list = repository.findAll();
        list.sort(Comparator.comparing(Account::getAccountNumber));
        return list;
    }

    public void logAction(String line) {
        try {
            TransactionLog.append(currentLogFile, line);
        } catch (IOException ignored) {
        }
    }

    public String generateReport() {
        try {
            Path reportFile = logsDir.resolve("summary_report.txt");
            return ReportGenerator.generate(logsDir, reportFile);
        } catch (IOException e) {
            return "Failed to generate report: " + e.getMessage();
        }
    }

    public void saveSavedState() {
        try {
            StatePersister.save(repository.findAll(), dataFile);
        } catch (IOException ignored) {
        }
    }

    public void loadSavedState() {
        try {
            Account[] accounts = StatePersister.load(dataFile);
            int maxId = 1000;
            for (Account acc : accounts) {
                repository.save(acc);
                try {
                    String numPart = acc.getAccountNumber().replaceAll("\\D", "");
                    if (!numPart.isEmpty()) {
                        int idVal = Integer.parseInt(numPart);
                        if (idVal > maxId) {
                            maxId = idVal;
                        }
                    }
                } catch (NumberFormatException ignored) {
                }
            }
            ID_GENERATOR.set(Math.max(ID_GENERATOR.get(), maxId));
        } catch (Exception ignored) {
        }
    }
}
