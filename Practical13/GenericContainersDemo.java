public class GenericContainersDemo {
    public static <E> E getFirst(E[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        return array[0];
    }

    public static void main(String[] args) {
        Box<String> stringBox = new Box<>("Hello Generics");
        Box<Integer> intBox = new Box<>(100);

        String strVal = stringBox.get();
        Integer intVal = intBox.get();

        System.out.println("String Box Value: " + strVal);
        System.out.println("Integer Box Value: " + intVal);

        Pair<String, Integer> personAge = new Pair<>("Alice", 21);
        Pair<Integer, Double> point = new Pair<>(10, 25.5);

        System.out.println("Pair 1: " + personAge.getFirst() + " -> " + personAge.getSecond());
        System.out.println("Pair 2: " + point.getFirst() + " -> " + point.getSecond());

        String[] names = {"Alice", "Bob", "Charlie"};
        Integer[] numbers = {10, 20, 30};

        System.out.println("First name: " + getFirst(names));
        System.out.println("First number: " + getFirst(numbers));
    }
}
