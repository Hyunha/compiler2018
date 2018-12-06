
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class Run {
  public static void main( String[] args) throws Exception {
    File file = new File(args[0]);
    FileInputStream fis = null;
    for (String option : args) {
        if (option.equals("-ce")) {
            Optimizer.opt_ce = true;
        }
        if (option.equals("-eb")) {
            Optimizer.opt_eb = true;
        }
        if (option.equals("-rs")) {
            Optimizer.opt_rs = true;
        }
        if (option.equals("-cf")) {
            Optimizer.opt_cf = true;
        }
        if (option.equals("-optAll")) {
            Optimizer.opt_ce = true;
            Optimizer.opt_eb = true;
            Optimizer.opt_rs = true;
            Optimizer.opt_cf = true;
        }
    }
    try {
        fis = new FileInputStream(file);
        CharStream inp = CharStreams.fromStream(fis);
        WhileLexer lexer = new WhileLexer(inp);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        WhileParser parser = new WhileParser(tokens);
        ParseTree tree = parser.prog();

        ASTBuilderVisitor builder = new ASTBuilderVisitor();
        AstWithEval ast = (AstWithEval)builder.visit(tree);
        ast.print();
        System.out.println("excuting ...");
        ast.execute();
        System.out.println("optimization ...");
        Optimizer opt = new Optimizer();
        ast = opt.run(ast);
        ast.print();
        System.out.println("excuting ...");
        ast.execute();
        // TypeCheckingVisitor type_chk = new TypeCheckingVisitor();
        // System.out.println("type checking...");
        // type_chk.visit(tree);
        // System.out.println("type checking finished");
        // ExecVisitor exec = new ExecVisitor();
        // exec.visit(tree);
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