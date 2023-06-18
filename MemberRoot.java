/**Vytvoří CalculationListMember typu odmocnina. */
public class MemberRoot extends CalculationListMember {
	
	/**@param radicand Radicand of the root member.
	 * @param nthRoot nthRoot of the root member.*/
	public MemberRoot(String radicand, String nthRoot) {
		super("(("+radicand+")^(1/("+nthRoot+")))", " ",radicand,"");
		
		super.secondArg = radicand;
		super.firstArg = nthRoot;
		super.argQnty = 2;
		
		int lenRadicand = radicand.length();
		int lenNthRoot = nthRoot.length();
		
		if(lenNthRoot==0) {
		this.setUpperViewMember(spaces(lenRadicand+1));	
		} else {
		this.setUpperViewMember(nthRoot+underscores(lenRadicand));	
		};

		this.setBaseViewMember(spaces(lenNthRoot-1)+"√"+radicand);
		this.setLowerViewMember(spaces(this.getBaseViewMember().length()));
	}
	
	/**@param underscoresLen Požadavaná délka string sestaveného z podtržítek.
	 * @return Vrací string sestavený ze zadaného počtu podtržítek.
	 * */
	public static String underscores(int underscoresLen) {
		String underscore = "";
		for (int i = 0; i < underscoresLen; i++){
			underscore = underscore + "_";
		}
		return underscore;
		}
		
	@Override
	public CalculationListMember changeArg(int argOrger, String newArg) {
		if(argOrger==1) {
		//první zadávaný argument je nthRoot		
			return new MemberRoot(this.secondArg, newArg);
		} else if (argOrger==2){
		//druhý zadávaný argument je radicand
			return new MemberRoot(newArg, this.firstArg);
		} else {
			throw new IllegalArgumentException("Non-existent order of argument!");
		}
	}
		
}
