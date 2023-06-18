/**Vytvoří CalculationListMember typu symbol. */
public class MemberSymbol extends CalculationListMember {
	
	/**Používá se, pokud se symbol pro zobrazení i evaluaci je stejný.
	 * @param symbol Symbol, který bude použit pro zobrazení i evaluaci.
	 * */
	public MemberSymbol(String symbol) {
		super(symbol, " ", symbol, " ");
		super.firstArg=symbol;
		super.argQnty=1;
		if(symbol.length()!=1) {
			throw new IllegalArgumentException("The length of the symbol must be 1, but it is not!");
		}	
	}
	
	/**Používá se, pokud se symbol pro zobrazení i evaluaci je různý.
	 * @param symbolToShow Symbol, který bude použit pro zobrazení.
	 * @param symbolToCalc Symbol, který bude použit pro evaluaci.*/
	public MemberSymbol(String symbolToShow, String symbolToCalc) {
		super(symbolToCalc, MemberSymbol.spaces(symbolToShow.length()), symbolToShow, MemberSymbol.spaces(symbolToShow.length()));
		super.argQnty=1;
	}
	
	@Override
	public CalculationListMember changeArg(int argOrder, String newArg) {
		return new MemberSymbol(super.firstArg);
	}
}
