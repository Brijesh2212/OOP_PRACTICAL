interface Switchable {
    void on();
    void off();
    default void toggle() {
        on();
    }
}

class Fan implements Switchable {
    public void on() {
        System.out.println("Fan ON");
    }

    public void off() {
        System.out.println("Fan OFF");
    }
}

class Light implements Switchable {
    public void on() {
        System.out.println("Light ON");
    }

    public void off() {
        System.out.println("Light OFF");
    }
}

@FunctionalInterface
interface Permission {
    boolean maySwitchOn(Switchable device, int hour);
}

public class RemoteControl {
    public static void main(String[] args) {
        Switchable[] devices = {new Light(), new Fan()};

        for (Switchable device : devices) {
            device.toggle();
        }

        Permission p1 = new Permission() {
            public boolean maySwitchOn(Switchable device, int hour) {
                return hour >= 6 && hour <= 22;
            }
        };

        System.out.println("Anonymous: " + p1.maySwitchOn(devices[0], 10));

        Permission p2 = (device, hour) -> hour >= 6 && hour <= 22;
        System.out.println("Lambda: " + p2.maySwitchOn(devices[1], 23));
    }
}
