public class Driver {

    public static void main(String[] args) {

        String[] passwords = {
            "abc",
            "abcdefgh",
            "abcd1234",
            "AbCd_1234"
        };

        for (String password : passwords) {

            System.out.println("Password: " + password);

            System.out.println("Length >= 8: "+ PasswordChecker.hasValidLength(password)
            );

            System.out.println("Uppercase letter: "+ PasswordChecker.hasUpperCase(password));

            System.out.println("Digit: "+ PasswordChecker.hasDigit(password));

            System.out.println("Special character: "+ PasswordChecker.hasSpecialCharacter(password));

            System.out.println("Strength: "+ PasswordChecker.strength(password));

            System.out.println();
        }
    }
}
 