/**Abstraktní třída sloužící pro definování základních společných atributů a metod členů výrazu.
 *  * @author Jakub KVAPIL
 * @version 1.08 
 * */
public abstract class CalculationListMember {
	/**Slouží pro evaluaci členu.*/
	private String evalMember="";
	/**Slouží pro zobrazení členu v horním řádku disleje.*/
	private String upperViewMember="";
	/**Slouží pro zobrazení členu ve středním řádku disleje.*/
	private String baseViewMember="";	
	/**Slouží pro zobrazení členu ve spodním řádku disleje.*/	
	private String lowerViewMember="";
	/**Počet argumentů u členu (buď jeden u single arg memberu nebo 2 u multi arg memberu).*/
	int argQnty;
	/**První argument u multi arg memberu.*/
    String firstArg; 
	/**Druhý argument u multi arg memberu.*/    
    String secondArg;
    /**Délka členu pro zobrazení.*/
	private int lenViewMember;
	
	CalculationListMember(String evalMember, String upperViewMember, String baseViewMember, String lowerViewMember) {
		this.evalMember = evalMember;
		this.upperViewMember = upperViewMember;
		this.baseViewMember = baseViewMember;
		this.lowerViewMember = lowerViewMember;
		updateMemberLength();
	};
	
	public void setEvalMember(String string) {
		this.evalMember = string;
		updateMemberLength();
	}
	
	public void setUpperViewMember(String string) {
		this.upperViewMember = string;
		updateMemberLength();
	}
	
	public void setBaseViewMember(String string) {
		this.baseViewMember = string;
		updateMemberLength();
	}
	
	public void setLowerViewMember(String string) {
		this.lowerViewMember = string;
		updateMemberLength();
	}
		
	public int getArgQnty() {
		return this.argQnty;
	}
	
	public String getEvalMember() {
		return this.evalMember;
	}
	
	public String getUpperViewMember() {
		return this.upperViewMember;
	}
		
	public String getBaseViewMember() {
		return this.baseViewMember;
	}
	
	public String getFirstArg() {
		System.out.println("Z volání getFirstArg, firstArg je: "+this.firstArg);
		return this.firstArg;
	}
	public String getSecondArg() {
		System.out.println("Z volání getSecondArg, secondArg je: "+this.secondArg);
		return this.secondArg;
	}
	
	public String getLowerViewMember() {
		return this.lowerViewMember;
	}
	
	public int getLenViewMember() {
		return lenViewMember;
	}
	
	@Override
	public String toString() {
		
		return "\n" + "evalMember     :" + getEvalMember() + "|length: " + getEvalMember().length() +
			   "\n" + "upperViewMember:" + getUpperViewMember() + "|length: " + getUpperViewMember().length() +
			   "\n" + "baseViewMember :" + getBaseViewMember() + "|length: " + getBaseViewMember().length() +
			   "\n" + "lowerViewMember:" + getLowerViewMember() + "|length: " + getLowerViewMember().length();
	}
	
	/**Updatuje lenViewMember (délku členu pro zobrazení).*/
	public void updateMemberLength() {
		int lenUpperViewMember = upperViewMember.length();
		int lenBaseViewMember = baseViewMember.length();	
		int lenLowerViewMember = lowerViewMember.length();
		int maxLen = Math.max(lenUpperViewMember, lenBaseViewMember);
		this.lenViewMember = Math.max(maxLen, lenLowerViewMember);
	}
	
	/**@return Vrací string obsahující zadaný počet mezer.
	 * @param numberOfSpaces Počet mezer, který má mít string.*/
	public static String spaces(int numberOfSpaces) {
		String space = "";
		for (int i = 0; i < numberOfSpaces; i++){
			space = space + " ";
		}
		return space;
	}
	
	/**@return Vrací true, je-li člen multi arg member.*/
	public boolean isMultiArgMember() {
		if (this.argQnty==1) {
			return false;
			} else {
			return true;}
	}
	
	/**Změní právě editovaný argument.
	 * @return Vrací upravený CalculationListMember
	 * @param argOrder Pořadí argumentu, který bude změněn.
	 * @param newArg Nový argument.
	 * */
	abstract public CalculationListMember changeArg(int argOrder, String newArg);
	
}
