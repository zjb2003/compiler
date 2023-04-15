package symbol;

/**
 * @author Ôø¼Ñ±¦
 * @date 2023/4/9 16:19
 */
public class Cell {
  private NonTerminalSymbol nonTerminalSymbol;
  private TerminalSymbol terminalSymbol;
  private Production production;

  public Cell(NonTerminalSymbol nonTerminalSymbol, TerminalSymbol terminalSymbol,
      Production production) {
    this.nonTerminalSymbol = nonTerminalSymbol;
    this.terminalSymbol = terminalSymbol;
    this.production = production;
  }

  @Override
  public String toString() {
    return "Cell{" +
        "nonTerminalSymbol=" + nonTerminalSymbol.getName() +
        ", terminalSymbol=" + terminalSymbol.getName() +
        ", production=" + production +
        '}' + '\n';
  }
}
