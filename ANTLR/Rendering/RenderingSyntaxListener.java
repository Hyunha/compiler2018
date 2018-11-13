// Generated from Rendering.g4 by ANTLR 4.7.1

// import org.antlr.v4.runtime.ParserRuleContext;
// import org.antlr.v4.runtime.tree.ErrorNode;
// import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.TokenStream; 
import org.antlr.v4.runtime.misc.Interval;

/**
 * This class provides an empty implementation of {@link RenderingListener},
 * which can be extended to create a listener which only needs to handle a subset
 * of the available methods.
 */
public class RenderingSyntaxListener implements RenderingBaseListener {
	RenderingParser parser;

	public RenderingSyntaxListener(RenderingParser parser) {
		this.parser = parser;
	}
	@Override public void enterDocument(RenderingParser.DocumentContext ctx) {
		System.out.println("<html>");
	}

	@Override public void exitDocument(RenderingParser.DocumentContext ctx) {
		System.out.println("</html>");
	}

	@Override public void enterSHARP(RenderingParser.SHARPContext ctx) { }

	@Override public void exitSHARP(RenderingParser.SHARPContext ctx) { }

	@Override public void enterSTAR(RenderingParser.STARContext ctx) { }

	@Override public void exitSTAR(RenderingParser.STARContext ctx) { }

	@Override public void enterNORMAL(RenderingParser.NORMALContext ctx) { }

	@Override public void exitNORMAL(RenderingParser.NORMALContext ctx) { }

}