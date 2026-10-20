import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Run {
}

class MyTests {
    @Run
    public void testLogin() {
        System.out.println("Running testLogin");
    }

    @Run
    public void testSignup() {
        System.out.println("Running testSignup");
    }

    public void normalMethod() {
        System.out.println("This method should not run");
    }

    @Run
    public void testLogout() {
        System.out.println("Running testLogout");
    }
}

public class MiniTestRunner {
    public static void main(String[] args) throws Exception {
        MyTests tests = new MyTests();
        int count = 0;

        for (Method method : MyTests.class.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Run.class)) {
                method.invoke(tests);
                count++;
            }
        }

        System.out.println("Total tests ran: " + count);
    }
}
