/**Vytvoří CalculationListMember typu číslice. */
public class MemberDigit extends CalculationListMember {
	
	/**@param digit Číslo, kterou má member představovat (např. 1, 2, 3,...)
	 * */
	public MemberDigit(String digit) {
		super(digit, " ",digit, " ");
		super.argQnty = 1;
		this.firstArg = digit;
		if(digit.length()!=1) {
			throw new IllegalArgumentException("The length of the input must be 1, but it is not!");
		}
	}
	
	@Override
	public CalculationListMember changeArg(int argOrder, String newArg) {
		return new MemberDigit(this.firstArg);
	}
}
