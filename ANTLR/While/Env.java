import java.util.Collection;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

public class Env {

  static Map<String, Integer> m = new HashMap<>();

  public static void insert(String var, int value) {
    m.put(var, value);
  }

  public static void delete(String var) {
    m.remove(var);
  }

  public static int lookup(String var) {
    return m.get(var);
  }
}