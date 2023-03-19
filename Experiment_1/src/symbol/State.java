package symbol;

/**
 * @author Ôø¼Ñ±¦
 * @date 2023/3/15 22:07
 */
public class State {
  private int stateId;
  private StateType type;
  private LexemeCategory category;
  static private int stateIdNum = 0;

  public State(int stateId, StateType type, LexemeCategory category) {
    this.stateId = stateId;
    this.type = type;
    this.category = category;
  }

  public int getStateId() {
    return stateId;
  }

  public StateType getType() {
    return type;
  }

  public LexemeCategory getCategory() {
    return category;
  }

  public static int getStateIdNum() {
    return stateIdNum;
  }

  public void setStateId(int stateId) {
    this.stateId = stateId;
  }

  public void setType(StateType type) {
    this.type = type;
  }

  public void setCategory(LexemeCategory category) {
    this.category = category;
  }

  public static void setStateIdNum(int stateIdNum) {
    State.stateIdNum = stateIdNum;
  }

  @Override
  public String toString() {
    return "State{" +
        "stateId=" + stateId +
        ", type=" + type +
        ", category=" + category +
        '}';
  }
}
