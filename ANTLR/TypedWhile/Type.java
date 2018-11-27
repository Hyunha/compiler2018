public class Type {
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

class VoidType extends Type { int tv = 0; };

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
class ArrType extends Type {
  int size;
  Type elt;
  ArrType() {
    tv = 3;
  }

  ArrType(int size, Type elt) {
    this.size = size;
    this.elt = elt;
  }

  boolean is_equals(Type t) {
    if (t instanceof ArrType) {
      return (this.size == ((ArrType)t).size) && this.elt.is_equals(((ArrType)t).elt);
    } else {
      return false;
    }
  }

  public String toString() {
    return "arr["+this.size+"] of " + this.elt;
  }
};