/**Vytvoří CalculationListMember typu zlomek. */
public class MemberFraction extends CalculationListMember {
	
	/**@param numerator Numerator of the fraction
	 * @param denominator Denominator of the fraction
	 * */
	public MemberFraction(String numerator, String denominator) {
		super("(("+numerator+")/("+denominator+"))", " "+numerator+" ", getFractionBar(1), " "+denominator+" ");
		super.firstArg = numerator;		
		super.secondArg = denominator;
		super.argQnty = 2;
		int lenNumerator = numerator.length();
		int lenDenominator = denominator.length();
		int lenFractionBar = Math.max(lenNumerator, lenDenominator)+2;
		this.setBaseViewMember(getFractionBar(lenFractionBar));
		if (lenNumerator>=lenDenominator) {
			int spacesBeforeNumber = (lenFractionBar - lenDenominator)/2; 
			int spacesAfterNumber = lenFractionBar - spacesBeforeNumber - lenDenominator;
			this.setLowerViewMember(MemberFraction.spaces(spacesBeforeNumber)+denominator+MemberFraction.spaces(spacesAfterNumber));
		} else {
			int spacesBeforeNumber = (lenFractionBar - lenNumerator)/2; 
			int spacesAfterNumber = lenFractionBar - spacesBeforeNumber - lenNumerator;
			this.setUpperViewMember(MemberFraction.spaces(spacesBeforeNumber)+numerator+MemberFraction.spaces(spacesAfterNumber));
		}
		this.setUpperViewMember(" "+this.getUpperViewMember()+" ");
		this.setBaseViewMember(" "+this.getBaseViewMember()+" ");
		this.setLowerViewMember(" "+this.getLowerViewMember()+" ");
	}
	
	/**@param barLen Požadavaná délka zlomkové čáry
	 * @return Vrací string představující zlomkovou čáru.
	 * */
	public static String getFractionBar(int barLen) {
		String bar = "";
		for (int i = 0; i < barLen; i++){
			bar = bar + "—";		
		}
		return bar; 
	}

	@Override
	public CalculationListMember changeArg(int argOrder, String newArg) {
		if(argOrder==1) {
			//první zadávaný argument je citatel		
				return new MemberFraction(newArg,this.secondArg);
			} else if (argOrder==2){
			//druhý zadávaný argument je jmenovatel
				return new MemberFraction(this.firstArg,newArg);
			} else {
				throw new IllegalArgumentException("Non-existent order of argument!");
			}
	}
	
}
