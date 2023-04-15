package symbol;

/**
 * @author 曾佳宝
 * @date 2023/4/9 16:28
 */
public class ProductionInfo {
  // 产生式序号
  private int indexId;
  // 头部非终结符
  private String headName;
  // 产生式体中文法符的个数
  private int bodySize;

  private static int idNum = 0;

  public ProductionInfo(String headName, int bodySize) {
    this.indexId = idNum++;
    this.headName = headName;
    this.bodySize = bodySize;
  }

  @Override
  public String toString() {
    return "\nProductionInfo{" +
        "indexId=" + indexId +
        ", headName='" + headName + '\'' +
        ", bodySize=" + bodySize +
        '}';
  }
}
