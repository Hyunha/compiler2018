import java.util.Collection;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

public class Env {

  Map<String, Value> m;

  public Env() {
    m = new HashMap<>();
  }

  Env clear() {
    m = new HashMap<>();
    return this;
  }

  Env insert(String var, Value value) {
    this.m.put(var, value);
    return this;
  }

  Env delete(String var) {
    this.m.remove(var);
    return this;
  }

  Value lookup(String var) {
    if (m.containsKey(var))
      return m.get(var);
    else {
      System.out.println("undefined or uninitialized var : " + var);
      return new NullValue();
    }
  }
}