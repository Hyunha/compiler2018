// Generated from While.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import javafx.util.*;

public class ASTBuilderVisitor extends WhileBaseVisitor<Node> {

  TypeEnv te;

  @Override public Node visitProg(WhileParser.ProgContext ctx) {
    Declaration d = (Declaration)visit(ctx.var_decls());
    te = d.execute(new TypeEnv());
    Statement s = (Statement)visit(ctx.stmts());
    return new Ast(d, s);
  }

  @Override public Node visitVarDecs(WhileParser.VarDecsContext ctx) {
    Declaration ds = (Declaration)visit(ctx.var_decls());
    Declaration d = (Declaration)visit(ctx.var_decl());
    if (ds.getClass() == Decls.class) {
      return ((Decls)ds).append(d);
    } else {
      return new Decls().append(ds).append(d);
    }
  }

  @Override public Node visitVarDec(WhileParser.VarDecContext ctx) {
    return visit(ctx.var_decl());
  }

  @Override public Node visitVar_decl(WhileParser.Var_declContext ctx) {
    Type t = (Type)visit(ctx.type_exp());
    Variable v = new Variable(ctx.ID().getText(), t);
    return new VarDecl(v);
  }

  @Override public Node visitIntType(WhileParser.IntTypeContext ctx) {
    return new IntType();
  }

  @Override public Node visitBoolType(WhileParser.BoolTypeContext ctx) {
    return new BoolType();
  }

  @Override public Node visitStmt_list(WhileParser.Stmt_listContext ctx) {
    Statement ss = (Statement)visit(ctx.stmts());
    Statement s = (Statement)visit(ctx.stmt());
    if (ss.getClass() == Stmts.class) {
      return ((Stmts)ss).append(s);
    } else {
      return new Stmts().append(ss).append(s);
    }
  }

  @Override public Node visitStmt_single(WhileParser.Stmt_singleContext ctx) {
    return (Statement)visit(ctx.stmt());
  }

  @Override public Node visitAssign(WhileParser.AssignContext ctx) {
    String name = ctx.ID().getText();
    Type t = te.lookup(name);
    Variable v = new Variable(name, t);
    Expr e = (Expr)visit(ctx.expr());
    return new Assign(v, e);
  }

  @Override public Node visitPrint(WhileParser.PrintContext ctx) {
    Expr e = (Expr)visit(ctx.expr());
    return new Print(e);
  }

  @Override public Node visitSkip(WhileParser.SkipContext ctx) {
    return new Skip();
  }

  @Override public Node visitIf0(WhileParser.If0Context ctx) {
    Expr cond = (Expr)visit(ctx.expr());
    Statement s = (Statement)visit(ctx.stmt());
    return new If0(cond, s);
  }

  @Override public Node visitIf(WhileParser.IfContext ctx) {
    Expr cond = (Expr)visit(ctx.expr());
    Statement st = (Statement)visit(ctx.stmt(0));
    Statement sf = (Statement)visit(ctx.stmt(1));
    return new If(cond, st, sf);
  }

  @Override public Node visitWhile(WhileParser.WhileContext ctx) {
    Expr cond = (Expr)visit(ctx.expr());
    Statement s = (Statement)visit(ctx.stmts());
    return new While(cond, s);
  }

  @Override public Node visitBlock(WhileParser.BlockContext ctx) {
    return visit(ctx.stmts());
  }

  @Override public Node visitE_Bop(WhileParser.E_BopContext ctx) {
    Expr left = (Expr)visit(ctx.expr(0));
    Expr right = (Expr)visit(ctx.expr(1));
    BinaryOperator op = (BinaryOperator)visit(ctx.bop());
    return new E_Bop(left, op, right);
  }

  @Override public Node visitE_Int(WhileParser.E_IntContext ctx) {
    return new E_Int(Integer.valueOf(ctx.INT().getText()));
  }

  @Override public Node visitE_Var(WhileParser.E_VarContext ctx) {
    String name = ctx.ID().getText();
    Type t = te.lookup(name);
    Variable v = new Variable(name, t);
    return new E_Var(v);
  }

  @Override public Node visitE_Paren(WhileParser.E_ParenContext ctx) {
    return (Expr)visit(ctx.expr());
  }

  @Override public Node visitE_True(WhileParser.E_TrueContext ctx) {
    return new E_True();
  }

  @Override public Node visitE_False(WhileParser.E_FalseContext ctx) {
    return new E_False();
  }

  @Override public Node visitE_Not(WhileParser.E_NotContext ctx) {
    return new E_Not((Expr)visit(ctx.expr()));
  }

  @Override public Node visitROP_GT(WhileParser.ROP_GTContext ctx) {
    return new RopGT();
  }

  @Override public Node visitROP_LT(WhileParser.ROP_LTContext ctx) {
    return new RopLT();
  }

  @Override public Node visitBOP_AND(WhileParser.BOP_ANDContext ctx) {
    return new BopAND();
  }

  @Override public Node visitBOP_OR(WhileParser.BOP_ORContext ctx) {
    return new BopOR();
  }

  @Override public Node visitAOP_PLUS(WhileParser.AOP_PLUSContext ctx) {
    return new AopPLUS();
  }

  @Override public Node visitAOP_MINUS(WhileParser.AOP_MINUSContext ctx) {
    return new AopMINUS();
  }

  @Override public Node visitAOP_MULT(WhileParser.AOP_MULTContext ctx) {
    return new AopMULT();
  }

  @Override public Node visitAOP_DIV(WhileParser.AOP_DIVContext ctx) {
    return new AopDIV();
  }
}