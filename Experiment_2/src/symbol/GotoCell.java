package symbol;

/**
 * @author Ôø¼Ñ±¦
 * @date 2023/4/9 16:27
 */
public class GotoCell {
  // ×Ý×ø±ê£º×´Ì¬ÐòºÅ
  private int stateId;
  // ºá×ø±ê£º·ÇÖÕ½á·û
  private String nonTerminalSymbolName;
  // ÏÂÒ»×´Ì¬
  private int nextStateId;

  public GotoCell(int stateId, String nonTerminalSymbolName, int nextStateId) {
    this.stateId = stateId;
    this.nonTerminalSymbolName = nonTerminalSymbolName;
    this.nextStateId = nextStateId;
  }

  @Override
  public String toString() {
    return "\nGotoCell{" +
        "stateId=" + stateId +
        ", nonTerminalSymbolName='" + nonTerminalSymbolName + '\'' +
        ", nextStateId=" + nextStateId +
        '}';
  }
}
