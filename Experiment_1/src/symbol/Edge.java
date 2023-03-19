package symbol;

/**
 * @author Ôø¼Ñ±¦
 * @date 2023/3/15 22:07
 */
public class Edge {
  private int fromState;
  private int nextState;
  private int driverId;
  private DriverType type;

  public Edge(int fromState, int nextState,int driverId, DriverType type) {
    this.driverId = driverId;
    this.fromState = fromState;
    this.nextState = nextState;
    this.type = type;
  }

  public Edge(int fromState, int nextState, DriverType type) {
    this.fromState = fromState;
    this.nextState = nextState;
    this.type = type;
    this.driverId = -1;
  }

  public int getFromState() {
    return fromState;
  }

  public int getNextState() {
    return nextState;
  }

  public int getDriverId() {
    return driverId;
  }

  public DriverType getType() {
    return type;
  }

  public void setFromState(int fromState) {
    this.fromState = fromState;
  }

  public void setNextState(int nextState) {
    this.nextState = nextState;
  }

  public void setDriverId(int driverId) {
    this.driverId = driverId;
  }

  public void setType(DriverType type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return "Edge{" +
        "fromState=" + fromState +
        ", nextState=" + nextState +
        ", driverId=" + driverId +
        ", type=" + type +
        '}';
  }
}
