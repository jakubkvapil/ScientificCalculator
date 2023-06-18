/**Vytvoří CalculationListMember typu goniometrická funkce nebo absolutní hodnota. */
public class MemberTrigonometricAbs extends CalculationListMember {
	
	/**@param function Funkce, kterou má CalculationListMember představovat.*/
	public MemberTrigonometricAbs(String function) {
		super(function+"(","",function+"(","");
		super.argQnty=1;
		super.firstArg = function;
		int lengthBaseMember = this.getBaseViewMember().length();
		this.setUpperViewMember(MemberTrigonometricAbs.spaces(lengthBaseMember));
		this.setLowerViewMember(MemberTrigonometricAbs.spaces(lengthBaseMember));
	}
	
	@Override
	public CalculationListMember changeArg(int argOrder, String newArg) {
		return new MemberTrigonometricAbs(super.firstArg);
	}
	
}
