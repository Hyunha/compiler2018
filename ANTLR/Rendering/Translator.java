
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class Translator {
  public static void main( String[] args) throws Exception {
    File file = new File(args[0]);
    FileInputStream fis = null;
    try {
        fis = new FileInputStream(file);
        CharStream inp = CharStreams.fromStream(fis);
        RenderingLexer lexer = new RenderingLexer(inp);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        RenderingParser parser = new RenderingParser(tokens);
        ParseTree tree = parser.document();

        ParseTreeWalker walker = new ParseTreeWalker();
        TranslatorListener listener = new TranslatorListener();
        walker.walk(listener, tree);
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