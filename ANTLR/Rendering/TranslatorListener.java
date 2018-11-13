import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

public class TranslatorListener extends RenderingBaseListener {

  @Override public void enterDocument(RenderingParser.DocumentContext ctx) {
    System.out.println("<html>");
  }

  @Override public void exitDocument(RenderingParser.DocumentContext ctx) {
    System.out.println("</html>");
  }

  @Override public void enterSHARP(RenderingParser.SHARPContext ctx) { }

  @Override public void exitSHARP(RenderingParser.SHARPContext ctx) {
    System.out.println(ctx.getText() + "<br>");
  }

  @Override public void enterSTAR(RenderingParser.STARContext ctx) { }

  @Override public void exitSTAR(RenderingParser.STARContext ctx) {
    System.out.println(ctx.getText() + "<br>");
  }

  @Override public void enterPLAINTEXT(RenderingParser.PLAINTEXTContext ctx) { }

  @Override public void exitPLAINTEXT(RenderingParser.PLAINTEXTContext ctx) {
    System.out.println(ctx.getText());
  }

  @Override public void enterHEADER(RenderingParser.HEADERContext ctx) { }

  @Override public void exitHEADER(RenderingParser.HEADERContext ctx) {
    int open = ctx.EQs(0).getText().length();
    int close = ctx.EQs(1).getText().length();
    if (open == close && open <= 6) {
      System.out.print("<h"+open+">");
      System.out.print(ctx.TEXT().getText());
      System.out.println("</h"+close+">");
    } else {
      System.out.print(ctx.EQs(0).getText());
      System.out.print(ctx.TEXT().getText());
      System.out.println(ctx.EQs(1).getText());
    }
  }

  @Override public void enterEveryRule(ParserRuleContext ctx) { }

  @Override public void exitEveryRule(ParserRuleContext ctx) { }

  @Override public void visitTerminal(TerminalNode node) { }

  @Override public void visitErrorNode(ErrorNode node) { }
}