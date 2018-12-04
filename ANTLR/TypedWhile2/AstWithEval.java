import java.util.*;

class Util {
  static String tab = "  ";

  static void print_tab(int size) {
    for(int i = 0; i < size; i++) {
      System.out.print(tab);
    }
  }
}

public class AstWithEval extends Node {
  Declaration d;
  Statement s;

  AstWithEval(Declaration d, Statement s) {
    this.d = d; this.s = s;
  }

  Env execute() {
    TypeEnv te = d.execute(new TypeEnv());
    Env env = new Env();
    return s.execute(env);
  }

  public String toString() {
    return d.toString() + ";\n" + s.toString();
  }

  void print() {
    System.out.println("- print code ----------------------------------");
    System.out.println(d.toString() + ";");
    s.print(0);
    System.out.println("\n-----------------------------------------------");
  }
}

class Variable extends Node {
  String name;
  Type t;

  public Variable() {
    this("", new VoidType());
  }

  public Variable(String name) {
    this(name, new VoidType());
  }

  public Variable(String name, Type t) {
    this.name = name;
    this.t = t;
  }

  public String toString() {
    return name + ":" + t;
  }
}

class Declaration extends Node {
  TypeEnv execute(TypeEnv te) {
    return te;
  }
};

class Decls extends Declaration {
  Vector<Declaration> ds;
  Decls() {
    ds = new Vector<Declaration>();
  }

  Decls append(Declaration d) {
    ds.add(d); return this;
  }

  TypeEnv execute(TypeEnv te) {
    for (Declaration d : ds) {
      te = d.execute(te);
    }
    return te;
  }

  public String toString() {
    String s = "";
    boolean fst = true;
    for (Declaration d : ds) {
      if (fst) {
        fst = false;
      } else {
        s += ";\n";
      }
      s += d.toString();
    }
    return s;
  }
}

class VarDecl extends Declaration {
  Variable v;
  VarDecl(Variable v) {
    this.v = v;
  }

  TypeEnv execute(TypeEnv te) {
    return te.insert(this.v.name, this.v.t);
  }

  public String toString() {
    return v.toString();
  }
}

class Statement extends Node {
  Env execute(Env env) {
    return env;
  }

  void print(int tab) {
    Util.print_tab(tab);
    System.out.print(this.toString());
  }
};

class Stmts extends Statement {
  Vector<Statement> ss;
  Stmts() {
    ss = new Vector<Statement>();
  }

  Stmts append(Statement s) {
    ss.add(s);
    return this;
  }

  Env execute(Env env) {
    for (Statement s : this.ss) {
      env = s.execute(env);
    }
    return env;
  }

  public String toString() {
    String s = "";
    boolean fst = true;
    for (Statement i : this.ss) {
      if (fst) {
        fst = false;
      } else {
        s += ";\n";
      }
      s += i;
    }
    return s;
  }

  void print(int tab) {
    boolean fst = true;
    for (Statement s : ss) {
      if (fst) {
        fst = false;
      } else {
        System.out.println(";");
      }
      s.print(tab);
    }
  }
}

class Assign extends Statement {
  Variable lhs; /* = */ Expression rhs;
  Assign(Variable lhs, Expression rhs) {
    this.lhs = lhs; this.rhs = rhs;
  }

  Env execute(Env env) {
    return env.insert(lhs.name, rhs.eval(env));
  }

  public String toString() {
    return lhs.toString() + " = " + rhs.toString();
  }
}

class Print extends Statement {
  /*print*/ Expression e;
  Print(Expression e) { this.e = e; }

  Env execute(Env env) {
    Value v = e.eval(env);
    System.out.println(v.toString());
    return env;
  }

  public String toString() {
    return "print (" + e.toString() +")";
  }
}

class Skip extends Statement {
  public String toString() {
    return "skip";
  }

  Env execute(Env env) {
    return env;
  }
}

class If extends Statement {
  /*if*/ Expression e; /*then*/ Statement s;
  If(Expression e, Statement s) {
    this.e = e; this.s = s;
  }

  Env execute(Env env) {
    Value e = this.e.eval(env);
    if (e instanceof BoolValue) {
      if (((BoolValue)e).v) {
        return this.s.execute(env);
      } else {
        return env;
      }
    } else {
      System.out.println("unexpected type of value : " + e);
      return env;
    }
  }

  public String toString() {
    return "if (" + e.toString() + ") then\n" +
      s.toString();
  }

  void print(int tab) {
    Util.print_tab(tab);
    System.out.println("if (" + e + ") then");
    s.print(tab + 1);
  }
}

class Expression extends Node {
  Value eval(Env env) {
    return new NullValue();
  }
};

class Aexp extends Expression {};
class AEop extends Aexp {
  Aexp e1; Aop op; Aexp e2;
  AEop(Aexp e1, Aop op, Aexp e2) {
    this.e1 = e1; this.op = op; this.e2 = e2;
  }

  Value eval(Env env) {
    Value v1 = this.e1.eval(env);
    Value v2 = this.e2.eval(env);
    if (v1 instanceof IntValue && v2 instanceof IntValue) {
      if (this.op instanceof AopPLUS) {
        return new IntValue(((IntValue)v1).v + ((IntValue)v2).v);
      } else if (this.op instanceof AopMINUS) {
        return new IntValue(((IntValue)v1).v - ((IntValue)v2).v);
      } else if (this.op instanceof AopMULT) {
        return new IntValue(((IntValue)v1).v * ((IntValue)v2).v);
      } else if (this.op instanceof AopDIV) {
        return new IntValue(((IntValue)v1).v / ((IntValue)v2).v);
      } else {
        return new NullValue();
      }
    } else {
      System.out.println("Unexpected type of values : " + v1 + "," + v2);
      return new NullValue();
    }
  }

  public String toString() {
    return e1.toString() + " " + op.toString() + " " + e2.toString();
  }
};
class AEint extends Aexp {
  int num;
  AEint(int num) { this.num = num; }

  Value eval(Env env) {
    return new IntValue(num);
  }

  public String toString() {
    return ""+num;
  }
}
class AEid extends Aexp {
  Variable v;
  AEid(Variable v) { this.v = v; }

  Value eval(Env env) {
    return env.lookup(v.name);
  }

  public String toString() {
    return v.toString();
  }
}

class Bexp extends Expression {
  Value eval(Env env) {
    return new NullValue();
  }
};
class BEop extends Bexp {
  Bexp e1; Bop op; Bexp e2;
  BEop(Bexp e1, Bop op, Bexp e2) {
    this.e1 = e1; this.op = op; this.e2 = e2;
  }

  Value eval(Env env) {
    Value v1 = this.e1.eval(env);
    Value v2 = this.e2.eval(env);
    if (v1 instanceof BoolValue && v2 instanceof BoolValue) {
      if (this.op instanceof BopAND) {
        return new BoolValue(((BoolValue)v1).v && ((BoolValue)v2).v);
      } else if (this.op instanceof BopOR) {
        return new BoolValue(((BoolValue)v1).v || ((BoolValue)v2).v);
      } else {
        return new NullValue();
      }
    } else {
      System.out.println("Unexpected type of values : " + v1 + "," + v2);
      return new NullValue();
    }
  }

  public String toString() {
    return e1.toString() + " " + op.toString() + " " + e2.toString();
  }
}
class BErop extends Bexp {
  Aexp e1; Rop op; Aexp e2;
  BErop(Aexp e1, Rop op, Aexp e2) {
    this.e1 = e1; this.op = op; this.e2 = e2;
  }

  Value eval(Env env) {
    Value v1 = this.e1.eval(env);
    Value v2 = this.e2.eval(env);
    if (v1 instanceof IntValue && v2 instanceof IntValue) {
      if (this.op instanceof RopGT) {
        return new BoolValue(((IntValue)v1).v > ((IntValue)v2).v);
      } else if (this.op instanceof RopLT) {
        return new BoolValue(((IntValue)v1).v < ((IntValue)v2).v);
      } else {
        return new NullValue();
      }
    } else {
      System.out.println("Unexpected type of values : " + v1 + "," + v2);
      return new NullValue();
    }
  }

  public String toString() {
    return e1.toString() + " " + op.toString() + " " + e2.toString();
  }
}
class BEtrue extends Bexp {
  Value eval(Env env) {
    return new BoolValue(true);
  }
  public String toString() {
    return "true";
  }
}
class BEfalse extends Bexp {
  Value eval(Env env) {
    return new BoolValue(false);
  }
  public String toString() {
    return "false";
  }
}
class BEnot extends Bexp {
  Bexp b;
  BEnot(Bexp b) { this.b = b; }

  Value eval(Env env) {
    Value v = this.b.eval(env);
    if (v instanceof BoolValue) {
      return ((BoolValue)v).negation();
    } else {
      return new NullValue();
    }
  }

  public String toString() {
    return "!(" + b.toString()+")";
  }
}
class BEid extends Bexp {
  Variable v;
  BEid(Variable v) { this.v = v; }

  Value eval(Env env) {
    return env.lookup(v.name);
  }

  public String toString() {
    return v.toString();
  }
}

class Aop extends Node {};
class AopPLUS extends Aop {
  public String toString() {
    return "+";
  }
};
class AopMINUS extends Aop {
  public String toString() {
    return "-";
  }
};
class AopMULT extends Aop {
  public String toString() {
    return "*";
  }
};
class AopDIV extends Aop {
  public String toString() {
    return "/";
  }
};
class Bop extends Node {};
class BopAND extends Bop {
  public String toString() {
    return "&";
  }
};
class BopOR extends Bop {
  public String toString() {
    return "|";
  }
};
class Rop extends Node {};
class RopGT extends Rop {
  public String toString() {
    return ">";
  }
};
class RopLT extends Rop {
  public String toString() {
    return "<";
  }
};