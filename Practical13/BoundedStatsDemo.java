public class BoundedStatsDemo {
    public static <N extends Number> double average(N[] values) {
        if (values == null || values.length == 0) {
            return 0.0;
        }
        double sum = 0.0;
        for (N val : values) {
            sum += val.doubleValue();
        }
        return sum / values.length;
    }

    public static void main(String[] args) {
        Integer[] intArr = {10, 20, 30, 40};
        Double[] doubleArr = {2.5, 3.5, 4.0};
        Long[] longArr = {100L, 200L, 300L, 400L};

        System.out.println("Integer average: " + average(intArr));
        System.out.println("Double average: " + average(doubleArr));
        System.out.println("Long average: " + average(longArr));
    }
}
