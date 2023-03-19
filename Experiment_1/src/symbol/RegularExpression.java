package symbol; /**
 * @author 曾佳宝
 * @date 2023/3/15 21:52
 */


public class RegularExpression {
  int regularId;
  String name;
  /**
   * 正则运算符，共有 7 种：‘=’, ‘~’，‘-’, ‘|’，‘.’, ‘*’, ‘+’, ‘?’
   */
  char operatorSymbol;
  /**
   * 左操作数
    */
  int operandId1;
  /**
   * 右操作数（一元运算时为null）
   */
  int operandId2;
  /**
   * 左操作数的类型
   */
  OperandType type1;
  /**
   * 右操作数的类型（一元运算时为null）
   */
  OperandType type2;
  /**
   * 运算结果的类型
   */
  OperandType resultType;
  /**
   * 词的 category 属性值
   */
  LexemeCategory category;
  /**
   * 对应的 NFA
   */
  Graph pNFA;

  private static int regularIdNum = 0;

  public RegularExpression(char operatorSymbol, int operandId1,
      int operandId2, OperandType type1, OperandType type2, OperandType resultType,
      LexemeCategory category, Graph pNFA) {
    this.regularId = regularIdNum++;
    this.name = Integer.toString(regularId);
    this.operatorSymbol = operatorSymbol;
    this.operandId1 = operandId1;
    this.operandId2 = operandId2;
    this.type1 = type1;
    this.type2 = type2;
    this.resultType = resultType;
    this.category = category;
    this.pNFA = pNFA;
  }
}
