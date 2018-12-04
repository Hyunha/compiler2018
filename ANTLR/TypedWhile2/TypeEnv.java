import java.util.Collection;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

public class TypeEnv {

  Map<String, Type> m;

  public TypeEnv() {
    m = new HashMap<>();
  }

  TypeEnv clear() {
    m = new HashMap<>();
    return this;
  }

  TypeEnv insert(String var, Type ty) {
    this.m.put(var, ty);
    return this;
  }

  TypeEnv delete(String var) {
    this.m.remove(var);
    return this;
  }

  Type lookup(String var) {
    if (m.containsKey(var))
      return m.get(var);
    else {
      System.out.println("undefined var : " + var);
      return new VoidType();
    }
  }
}