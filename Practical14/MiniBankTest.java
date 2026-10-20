import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MiniBankTest {

    @Test
    public void testDepositIncreasesBalance(@TempDir Path tempDir) throws BankException {
        BankService service = new BankService(tempDir.resolve("accounts.dat"), tempDir.resolve("logs"));
        Account account = service.openAccount("Alice", 1000, "9876543210", "alice@example.com");

        service.deposit(account.getAccountNumber(), 500);
        Account updated = service.getRepository().findById(account.getAccountNumber());
        assertEquals(1500, updated.getBalance());
    }

    @Test
    public void testWithdrawalOverLimitThrowsException(@TempDir Path tempDir) throws BankException {
        BankService service = new BankService(tempDir.resolve("accounts.dat"), tempDir.resolve("logs"));
        Account account = service.openAccount("Bob", 1000, "9876543210", "bob@example.com");

        InsufficientFundsException ex = assertThrows(InsufficientFundsException.class, () -> {
            service.withdraw(account.getAccountNumber(), 1500);
        });

        assertEquals(500, ex.getShortfall());
        assertEquals(1000, account.getBalance());
    }

    @Test
    public void testConcurrentDepositsKeepBalanceCorrect(@TempDir Path tempDir) throws Exception {
        BankService service = new BankService(tempDir.resolve("accounts.dat"), tempDir.resolve("logs"));
        Account account = service.openAccount("Charlie", 0, "9876543210", "charlie@example.com");

        int threadCount = 10;
        int depositsPerThread = 100;
        long amountPerDeposit = 10;

        ExecutorService pool = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            pool.submit(() -> {
                try {
                    for (int j = 0; j < depositsPerThread; j++) {
                        service.deposit(account.getAccountNumber(), amountPerDeposit);
                    }
                } catch (Exception ignored) {
                } finally {
                    latch.countDown();
                }
            });
        }

        assertTrue(latch.await(10, TimeUnit.SECONDS));
        pool.shutdown();

        long expectedBalance = (long) threadCount * depositsPerThread * amountPerDeposit;
        assertEquals(expectedBalance, service.getRepository().findById(account.getAccountNumber()).getBalance());
    }

    @Test
    public void testTransferBetweenAccounts(@TempDir Path tempDir) throws BankException {
        BankService service = new BankService(tempDir.resolve("accounts.dat"), tempDir.resolve("logs"));
        Account a1 = service.openAccount("Sender", 2000, "9876543210", "sender@example.com");
        Account a2 = service.openAccount("Receiver", 500, "9876543210", "receiver@example.com");

        service.transfer(a1.getAccountNumber(), a2.getAccountNumber(), 800);

        assertEquals(1200, service.getRepository().findById(a1.getAccountNumber()).getBalance());
        assertEquals(1300, service.getRepository().findById(a2.getAccountNumber()).getBalance());
    }

    @Test
    public void testPersistenceAcrossRestart(@TempDir Path tempDir) throws Exception {
        Path dataFile = tempDir.resolve("accounts.dat");
        Path logsDir = tempDir.resolve("logs");

        BankService service1 = new BankService(dataFile, logsDir);
        Account acc = service1.openAccount("PersistentUser", 3500, "9876543210", "user@example.com");
        service1.saveSavedState();

        BankService service2 = new BankService(dataFile, logsDir);
        Account reloaded = service2.getRepository().findById(acc.getAccountNumber());
        assertNotNull(reloaded);
        assertEquals("PersistentUser", reloaded.getOwnerName());
        assertEquals(3500, reloaded.getBalance());
    }
}
