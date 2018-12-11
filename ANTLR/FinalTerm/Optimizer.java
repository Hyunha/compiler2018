class Optimizer {
  static boolean changed = false;

  static boolean opt_ce = false;
  static boolean opt_eb = false;
  static boolean opt_rs = false;
  static boolean opt_cf = false;
  static boolean opt_rrd = false;

  Ast run(Ast ast) {
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
      if (opt_rrd)
        ast = smp.remove_redundant_declaration(ast);
    } while(changed);
    return ast;
  }

  class Simplifier {
///// 2.4
/////
///// remove redundant declaration
/////
    // 선언되었지만 아무곳에서도 사용되지 않는 변수의 선언을 찾아서 삭제합니다.
    Ast remove_redundant_declaration(Ast ast) {
      return ast;
    }

    Declaration rrd_decl(Declaration d) {
      return d;
    }

    // constant folding
    // - Expr 에서 변수 없이 상수의 계산만으로 구성된 계산식을 상수값으로 치환합니다.
    Ast constant_folding(Ast ast) {
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
        Expr e = ((Print)s).e;
        Value v = e.eval(new Env());
        return new Print(e.fromValue(v));
      } else if (s instanceof If0) {
        Expr cond = (Expr)(((If0)s).e);
        Statement body = ((If0)s).s;
        Value v = cond.eval(new Env());
        body = constant_folding_Stmt(body);
        return new If0(cond.fromValue(v), body);
      } else if (s instanceof If) {
        Expr cond = (Expr)(((If)s).e);
        Statement st = ((If)s).st;
        Statement sf = ((If)s).sf;
        Value v = cond.eval(new Env());
        st = constant_folding_Stmt(st);
        sf = constant_folding_Stmt(sf);
        return new If(cond.fromValue(v), st, sf);
      } else if (s instanceof While) {
        Expr cond = (Expr)(((While)s).e);
        Statement body = ((While)s).s;
        Value v = cond.eval(new Env());
        body = constant_folding_Stmt(body);
        return new While(cond.fromValue(v), body);
      } else if (s instanceof Assign) {
        Variable lhs = ((Assign)s).lhs;
        Expr rhs = ((Assign)s).rhs;
        Value v = rhs.eval(new Env());
        return new Assign(lhs, rhs.fromValue(v));
      } else {
        return s;
      }
    }

    // replace empty Stmts to Skip statement.
    // - Stmts 에서 Vector ss 가 비어있는 경우, Skip으로 대체합니다.
    Ast remove_empty_blocks(Ast ast) {
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
      } else if (s instanceof If0) {
        Expr cond = (Expr)(((If0)s).e);
        Statement body = ((If0)s).s;
        body = remove_empty_blocks_Stmt(body);
        return new If0(cond, body);
      } else if (s instanceof If) {
        Expr cond = (Expr)(((If)s).e);
        Statement st = ((If)s).st;
        Statement sf = ((If)s).sf;
        st = remove_empty_blocks_Stmt(st);
        sf = remove_empty_blocks_Stmt(sf);
        return new If(cond, st, sf);
      } else if (s instanceof While) {
        Expr cond = (Expr)(((While)s).e);
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
    Ast remove_skip(Ast ast) {
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
      } else if (s instanceof If0) {
        Expr cond = (Expr)(((If0)s).e);
        Statement body = ((If0)s).s;
        body = remove_skip_Stmt(body);
        if (body instanceof Skip) {
          changed = true;
          return new Skip();
        } else {
          return new If0(cond, body);
        }
      } else if (s instanceof If) {
        Expr cond = (Expr)(((If)s).e);
        Statement st = ((If)s).st;
        Statement sf = ((If)s).sf;
        st = remove_skip_Stmt(st);
        sf = remove_skip_Stmt(sf);
        if (sf instanceof Skip) {
          changed = true;
          return new If0(cond, st);
        } else {
          return new If(cond, st, sf);
        }
      } else if (s instanceof While) {
        Expr cond = (Expr)(((While)s).e);
        Statement body = ((While)s).s;
        body = remove_skip_Stmt(body);
        return new While(cond, body);
      } else {
        return s;
      }
    }
  }

  class DeadCode {
    // remove deadcode
    // - if([true]) s fi => s
    // - if([false]) s fi => skip
    // - while([false]) do skip od => skip
    Ast simpleCondElimination(Ast ast) {
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
      } else if (s instanceof If0) {
        Expr cond = (Expr)(((If0)s).e);
        Statement body = ((If0)s).s;
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
          return new If0(cond, body);
        }
      } else if (s instanceof If) {
        Expr cond = (Expr)(((If)s).e);
        Statement st = ((If)s).st;
        Statement sf = ((If)s).sf;
        Value v = cond.eval(new Env());
        if (v instanceof BoolValue) {
          changed = true;
          if (((BoolValue)v).v) {
            st = simpleCE_Stmt(st);
            return st;
          } else {
            sf = simpleCE_Stmt(sf);
            return sf;
          }
        } else {
          st = simpleCE_Stmt(st);
          sf = simpleCE_Stmt(sf);
          return new If(cond, st, sf);
        }
      } else if (s instanceof While) {
        Expr cond = (Expr)(((While)s).e);
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


