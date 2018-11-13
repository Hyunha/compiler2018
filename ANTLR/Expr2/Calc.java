
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class Calc {
  public static void main( String[] args) throws Exception {
    File file = new File(args[0]);
    FileInputStream fis = null;
    try {
        fis = new FileInputStream(file);
        CharStream inp = CharStreams.fromStream(fis);
        LExprLexer lexer = new LExprLexer(inp);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        LExprParser parser = new LExprParser(tokens);
        ParseTree tree = parser.prog();
        EvalVisitor eval = new EvalVisitor();
        int r = eval.visit(tree);
        System.out.println(r);
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        try {
          if (fis != null) {
            fis.close();
          }
        } catch (IOException ex) {
          ex.printStackTrace();
        }
    }
  }
}