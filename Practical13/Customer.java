import java.io.Serializable;

public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private final String customerId;
    private String name;
    private String email;

    public Customer(String customerId, String name, String email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return customerId + " | " + name + " | " + email;
    }
}
