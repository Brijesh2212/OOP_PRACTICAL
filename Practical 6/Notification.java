@FunctionalInterface
interface Notifier {
    void send(String message);
}

interface Urgent {
}

class UrgentEmail implements Notifier, Urgent {
    public void send(String message) {
        System.out.println("Email: " + message);
    }
}

class UrgentSMS implements Notifier, Urgent {
    public void send(String message) {
        System.out.println("SMS: " + message);
    }
}

public class Notification {
    public static void main(String[] args) {
        Notifier email = message -> System.out.println("Email: " + message);
        Notifier sms = message -> System.out.println("SMS: " + message);
        Notifier[] senders = {email, sms, new UrgentEmail(), new UrgentSMS()};
        String message = "Bank account alert";

        for (Notifier sender : senders) {
            sender.send(message);
            if (sender instanceof Urgent) {
                sender.send(message);
            }
        }
    }
}
