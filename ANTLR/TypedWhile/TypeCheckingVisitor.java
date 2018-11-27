// Generated from While.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import javafx.util.*;

public class TypeCheckingVisitor extends WhileBaseVisitor<Type> {

  @Override public Type visitProg(WhileParser.ProgContext ctx) {
    visit(ctx.var_decls());
    visit(ctx.stmts());
    return new VoidType();
  }

  @Override public Type visitVarDecs(WhileParser.VarDecsContext ctx) {
    visit(ctx.var_decls());
    visit(ctx.var_decl());
    return new VoidType();
  }

  @Override public Type visitVarDec(WhileParser.VarDecContext ctx) {
    visit(ctx.var_decl());
    return new VoidType();
  }

  @Override public Type visitVar_decl(WhileParser.Var_declContext ctx) {
    String id = ctx.ID().getText();
    Type typ = visit(ctx.type_exp());
    TypeEnv.insert(id, typ);
    return new VoidType();
  }

  @Override public Type visitIntType(WhileParser.IntTypeContext ctx) {
    return new IntType();
  }

  @Override public Type visitBoolType(WhileParser.BoolTypeContext ctx) {
    return new BoolType();
  }

  @Override public Type visitArrType(WhileParser.ArrTypeContext ctx) {
    return new ArrType(Integer.valueOf(ctx.INT().getText()), visit(ctx.type_exp()));
  }

  @Override public Type visitStmt_list(WhileParser.Stmt_listContext ctx) {
    visit(ctx.stmts());
    visit(ctx.stmt());
    return new VoidType();
  }

  @Override public Type visitStmt_single(WhileParser.Stmt_singleContext ctx) {
    visit(ctx.stmt());
    return new VoidType();
  }

  @Override public Type visitAssign(WhileParser.AssignContext ctx) {
    String id = ctx.ID().getText();
    Type of_id = TypeEnv.lookup(id);
    Type of_rvalue = visit(ctx.expr());
    if (!of_id.is_equals(of_rvalue)) {
      System.out.println("type mismatching : " + id);
    }
    return new VoidType();
  }

  @Override public Type visitExprA(WhileParser.ExprAContext ctx) {
    return visit(ctx.aexp());
  }

  @Override public Type visitExprB(WhileParser.ExprBContext ctx) {
    return visit(ctx.bexp());
  }

  @Override public Type visitPrint(WhileParser.PrintContext ctx) {
    Type of_rvalue = visit(ctx.aexp());
    if (!of_rvalue.is_int()) {
      System.out.println("type mismatching of print(" + ctx.aexp().getText() + ")");
    }
    return new VoidType();
  }

  @Override public Type visitIf(WhileParser.IfContext ctx) {
    if (!visit(ctx.bexp()).is_bool()) {
      System.out.println("type mismatching of if (" + ctx.bexp().getText() + ") ...");
    }
    visit(ctx.stmt());
    return new VoidType();
  }

  @Override public Type visitAE_Var(WhileParser.AE_VarContext ctx) {
    String id = ctx.ID().getText();
    return TypeEnv.lookup(id);
  }

  @Override public Type visitAE_Parens(WhileParser.AE_ParensContext ctx) {
    return visit(ctx.aexp());
  }

  @Override public Type visitAE_Int(WhileParser.AE_IntContext ctx) {
    return new IntType();
  }

  @Override public Type visitAE_OP(WhileParser.AE_OPContext ctx) {
    Type left = visit(ctx.aexp(0));
    if (left.tv != 1) {
      System.out.println("[left of AOP] : '" + ctx.aexp(0).getText() + "' is not int type.");
    }
    Type right = visit(ctx.aexp(1));
    if (!right.is_int()) {
      System.out.println("[right of AOP] : '" + ctx.aexp(1).getText() + "' is not int type.");
    }
    return new IntType();
  }

  @Override public Type visitBE_Parens(WhileParser.BE_ParensContext ctx) {
    return visit(ctx.bexp());
  }

  @Override public Type visitBE_BOP(WhileParser.BE_BOPContext ctx) {
    Type left = visit(ctx.bexp(0));
    if (!left.is_bool()) {
      System.out.println("[left of BOP] '" + ctx.bexp(0).getText() + "' is not bool type.");
    }
    Type right = visit(ctx.bexp(1));
    if (!right.is_bool()) {
      System.out.println("[right of BOP] '" + ctx.bexp(1).getText() + "' is not bool type.");
    }
    return new BoolType();
  }

  @Override public Type visitBE_ROP(WhileParser.BE_ROPContext ctx) {
    Type left = visit(ctx.aexp(0));
    if (!left.is_int()) {
      System.out.println("[left of ROP] '" + ctx.aexp(0).getText() + "' is not int type.");
    }
    Type right = visit(ctx.aexp(1));
    if (!right.is_int()) {
      System.out.println("[right of ROP] '" + ctx.aexp(0).getText() + "' is not int type.");
    }
    return new BoolType();
  }

  @Override public Type visitBE_True(WhileParser.BE_TrueContext ctx) {
    return new BoolType();
  }

  @Override public Type visitBE_False(WhileParser.BE_FalseContext ctx) {
    return new BoolType();
  }

  @Override public Type visitBE_Not(WhileParser.BE_NotContext ctx) {
    return visit(ctx.bexp());
  }

  @Override public Type visitBE_Var(WhileParser.BE_VarContext ctx) {
    String id = ctx.ID().getText();
    return TypeEnv.lookup(id);
  }

  @Override public Type visitROP_GT(WhileParser.ROP_GTContext ctx) {
    return new VoidType();
  }

  @Override public Type visitROP_LT(WhileParser.ROP_LTContext ctx) {
    return new VoidType();
  }

  @Override public Type visitBOP_AND(WhileParser.BOP_ANDContext ctx) {
    return new VoidType();
  }

  @Override public Type visitBOP_OR(WhileParser.BOP_ORContext ctx) {
    return new VoidType();
  }

  @Override public Type visitAOP_PLUS(WhileParser.AOP_PLUSContext ctx) {
    return new VoidType();
  }

  @Override public Type visitAOP_MINUS(WhileParser.AOP_MINUSContext ctx) {
    return new VoidType();
  }

  @Override public Type visitAOP_MULT(WhileParser.AOP_MULTContext ctx) {
    return new VoidType();
  }

  @Override public Type visitAOP_DIV(WhileParser.AOP_DIVContext ctx) {
    return new VoidType();
  }
}