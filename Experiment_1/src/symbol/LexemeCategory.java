package symbol;

/**
 * @author 曾佳宝
 * @date 2023/3/15 21:55
 */
public enum LexemeCategory {
  // 空字符
  EMPTY,
  // 整数常量
  INTEGER_CONST,
  // 实数常量
  FLOAT_CONST,
  // 科学计数法常量
  SCIENTIFIC_CONST,
  // 数值运算词
  NUMERIC_OPERATOR,
  // 注释
  NOTE,
  // 字符串常量
  STRING_CONST,
  // 空格常量
  SPACE_CONST,
  // 比较运算词
  COMPARE_CONST,
  // 变量词
  ID,
  // 逻辑运算词
  LOGIC_OPERATOR,
  // 关键字
  KEYWORD
}
