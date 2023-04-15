import java.util.ArrayList;
import java.util.Set;
import org.junit.Test;
import symbol.DFA;
import symbol.ItemCategoy;
import symbol.ItemSet;
import symbol.LR0Item;
import symbol.NonTerminalSymbol;
import symbol.Production;
import symbol.ProductionInfo;
import symbol.SymbolType;
import symbol.TerminalSymbol;

/**
 * @author 曾佳宝
 * @date 2023/4/10 15:06
 */
public class Problem2Test {
  @Test
  public void TestGetClosure() {
    // 非终结符E,,T,F，E'
    NonTerminalSymbol E_dot = new NonTerminalSymbol("E'", SymbolType.NONTERMINAL);
    NonTerminalSymbol E = new NonTerminalSymbol("E", SymbolType.NONTERMINAL);
    NonTerminalSymbol T = new NonTerminalSymbol("T", SymbolType.NONTERMINAL);
    NonTerminalSymbol F = new NonTerminalSymbol("F", SymbolType.NONTERMINAL);
    // 终结符+,*,(,),id,ε
    TerminalSymbol plus = new TerminalSymbol("+", SymbolType.TERMINAL);
    TerminalSymbol multi = new TerminalSymbol("*", SymbolType.TERMINAL);
    TerminalSymbol left = new TerminalSymbol("(", SymbolType.TERMINAL);
    TerminalSymbol right = new TerminalSymbol(")", SymbolType.TERMINAL);
    TerminalSymbol id = new TerminalSymbol("id", SymbolType.TERMINAL);
    TerminalSymbol epsilon = new TerminalSymbol("ε", SymbolType.NULL);
    // 产生式E'->E
    Production p0 = new Production();
    p0.addSymbolAtLast(E);
    E_dot.addProduction(p0);
    // 产生式E->E+T
    Production p1 = new Production();
    p1.addSymbolAtLast(E);
    p1.addSymbolAtLast(plus);
    p1.addSymbolAtLast(T);
    E.addProduction(p1);
    // 产生式E->T
    Production p2 = new Production();
    p2.addSymbolAtLast(T);
    E.addProduction(p2);
    // 产生式T->T*F
    Production p4 = new Production();
    p4.addSymbolAtLast(T);
    p4.addSymbolAtLast(multi);
    p4.addSymbolAtLast(F);
    T.addProduction(p4);
    // 产生式T->F
    Production p5 = new Production();
    p5.addSymbolAtLast(F);
    T.addProduction(p5);
    // 产生式F->(E)
    Production p6 = new Production();
    p6.addSymbolAtLast(left);
    p6.addSymbolAtLast(E);
    p6.addSymbolAtLast(right);
    F.addProduction(p6);
    // 产生式F->id
    Production p7 = new Production();
    p7.addSymbolAtLast(id);
    F.addProduction(p7);

    // I0
    ItemSet item0 = new ItemSet();
    LR0Item lr = new LR0Item(E_dot, p0, 0, ItemCategoy.CORE);
    item0.addItem(lr);
    Problem2.getClosure(item0);
    System.out.println(item0);
  }

  @Test
  public void TestExhaustTransition() {
    // 非终结符E,,T,F，E'
    NonTerminalSymbol E_dot = new NonTerminalSymbol("E'", SymbolType.NONTERMINAL);
    NonTerminalSymbol E = new NonTerminalSymbol("E", SymbolType.NONTERMINAL);
    NonTerminalSymbol T = new NonTerminalSymbol("T", SymbolType.NONTERMINAL);
    NonTerminalSymbol F = new NonTerminalSymbol("F", SymbolType.NONTERMINAL);
    // 终结符+,*,(,),id,ε
    TerminalSymbol plus = new TerminalSymbol("+", SymbolType.TERMINAL);
    TerminalSymbol multi = new TerminalSymbol("*", SymbolType.TERMINAL);
    TerminalSymbol left = new TerminalSymbol("(", SymbolType.TERMINAL);
    TerminalSymbol right = new TerminalSymbol(")", SymbolType.TERMINAL);
    TerminalSymbol id = new TerminalSymbol("id", SymbolType.TERMINAL);
    TerminalSymbol epsilon = new TerminalSymbol("ε", SymbolType.NULL);
    // 产生式E'->E
    Production p0 = new Production();
    p0.addSymbolAtLast(E);
    E_dot.addProduction(p0);
    // 产生式E->E+T
    Production p1 = new Production();
    p1.addSymbolAtLast(E);
    p1.addSymbolAtLast(plus);
    p1.addSymbolAtLast(T);
    E.addProduction(p1);
    // 产生式E->T
    Production p2 = new Production();
    p2.addSymbolAtLast(T);
    E.addProduction(p2);
    // 产生式T->T*F
    Production p4 = new Production();
    p4.addSymbolAtLast(T);
    p4.addSymbolAtLast(multi);
    p4.addSymbolAtLast(F);
    T.addProduction(p4);
    // 产生式T->F
    Production p5 = new Production();
    p5.addSymbolAtLast(F);
    T.addProduction(p5);
    // 产生式F->(E)
    Production p6 = new Production();
    p6.addSymbolAtLast(left);
    p6.addSymbolAtLast(E);
    p6.addSymbolAtLast(right);
    F.addProduction(p6);
    // 产生式F->id
    Production p7 = new Production();
    p7.addSymbolAtLast(id);
    F.addProduction(p7);

    // I0
    ItemSet item0 = new ItemSet();
    LR0Item lr = new LR0Item(E_dot, p0, 0, ItemCategoy.CORE);
    item0.addItem(lr);
    Problem2.getClosure(item0);
    Problem2.addItemSet(item0);

    Problem2.exhaustTransition(item0);
    System.out.println(Problem2.getAllItemSet());
  }

  @Test
  public void TestGetDFA() {
    // 非终结符E,,T,F，E'
    NonTerminalSymbol E_dot = new NonTerminalSymbol("E'", SymbolType.NONTERMINAL);
    NonTerminalSymbol E = new NonTerminalSymbol("E", SymbolType.NONTERMINAL);
    NonTerminalSymbol T = new NonTerminalSymbol("T", SymbolType.NONTERMINAL);
    NonTerminalSymbol F = new NonTerminalSymbol("F", SymbolType.NONTERMINAL);
    // 终结符+,*,(,),id,ε
    TerminalSymbol plus = new TerminalSymbol("+", SymbolType.TERMINAL);
    TerminalSymbol multi = new TerminalSymbol("*", SymbolType.TERMINAL);
    TerminalSymbol left = new TerminalSymbol("(", SymbolType.TERMINAL);
    TerminalSymbol right = new TerminalSymbol(")", SymbolType.TERMINAL);
    TerminalSymbol id = new TerminalSymbol("id", SymbolType.TERMINAL);
    TerminalSymbol epsilon = new TerminalSymbol("ε", SymbolType.NULL);
    // 产生式E'->E
    Production p0 = new Production();
    p0.addSymbolAtLast(E);
    E_dot.addProduction(p0);
    // 产生式E->E+T
    Production p1 = new Production();
    p1.addSymbolAtLast(E);
    p1.addSymbolAtLast(plus);
    p1.addSymbolAtLast(T);
    E.addProduction(p1);
    // 产生式E->T
    Production p2 = new Production();
    p2.addSymbolAtLast(T);
    E.addProduction(p2);
    // 产生式T->T*F
    Production p4 = new Production();
    p4.addSymbolAtLast(T);
    p4.addSymbolAtLast(multi);
    p4.addSymbolAtLast(F);
    T.addProduction(p4);
    // 产生式T->F
    Production p5 = new Production();
    p5.addSymbolAtLast(F);
    T.addProduction(p5);
    // 产生式F->(E)
    Production p6 = new Production();
    p6.addSymbolAtLast(left);
    p6.addSymbolAtLast(E);
    p6.addSymbolAtLast(right);
    F.addProduction(p6);
    // 产生式F->id
    Production p7 = new Production();
    p7.addSymbolAtLast(id);
    F.addProduction(p7);

    // I0
    ItemSet item0 = new ItemSet();
    LR0Item lr = new LR0Item(E_dot, p0, 0, ItemCategoy.CORE);
    item0.addItem(lr);
    Problem2.getClosure(item0);
    Problem2.addItemSet(item0);

    DFA dfa = Problem2.getDFA(item0);
    System.out.println(Problem2.getAllItemSet());
    System.out.println(dfa);
  }

  @Test
  public void TestIsSLR1() {
    // 非终结符Z', Z
    NonTerminalSymbol Z_dot = new NonTerminalSymbol("Z'", SymbolType.NONTERMINAL);
    NonTerminalSymbol Z = new NonTerminalSymbol("Z", SymbolType.NONTERMINAL);
    // 终结符a,c,d
    TerminalSymbol a = new TerminalSymbol("a", SymbolType.TERMINAL);
    TerminalSymbol c = new TerminalSymbol("c", SymbolType.TERMINAL);
    TerminalSymbol d = new TerminalSymbol("d", SymbolType.TERMINAL);

    // 产生式Z'->Z
    Production p0 = new Production();
    p0.addSymbolAtLast(Z);
    Z_dot.addProduction(p0);
    // 产生式Z->d
    Production p1 = new Production();
    p1.addSymbolAtLast(d);
    Z.addProduction(p1);
    // 产生式Z->cZa
    Production p2 = new Production();
    p2.addSymbolAtLast(c);
    p2.addSymbolAtLast(Z);
    p2.addSymbolAtLast(a);
    Z.addProduction(p2);
    // 产生式Z->Za
    Production p3 = new Production();
    p3.addSymbolAtLast(Z);
    p3.addSymbolAtLast(a);
    Z.addProduction(p3);

    // 求FIRST集合
    Set<TerminalSymbol> firstZ_dot = Problem1.firstOfSymbol(Z_dot);
    Set<TerminalSymbol> firstZ = Problem1.firstOfSymbol(Z);
    // 求FOLLOW集合
    // 结束符#
    TerminalSymbol end = new TerminalSymbol("#", SymbolType.TERMINAL);
    Z_dot.addFollow(end);
    // 求FOLLOW集合
    Problem1.followOfSymbol(Z_dot);
    Problem1.followOfSymbol(Z);
    // 添加依赖
    Z_dot.addFollowDependent();
    Z.addFollowDependent();
//    System.out.println(Z_dot);
//    System.out.println(Z);

    // I0
    ItemSet item0 = new ItemSet();
    LR0Item lr = new LR0Item(Z_dot, p0, 0, ItemCategoy.CORE);
    item0.addItem(lr);
    Problem2.getClosure(item0);
    Problem2.addItemSet(item0);

    DFA dfa = Problem2.getDFA(item0);
    System.out.println(Problem2.getAllItemSet());
    System.out.println(dfa);

    System.out.println(Problem2.isSLR1());
  }

  @Test
  public void TestGetCell() {
    // 非终结符E,T,F，E'
    NonTerminalSymbol E_dot = new NonTerminalSymbol("E'", SymbolType.NONTERMINAL);
    NonTerminalSymbol E = new NonTerminalSymbol("E", SymbolType.NONTERMINAL);
    NonTerminalSymbol T = new NonTerminalSymbol("T", SymbolType.NONTERMINAL);
    NonTerminalSymbol F = new NonTerminalSymbol("F", SymbolType.NONTERMINAL);
    // 终结符+,*,(,),id,ε
    TerminalSymbol plus = new TerminalSymbol("+", SymbolType.TERMINAL);
    TerminalSymbol multi = new TerminalSymbol("*", SymbolType.TERMINAL);
    TerminalSymbol left = new TerminalSymbol("(", SymbolType.TERMINAL);
    TerminalSymbol right = new TerminalSymbol(")", SymbolType.TERMINAL);
    TerminalSymbol id = new TerminalSymbol("id", SymbolType.TERMINAL);
    TerminalSymbol epsilon = new TerminalSymbol("ε", SymbolType.NULL);
    // 产生式E'->E
    Production p0 = new Production();
    p0.addSymbolAtLast(E);
    E_dot.addProduction(p0);
    // 产生式E->E+T
    Production p1 = new Production();
    p1.addSymbolAtLast(E);
    p1.addSymbolAtLast(plus);
    p1.addSymbolAtLast(T);
    E.addProduction(p1);
    // 产生式E->T
    Production p2 = new Production();
    p2.addSymbolAtLast(T);
    E.addProduction(p2);
    // 产生式T->T*F
    Production p4 = new Production();
    p4.addSymbolAtLast(T);
    p4.addSymbolAtLast(multi);
    p4.addSymbolAtLast(F);
    T.addProduction(p4);
    // 产生式T->F
    Production p5 = new Production();
    p5.addSymbolAtLast(F);
    T.addProduction(p5);
    // 产生式F->(E)
    Production p6 = new Production();
    p6.addSymbolAtLast(left);
    p6.addSymbolAtLast(E);
    p6.addSymbolAtLast(right);
    F.addProduction(p6);
    // 产生式F->id
    Production p7 = new Production();
    p7.addSymbolAtLast(id);
    F.addProduction(p7);

    ArrayList<NonTerminalSymbol> nonTerminalSymbols = new ArrayList<>();
    nonTerminalSymbols.add(E_dot);
    nonTerminalSymbols.add(E);
    nonTerminalSymbols.add(T);
    nonTerminalSymbols.add(F);
    // 求FIRST集合
    for (NonTerminalSymbol symbol: nonTerminalSymbols) {
      Problem1.firstOfSymbol(symbol);
    }
    // 求FOLLOW集合
    // 结束符#
    TerminalSymbol end = new TerminalSymbol("#", SymbolType.TERMINAL);
    E_dot.addFollow(end);
    // 求FOLLOW集合
    for (NonTerminalSymbol symbol: nonTerminalSymbols) {
      Problem1.followOfSymbol(symbol);
    }
    // 添加依赖
    for (NonTerminalSymbol symbol: nonTerminalSymbols) {
      symbol.addFollowDependent();
      System.out.println(symbol);
    }

    // I0
    ItemSet item0 = new ItemSet();
    LR0Item lr = new LR0Item(E_dot, p0, 0, ItemCategoy.CORE);
    item0.addItem(lr);
    Problem2.getClosure(item0);
    Problem2.addItemSet(item0);
    // DFA
    DFA dfa = Problem2.getDFA(item0);
    Problem2.isSLR1();

    // 产生式概述表
    ArrayList<ProductionInfo> productionInfoTable = new ArrayList<>();
    for (NonTerminalSymbol symbol: nonTerminalSymbols) {
      productionInfoTable.addAll(Problem2.createInfo(symbol));
    }
    productionInfoTable.remove(0);

    // 语法分析表
    Problem2.getCell(dfa);
    System.out.println(productionInfoTable);
    System.out.println(Problem2.getpActionCellTable());
    System.out.println(Problem2.getpGotoCellTable());
  }
}
