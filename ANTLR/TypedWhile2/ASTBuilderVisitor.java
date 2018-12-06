// Generated from While.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import javafx.util.*;

public class ASTBuilderVisitor extends WhileBaseVisitor<Node> {

  TypeEnv te;

  @Override public Node visitProg(WhileParser.ProgContext ctx) {
    Declaration d = (Declaration)visit(ctx.var_decls());
    te = d.execute(new TypeEnv());
    Statement s = (Statement)visit(ctx.stmts());
    return new AstWithEval(d, s);
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

  @Override public Node visitArrType(WhileParser.ArrTypeContext ctx) {
    int size = Integer.valueOf(ctx.INT().getText());
    Type t = (Type)visit(ctx.type_exp());
    return new ArrType(size, t);
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
    Expression e = (Expression)visit(ctx.expr());
    return new Assign(v, e);
  }

  @Override public Node visitExprA(WhileParser.ExprAContext ctx) {
    return (Aexp)visit(ctx.aexp());
  }

  @Override public Node visitExprB(WhileParser.ExprBContext ctx) {
    return (Bexp)visit(ctx.bexp());
  }

  @Override public Node visitPrint(WhileParser.PrintContext ctx) {
    Aexp e = (Aexp)visit(ctx.aexp());
    return new Print(e);
  }

  @Override public Node visitSkip(WhileParser.SkipContext ctx) {
    return new Skip();
  }

  @Override public Node visitIf(WhileParser.IfContext ctx) {
    Bexp cond = (Bexp)visit(ctx.bexp());
    Statement s = (Statement)visit(ctx.stmt());
    return new If(cond, s);
  }

  @Override public Node visitWhile(WhileParser.WhileContext ctx) {
    Bexp cond = (Bexp)visit(ctx.bexp());
    Statement s = (Statement)visit(ctx.stmts());
    return new While(cond, s);
  }

  @Override public Node visitAE_Var(WhileParser.AE_VarContext ctx) {
    String name = ctx.ID().getText();
    Type t = te.lookup(name);
    Variable v = new Variable(name, t);
    return new AEid(v);
  }

  @Override public Node visitAE_Parens(WhileParser.AE_ParensContext ctx) {
    return (Aexp)visit(ctx.aexp());
  }

  @Override public Node visitAE_Int(WhileParser.AE_IntContext ctx) {
    return new AEint(Integer.valueOf(ctx.INT().getText()));
  }

  @Override public Node visitAE_OP(WhileParser.AE_OPContext ctx) {
    Aexp left = (Aexp)visit(ctx.aexp(0));
    Aexp right = (Aexp)visit(ctx.aexp(1));
    Aop aop = (Aop)visit(ctx.aop());
    return new AEop(left, aop, right);
  }

  @Override public Node visitBE_Parens(WhileParser.BE_ParensContext ctx) {
    return (Bexp)visit(ctx.bexp());
  }

  @Override public Node visitBE_BOP(WhileParser.BE_BOPContext ctx) {
    Bexp left = (Bexp)visit(ctx.bexp(0));
    Bexp right = (Bexp)visit(ctx.bexp(1));
    Bop bop = (Bop)visit(ctx.bop());
    return new BEop(left, bop, right);
  }

  @Override public Node visitBE_ROP(WhileParser.BE_ROPContext ctx) {
    Aexp left = (Aexp)visit(ctx.aexp(0));
    Aexp right = (Aexp)visit(ctx.aexp(1));
    Rop rop = (Rop)visit(ctx.rop());
    return new BErop(left, rop, right);
  }

  @Override public Node visitBE_True(WhileParser.BE_TrueContext ctx) {
    return new BEtrue();
  }

  @Override public Node visitBE_False(WhileParser.BE_FalseContext ctx) {
    return new BEfalse();
  }

  @Override public Node visitBE_Not(WhileParser.BE_NotContext ctx) {
    return new BEnot((Bexp)visit(ctx.bexp()));
  }

  @Override public Node visitBE_Var(WhileParser.BE_VarContext ctx) {
    String name = ctx.ID().getText();
    Type t = te.lookup(name);
    Variable v = new Variable(name, t);
    return new BEid(v);
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