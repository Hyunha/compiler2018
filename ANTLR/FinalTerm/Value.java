
public class Value {};

class IntValue extends Value {
  int v;
  IntValue(int v) {
    this.v = v;
  }

  public String toString() {
    return ""+v;
  }
}

class BoolValue extends Value {
  boolean v;
  BoolValue(boolean v) {
    this.v = v;
  }

  BoolValue negation() {
    this.v = !this.v;
    return this;
  }

  public String toString() {
    return ""+v;
  }
}

class NullValue extends Value { }