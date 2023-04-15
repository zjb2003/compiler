package symbol;

/**
 * @author Ôø¼Ñ±¦
 * @date 2023/4/9 16:26
 */
public class ActionCell {
  // ×Ý×ø±ê£º×´Ì¬ÐòºÅ
  private int stateId;
  // ºá×ø±ê£ºÖÕ½á·û
  private String terminalSymbolName;
  // Action Àà±ð
  private ActionCategory type ;
  // Action µÄ id
  private int id;

  public ActionCell(int stateId, String terminalSymbolName, ActionCategory type, int id) {
    this.stateId = stateId;
    this.terminalSymbolName = terminalSymbolName;
    this.type = type;
    this.id = id;
  }

  @Override
  public String toString() {
    return "ActionCell{" +
        "stateId=" + stateId +
        ", terminalSymbolName='" + terminalSymbolName + '\'' +
        ", type=" + type + id +
        '}';
  }
}
