/**Vytvoří CalculationListMember typu binární operátor. */
public class MemberBinOperator extends CalculationListMember {
	
	/**@param operator Operátor (např. +, -, *, atd...)
	 * */
	public MemberBinOperator(String operator) {
		super(operator, "   ", " "+operator+" ", "   ");
		this.firstArg = operator;
		super.argQnty = 1;
		if(operator.length()!=1) {
			throw new IllegalArgumentException("The length of the binary operator must be 1, but it is not!");
		}
	}
	
	@Override
	public CalculationListMember changeArg(int argOrder, String newArg) {
		return new MemberBinOperator(this.firstArg);
	};
}
