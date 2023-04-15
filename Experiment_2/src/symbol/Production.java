package symbol;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 曾佳宝
 * @date 2023/4/9 15:55
 */
public class Production {
  /**
   * 产生式
   */
  // 产生式序号，起标识作用
  private int productionId;
  // 产生式体中包含的文法符个数
  private int bodySize;
  // 产生式体中包含的文法符
  private ArrayList<GrammarSymbol> pBodySymbolTable;
  // 产生式的 FIRST 函数值
  private Set<TerminalSymbol> pFirstSet;

  public static int idNum = 0;

  public Production() {
    productionId = idNum++;
    bodySize = 0;
    pBodySymbolTable = new ArrayList<>();
    pFirstSet = new HashSet<>();
  }

  public int getProductionId() {
    return productionId;
  }

  public void setProductionId(int productionId) {
    this.productionId = productionId;
  }

  public int getBodySize() {
    return bodySize;
  }

  public void setBodySize(int bodySize) {
    this.bodySize = bodySize;
  }

  public ArrayList<GrammarSymbol> getpBodySymbolTable() {
    return pBodySymbolTable;
  }

  public void setpBodySymbolTable(ArrayList<GrammarSymbol> pBodySymbolTable) {
    this.pBodySymbolTable = pBodySymbolTable;
  }

  public Set<TerminalSymbol> getpFirstSet() {
    return pFirstSet;
  }

  public void setpFirstSet(Set<TerminalSymbol> pFirstSet) {
    this.pFirstSet = pFirstSet;
  }

  public void addSymbolAtLast(GrammarSymbol symbol) {
    pBodySymbolTable.add(symbol);
    bodySize ++;
  }

  public void removeFirstSymbol(GrammarSymbol symbol) {
    pBodySymbolTable.remove(symbol);
    bodySize --;
  }

  public Boolean isEpsilon() {
    if (bodySize == 1 && pBodySymbolTable.get(0).getName().equals("ε")) {
      return true;
    }
    else {
      return false;
    }
  }

  @Override
  public String toString() {
    String s = new String();
    for (GrammarSymbol symbol: pBodySymbolTable) {
      s += symbol.getName();
    }
    return "{" +
        "productionId=" + productionId +
        ", bodySize=" + bodySize +
        ", pBodySymbolTable=" + s +
//        ", pFirstSet=" + pFirstSet +
        '}';
  }
}
//产生式体中，文法符之间都是连接运算，因此也就可省去连接运算符。把产生式
//中的某个文法符放入 pBodySymbolTable 之前，要强制类型转换，变成 GrammarSymbol *类
//型。这种类型转换没有问题。因为 TerminalSymbol 和 NonTerminalSymbol 都是
//GrammarSymbol 的子类。在使用 pBodySymbolTable 中元素时，检查其成员变量 type 的
//值，如果为 NONTERMINAL，则将其强制类型转换，变回 NonTerminalSymbol *类型。如
//果为 TERMINAL，则将其强制类型转换，变回 TerminalSymbol *类型。
