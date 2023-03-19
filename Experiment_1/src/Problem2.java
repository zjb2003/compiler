/**
 * @author 曾佳宝
 * @date 2023/3/15 22:23
 */
import java.util.ArrayList;
import symbol.*;

public class Problem2 {
  public static Graph generateBasicNFA(DriverType driverType, int driverId, LexemeCategory category) {
    Graph pNFA = new Graph();
    State pState1 = new State(0, StateType.UNMATCH, LexemeCategory.EMPTY);
    State pState2 = new State(1, StateType.MATCH, category);
    Edge edge = new Edge(pState1.getStateId(), pState2.getStateId(), driverId, driverType);
    // 加入NFA中
    pNFA.addState(pState1);
    pNFA.setStart(pState1);
    pNFA.addState(pState2);
    pNFA.addEdge(edge);
    return pNFA;
  }

  public static Graph union(Graph pNFA1, Graph pNFA2) {
    /**
     * 并运算
     */
    Graph graph = new Graph();
    // 并运算之前对pNFA1和pNFA2进行等价改造
    pNFA1.change();
    pNFA2.change();
    // 获取初始状态
    State start = pNFA1.getStart();
    graph.setStart(start);
    // 获取状态数目
    int stateNum1 = pNFA1.getNumOfStates();
    int stateNum2 = pNFA2.getNumOfStates();
    graph.setNumOfStates(stateNum1+stateNum2-2);
    // 重新编号并加入新的graph
    pNFA2.reNumber(stateNum1-2);
    // 将pNFA1和pNFA2加入结果图中
    graph.addTable(pNFA1);
    graph.addTable(pNFA2);
    // 合并终结状态
    graph.mergeEnd(pNFA1, stateNum1+stateNum2-3);
    // 合并初始状态
    graph.mergeStart(pNFA2);
    return graph;
  }

  public static Graph product(Graph pNFA1, Graph pNFA2) {
    /**
     * 连接运算
     */
    Graph graph = new Graph();
    // 获取初始状态，编号为0
    State start = pNFA1.getStart();
    graph.setStart(start);
    // 添加pNFA1到结果中
    graph.addTable(pNFA1);
    // 获取状态数目
    int stateNum1 = pNFA1.getNumOfStates();
    int stateNum2 = pNFA2.getNumOfStates();
    // 判断有无出入边
    if (pNFA1.haveOutside(pNFA1.getpEndStateTable()) && pNFA2.haveInSide(pNFA2.getStart())) {
      // 重新编号
      pNFA2.reNumber(stateNum1);
      // 设置状态信息
      graph.setNumOfStates(stateNum1+stateNum2);
      // 添加ε边
      for (State state:pNFA1.getpEndStateTable()) {
        Edge edge = new Edge(state.getStateId(), pNFA2.getStart().getStateId(), DriverType.NULL);
        graph.addEdge(edge);
      }
    }
    else {
      // 重新编号
      pNFA2.reNumber(stateNum1-1);
      // 初始状态和pNFA2的终结状态合并
      pNFA2.removeStateById(stateNum1-1);
      // 设置状态信息
      graph.setNumOfStates(stateNum1+stateNum2-1);
    }
    for (State state: graph.getpStateTable()) {
      state.setType(StateType.UNMATCH);
    }
    graph.setpEndStateTable(new ArrayList<>());
    // 构建pNFA1到pNFA2的边
    graph.addTable(pNFA2);
    return graph;
  }

  public static Graph plusClosure(Graph pNFA) {
    /**
     * 正闭包运算
     */
    Graph graph = new Graph();
    // 构建初始状态，即pNFA的初始状态
    State start = pNFA.getStart();
    graph.setStart(start);
    // 获取状态数目
    int stateNum1 = pNFA.getNumOfStates();
    graph.setNumOfStates(stateNum1);
    // 构建pNFA从结束到开始的边
    graph.addTable(pNFA);
    pNFA.addEndEdgeToOther(pNFA.getStart());
    return graph;
  }

  public static Graph closure(Graph pNFA) {
    /**
     * 闭包运算
     */
    State start = pNFA.getStart();
    ArrayList<State> endStateList = pNFA.getpEndStateTable();
    // 判断出入边，并添加相关状态信息
    int changeType = pNFA.change();
    if (changeType == 0) {
      // 无出入边，终结状态合并到开始状态
      pNFA.mergeEnd(pNFA, 0);
      pNFA.setNumOfStates(pNFA.getpStateTable().size());
      for (State state: pNFA.getpStateTable()) {
        if (state.getStateId() == 0) {
          state.setType(StateType.MATCH);
          pNFA.addEndState(state);
        }
      }
    }
    else {
      // 添加从原开始状态到原终结状态的ε边
      for (State state: endStateList) {
        Edge edge = new Edge(state.getStateId(), start.getStateId(), DriverType.NULL);
        pNFA.addEdge(edge);
      }
      // 添加从开始到终结的ε边
      start = pNFA.getStart();
      ArrayList<Edge> edgeList = new ArrayList<>();
      for (State state: pNFA.getpEndStateTable()) {
        Edge edge = new Edge(start.getStateId(), state.getStateId(), DriverType.NULL);
        // 由于涉及到更改结束状态集合的操作，因此需要先遍历再添加边
        edgeList.add(edge);
      }
      for (Edge edge: edgeList) {
        pNFA.addEdge(edge);
      }
    }
    // 新建一个NFA，复制进来即可
    Graph graph = new Graph(pNFA);
    return graph;
  }

  public static Graph zeroOrOne(Graph pNFA) {
    /**
     * 0 或者 1 个运算。
     */
    Graph graph = new Graph();
    // 获取初始状态
    State start = pNFA.getStart();
    graph.setStart(start);
    graph.addTable(pNFA);
    // 根据出入边添加或调整状态信息
    graph.change();
    // 设置状态数目
    graph.setNumOfStates(graph.getpStateTable().size());
    // 构建pNFA的初始状态到终结状态的边
    for (State state: graph.getpEndStateTable()) {
      Edge edge = new Edge(start.getStateId(), state.getStateId(), DriverType.NULL);
      graph.addEdge(edge);
    }
    return graph;
  }
}
