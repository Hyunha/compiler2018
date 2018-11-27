// Generated from While.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

public class ExecVisitor extends WhileBaseVisitor<Integer> {

  @Override public Integer visitProg(WhileParser.ProgContext ctx) {
    visit(ctx.stmts());
    return 0;
  }

  @Override public Integer visitVarDecs(WhileParser.VarDecsContext ctx) {
    return 0;
  }

  @Override public Integer visitVarDec(WhileParser.VarDecContext ctx) {
    return 0;
  }

  @Override public Integer visitVar_dec(WhileParser.Var_decContext ctx) {
    return 0;
  }

  @Override public Integer visitIntType(WhileParser.IntTypeContext ctx) {
    return 0;
  }

  @Override public Integer visitBoolType(WhileParser.BoolTypeContext ctx) {
    return 0;
  }

  @Override public Integer visitArrType(WhileParser.ArrTypeContext ctx) {
    return 0;
  }


  @Override public Integer visitStmt_list(WhileParser.Stmt_listContext ctx) {
    visit(ctx.stmts());
    visit(ctx.stmt());
    return 0;
  }

  @Override public Integer visitStmt_single(WhileParser.Stmt_singleContext ctx) {
    visit(ctx.stmt());
    return 0;
  }

  @Override public Integer visitAssign(WhileParser.AssignContext ctx) {
    Env.insert(ctx.ID().getText(), visit(ctx.aexp()));
    return 0;
  }

  @Override public Integer visitPrint(WhileParser.PrintContext ctx) {
    System.out.println(visit(ctx.aexp()));
    return 0;
  }

  @Override public Integer visitIf(WhileParser.IfContext ctx) {
    if (visit(ctx.bexp()) != 0)
      visit(ctx.stmt());
    return 0;
  }

  @Override public Integer visitVar(WhileParser.VarContext ctx) {
    return Env.lookup(ctx.ID().getText());
  }

  @Override public Integer visitParens(WhileParser.ParensContext ctx) {
    return visit(ctx.aexp());
  }

  @Override public Integer visitInt(WhileParser.IntContext ctx) {
    return Integer.valueOf(ctx.INT().getText());
  }

  @Override public Integer visitBinaryOp(WhileParser.BinaryOpContext ctx) {
    int left = visit(ctx.aexp(0));
    int right = visit(ctx.aexp(1));
    switch (visit(ctx.aop())) {
      case 0: return left + right;
      case 1: return left - right;
      case 2: return left * right;
      default: return left / right;
    }
  }


  @Override public Integer visitBETrue(WhileParser.BETrueContext ctx) {
    return 1;
  }

  @Override public Integer visitBEFalse(WhileParser.BEFalseContext ctx) {
    return 0;
  }

  @Override public Integer visitBENot(WhileParser.BENotContext ctx) {
    if (visit(ctx.bexp()) == 0)
      return 1;
    else
      return 0;
  }

  @Override public Integer visitBERop(WhileParser.BERopContext ctx) {
    int left = visit(ctx.aexp(0));
    int right = visit(ctx.aexp(1));
    boolean b;
    switch (visit(ctx.rop())) {
      case 0: b = left > right; break;
      default: b = left < right; break;
    }
    if (b)
      return 1;
    else
      return 0;
  }
  @Override public Integer visitBEBop(WhileParser.BEBopContext ctx) {
    int left = visit(ctx.bexp(0));
    int right = visit(ctx.bexp(1));
    switch (visit(ctx.bop())) {
      case 0: return left * right; // AND
      default: return left + right; // OR
    }
  }

  @Override public Integer visitRopGT(WhileParser.RopGTContext ctx) {
    return 0;
  }

  @Override public Integer visitRopLT(WhileParser.RopLTContext ctx) {
    return 1;
  }

  @Override public Integer visitBopAND(WhileParser.BopANDContext ctx) {
    return 0;
  }

  @Override public Integer visitBopOR(WhileParser.BopORContext ctx) {
    return 0;
  }


  @Override public Integer visitPlus(WhileParser.PlusContext ctx) {
    return 0;
  }

  @Override public Integer visitMinus(WhileParser.MinusContext ctx) {
    return 1;
  }

  @Override public Integer visitMult(WhileParser.MultContext ctx) {
    return 2;
  }

  @Override public Integer visitDiv(WhileParser.DivContext ctx) {
    return 3;
  }
}