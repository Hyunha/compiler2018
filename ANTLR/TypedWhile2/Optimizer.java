class Optimizer {
  static boolean changed = false;

  AstWithEval run(AstWithEval ast) {
    DeadCode dc = new DeadCode();
    Simplifier smp = new Simplifier();
    do {
      changed = false;
      ast = dc.simpleCondElimination(ast);
      ast = smp.remove_empty_blocks(ast);
      ast = smp.remove_skip(ast);
    } while(changed);
    return ast;
  }

  class Simplifier {
    // replace empty Stmts to Skip statement.
    // - Stmts 에서 Vector ss 가 비어있는 경우, Skip으로 대체합니다.
    AstWithEval remove_empty_blocks(AstWithEval ast) {
      return ast;
    }

    // remove useless Skip statements.
    // - 의미없는 skip 문장을 삭제합니다.
    // - skip만 포함한 if 문을 삭제합니다.
    AstWithEval remove_skip(AstWithEval ast) {
      return ast;
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
      } else {
        return s;
      }
    }
  }
}


