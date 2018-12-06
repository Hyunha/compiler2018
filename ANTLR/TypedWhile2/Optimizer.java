class Optimizer {
  static boolean changed = false;

  static boolean opt_ce = false;
  static boolean opt_eb = false;
  static boolean opt_rs = false;
  static boolean opt_cf = false;

  AstWithEval run(AstWithEval ast) {
    DeadCode dc = new DeadCode();
    Simplifier smp = new Simplifier();
    do {
      changed = false;
      if (opt_ce)
        ast = dc.simpleCondElimination(ast);
      if (opt_eb)
        ast = smp.remove_empty_blocks(ast);
      if (opt_rs)
        ast = smp.remove_skip(ast);
      if (opt_cf)
        ast = smp.constant_folding(ast);
    } while(changed);
    return ast;
  }

  class Simplifier {
    // replace empty Stmts to Skip statement.
    // - Expression 에서 항상 상수(IntValue, BoolValue)가 되는 계산식을 
    //   계산된 상수값으로 치환하세요.
    AstWithEval constant_folding(AstWithEval ast) {
      ast.s = constant_folding_Stmt(ast.s);
      return ast;
    }

    Statement constant_folding_Stmt(Statement s) {
      if (s instanceof Stmts) {
        Stmts block = new Stmts();
        for (Statement si : ((Stmts)s).ss) {
          block.append(constant_folding_Stmt(si));
        }
        return block;
      } else if (s instanceof Print) {
        Expression e = ((Print)s).e;
        Value v = e.eval(new Env());
        return new Print(e.fromValue(v));
      } else if (s instanceof If) {
        Bexp cond = (Bexp)(((If)s).e);
        Statement body = ((If)s).s;
        Value v = cond.eval(new Env());
        body = constant_folding_Stmt(body);
        return new If(cond.fromValue(v), body);
      } else if (s instanceof While) {
        Bexp cond = (Bexp)(((While)s).e);
        Statement body = ((While)s).s;
        Value v = cond.eval(new Env());
        body = constant_folding_Stmt(body);
        return new While(cond.fromValue(v), body);
      } else if (s instanceof Assign) {
        Variable lhs = ((Assign)s).lhs;
        Expression rhs = ((Assign)s).rhs;
        Value v = rhs.eval(new Env());
        return new Assign(lhs, rhs.fromValue(v));
      } else {
        return s;
      }
    }

    Expression constant_folding_E(Expression e) {
      return e;
    }

    Aexp constant_folding_AE(Aexp e) {
      return e;
    }

    Bexp constant_folding_BE(Bexp e) {
      return e;
    }

    // replace empty Stmts to Skip statement.
    // - Stmts 에서 Vector ss 가 비어있는 경우, Skip으로 대체합니다.
    AstWithEval remove_empty_blocks(AstWithEval ast) {
      ast.s = remove_empty_blocks_Stmt(ast.s);
      return ast;
    }

    Statement remove_empty_blocks_Stmt(Statement s) {
      if (s instanceof Stmts) {
        Stmts block = new Stmts();
        for (Statement si : ((Stmts)s).ss) {
          block.append(remove_empty_blocks_Stmt(si));
        }
        if (block.ss.isEmpty()) {
          changed = true;
          return new Skip();
        } else {
          return block;
        }
      } else if (s instanceof If) {
        Bexp cond = (Bexp)(((If)s).e);
        Statement body = ((If)s).s;
        body = remove_empty_blocks_Stmt(body);
        return new If(cond, body);
      } else if (s instanceof While) {
        Bexp cond = (Bexp)(((While)s).e);
        Statement body = ((While)s).s;
        body = remove_empty_blocks_Stmt(body);
        return new While(cond, body);
      } else {
        return s;
      }
    }

    // remove useless Skip statements.
    // - 의미없는 skip 문장을 삭제합니다.
    // - skip만 포함한 if 문을 삭제합니다.
    AstWithEval remove_skip(AstWithEval ast) {
      ast.s = remove_skip_Stmt(ast.s);
      return ast;
    }

    Statement remove_skip_Stmt(Statement s) {
      if (s instanceof Stmts) {
        Stmts block = new Stmts();
        for (Statement si : ((Stmts)s).ss) {
          si = remove_skip_Stmt(si);
          if (si instanceof Skip) {
            changed = true;
          } else {
            block.append(si);
          }
        }
        return block;
      } else if (s instanceof If) {
        Bexp cond = (Bexp)(((If)s).e);
        Statement body = ((If)s).s;
        body = remove_skip_Stmt(body);
        if (body instanceof Skip) {
          changed = true;
          return new Skip();
        } else {
          return new If(cond, body);
        }
      } else if (s instanceof While) {
        Bexp cond = (Bexp)(((While)s).e);
        Statement body = ((While)s).s;
        body = remove_skip_Stmt(body);
        return new While(cond, body);
      } else {
        return s;
      }
    }
  }

  class DeadCode {
    AstWithEval simpleCondElimination(AstWithEval ast) {
      ast.s = simpleCE_Stmt(ast.s);
      return ast;
    }

    Statement simpleCE_Stmt(Statement s) {
      if (s instanceof Stmts) {
        Stmts ss = new Stmts();
        for (Statement si : ((Stmts)s).ss) {
          ss.append(simpleCE_Stmt(si));
        }
        return ss;
      } else if (s instanceof If) {
        Bexp cond = (Bexp)(((If)s).e);
        Statement body = ((If)s).s;
        Value v = cond.eval(new Env());
        if (v instanceof BoolValue) {
          changed = true;
          if (((BoolValue)v).v) {
            body = simpleCE_Stmt(body);
            return body;
          } else {
            return new Skip();
          }
        } else {
          body = simpleCE_Stmt(body);
          return new If(cond, body);
        }
      } else if (s instanceof While) {
        Bexp cond = (Bexp)(((While)s).e);
        Statement body = ((While)s).s;
        Value v = cond.eval(new Env());
        if (v instanceof BoolValue) {
          if (((BoolValue)v).v) {
            body = simpleCE_Stmt(body);
            return new While(cond, body);
          } else {
            changed = true;
            return new Skip();
          }
        } else {
          body = simpleCE_Stmt(body);
          return new While(cond, body);
        }
      } else {
        return s;
      }
    }
  }
}


