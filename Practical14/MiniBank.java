import java.util.List;
import java.util.Scanner;

public class MiniBank {
    public static void main(String[] args) {
        BankInfo bank = new BankInfo("MiniBank", "Main Branch");
        System.out.println("===================================");
        System.out.println(bank);
        System.out.println("===================================");

        BankService service = new BankService();
        TransactionProcessor processor = new TransactionProcessor();

        Scanner sc = new Scanner(System.in);
        MenuOption[] options = MenuOption.values();

        boolean running = true;
        while (running) {
            printMenu(options);
            System.out.print("Enter your choice (1-" + options.length + "): ");
            String input = sc.nextLine().trim();

            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.\n");
                continue;
            }

            if (choice < 1 || choice > options.length) {
                System.out.println("Invalid choice. Please select between 1 and " + options.length + ".\n");
                continue;
            }

            MenuOption selected = options[choice - 1];
            try {
                switch (selected) {
                    case OPEN_ACCOUNT:
                        System.out.print("Enter owner name: ");
                        String name = sc.nextLine().trim();
                        System.out.print("Enter initial deposit: ");
                        long initDeposit = Long.parseLong(sc.nextLine().trim());
                        System.out.print("Enter mobile (10 digits): ");
                        String mobile = sc.nextLine().trim();
                        System.out.print("Enter email: ");
                        String email = sc.nextLine().trim();

                        Account acc = service.openAccount(name, initDeposit, mobile, email);
                        System.out.println("Account opened successfully: " + acc.getAccountNumber());
                        break;

                    case DEPOSIT:
                        System.out.print("Enter account number: ");
                        String depAcc = sc.nextLine().trim();
                        System.out.print("Enter amount to deposit: ");
                        long depAmt = Long.parseLong(sc.nextLine().trim());
                        service.deposit(depAcc, depAmt);
                        System.out.println("Deposit of " + depAmt + " successful into " + depAcc);
                        break;

                    case WITHDRAW:
                        System.out.print("Enter account number: ");
                        String withAcc = sc.nextLine().trim();
                        System.out.print("Enter amount to withdraw: ");
                        long withAmt = Long.parseLong(sc.nextLine().trim());
                        service.withdraw(withAcc, withAmt);
                        System.out.println("Withdrawal of " + withAmt + " successful from " + withAcc);
                        break;

                    case TRANSFER:
                        System.out.print("Enter sender account number: ");
                        String fromAcc = sc.nextLine().trim();
                        System.out.print("Enter recipient account number: ");
                        String toAcc = sc.nextLine().trim();
                        System.out.print("Enter transfer amount: ");
                        long trAmt = Long.parseLong(sc.nextLine().trim());
                        service.transfer(fromAcc, toAcc, trAmt);
                        System.out.println("Transferred " + trAmt + " from " + fromAcc + " to " + toAcc);
                        break;

                    case STATEMENT:
                        System.out.print("Enter account number: ");
                        String stAcc = sc.nextLine().trim();
                        System.out.println(service.statement(stAcc));
                        break;

                    case LIST_ACCOUNTS:
                        List<Account> accounts = service.listAccounts();
                        if (accounts.isEmpty()) {
                            System.out.println("No accounts currently registered.");
                        } else {
                            System.out.println("Registered Accounts:");
                            for (Account a : accounts) {
                                System.out.println(a);
                            }
                        }
                        break;

                    case BATCH_TRANSACTIONS:
                        System.out.print("Enter account number for batch deposit: ");
                        String batchAcc = sc.nextLine().trim();
                        Account targetAcc = service.getRepository().findById(batchAcc);
                        if (targetAcc == null) {
                            System.out.println("Account not found: " + batchAcc);
                            break;
                        }
                        System.out.println("Submitting 10 concurrent deposit tasks of 100 each...");
                        for (int i = 0; i < 10; i++) {
                            processor.submit(() -> {
                                try {
                                    service.deposit(batchAcc, 100);
                                } catch (BankException ignored) {
                                }
                            });
                        }
                        System.out.println("Batch tasks queued to thread pool.");
                        break;

                    case REPORT:
                        System.out.println(service.generateReport());
                        break;

                    case EXIT:
                        service.saveSavedState();
                        processor.stop();
                        System.out.println("State saved. Goodbye! Thank you for using MiniBank.");
                        running = false;
                        break;
                }
            } catch (BankException e) {
                System.out.println("Operation failed: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Invalid numeric input.");
            }
            System.out.println();
        }
        sc.close();
    }

    private static void printMenu(MenuOption[] options) {
        System.out.println("---- MiniBank Menu ----");
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + formatLabel(options[i]));
        }
    }

    private static String formatLabel(MenuOption option) {
        String[] words = option.name().split("_");
        StringBuilder sb = new StringBuilder();
        for (String w : words) {
            sb.append(Character.toUpperCase(w.charAt(0)))
              .append(w.substring(1).toLowerCase())
              .append(" ");
        }
        return sb.toString().trim();
    }
}
