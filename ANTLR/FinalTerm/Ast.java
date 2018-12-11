import java.util.*;

public class Ast extends Node {
  Declaration d;
  Statement s;

  Ast(Declaration d, Statement s) {
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
  Variable lhs; /* = */ Expr rhs;
  Assign(Variable lhs, Expr rhs) {
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
  /*print*/ Expr e;
  Print(Expr e) { this.e = e; }

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

class If0 extends Statement {
  /*if*/ Expr e; /*then*/ Statement s;
  If0(Expr e, Statement s) {
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

class If extends Statement {
  /*if*/ Expr e;
    /*then*/ Statement st;
    /*else*/ Statement sf;
  If(Expr e, Statement st, Statement sf) {
    this.e = e; this.st = st; this.sf = sf;
  }

  Env execute(Env env) {
    Value e = this.e.eval(env);
    if (e instanceof BoolValue) {
      if (((BoolValue)e).v) {
        return this.st.execute(env);
      } else {
        return this.sf.execute(env);
      }
    } else {
      System.out.println("unexpected type of value : " + e);
      return env;
    }
  }

  public String toString() {
    return "if (" + e.toString() + ") then\n" +
      st.toString() + "else\n" + sf.toString();
  }

  void print(int tab) {
    Util.print_tab(tab);
    System.out.println("if (" + e + ") then");
    st.print(tab + 1);
    Util.print_tab(tab);
    System.out.println("else");
    sf.print(tab + 1);
  }
}

class While extends Statement {
  /*while*/ Expr e; /*do*/ Statement s /*od*/;
  While(Expr e, Statement s) {
    this.e = e; this.s = s;
  }

  Env execute(Env env) {
    Value e = this.e.eval(env);
    if (e instanceof BoolValue) {
      if (((BoolValue)e).v) {
        return this.execute(this.s.execute(env));
      } else {
        return env;
      }
    } else {
      System.out.println("unexpected type of value : " + e);
      return env;
    }
  }

  public String toString() {
    return "while (" + e.toString() + ") do\n" +
      s.toString() + "od\n";
  }

  void print(int tab) {
    Util.print_tab(tab);
    System.out.println("while (" + e + ") do");
    s.print(tab + 1);
    System.out.println("");
    Util.print_tab(tab);
    System.out.print("od");
  }
}

class Expr extends Node {
  Value eval(Env env) {
    return new NullValue();
  }

  Expr fromValue(Value v) {
    if (v instanceof IntValue) {
      Expr e = new E_Int(((IntValue)v).v);
      return e;
    } else if (v instanceof BoolValue) {
      if (((BoolValue)v).v) {
        Expr e = new E_True();
        return e;
      } else {
        Expr e = new E_False();
        return e;
      }
    } else {
      return this;
    }
  }
};

class E_Bop extends Expr {
  Expr e1; BinaryOperator op; Expr e2;
  E_Bop(Expr e1, BinaryOperator op, Expr e2) {
    this.e1 = e1; this.op = op; this.e2 = e2;
  }

  Value eval(Env env) {
    if (op instanceof Rop) {
      IntValue v1 = (IntValue)this.e1.eval(env);
      IntValue v2 = (IntValue)this.e2.eval(env);
      if (this.op instanceof RopGT) {
        return new BoolValue(((IntValue)v1).v > ((IntValue)v2).v);
      } else if (this.op instanceof RopLT) {
        return new BoolValue(((IntValue)v1).v < ((IntValue)v2).v);
      } else {
        return new NullValue();
      }
    } else if (op instanceof Aop) {
      IntValue v1 = (IntValue)this.e1.eval(env);
      IntValue v2 = (IntValue)this.e2.eval(env);
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
    } else if (op instanceof Bop) {
      BoolValue v1 = (BoolValue)this.e1.eval(env);
      BoolValue v2 = (BoolValue)this.e2.eval(env);
      if (this.op instanceof BopAND) {
        return new BoolValue(((BoolValue)v1).v && ((BoolValue)v2).v);
      } else if (this.op instanceof BopOR) {
        return new BoolValue(((BoolValue)v1).v || ((BoolValue)v2).v);
      } else {
        return new NullValue();
      }
    } else {
      return new NullValue();
    }
  }

  public String toString() {
    return e1.toString() + " " + op.toString() + " " + e2.toString();
  }
};
class E_Int extends Expr {
  int num;
  E_Int(int num) { this.num = num; }

  Value eval(Env env) {
    return new IntValue(num);
  }

  public String toString() {
    return ""+num;
  }
}
class E_Var extends Expr {
  Variable v;
  E_Var(Variable v) { this.v = v; }

  Value eval(Env env) {
    return env.lookup(v.name);
  }

  public String toString() {
    return v.toString();
  }
}
class E_True extends Expr {
  Value eval(Env env) {
    return new BoolValue(true);
  }
  public String toString() {
    return "true";
  }
}
class E_False extends Expr {
  Value eval(Env env) {
    return new BoolValue(false);
  }
  public String toString() {
    return "false";
  }
}
class E_Not extends Expr {
  Expr e;
  E_Not(Expr e) { this.e = e; }

  Value eval(Env env) {
    Value v = this.e.eval(env);
    if (v instanceof BoolValue) {
      return ((BoolValue)v).negation();
    } else {
      return new NullValue();
    }
  }

  public String toString() {
    return "!(" + e.toString()+")";
  }
}

class BinaryOperator extends Node {};
class Aop extends BinaryOperator {};
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
class Bop extends BinaryOperator {};
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
class Rop extends BinaryOperator {};
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