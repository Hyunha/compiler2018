import java.util.*;

public class Ast extends Node {
  Declaration d;
  Statement s;
  Ast(Declaration d, Statement s) {
    this.d = d; this.s = s;
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
}

class Declaration extends Node {};

class Decls extends Declaration {
  Vector<Declaration> ds;
  Decls() {
    ds = new Vector<Declaration>();
  }

  Decls append(Declaration d) {
    ds.add(d); return this;
  }
}

class VarDecl extends Declaration {
  Variable v;
  VarDecl(Variable v) {
    this.v = v;
  }
}

class Statement extends Node {};

class Stmts extends Statement {
  Vector<Statement> ss;
  Stmts() {
    ss = new Vector<Statement>();
  }

  Stmts append(Statement s) {
    ss.add(s);
    return this;
  }
}

class Assign extends Statement {
  Variable lhs; /* = */ Expression rhs;
  Assign(Variable lhs, Expression rhs) {
    this.lhs = lhs; this.rhs = rhs;
  }
}

class Print extends Statement {
  /*print*/ Expression e;
  Print(Expression e) { this.e = e; }
}

class If extends Statement {
  /*if*/ Expression e; /*then*/ Statement s;
  If(Expression e, Statement s) {
    this.e = e; this.s = s;
  }
}

class Expression extends Node {};

class Aexp extends Expression {};
class AEop extends Aexp {
  Aexp e1; Aop op; Aexp e2;
  AEop(Aexp e1, Aop op, Aexp e2) {
    this.e1 = e1; this.op = op; this.e2 = e2;
  }
};
class AEint extends Aexp {
  int num;
  AEint(int num) { this.num = num; }
}
class AEid extends Aexp {
  Variable v;
  AEid(Variable v) { this.v = v; }
}

class Bexp extends Expression {};
class BEop extends Bexp {
  Bexp e1; Bop op; Bexp e2;
  BEop(Bexp e1, Bop op, Bexp e2) {
    this.e1 = e1; this.op = op; this.e2 = e2;
  }
}
class BErop extends Bexp {
  Aexp e1; Rop op; Aexp e2;
  BErop(Aexp e1, Rop op, Aexp e2) {
    this.e1 = e1; this.op = op; this.e2 = e2;
  }
}
class BEtrue extends Bexp {}
class BEfalse extends Bexp {}
class BEnot extends Bexp {
  Bexp b;
  BEnot(Bexp b) { this.b = b; }
}
class BEid extends Bexp {
  Variable v;
  BEid(Variable v) { this.v = v; }
}

class Aop extends Node {};
class AopPLUS extends Aop {};
class AopMINUS extends Aop {};
class AopMULT extends Aop {};
class AopDIV extends Aop {};
class Bop extends Node {};
class BopAND extends Bop {};
class BopOR extends Bop {};
class Rop extends Node {};
class RopGT extends Rop {};
class RopLT extends Rop {};