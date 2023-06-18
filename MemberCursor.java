/**Vytvoří CalculationListMember typu kurzor. */
public class MemberCursor extends CalculationListMember {
	
	public MemberCursor() {
		super("", "⎜", "⎜", "⎜");
		super.argQnty = 1;
	}
	
	@Override
	public CalculationListMember changeArg(int argOrder, String newArg) {
		return new MemberCursor();
	};
}
