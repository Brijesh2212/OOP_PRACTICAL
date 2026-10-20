import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class Repository<T> {
    private final ConcurrentHashMap<String, T> store = new ConcurrentHashMap<>();

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
        if (id == null) {
            return null;
        }
        return store.get(id);
    }

    public ArrayList<T> findAll() {
        return new ArrayList<>(store.values());
    }

    public boolean delete(String id) {
        if (id == null) {
            return false;
        }
        return store.remove(id) != null;
    }

    public int count() {
        return store.size();
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
}
