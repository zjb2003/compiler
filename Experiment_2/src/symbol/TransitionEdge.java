package symbol;

/**
 * @author 曾佳宝
 * @date 2023/4/9 16:22
 */
public class TransitionEdge {
  /**
   * 变迁边
   */
  // 驱动文法符
  private GrammarSymbol driverSymbol;
  // 出发项集
  private ItemSet fromItemSet;
  // 到达项集
  private ItemSet toItemSet;

  public TransitionEdge(GrammarSymbol driverSymbol, ItemSet fromItemSet, ItemSet toItemSet) {
    this.driverSymbol = driverSymbol;
    this.fromItemSet = fromItemSet;
    this.toItemSet = toItemSet;
  }

  public GrammarSymbol getDriverSymbol() {
    return driverSymbol;
  }

  public void setDriverSymbol(GrammarSymbol driverSymbol) {
    this.driverSymbol = driverSymbol;
  }

  public ItemSet getFromItemSet() {
    return fromItemSet;
  }

  public void setFromItemSet(ItemSet fromItemSet) {
    this.fromItemSet = fromItemSet;
  }

  public ItemSet getToItemSet() {
    return toItemSet;
  }

  public void setToItemSet(ItemSet toItemSet) {
    this.toItemSet = toItemSet;
  }

  @Override
  public String toString() {
    return "TransitionEdge{" +
        "driverSymbol=" + driverSymbol.getName() +
        ", fromItemSet=" + fromItemSet.getStateId() +
        ", toItemSet=" + toItemSet.getStateId() +
        '}' + '\n';
  }
}
