package symbol; /**
 * @author 曾佳宝
 * @date 2023/3/15 22:00
 */

public class CharSet {
  /*
    字符进行范围运算('a'~'z')、并运算(a|b)和差运算(-) 都返回一个新的字符集对象
    */
  /** 字符集id */
  private final int indexId;
  /** 字符集中的段 id。一个字符集可以包含多个段 */
  private final int segmentId;
  /** 段的起始字符 */
  private final char fromChar;
  /** 段的结尾字符 */
  private final char toChar;

  private static int indexIdNum = 0;
  private static int segmentIdNum = 0;

  public CharSet(char fromChar, char toChar) {
    /**
     * 构造新的字符集
     */
    this.indexId = indexIdNum++;
    this.segmentId = segmentIdNum++;
    this.fromChar = fromChar;
    this.toChar = toChar;
  }

  public CharSet(char fromChar, char toChar, int indexId) {
    /**
     * 构造新的字符集的段
     */
    this.indexId = indexId;
    this.segmentId = segmentIdNum++;
    this.fromChar = fromChar;
    this.toChar = toChar;
  }

  public int getIndexId() {
    return indexId;
  }

  public int getSegmentId() {
    return segmentId;
  }

  public char getFromChar() {
    return fromChar;
  }

  public char getToChar() {
    return toChar;
  }

  public static int getIndexIdNum() {
    return indexIdNum;
  }
}
