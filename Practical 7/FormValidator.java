import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface NotBlank {
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface MaxLength {
    int value();
}

class SignupForm {
    @NotBlank
    @MaxLength(20)
    String name;

    @NotBlank
    @MaxLength(30)
    String email;

    @NotBlank
    @MaxLength(15)
    String password;

    SignupForm(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}

class Validator {
    public static List<String> validate(Object object) {
        List<String> errors = new ArrayList<>();
        Class<?> clazz = object.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object objVal = field.get(object);
                String value = objVal == null ? null : objVal.toString();

                if (field.isAnnotationPresent(NotBlank.class)) {
                    if (value == null || value.trim().isEmpty()) {
                        errors.add(field.getName() + " must not be blank");
                    }
                }

                if (field.isAnnotationPresent(MaxLength.class) && value != null) {
                    MaxLength annotation = field.getAnnotation(MaxLength.class);
                    if (value.length() > annotation.value()) {
                        errors.add(field.getName() + " exceeds max length of " + annotation.value());
                    }
                }
            } catch (IllegalAccessException e) {
                errors.add("Cannot access field: " + field.getName());
            }
        }
        return errors;
    }
}

public class FormValidator {
    public static void main(String[] args) {
        SignupForm form = new SignupForm("", "abcdef123456@gmail.com", "9876543210");
        List<String> errors = Validator.validate(form);

        if (errors.isEmpty()) {
            System.out.println("Form is valid");
        } else {
            System.out.println("Validation Errors:");
            for (String error : errors) {
                System.out.println(error);
            }
        }
    }
}
