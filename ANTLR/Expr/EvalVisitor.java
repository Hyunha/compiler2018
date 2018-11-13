import java.util.HashMap;
import java.util.Map;

public class EvalVisitor extends LExprBaseVisitor<Integer> {

	@Override
	public Integer visitProg(LExprParser.ProgContext ctx) {
		return visit(ctx.expr());
	}

	@Override public Integer visitDiv(LExprParser.DivContext ctx) {
		int left = visit(ctx.expr(0));
		int right = visit(ctx.expr(1));
		return left / right;
	}

	@Override public Integer visitMult(LExprParser.MultContext ctx) {
		int left = visit(ctx.expr(0));
		int right = visit(ctx.expr(1));
		return left * right;
	}

	@Override public Integer visitParens(LExprParser.ParensContext ctx) {
		return visit(ctx.expr()); 
	}

	@Override public Integer visitPlus(LExprParser.PlusContext ctx) {
		int left = visit(ctx.expr(0));
		int right = visit(ctx.expr(1));
		return left + right;
	}

	@Override public Integer visitInt(LExprParser.IntContext ctx) {
		return Integer.valueOf(ctx.INT().getText());
	}

	@Override public Integer visitMinus(LExprParser.MinusContext ctx) {
		int left = visit(ctx.expr(0));
		int right = visit(ctx.expr(1));
		return left - right;
	}
}