import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Repository<T> {
    private final Map<String, T> store = new HashMap<>();

    public T save(T entity) {
        if (entity == null) {
            return null;
        }
        String id = extractId(entity);
        if (id != null) {
            store.put(id, entity);
        }
        return entity;
    }

    @SafeVarargs
    public final void saveAll(T... items) {
        if (items != null) {
            for (T item : items) {
                save(item);
            }
        }
    }

    public T findById(String id) {
        return store.get(id);
    }

    public ArrayList<T> findAll() {
        return new ArrayList<>(store.values());
    }

    public boolean delete(String id) {
        return store.remove(id) != null;
    }

    private String extractId(T entity) {
        Class<?> clazz = entity.getClass();
        while (clazz != null && clazz != Object.class) {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Id.class)) {
                    field.setAccessible(true);
                    try {
                        Object val = field.get(entity);
                        return val != null ? val.toString() : null;
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            clazz = clazz.getSuperclass();
        }
        return null;
    }

    public static <N extends Number> double average(N[] values) {
        if (values == null || values.length == 0) {
            return 0.0;
        }
        double sum = 0.0;
        for (N num : values) {
            sum += num.doubleValue();
        }
        return sum / values.length;
    }
}
