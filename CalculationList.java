import java.util.ArrayList;
import com.fathzer.soft.javaluator.DoubleEvaluator; // from https://javaluator.sourceforge.net/en/home/

/**Slouží k uchovávání a manipulaci s výrazem, který je sestaven z CalculationListMember. Výraz je uchováván ve
 * formě pro zobrazení, tak ve formě pro evaluaci.
 * 
 * @author Jakub KVAPIL
 * @version 1.03 
 * */
public class CalculationList {
	/**Třída CalculationArray je singleton.*/
	private static final CalculationList instance = new CalculationList(); 
	/** ArrayList, do kterého se ukládají postupně členy výrazu k evaluaci. Nese v sobě všechny informace potřebné k výpočtu.*/
	private ArrayList<CalculationListMember> calculationList;
	/**Zde jsou uloženy výrazy v evaluaci. Neslouží pro zobrazení. Musí být ve formátu, který dokaže zpracovat evaluator*/
	private String evalMemberString = "";
	/**První řádek na displeyi kalkulačky. Slouží pouze pro zobrazení.*/
	private String upperViewMemberString = "";
	/**Druhý řádek na displeyi kalkulačky. Slouží pouze pro zobrazení.*/
	private String baseViewMemberString = "";
	/**Třetí řádek na displeyi kalkulačky. Slouží pouze pro zobrazení.*/
	private String lowerViewMemberString = "";
	/**Velký kurzor(přes všechny 3 řádky). Vystupuje jako člen v calculationList.  Slouží pouze pro zobrazení.*/
	private MemberCursor cursor;
	/**Index kurzoru v calculationList*/
	private int cursorIndex;
	/**Reprezentuje vizibilitu kurzoru v calculationList*/
	private boolean cursorVisibility;
	/**Slouží pro evaluaci výsledku. Objekt vytvořen pomocí knihovny Javaluator*/
	private DoubleEvaluator evaluator;
	/**Označuje, zda je aktivní multiagrument mód.*/
	private boolean multiArgModeState;
	/**Právě upravovaný string argumentu v multiargument módu.*/
	private String currentlyEditedString;
	/**Index kurzoru v právě upravovaném stringu argumentu v multiargument módu.*/
	private int currentlyEditedCursorIndexString;
	/**Pořadí zadávání argumentů multiarg memberu.*/
	private int currentlyEditedArgOrder;
	/**Označuje který multiarg člen v calculationListu je právě upravován.*/
	private int currentlyEditedCalculationListMemberIndex;
	/**Podoba jednoduchého kurzoru při úpravách argumentů multiarg membberu.*/
	final public static String CURSOR_STRING = "⎜";	
	
	private CalculationList() {
		this.calculationList = new ArrayList<CalculationListMember>();
		this.evaluator = new DoubleEvaluator();
		this.cursor = new MemberCursor();
		this.multiArgModeState = false;
		this.currentlyEditedCursorIndexString = 0;
		this.cursorVisibility = true;
		this.currentlyEditedString = "";
		calculationList.add(this.cursor);
		cursorIndex = 0;
		update();
	}
	
	/**Smaže veškeré zadané hodnoty. Uvede vše do původního stavu (stavu při zapnutí kalkulačky).*/
	public void allClear() {
		calculationList.clear();
		this.multiArgModeState = false;
		this.currentlyEditedString = "";
		this.currentlyEditedCursorIndexString = 0;
		this.cursor = new MemberCursor();
		calculationList.add(this.cursor);
		cursorIndex = 0;
		update();
		
	}
	
	/**Vloží string do stringu na uvedený index.
	 * @param string String, do kterého bude vkládán jiný string.
	 * @param stringToBeInserted String, který bude vložen do jiného stringu.
	 * @param index Index místa stringu, na které bude vložen jiný string.
	 * @return Vrací string, který vznikl vložením jednoho stringu do druhého na místo označené indexem.
	 * */
	public static String insertToString(String string, String stringToBeInserted, int index) {
		
		String firstPartString = string.substring(0, index);
		String lastPartString = string.substring(index, string.length());
	    return firstPartString + stringToBeInserted + lastPartString;
	}
	
	/**Smaže část stringu dle zadaných parametrů.
	 * @param string String, ze kterého bude část smazána.
	 * @param indexFrom Označuje pozici, od které bude část smazána.
	 * @param lengthOfRemoval Označuje počet znaků, které budou smazány. 
	 * @return Vrací výsledný string, ze kterého byla část odmazána.
	 * */
	public static String removeFromString(String string, int indexFrom, int lengthOfRemoval) {
		String firstPartString = string.substring(0, indexFrom);
		//System.out.println("firstPartString: " +firstPartString);
		String lastPartString = string.substring(indexFrom+lengthOfRemoval, string.length());
		//System.out.println("lastPartString: " +lastPartString);		
	    return firstPartString + lastPartString;
	}
	
	/**Vrací odkaz na instanci této třídy*/
	public static CalculationList getInstance() {
		return instance;
	}
	
	/**Přidí člena do calculationList.*/
	void add(CalculationListMember member) {
		calculationList.add(member);
		update();
	}
	
	/**Přidí člena do calculationList na místo určené indexem.
	 * @param index Označuje pozici v calculationList, na které bude člen přidán.
	 * @param member Člen, který má být přidán do calculationList.*/
	private void add(int index, CalculationListMember member) {
		calculationList.add(index, member);
		update();
	}
	
	/**Přidí člena do calculationList na místo za kurzorem.
	 * @param member Člen, který má být přidán do calculationList.*/
	public int addAfterCursor(CalculationListMember member) {
		int addedMemberIndex = cursorIndex;
		add(cursorIndex, member);
		cursorIndex = cursorIndex+1;
		return addedMemberIndex; 
	}
	
	/**Odebere člena z calculationList na místě určené indexem.
	 * @param index Označuje pozici v calculationList, na které bude člen odebrán.*/
	private void remove(int index) {
		calculationList.remove(index);
		update();
	}
	
	/**Odebere člena z calculationList na místě před kurzorem.*/
	public void deleteBeforeCursor() {
		if (cursorIndex>0) {
			remove(cursorIndex-1);
		cursorIndex = cursorIndex - 1;		
		}
	}
	
	/**Nastaví právě editované pořadí argumentu u multiarg členu. */
	public void setCurrentlyEditedArgOrder(int order) {
		if(order==1) {
			this.currentlyEditedArgOrder = order;	
			this.currentlyEditedString = calculationList.get(this.currentlyEditedCalculationListMemberIndex).getFirstArg();	
		} else if (order==2) {
			this.currentlyEditedArgOrder = order;	
			this.currentlyEditedString =calculationList.get(this.currentlyEditedCalculationListMemberIndex).getSecondArg();
		} else {
			System.out.println("Vyjimecny pocet argumentu. setCurrentlyEditedArgOrder se snazil nastavit: "+order);
		}
	}
	
	
	/**Přepne na editování dalšího argumentu multiarg členu.*/
	public void incrementCurrentlyEditedArgOrder() {
		int argMbrQnty = calculationList.get(this.currentlyEditedCalculationListMemberIndex).getArgQnty();
		if ((this.currentlyEditedArgOrder<argMbrQnty)&&multiArgModeState) {	
			
			if(this.currentlyEditedString!=null&&this.currentlyEditedString!="") {
				int lenEditedStr = this.currentlyEditedString.length();
				char lastChar = this.currentlyEditedString.charAt(lenEditedStr-1);
				if(lastChar=='⎜') {
					this.removeLastCharFromCurrentlyEditedArg();	
				}	
			}
			this.currentlyEditedString = this.currentlyEditedString.replace(CURSOR_STRING, "");		
			
			setCurrentlyEditedArgOrder(this.currentlyEditedArgOrder + 1);			
			setCurrentlyEditedStringCursorIndex(0);

			if(getCurrentlyEditedString().equals("")) {
				this.addStringToCurrentlyEditedArg("");
			} else {		
				String newString = insertToString(this.currentlyEditedString.replace(CURSOR_STRING, ""), CURSOR_STRING, getCurrentlyEditedStringCursorIndex());
				this.currentlyEditedString = newString;
			}
			changeCurrentlyEditedArg();	
			
		} else {
			this.currentlyEditedString = this.currentlyEditedString.replace(CURSOR_STRING, "");
			this.setMultiArgModeState(false);
			changeCurrentlyEditedArg();	
			instance.remove(cursorIndex);
			
			if (cursorIndex==instance.getCurrentlyEditedCalculationListMemberIndex()-1)
			{
				this.cursorIndex = this.cursorIndex+1;
			} else {
			this.cursorIndex = instance.getCurrentlyEditedCalculationListMemberIndex() +1;	
			}
			instance.add(this.cursorIndex, cursor);		
		}
    }
	
	/**Přepne na editování předchozího argumentu multiarg členu.*/
	public void decrementCurrentlyEditedArgOrder() {

		if ((this.currentlyEditedArgOrder>1)&&multiArgModeState) {	
			this.currentlyEditedString = this.currentlyEditedString.replace(CURSOR_STRING, "");
			changeCurrentlyEditedArg();	
			setCurrentlyEditedArgOrder(this.currentlyEditedArgOrder -1);
			setCurrentlyEditedStringCursorIndex(this.currentlyEditedString.length()+1);
			this.addStringToCurrentlyEditedArg("");
			changeCurrentlyEditedArg();
		} else {
			this.currentlyEditedString = this.currentlyEditedString.replace(CURSOR_STRING, "");
			this.setMultiArgModeState(false);
			changeCurrentlyEditedArg();	
			instance.remove(cursorIndex);
			this.cursorIndex = this.cursorIndex - 1;
			if ((this.cursorIndex-1)<0) {
				this.cursorIndex = 0;
			}
			instance.add(this.cursorIndex, cursor);
		}
	}	
	
	/**Nastaví právě upravovaný člen CalculationList. 
	 * @param index CalculationList členu*/
	public void setCurrentlyEditedCalculationListMemberIndex(int index) {
		this.currentlyEditedCalculationListMemberIndex = index;
	}
	
	/**@return Nastaví právě upravovaný člen CalculationList.*/ 
	public int getCurrentlyEditedCalculationListMemberIndex() {
		return this.currentlyEditedCalculationListMemberIndex;
	}
	
	@Override
	public String toString() {
		String string = "";
		for(int i = 0; i<calculationList.size(); i++) {
		string = string + calculationList.get(i).toString() + "\n";
		}
		return string;
	}
	
	/**Updatuje evalMemberString, upperViewMemberString, baseViewMemberString, lowerViewMemberString podle calculationList.*/
	public void update() {
		
		this.evalMemberString = "";
		this.upperViewMemberString = "";
		this.baseViewMemberString = "";
		this.lowerViewMemberString = "";
		
		for(int i = 0; i<calculationList.size(); i++) {
			this.evalMemberString = this.evalMemberString + calculationList.get(i).getEvalMember();
			this.upperViewMemberString = this.upperViewMemberString + calculationList.get(i).getUpperViewMember();
			this.baseViewMemberString = this.baseViewMemberString + calculationList.get(i).getBaseViewMember();	
			this.lowerViewMemberString = this.lowerViewMemberString + calculationList.get(i).getLowerViewMember();
		}	
		this.evalMemberString = this.evalMemberString.replace(CURSOR_STRING, "");
		this.evalMemberString = this.evalMemberString.replace(",", ".");
		this.evalMemberString = this.evalMemberString.replace("π", "pi");
		this.evalMemberString = this.evalMemberString.replace("·", "*");		
		this.evalMemberString = this.evalMemberString.replace("°", "*(pi/180)");		
		
	}
	
	public void setMultiArgModeState(boolean state) {
		this.multiArgModeState = state;
		this.setCursorVisibility(!state);
	}
	
	public boolean getMultiArgModeState() {
		return this.multiArgModeState;
	}
	
	public String getCurrentlyEditedString() {
		return this.currentlyEditedString;
	}

	public String getUpperViewMemberString() {
		return this.upperViewMemberString;
	}
	
	public String getBaseViewMemberString() {
		return this.baseViewMemberString;
	}
	
	public String getLowerViewMemberString() {
		return this.lowerViewMemberString;
	}
	
	public String getViewString() {
		String viewString = "";
		viewString = getUpperViewMemberString()+"\n"+getBaseViewMemberString()+"\n"+getLowerViewMemberString();
		return viewString;
	}
	
	/**Nastavuje visibilitu velkého kurzoru.*/
	public void setCursorVisibility(boolean visibility) {
		this.cursorVisibility = visibility;
		if (visibility) {
			calculationList.get(cursorIndex).setUpperViewMember(CURSOR_STRING);
			calculationList.get(cursorIndex).setBaseViewMember(CURSOR_STRING);
			calculationList.get(cursorIndex).setLowerViewMember(CURSOR_STRING);		
		} else {
			calculationList.get(cursorIndex).setUpperViewMember("");
			calculationList.get(cursorIndex).setBaseViewMember("");
			calculationList.get(cursorIndex).setLowerViewMember("");
		}
		update();
	}
	
	/**Nastaví index kurzoru v právě upravovaném multiarg členu CalculationListu.
	 * @param index Index kurzoru, který bude nastaven*/
	public void setCurrentlyEditedStringCursorIndex(int index) {
		this.currentlyEditedCursorIndexString = index;
	}
	
	/**Vrací index kurzoru v právě upravovaném multiarg členu CalculationListu.*/
	private int getCurrentlyEditedStringCursorIndex() {
		return this.currentlyEditedCursorIndexString;
	}
	
	/**Posune hlavní kurzor doleva*/
	public void cursorLeft() {
		
		if (this.cursorIndex>0) {	
		//Jestli je předchozí člen multiarg member
			if (calculationList.get(this.cursorIndex-1).isMultiArgMember()) {
				instance.setMultiArgModeState(true);
				this.setCurrentlyEditedCalculationListMemberIndex(cursorIndex-1);
				instance.setCurrentlyEditedArgOrder(calculationList.get(cursorIndex-1).getArgQnty());	
				instance.setCurrentlyEditedStringCursorIndex(getCurrentlyEditedString().length());
				this.currentlyEditedString = this.currentlyEditedString + CURSOR_STRING;
				changeCurrentlyEditedArg();	
				update(); 
		
			} else {
				instance.remove(cursorIndex);
				this.cursorIndex = this.cursorIndex - 1;
				instance.add(this.cursorIndex, cursor);					
			}    
		}
	}
	
	/**Posune hlavní kurzor doprava*/
	public void cursorRight() {
		if (this.cursorIndex<this.calculationList.size()-1) {		
			
			//jestli je nasledujici member multiarg member
			if (calculationList.get(this.cursorIndex+1).isMultiArgMember()) {	
				instance.setMultiArgModeState(true);
				this.setCurrentlyEditedCalculationListMemberIndex(cursorIndex+1);
				instance.setCurrentlyEditedArgOrder(1);
				instance.setCurrentlyEditedStringCursorIndex(0);
				this.currentlyEditedString = CURSOR_STRING+this.currentlyEditedString ;
				changeCurrentlyEditedArg();	
				update(); 	
				return;
			}
			
		instance.remove(cursorIndex);
		this.cursorIndex = this.cursorIndex + 1;
		instance.add(this.cursorIndex, cursor);
		}
	}
	
	/**Připojí string na konec právě editovaného argumentu multiarg memberu*/	
	public void addStringToCurrentlyEditedArg(String addedString) {
		
		if(this.currentlyEditedString!=null&&this.currentlyEditedString!="") {
			int lenEditedStr = this.currentlyEditedString.length();
			char lastChar = this.currentlyEditedString.charAt(lenEditedStr-1);
			if(lastChar=='⎜') {
				this.removeLastCharFromCurrentlyEditedArg();	
			}
		}
		this.currentlyEditedString = this.currentlyEditedString + addedString + CURSOR_STRING;
		changeCurrentlyEditedArg();	
	}
	
	/**Vloží string na kurzor v právě editovaném argumentu multiarg memberu*/	
	public void addStringAfterCursorCurrentlyEditedArg(String addedString) {
		
		System.out.println("!!!Voláno z addStringAfterCursorCurrentlyEditedArg");		
		System.out.println("přidavany String je: " + addedString);		
		int oldIndex = getCurrentlyEditedStringCursorIndex();
		int newIndex = oldIndex + addedString.length();
		
		System.out.println("oldIndex: "+ oldIndex);
		System.out.println("newIndex: "+ newIndex);		
	
		String newString = insertToString(this.currentlyEditedString, addedString, oldIndex);

		setCurrentlyEditedStringCursorIndex(newIndex);
		this.currentlyEditedString = newString;
		changeCurrentlyEditedArg();	

		System.out.println();
	}
	
	/**Posune kurzor v multiarg stringu doleva o jeden znak*/
	public void argStringCursorLeft() {	
		if (this.currentlyEditedCursorIndexString==0) {
		this.decrementCurrentlyEditedArgOrder();
		}
		
		if (this.currentlyEditedCursorIndexString>0) {
		int newIndex = previousMeaningfulMultiArgStringIndex();
		String newString = insertToString(this.currentlyEditedString.replace(CURSOR_STRING, ""), CURSOR_STRING, newIndex);
		setCurrentlyEditedStringCursorIndex(newIndex);
		this.currentlyEditedString = newString;
		changeCurrentlyEditedArg();	
		}
		
	}
	
	/**Posune kurzor v multiarg stringu doprava o jeden znak*/
	public void argStringCursorRight() {
		if (this.currentlyEditedCursorIndexString==this.currentlyEditedString.length()-1) {
			this.incrementCurrentlyEditedArgOrder();	
			return;
		}
		if (this.currentlyEditedCursorIndexString<this.currentlyEditedString.length()-1) {
			int newIndex = nextMeaningfulMultiArgStringIndex();
			String newString = insertToString(this.currentlyEditedString.replace(CURSOR_STRING, ""), CURSOR_STRING, newIndex);
			setCurrentlyEditedStringCursorIndex(newIndex);
			this.currentlyEditedString = newString;
			changeCurrentlyEditedArg();	
			return;
		}
	}
	
	/**Odstraní poslední znak z právě editovaného stringu multiarg členu*/
	private void removeLastCharFromCurrentlyEditedArg() {
		if(this.currentlyEditedString != null && this.currentlyEditedString.length()!=0) {
		String newString = this.currentlyEditedString.substring(0, this.currentlyEditedString.length()-1);
		this.currentlyEditedString = newString;
		changeCurrentlyEditedArg();
		}
	}	
	
	/**@return Vrací předchozí smysluplný index od indexu kurzoru v právě editovaném stringu v multiarg memberu.*/
	private int previousMeaningfulMultiArgStringIndex () {
		
		boolean isFuncSubString4CharBefCursor = false; 
		boolean isFuncSubString5CharBefCursor = false;
		boolean isFuncSubString3CharBefCursor = false;

		if (this.currentlyEditedString.substring(0, this.currentlyEditedCursorIndexString).length()>4) {
			String subString5CharBefCursor =  this.currentlyEditedString.substring(this.currentlyEditedCursorIndexString-5, this.currentlyEditedCursorIndexString);	
			isFuncSubString5CharBefCursor = subString5CharBefCursor.equals("asin(") || subString5CharBefCursor.equals("acos(") || subString5CharBefCursor.equals("atan(") ||
											subString5CharBefCursor.equals("sinh(") || subString5CharBefCursor.equals("cosh(") || subString5CharBefCursor.equals("tanh(");
		}
		if (this.currentlyEditedString.substring(0, this.currentlyEditedCursorIndexString).length()>3) {
			String subString4CharBefCursor =  this.currentlyEditedString.substring(this.currentlyEditedCursorIndexString-4, this.currentlyEditedCursorIndexString);	
			isFuncSubString4CharBefCursor = subString4CharBefCursor.equals("sin(") || subString4CharBefCursor.equals("cos(") || subString4CharBefCursor.equals("tan(") ||
					                        subString4CharBefCursor.equals("abs(")	;
		}
		
		if (this.currentlyEditedString.substring(0, this.currentlyEditedCursorIndexString).length()>2) {
			String subString3CharBefCursor =  this.currentlyEditedString.substring(this.currentlyEditedCursorIndexString-3, this.currentlyEditedCursorIndexString);	
			isFuncSubString3CharBefCursor = subString3CharBefCursor.equals("ln(");
		}
		
		if (isFuncSubString5CharBefCursor) {
			return this.currentlyEditedCursorIndexString-5;
		} else if (isFuncSubString4CharBefCursor) {
			return this.currentlyEditedCursorIndexString-4;
		} else if (isFuncSubString3CharBefCursor) {
			return this.currentlyEditedCursorIndexString-3;				
		} else {
			if (this.currentlyEditedCursorIndexString-1>=0) {
				return this.currentlyEditedCursorIndexString-1;
			} else {
				return 0;
			}
		}
	}

	/**@return Vrací nadcházející smysluplný index od indexu kurzoru v právě editovaném stringu v multiarg memberu.*/
	public int nextMeaningfulMultiArgStringIndex () {
		
		boolean isFuncSubString4CharAfterCursor = false; 
		boolean isFuncSubString5CharAfterCursor = false;
		boolean isFuncSubString3CharAfterCursor = false;
		
		if ((this.currentlyEditedString.length() - this.currentlyEditedCursorIndexString)>5)  {
			String subString5CharAfterCursor =  this.currentlyEditedString.substring(this.currentlyEditedCursorIndexString+1, this.currentlyEditedCursorIndexString+6);	
			isFuncSubString5CharAfterCursor = subString5CharAfterCursor.equals("asin(") || subString5CharAfterCursor.equals("acos(") || subString5CharAfterCursor.equals("atan(") ||
											  subString5CharAfterCursor.equals("sinh(") || subString5CharAfterCursor.equals("cosh(") || subString5CharAfterCursor.equals("tanh(");
		}
		
		if ((this.currentlyEditedString.length() - this.currentlyEditedCursorIndexString)>4) {
			String subString4CharAfterCursor =  this.currentlyEditedString.substring(this.currentlyEditedCursorIndexString+1, this.currentlyEditedCursorIndexString+5);	
			isFuncSubString4CharAfterCursor = subString4CharAfterCursor.equals("sin(") || subString4CharAfterCursor.equals("cos(") || subString4CharAfterCursor.equals("tan(") ||
											  subString4CharAfterCursor.equals("abs(") || subString4CharAfterCursor.equals("log(")  ;
		}
		
		if ((this.currentlyEditedString.length() - this.currentlyEditedCursorIndexString)>3) {
			String subString3CharAfterCursor =  this.currentlyEditedString.substring(this.currentlyEditedCursorIndexString+1, this.currentlyEditedCursorIndexString+4);	
			isFuncSubString3CharAfterCursor = subString3CharAfterCursor.equals("ln(");
		}
		
		if (isFuncSubString5CharAfterCursor) {
			return this.currentlyEditedCursorIndexString+5;
		} else if (isFuncSubString4CharAfterCursor) {
			return this.currentlyEditedCursorIndexString+4;
		} else if (isFuncSubString3CharAfterCursor) {
			return this.currentlyEditedCursorIndexString+3;	
		} else {
			return this.currentlyEditedCursorIndexString+1;
		}
	}
    
    /**Smaže poslední smysluplný člen v právě editovaném argumentu multiarg členu.*/
    public void removeLastMemberFromCurrentlyEditedStringBefCursor() {
		if(this.currentlyEditedString != null && this.currentlyEditedString.length()>1) {		
			String oldString = getCurrentlyEditedString();
			int oldIndex = 	this.currentlyEditedCursorIndexString;		
			int newIndex =this.previousMeaningfulMultiArgStringIndex();	
			
			if ((oldIndex-newIndex)>=0) {
				this.currentlyEditedCursorIndexString = newIndex;
				this.currentlyEditedString = this.removeFromString(oldString, newIndex, oldIndex-newIndex);
				changeCurrentlyEditedArg();
			}
		}
	}
	
	/**@return Vrací vypočítanou hodnotu CalculationListu*/
	public String getEvaluatedValue() {
		update();
		String result;	

		try {
			result = evaluator.evaluate(this.evalMemberString).toString();
			}
			catch(Exception e) {
				try {
					//Přidá ) pro evaluaci
					String newEvalMemberString = this.evalMemberString.toString()+")";
					result = evaluator.evaluate(newEvalMemberString)+"";
				}catch(Exception E) {
					try {
						//Přidá )) pro evaluaci
						String newEvalMemberString = this.evalMemberString.toString()+"))";
						result = evaluator.evaluate(newEvalMemberString)+"";
					}catch(Exception er) {
						result = "Error";
					}
				}
			}
		return result; 
	}
	
	/**Přepíše editovaný argument členu s více argumenty*/
	public void changeCurrentlyEditedArg() {
		CalculationListMember member = calculationList.get(this.currentlyEditedCalculationListMemberIndex).changeArg(this.currentlyEditedArgOrder, this.currentlyEditedString);
		calculationList.set(this.currentlyEditedCalculationListMemberIndex, member);
		update();
	}
	
}
