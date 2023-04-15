package symbol;

/**
 * @author 曾佳宝
 * @date 2023/4/9 15:42
 */
public class GrammarSymbol {
  /**
   * 文法符
   */
  // 名字
  private String name;
  // 文法符的类别
  private SymbolType type;

  public GrammarSymbol(String name, SymbolType type) {
    this.name = name;
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public SymbolType getType() {
    return type;
  }

  public void setType(SymbolType type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return "GrammarSymbol{" +
        "name='" + name + '\'' +
        ", type=" + type +
        '}';
  }
}
