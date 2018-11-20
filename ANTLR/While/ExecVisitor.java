// Generated from While.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

public class ExecVisitor extends WhileBaseVisitor<Integer> {

  @Override public Integer visitProg(WhileParser.ProgContext ctx) {
    visit(ctx.stmts());
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
    Env.insert(ctx.ID().getText(), visit(ctx.expr()));
    return 0;
  }

  @Override public Integer visitPrint(WhileParser.PrintContext ctx) {
    System.out.println(visit(ctx.expr()));
    return 0;
  }

  @Override public Integer visitVar(WhileParser.VarContext ctx) {
    return Env.lookup(ctx.ID().getText());
  }

  @Override public Integer visitParens(WhileParser.ParensContext ctx) {
    return visit(ctx.expr());
  }

  @Override public Integer visitInt(WhileParser.IntContext ctx) {
    return Integer.valueOf(ctx.INT().getText());
  }

  @Override public Integer visitBinaryOp(WhileParser.BinaryOpContext ctx) {
    int left = visit(ctx.expr(0));
    int right = visit(ctx.expr(1));
    switch (visit(ctx.bop())) {
      case 0: return left + right;
      case 1: return left - right;
      case 2: return left * right;
      default: return left / right;
    }
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