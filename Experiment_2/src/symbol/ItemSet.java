package symbol;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @author 曾佳宝
 * @date 2023/4/9 16:21
 */
public class ItemSet {
  /**
   * LR(0)项集
   */
  // 状态序号
  private int stateId;
  // LR0 项目表
  private ArrayList<LR0Item> pItemTable;

  private static int idNum = 0;

  public ItemSet() {
    stateId = idNum++;
    pItemTable = new ArrayList<>();
  }

  public ItemSet(int stateId) {
    this.stateId = stateId;
    pItemTable = new ArrayList<>();
  }

  public int getStateId() {
    return stateId;
  }

  public void setStateId() {
    this.stateId = idNum++;
  }

  public ArrayList<LR0Item> getpItemTable() {
    return pItemTable;
  }

  public void setpItemTable(ArrayList<LR0Item> pItemTable) {
    this.pItemTable = pItemTable;
  }

  public void addItem(LR0Item item) {
    pItemTable.add(item);
  }

  public Boolean containItem(Production production, int pos) {
    for (LR0Item item1: pItemTable) {
      if (item1.getProduction() == production && item1.getDotPosition() == pos) {
        return true;
      }
    }
    return false;
  }

  public Boolean isSame(ItemSet itemSet) {
    // 项目数目不同，则必不相同
    if (pItemTable.size() != itemSet.getpItemTable().size()) {
      return false;
    }
    // 此时项目数目相同
    for (int i = 0; i < pItemTable.size(); i ++) {
      // 若itemSet中不包含该项目，则两者必然存在差异
      if (!pItemTable.get(i).equals(itemSet.getpItemTable().get(i))) {
        return false;
      }
    }
    return true;
  }

  @Override
  public String toString() {
    return "ItemSet{" +
        "stateId=" + stateId +
        ", pItemTable=" + pItemTable +
        '}';
  }
}
