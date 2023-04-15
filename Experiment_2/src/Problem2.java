import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;
import symbol.ActionCategory;
import symbol.ActionCell;
import symbol.DFA;
import symbol.GotoCell;
import symbol.GrammarSymbol;
import symbol.ItemCategoy;
import symbol.ItemSet;
import symbol.LR0Item;
import symbol.NonTerminalSymbol;
import symbol.Production;
import symbol.ProductionInfo;
import symbol.SymbolType;
import symbol.TerminalSymbol;
import symbol.TransitionEdge;

/**
 * @author 曾佳宝
 * @date 2023/4/10 15:06
 */
public class Problem2 {
  public static void getClosure(ItemSet itemSet) {
    // 栈：用于保存未求其后续文法符的项目
    Stack<LR0Item> item = new Stack<>();
    // 将所有核心项推入栈中
    for (LR0Item lr: itemSet.getpItemTable()) {
      item.push(lr);
    }
    while (!item.isEmpty()) {
      LR0Item lr = item.pop();
      int pos = lr.getDotPosition();
      // pos在产生式最后面，说明为规约项目
      if (pos == lr.getProduction().getBodySize()) {
        continue;
      }
      else {
        // 找到后续文法符
        GrammarSymbol symbol = lr.getProduction().getpBodySymbolTable().get(pos);
        // 该文法符为非终结符，说明为待约项目
        if (symbol.getType() == SymbolType.NONTERMINAL) {
          // 遍历该非终结符的每个产生式
          for (Production production: ((NonTerminalSymbol)symbol).getpProductionTable()) {
            if (!itemSet.containItem(production, 0)) {
              // 新建一个非核心项，原点位置为0
              LR0Item newItem = new LR0Item((NonTerminalSymbol)symbol, production, 0,
                  ItemCategoy.NONCORE);
              // 添加进闭包
              itemSet.addItem(newItem);
              // 推入栈中
              item.push(newItem);
            }
          }
        }
      }
    }
  }

  private static ArrayList<ItemSet> allItemSet = new ArrayList<>();
  public static void addItemSet(ItemSet set) {
    allItemSet.add(set);
  }

  public static ArrayList<ItemSet> getAllItemSet() {
    return allItemSet;
  }
  private static ArrayList<ItemSet> newItemSet = new ArrayList<>();

  public static ArrayList<TransitionEdge> exhaustTransition(ItemSet itemSet) {
    // 保存新创建的变迁边
    ArrayList<TransitionEdge> edges = new ArrayList<>();
    Map<GrammarSymbol, Vector<LR0Item>> map = new HashMap<>();
    // 穷举所有驱动符，并将其保存在map中
    for (LR0Item item: itemSet.getpItemTable()) {
      int pos = item.getDotPosition();
      // 当前项目为规约项目
      if (pos == item.getProduction().getBodySize()) {
        continue;
      }
      // 获得后续文法符
      GrammarSymbol symbol = item.getProduction().getpBodySymbolTable().get(pos);
      // 该文法符未出现，则新创建一个vector对象
      if (map.get(symbol) == null) {
        Vector<LR0Item> v = new Vector<>();
        v.add(item);
        map.put(symbol, v);
      }
      // 之前出现过，则在后面添加item
      else {
        map.get(symbol).add(item);
      }
    }
    // 遍历所有驱动符
    for (GrammarSymbol symbol: map.keySet()) {
      // 下一项集的建立,id为-1（防止重复导致的状态序号不连续）
      ItemSet toSet = new ItemSet(-1);
      for (LR0Item item: map.get(symbol)) {
        // 新建一个核心项，其type为CORE，pos加一
        LR0Item lr = new LR0Item(item);
        toSet.addItem(lr);
      }
      // 求该项集的闭包
      getClosure(toSet);
      // 判断下一项集是否为新项集
      Boolean isExist = false;
      for (ItemSet set: allItemSet) {
        if (toSet.isSame(set)) {
          isExist = true;
          toSet = set;
          break;
        }
      }
      // 该项集为新项集
      if (!isExist) {
        // 设置该项集为新的项集id，保证连续
        toSet.setStateId();
        allItemSet.add(toSet);
        newItemSet.add(toSet);
      }
      // 创建一条变迁边
      TransitionEdge edge = new TransitionEdge(symbol, itemSet, toSet);
      edges.add(edge);
    }
    return edges;
  }

  public static DFA getDFA(ItemSet start) {
    // 新建一个DFA
    DFA dfa = new DFA(start);
    // 保存未穷举状态的项集
    Deque<ItemSet> queue = new ArrayDeque<>();
    queue.push(start);
    while (!queue.isEmpty()) {
      ItemSet current = queue.pop();
      // 对当前项集进行穷举，得到变迁边
      ArrayList<TransitionEdge> edges = exhaustTransition(current);
      queue.addAll(newItemSet);
      newItemSet.clear();
      // 添加所有变迁表到dfa中
      dfa.addEdges(edges);
    }
    return dfa;
  }

  public static Boolean isSLR1() {
    // 遍历每个项集
    for (ItemSet set: allItemSet) {
      // 设置规约项目和移入终结符集合
      ArrayList<LR0Item> reduce = new ArrayList<>();
      ArrayList<TerminalSymbol> shift = new ArrayList<>();
      // 遍历项集里面的每个项目
      for (LR0Item item: set.getpItemTable()) {
        Production production = item.getProduction();
        int pos = item.getDotPosition();
        // 该项目为规约项目，添加该项目到规约项目集合
        if (pos == production.getBodySize()) {
          reduce.add(item);
        }
        // 该项目为移入项目，添加终结符到移入终结符集合
        else if (production.getpBodySymbolTable().get(pos).getType() == SymbolType.TERMINAL) {
          shift.add((TerminalSymbol) production.getpBodySymbolTable().get(pos));
        }
      }
      // 判断规约-移入冲突
      if (shift.size() > 0 && reduce.size() > 0) {
        // 遍历每个规约项目
        for (LR0Item item: reduce) {
          // 求其产生式左部的非终结符的FOLLOW集合
          Set<TerminalSymbol> follow = item.getNonTerminalSymbol().getpFollowSet();
          // FOLLOW集合不能与移入终结符集合相交，否则不为SLR(1)文法
          for (TerminalSymbol symbol: follow) {
            if (shift.contains(symbol)) {
              return false;
            }
          }
        }
      }
      // 判断规约-规约冲突
      if (reduce.size() > 1) {
        Set<TerminalSymbol> followReduce = new HashSet<>();
        for (LR0Item item: reduce) {
          // 求其产生式左部的非终结符的FOLLOW集合
          Set<TerminalSymbol> follow = item.getNonTerminalSymbol().getpFollowSet();
          for (TerminalSymbol symbol: follow) {
            // 若该FOLLOW集合中的元素在其他规约项目的FOLLOW集中出现过，则存在规约-规约冲突
            if (followReduce.contains(symbol)) {
              return false;
            }
            // 若未出现，则加入
            else {
              followReduce.add(symbol);
            }
          }
        }
      }
    }
    return true;
  }

  // LR语法分析表的ACTION部分
  private static ArrayList<ActionCell> pActionCellTable = new ArrayList<>();
  // LR语法分析表的GOTO部分
  private static ArrayList<GotoCell> pGotoCellTable = new ArrayList<>();

  public static ArrayList<ActionCell> getpActionCellTable() {
    return pActionCellTable;
  }

  public static ArrayList<GotoCell> getpGotoCellTable() {
    return pGotoCellTable;
  }

  public static void getCell(DFA dfa) {
    // 遍历所有状态集合
    for (ItemSet set: allItemSet) {
      // 遍历每个项集下的所有项目
      for (LR0Item item: set.getpItemTable()) {
        Production production = item.getProduction();
        // 原点所处位置
        int pos = item.getDotPosition();
        // 该项目为规约项目，找到FOLLOW集合填r
        if (pos == production.getBodySize()) {
          // 其中该项目为接受项目，在“#”上填a
          if (item.getProduction().getProductionId() == 0) {
            // 创建对应ACTION
            ActionCell cell = new ActionCell(set.getStateId(), "#",
                ActionCategory.a, 0);
            pActionCellTable.add(cell);
          }
          // 求其产生式左部的非终结符的FOLLOW集合
          Set<TerminalSymbol> follow = item.getNonTerminalSymbol().getpFollowSet();
          // 遍历FOLLOW集合中的每个终结符
          for (TerminalSymbol symbol: follow) {
            // 创建对应ACTION
            ActionCell cell = new ActionCell(set.getStateId(), symbol.getName(),
                ActionCategory.r, item.getProduction().getProductionId());
            pActionCellTable.add(cell);
          }
          continue;
        }
        // 原点后的文法符
        GrammarSymbol symbol = production.getpBodySymbolTable().get(pos);
        // 找到该文法符驱动的下一状态
        ItemSet nextSet = dfa.findNextSet(set, symbol);
        // 该项目为移入项目，找到终结符填s
        if (symbol.getType() == SymbolType.TERMINAL) {
          // 创建对应ACTION
          ActionCell cell = new ActionCell(set.getStateId(), symbol.getName(),
              ActionCategory.s, nextSet.getStateId());
          pActionCellTable.add(cell);
        }
        // 该项目为待约项目，找到非终结符在GOTO填状态序号
        else {
          // 创建对应GOTO
          GotoCell cell = new GotoCell(set.getStateId(), symbol.getName(),
              nextSet.getStateId());
          pGotoCellTable.add(cell);
        }
      }
    }
  }

  public static ArrayList<ProductionInfo> createInfo(NonTerminalSymbol symbol) {
    ArrayList<ProductionInfo> productionInfoTable = new ArrayList<>();
    for (Production production: symbol.getpProductionTable()) {
      ProductionInfo info = new ProductionInfo(symbol.getName(), production.getBodySize());
      productionInfoTable.add(info);
    }
    return productionInfoTable;
  }
}
