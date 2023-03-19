/**
 * @author 曾佳宝
 * @date 2023/3/15 22:25
 */
import java.util.ArrayList;
import symbol.*;
public class Problem3 {
  public static ArrayList<Integer> move(Graph graph, int stateId, int driveId) {
    /**
     * 状态转移函数
     */
    ArrayList<Edge> edgeArrayList = graph.getpEdgeTable();
    ArrayList<Integer> stateArrayListAnswer = new ArrayList<>();
    for (Edge edge: edgeArrayList) {
      if (edge.getFromState() == stateId && edge.getDriverId() == driveId) {
        int nextState = edge.getNextState();
        stateArrayListAnswer.add(nextState);
      }
    }
    return stateArrayListAnswer;
  }

  public static ArrayList<Integer> closure(Graph graph, ArrayList<Integer> stateArrayList) {
    /**
     * 求空闭包
     */
    ArrayList<Edge> edgeArrayList = graph.getpEdgeTable();
    ArrayList<Integer> resultStateArrayList, currentStateArrayList, nextSateArrayList = new ArrayList<>();
    resultStateArrayList = stateArrayList;
    currentStateArrayList = stateArrayList;
    while(!currentStateArrayList.isEmpty()) {
      // 找到能够空转换的状态集合
      for (Edge edge: edgeArrayList) {
        if (currentStateArrayList.contains(edge.getFromState()) && edge.getType() == DriverType.NULL) {
          if (!nextSateArrayList.contains(edge.getNextState())) {
            nextSateArrayList.add(edge.getNextState());
          }
        }
      }
      // 去除结果状态集合后的状态集合
      for (Integer stateId: resultStateArrayList) {
        if (nextSateArrayList.contains(stateId)) {
          nextSateArrayList.remove(stateId);
        }
      }
      // 去除后为空表示闭包搜索完毕
      if (nextSateArrayList.isEmpty()) {
        break;
      }
      else {
        resultStateArrayList.addAll(nextSateArrayList);
        currentStateArrayList = nextSateArrayList;
      }
    }
    return resultStateArrayList;
  }

  public static ArrayList<Integer> dTran(Graph graph, ArrayList<Integer> currentStateArrayList, int driveId) {
    /**
     * 状态转移集合
     */
    ArrayList<Integer> nextStateArrayList = new ArrayList<>();
    for (int stateId: currentStateArrayList) {
      nextStateArrayList.addAll(move(graph, stateId, driveId));
    }
    return closure(graph, nextStateArrayList);
  }

  public static Graph NFAToDFA(Graph pNFA) {
    /**
     * NFA转换为DFA
     */
    Graph graph = new Graph();
    ArrayList<Edge> edgeArrayList = pNFA.getpEdgeTable();
    // 初始状态的闭包
    State start = pNFA.getStart();
    ArrayList<Integer> currentStateArrayList = new ArrayList<>();
    currentStateArrayList.add(start.getStateId());
    currentStateArrayList = closure(pNFA, currentStateArrayList);
    // 用于存储所有状态集合的集合
    ArrayList<ArrayList<Integer>> allStateList = new ArrayList<>();
    // 存储最终状态集合
    ArrayList<Edge> edgeList = new ArrayList<>();
    // 存储开始状态
    allStateList.add(currentStateArrayList);
    State startList = new State(0, StateType.UNMATCH, LexemeCategory.EMPTY);
    ArrayList<State> stateList = new ArrayList<>();
    stateList.add(startList);
    graph.setStart(startList);
    // 存储结束状态集合
    ArrayList<State> endStateList = new ArrayList<>();
    // 表示当前状态序号
    int currentState = 0;
    while (currentState < allStateList.size()) {
      currentStateArrayList = allStateList.get(currentState);
      ArrayList<Integer> driverIdList = new ArrayList<>();
      for (Edge edge: edgeArrayList) {
        if (currentStateArrayList.contains(edge.getFromState()) && edge.getType() != DriverType.NULL
            && !driverIdList.contains(edge.getDriverId())) {
          // 驱动字符
          ArrayList<Integer> stateArrayList = dTran(pNFA, currentStateArrayList, edge.getDriverId());
          driverIdList.add(edge.getDriverId());
          // 判断是否为新的状态集合
          if (!allStateList.contains(stateArrayList)) {
            allStateList.add(stateArrayList);
            // 添加新边
            Edge edgeNew = new Edge(currentState, stateList.size(), edge.getDriverId(), edge.getType());
            edgeList.add(edgeNew);
            // 添加新的状态,判断是否含有终结状态和属性是否为空
            StateType symbol = StateType.UNMATCH;
            LexemeCategory category = LexemeCategory.EMPTY;
            for (State x: pNFA.getpStateTable()) {
              if (stateArrayList.contains(x.getStateId()) && x.getType() == StateType.MATCH) {
                symbol = StateType.MATCH;
              }
              if (x.getCategory() != LexemeCategory.EMPTY) {
                category = x.getCategory();
              }
            }
            State state = new State(stateList.size(), symbol, category);
            if (symbol == StateType.MATCH) {
              endStateList.add(state);
            }
            stateList.add(state);
          }
          else {
            int stateNum = allStateList.indexOf(stateArrayList);
            Edge edgeNew = new Edge(currentState, stateNum, edge.getDriverId(), edge.getType());
            edgeList.add(edgeNew);
          }
        }
      }
      currentState ++;
    }
    graph.setNumOfStates(currentState);
    graph.setpEndStateTable(endStateList);
    graph.setpStateTable(stateList);
    graph.setpEdgeTable(edgeList);
    return graph;
  }
}
