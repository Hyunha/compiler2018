import java.util.Collection;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

public class TypeEnv {
  static Map<String, Type> m = new HashMap<>();

  public static void insert(String var, Type ty) {
    m.put(var, ty);
  }

  public static void delete(String var) {
    m.remove(var);
  }

  public static Type lookup(String var) {
    if (m.containsKey(var))
      return m.get(var);
    else {
      System.out.println("undefined var : " + var);
      return new VoidType();
    }
  }
}