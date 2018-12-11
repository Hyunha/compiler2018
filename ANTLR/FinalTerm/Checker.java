import java.util.*;

class Checker {
///// 2.1
/////
///// Type checker
/////
  // 타입 검사를 수행합니다.
  boolean type_checker(Ast ast) {
    return true;
  }

  TypeEnv type_checker_decl(Declaration d) {
    return new TypeEnv();
  }

  boolean type_checker_stmt(Statement s, TypeEnv te) {
    return true;
  }

  boolean type_checker_expr(Statement s, TypeEnv te) {
    return true;
  }

///// 2.1
/////
///// Declared variables
/////
  // 프로그램에서 선언만하고 사용되지 않은 변수의 집합을 모읍니다.
  HashSet declared_variables(Ast ast) {
    return unused_variables_decl(ast.d);
  }

  // Declared variables - declared_variables_variables_decl
  // 선언된 변수의 집합을 모읍니다.
  HashSet unused_variables_decl(Declaration d) {
    return new HashSet();
  }

///// 2.2
/////
///// Used variables
/////
  // 프로그램에서 사용한 변수의 집합을 모읍니다.
  HashSet used_variables(Ast ast) {
    return used_variables_stmt(ast.s, new HashSet());
  }

  // Used variables - declared_variables_variables_decl
  // 선언된 변수의 집합을 모읍니다.
  // Used variables - used_variables_stmt
  HashSet used_variables_stmt(Statement s, HashSet vars) {
    return vars;
  }

  // Used variables - used_variables_expr
  HashSet used_variables_expr(Expr e, HashSet vars) {
    return vars;
  }

///// 2.3
/////
///// use of undefined variables
/////
  // 프로그램에서 선언만하고 사용되지 않은 변수가 있는지 확인합니다.
  boolean use_of_undefined_variables(Ast ast) {
    return false;
  }

  // ...
}