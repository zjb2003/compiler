import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import symbol.Cell;
import symbol.GrammarSymbol;
import symbol.NonTerminalSymbol;
import symbol.Production;
import symbol.SymbolType;
import symbol.TerminalSymbol;

/**
 * @author 曾佳宝
 * @date 2023/4/9 16:35
 */
public class Problem1 {
  public static NonTerminalSymbol leftRecursion(NonTerminalSymbol nonTerminalSymbol) {
    // 获取产生式左部的非终结符A
    String name = nonTerminalSymbol.getName();
    // 保存A=>Aα的链表
    ArrayList<Production> left = new ArrayList<>();
    // 保存A=>β的链表
    ArrayList<Production> constant = new ArrayList<>();
    // 遍历所有产生式
    for (Production production: nonTerminalSymbol.getpProductionTable()) {
      // 产生式右部的头部文法符名字为A，则将其放入left;否则放入constant
      if (production.getpBodySymbolTable().get(0).getName().equals(name)) {
        left.add(production);
      }
      else {
        constant.add(production);
      }
    }
    // left为空，说明不含有左递归
    if (left.isEmpty()) {
      System.out.println("不存在左递归");
      return null;
    }
    System.out.println("存在左递归");
    // 新建一个非终结符A‘
    NonTerminalSymbol S_dot = new NonTerminalSymbol(nonTerminalSymbol.getName()+'\'', SymbolType.NONTERMINAL);
    // 将A=>β变化为A=>βA'
    for (Production production: constant) {
      production.addSymbolAtLast(S_dot);
    }
    // 将A=>Aα变化为A'=>αA'
    for (Production production: left) {
      // 非终结符A移除产生式
      nonTerminalSymbol.removeProduction(production);
      // 产生式头部去除A
      production.removeFirstSymbol(nonTerminalSymbol);
      // 产生式尾部加上A’
      production.addSymbolAtLast(S_dot);
      // 添加到非终结符A‘的产生式集合中
      S_dot.addProduction(production);
    }
    // 添加A=>ε
    Production production = new Production();
    GrammarSymbol epsilon = new GrammarSymbol("ε", SymbolType.NULL);
    production.addSymbolAtLast(epsilon);
    // 加入非终结符A’的产生式集合中
    S_dot.addProduction(production);
    return S_dot;
  }

  public static ArrayList<NonTerminalSymbol> leftCommonFactor(NonTerminalSymbol symbol) {
    Map<GrammarSymbol, ArrayList<Production>> head = new HashMap<>();
    Boolean flag = false;
    for (Production production: symbol.getpProductionTable()) {
      GrammarSymbol s = production.getpBodySymbolTable().get(0);
      // 不存在该头部信息，则表示其第一次出现，加入map中
      if (head.get(s) == null) {
        ArrayList<Production> p = new ArrayList<>();
        p.add(production);
        head.put(s, p);
      }
      // 说明已经出现过，此时存在左公因子
      else {
        head.get(s).add(production);
        flag = true;
      }
    }
    if (flag == false) {
      System.out.println("不存在左公因子");
      return null;
    }
    System.out.println("存在左公因子");
    ArrayList<NonTerminalSymbol> ans = new ArrayList<>();
    // 遍历所有头部信息
    for (GrammarSymbol key: head.keySet()) {
      // 判断具有相同头部信息的个数
      if (head.get(key).size() > 1) {
        // 新建一个非终结符A‘
        NonTerminalSymbol A_dot = new NonTerminalSymbol(symbol.getName()+"\'", SymbolType.NONTERMINAL);
        // 新建一个产生式A->key A'
        Production p = new Production();
        p.addSymbolAtLast(key);
        p.addSymbolAtLast(A_dot);
        symbol.addProduction(p);
        // 遍历左公因子
        for (Production production: head.get(key)) {
          // 删除A->key ……中的key
          production.removeFirstSymbol(key);
          // 删除A->……的产生式左部
          symbol.removeProduction(production);
          // 产生式左部为A’->……
          A_dot.addProduction(production);
        }
        // 添加新的非终结符
        ans.add(A_dot);
      }
    }
    return ans;
  }

  public static Set<TerminalSymbol> firstOfProduction(Production production) {
    // ε是否持续
    Boolean nullStand = true;
    int i = 0;
    Set<TerminalSymbol> pFirstSet = new HashSet<>();
    ArrayList<GrammarSymbol> pBodySymbolTable = production.getpBodySymbolTable();
    // 新建一个ε文法符用于判断
    TerminalSymbol epsilon = new TerminalSymbol("ε", SymbolType.NULL);
    // 当前面文法符FIRST都包含ε时
    while(nullStand && i < production.getBodySize()) {
      // 获取当前文法符的FIRST
      Set<TerminalSymbol> firstY = firstOfSymbol(pBodySymbolTable.get(i));
      // 判断当前文法符FIRST是否包含ε
      if (firstY.contains(epsilon)) {
        // 跳转到下一个文法符，去掉ε
        i ++;
        firstY.remove(epsilon);
      }
      else {
        // ε不再持续
        nullStand = false;
      }
      // 把当前文法符的FIRST加入结果中
      pFirstSet.addAll(firstY);
    }
    // 如果最终能推导出ε，则FIRST集合中包含ε
    if (nullStand && i == production.getBodySize()) {
      pFirstSet.add(epsilon);
    }
    // 设置产生式的FIRST集合
    production.setpFirstSet(pFirstSet);
    return pFirstSet;
  }

  public static Set<TerminalSymbol> firstOfSymbol(GrammarSymbol symbol) {
    Set<TerminalSymbol> ans = new HashSet<>();
    // 当前文法符为终结符或ε，则直接返回其本身
    if (symbol.getType() == SymbolType.TERMINAL || symbol.getType() == SymbolType.NULL) {
      ans.add((TerminalSymbol) symbol);
      return ans;
    }
    // 当前文法符为非终结符，遍历每个产生式
    for (Production production: ((NonTerminalSymbol)symbol).getpProductionTable()) {
      if (production.getpBodySymbolTable().get(0) == symbol) {
        continue;
      }
      // 对每个产生式求其FIRST集
      for (TerminalSymbol s: firstOfProduction(production)) {
        // 将未加入的终结符加入FIRST集合
        if (!ans.contains(s)) {
          ans.add(s);
        }
      }
    }
    // 设置FIRST非终结符的FIRST集合
    ((NonTerminalSymbol) symbol).setpFirstSet(ans);
    return ans;
  }

  public static void followOfSymbol(NonTerminalSymbol symbol) {
    // 新建ε
    TerminalSymbol epsilon = new TerminalSymbol("ε", SymbolType.NULL);
    // 遍历产生式
    for (Production production: symbol.getpProductionTable()) {
      // 产生式文法符个数
      int size = production.getBodySize();
      // 获取最后一个文法符
      GrammarSymbol Yn = production.getpBodySymbolTable().get(size - 1);
      // symbol->ε，表示为空，跳过该产生式
      if (Yn.getName().equals("ε")) {
        continue;
      }
      // 最后一个文法符为非终结符，其FOLLOW集合依赖于symbol的FOLLOW集合
      if (Yn.getType() == SymbolType.NONTERMINAL) {
        ((NonTerminalSymbol) Yn).addDependentSetFollow(symbol);
      }
      // ε是否持续（从最后一个开始往前找）
      Boolean nullStand = true;
      // 从倒数第二个开始
      int i = size-2, j = size-1;
      while (i >= 0) {
        GrammarSymbol Yi = production.getpBodySymbolTable().get(i);
        // 当前文法符为非终结符
        if (Yi.getType() == SymbolType.NONTERMINAL) {
          // 遍历其后FIRST连续包含ε的非终结符
          for (int k = i+1; k <= j; k ++) {
            // 若第k个文法符为终结符，将其自身加入FOLLOW集合即可
            if (production.getpBodySymbolTable().get(k).getType() == SymbolType.TERMINAL) {
              ((NonTerminalSymbol)Yi).addFollow((TerminalSymbol) production.getpBodySymbolTable().get(k));
              nullStand = false;
            }
            else {
              NonTerminalSymbol Yk = (NonTerminalSymbol)production.getpBodySymbolTable().get(k);
              Set<TerminalSymbol> firstYk = Yk.removeEpsilon();
              // 若当前终结符的后续非终结符的FIRST集合不包含ε，说明无法持续到最后，nullStand置0
              if (!Yk.containsEpsilon()) {
                nullStand = false;
              }
              // 将其FIRST集合-{ε}加入非终结符的FOLLOW集合
              ((NonTerminalSymbol)Yi).addFollowSet(firstYk);
            }
          }
          // 如果当前文法符的FIRST集合不包含ε，说明FOLLOW集合无法到达后续，将j修改为当前i
          if (!((NonTerminalSymbol)Yi).getpFirstSet().contains(epsilon)) {
            j = i;
          }
          // 如果nullStand仍为true，表示当前文法符仍能到达最后，则其FOLLOW集合依赖于symbol
          if (nullStand) {
            ((NonTerminalSymbol)Yi).addDependentSetFollow(symbol);
          }
        }
        else {
          j = i;
          if (nullStand) {
            nullStand = false;
          }
        }
        i --;
      }
    }
  }

  public static Boolean isLL1 (NonTerminalSymbol symbol) {
    Map<TerminalSymbol, Integer> map = new HashMap<>();
    // 判断是否有X->ε,若存在需将FOLLOW保存进map
    if (symbol.containsEpsilon()) {
      for (TerminalSymbol s: symbol.getpFollowSet()) {
        map.put(s, -1);
      }
    }
    // 遍历产生式
    for (Production production: symbol.getpProductionTable()) {
      // 遍历产生式的FIRST集合，若其未出现过，说明无交集，否则有交集（不为LL（1）文法）
      for (TerminalSymbol s: production.getpFirstSet()) {
        if (map.get(s) == null) {
          map.put(s,production.getProductionId());
        }
        else {
          return false;
        }
      }
    }
    return true;
  }

  public static ArrayList<Cell> parseTable(ArrayList<NonTerminalSymbol> pNonTerminalSymbolTable) {
    ArrayList<Cell> pParseTableOfLL = new ArrayList<>();
    // 遍历每个非终结符
    for (NonTerminalSymbol symbol: pNonTerminalSymbolTable) {
      // 遍历每个产生式
      for (Production production: symbol.getpProductionTable()) {
        // 若该产生式为X->ε，则选择FOLLOW集合中的终结符填入该产生式
        if (production.isEpsilon()) {
          for (TerminalSymbol t: symbol.getpFollowSet()) {
            Cell cell = new Cell(symbol, t, production);
            pParseTableOfLL.add(cell);
          }
        }
        // 否则选择FIRST集合中的终结符填入该产生式
        else {
          for (TerminalSymbol t: production.getpFirstSet()) {
            Cell cell = new Cell(symbol, t, production);
            pParseTableOfLL.add(cell);
          }
        }
      }
    }
    return pParseTableOfLL;
  }
}
