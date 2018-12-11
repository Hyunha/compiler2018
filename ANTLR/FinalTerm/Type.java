public class Type extends Node {
  int tv;
  boolean is_equals(Type t) {
    return t.tv == this.tv;
  }

  boolean is_int() {
    return is_equals(new IntType());
  }

  boolean is_bool() {
    return is_equals(new BoolType());
  }
}

class VoidType extends Type {
  int tv = 0;

  public String toString() {
    return "?";
  }
};

class IntType extends Type {
  IntType() {
    tv = 1;
  }

  public String toString() {
    return "int";
  }
};
class BoolType extends Type {
  BoolType() {
    tv = 2;
  }

  public String toString() {
    return "bool";
  }
};
