/**Vytvoří CalculationListMember typu mocnina. */
public class MemberExponent extends CalculationListMember {

	/**@param base Base of the exponent member.
	 * @param power Power of the exponent member.
	 * */
	public MemberExponent(String base, String power) {
		super("(("+base+")^("+power+"))", " ",base, "");
		super.firstArg = base;
		super.secondArg = power;
		super.argQnty = 2;
		int lenBase = base.length();
		int lenPower = power.length();
		this.setUpperViewMember(MemberExponent.spaces(lenBase)+power);
		this.setBaseViewMember(base+MemberExponent.spaces(lenPower));
		this.setLowerViewMember(MemberExponent.spaces(lenBase+lenPower));
	} 
	
	@Override
	public boolean isMultiArgMember() {
		return true;
	}
	
	@Override
	public CalculationListMember changeArg(int argOrger,String newArg) {
		if(argOrger==1) {
			//první zadávaný argument je base		
				return new MemberExponent(newArg,this.secondArg);
		} else if (argOrger==2){
			//druhý zadávaný argument je power
				return new MemberExponent(this.firstArg, newArg);
		} else {
				throw new IllegalArgumentException("Non-existent order of argument!");
		}
	}
}
