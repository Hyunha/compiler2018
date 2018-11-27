// Generated from While.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import javafx.util.*;

public class ExecVisitor extends WhileBaseVisitor<Integer> {

  @Override public Integer visitProg(WhileParser.ProgContext ctx) {
    visit(ctx.stmts());
    return 0;
  }

  @Override public Integer visitVarDecs(WhileParser.VarDecsContext ctx) {
    visit(ctx.var_decls());
    visit(ctx.var_decl());
    return 0;
  }

  @Override public Integer visitVarDec(WhileParser.VarDecContext ctx) {
    visit(ctx.var_decl());
    return 0;
  }

  @Override public Integer visitVar_decl(WhileParser.Var_declContext ctx) {
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
    Env.insert(ctx.ID().getText(), visit(ctx.expr()));
    return 0;
  }

  @Override public Integer visitExprA(WhileParser.ExprAContext ctx) {
    return visit(ctx.aexp());
  }

  @Override public Integer visitExprB(WhileParser.ExprBContext ctx) {
    return visit(ctx.bexp());
  }

  @Override public Integer visitPrint(WhileParser.PrintContext ctx) {
    System.out.println(visit(ctx.aexp()));
    return 0;
  }

  @Override public Integer visitIf(WhileParser.IfContext ctx) {
    if (visit(ctx.bexp()) != 0) {
      visit(ctx.stmt());
    }
    return 0;
  }

  @Override public Integer visitAE_Var(WhileParser.AE_VarContext ctx) {
    String id = ctx.ID().getText();
    return Env.lookup(id);
  }

  @Override public Integer visitAE_Parens(WhileParser.AE_ParensContext ctx) {
    return visit(ctx.aexp());
  }

  @Override public Integer visitAE_Int(WhileParser.AE_IntContext ctx) {
    return Integer.valueOf(ctx.INT().getText());
  }

  @Override public Integer visitAE_OP(WhileParser.AE_OPContext ctx) {
    int left = visit(ctx.aexp(0));
    int right = visit(ctx.aexp(1));
    switch (visit(ctx.aop())) {
      case 0: return left + right;
      case 1: return left - right;
      case 2: return left * right;
      default: return left / right;
    }
  }

  @Override public Integer visitBE_Parens(WhileParser.BE_ParensContext ctx) {
    return visit(ctx.bexp());
  }

  @Override public Integer visitBE_BOP(WhileParser.BE_BOPContext ctx) {
    int left = visit(ctx.bexp(0));
    int right = visit(ctx.bexp(1));
    switch (visit(ctx.bop())) {
      case 0: return left * right;
      default: return left + right;
    }
  }

  @Override public Integer visitBE_ROP(WhileParser.BE_ROPContext ctx) {
    int left = visit(ctx.aexp(0));
    int right = visit(ctx.aexp(1));
    boolean r;
    switch (visit(ctx.rop())) {
      case 0: r = left > right; break;
      default: r = left < right; break;
    }
    return r ? 1 : 0;
  }

  @Override public Integer visitBE_True(WhileParser.BE_TrueContext ctx) {
    return 1;
  }

  @Override public Integer visitBE_False(WhileParser.BE_FalseContext ctx) {
    return 0;
  }

  @Override public Integer visitBE_Not(WhileParser.BE_NotContext ctx) {
    return visit(ctx.bexp()) == 1 ? 0 : 1;
  }

  @Override public Integer visitBE_Var(WhileParser.BE_VarContext ctx) {
    String id = ctx.ID().getText();
    return Env.lookup(id);
  }

  @Override public Integer visitROP_GT(WhileParser.ROP_GTContext ctx) {
    return 0;
  }

  @Override public Integer visitROP_LT(WhileParser.ROP_LTContext ctx) {
    return 1;
  }

  @Override public Integer visitBOP_AND(WhileParser.BOP_ANDContext ctx) {
    return 0;
  }

  @Override public Integer visitBOP_OR(WhileParser.BOP_ORContext ctx) {
    return 1;
  }


  @Override public Integer visitAOP_PLUS(WhileParser.AOP_PLUSContext ctx) {
    return 0;
  }

  @Override public Integer visitAOP_MINUS(WhileParser.AOP_MINUSContext ctx) {
    return 1;
  }

  @Override public Integer visitAOP_MULT(WhileParser.AOP_MULTContext ctx) {
    return 2;
  }

  @Override public Integer visitAOP_DIV(WhileParser.AOP_DIVContext ctx) {
    return 3;
  }
}