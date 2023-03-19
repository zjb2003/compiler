import java.util.ArrayList;
import symbol.CharSet;

/**
 * @author 曾佳宝
 * @date 2023/3/15 22:13
 */

public class Problem1 {

  /**
   * 字符集表的定义
   */
  static public ArrayList<CharSet> pCharSetTable = new ArrayList<>();

  public static int range(char fromChar, char toChar) {
    /**
     * 字符的范围运算
     */
    CharSet c = new CharSet(fromChar, toChar);
    pCharSetTable.add(c);
    return c.getIndexId();
  }

  public static int union(char c1, char c2) {
    /**
     * 字符的并运算
     */
    CharSet charSet1 = new CharSet(c1, c1);
    pCharSetTable.add(charSet1);
    if (c1 != c2) {
      CharSet charSet2 = new CharSet(c1, c2, charSet1.getIndexId());
      pCharSetTable.add(charSet2);
    }
    return charSet1.getIndexId();
  }

  public static int union(int CharSetId, char c) {
    /**
     * 字符集与字符之间的并运算
     */
    int newId = 0;
    boolean flag = false;
    for (CharSet charSet: pCharSetTable) {
      if (charSet.getIndexId() == CharSetId) {
        if (flag) {
          // 新建一个段
          CharSet newCharSet = new CharSet(charSet.getFromChar(), charSet.getToChar(), newId);
          pCharSetTable.add(newCharSet);
        }
        else {
          // 新建一个字符集
          CharSet newCharSet = new CharSet(charSet.getFromChar(), charSet.getToChar());
          newId = newCharSet.getIndexId();
          pCharSetTable.add(newCharSet);
          flag = true;
        }
      }
    }
    CharSet newSegment = new CharSet(c, c, newId);
    pCharSetTable.add(newSegment);
    return newId;
  }

  public static int union(int CharSetId1, int CharSetId2) {
    /**
     * 字符集与字符集的并运算
     */
    int newId = 0;
    boolean flag = false;
    ArrayList<CharSet> addList = new ArrayList<>();
    for (CharSet charSet: pCharSetTable) {
      if (charSet.getIndexId() == CharSetId1) {
        if (flag) {
          // 新建一个段
          CharSet newCharSet = new CharSet(charSet.getFromChar(), charSet.getToChar(), newId);
          addList.add(newCharSet);
        }
        else {
          // 新建一个字符集
          CharSet newCharSet = new CharSet(charSet.getFromChar(), charSet.getToChar());
          newId = newCharSet.getIndexId();
          addList.add(newCharSet);
          flag = true;
        }
      }
    }
    for (CharSet charSet: pCharSetTable) {
      if (charSet.getIndexId() == CharSetId2) {
        // 新建一个段
        CharSet newCharSet = new CharSet(charSet.getFromChar(), charSet.getToChar(), newId);
        addList.add(newCharSet);
      }
    }
    for (CharSet charSet: addList) {
      pCharSetTable.add(charSet);
    }
    return newId;
  }

  public static int difference(int CharSetId, char c) {
    /**
     * 字符集与字符之间的差运算
     */
    int newId = 0;
    boolean flag = false;
    for (CharSet charSet: pCharSetTable) {
      if (charSet.getIndexId() == CharSetId) {
        if (charSet.getFromChar() < c && charSet.getToChar() > c) {
          if (flag) {
            // 新建一个段
            CharSet newCharSet1 = new CharSet(charSet.getFromChar(), (char)(c-1), newId);
            pCharSetTable.add(newCharSet1);
          }
          else {
            // 新建一个字符集
            CharSet newCharSet1 = new CharSet(charSet.getFromChar(), (char)(c-1));
            newId = newCharSet1.getIndexId();
            pCharSetTable.add(newCharSet1);
            flag = true;
          }
          CharSet newCharSet2 = new CharSet((char)(c+1), charSet.getToChar(), newId);
          pCharSetTable.add(newCharSet2);
        }
        else if (charSet.getFromChar() == c) {
          if (flag) {
            // 新建一个段
            CharSet newCharSet = new CharSet((char)(c+1), charSet.getToChar(), newId);
            pCharSetTable.add(newCharSet);
          }
          else {
            // 新建一个字符集
            CharSet newCharSet = new CharSet((char)(c+1), charSet.getToChar());
            newId = newCharSet.getIndexId();
            pCharSetTable.add(newCharSet);
            flag = true;
          }
        }
        else if (charSet.getToChar() == c) {
          if (flag) {
            // 新建一个段
            CharSet newCharSet = new CharSet(charSet.getFromChar(), (char)(c-1), newId);
            pCharSetTable.add(newCharSet);
          }
          else {
            // 新建一个字符集
            CharSet newCharSet = new CharSet(charSet.getFromChar(), (char)(c-1));
            newId = newCharSet.getIndexId();
            pCharSetTable.add(newCharSet);
            flag = true;
          }
        }
        else {
          if (flag) {
            // 新建一个段
            CharSet newCharSet = new CharSet(charSet.getFromChar(), charSet.getToChar(), newId);
            pCharSetTable.add(newCharSet);
          }
          else {
            // 新建一个字符集
            CharSet newCharSet = new CharSet(charSet.getFromChar(), charSet.getToChar());
            newId = newCharSet.getIndexId();
            pCharSetTable.add(newCharSet);
            flag = true;
          }
        }
      }
    }
    return newId;
  }

  public static void main(String[] args) {
    // rangeTest
    int rangeId1 = Problem1.range('a', 'c');
    int rangeId2 = Problem1.range('a', 'c');
    System.out.println(rangeId1);
    System.out.println(rangeId2);
    // unionTestCharAndChar
//    int rangeId2 = Problem1.union('a','a');
//    System.out.println(Problem1.pCharSetTable.size());
//    rangeId2 = Problem1.union('a','c');
//    System.out.println(Problem1.pCharSetTable.size());
    // unionTestCharSetAndChar
    // unionTestCharSetAndCharset
    // differenceTest
  }
}
