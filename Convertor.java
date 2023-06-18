/**Zabezpečuje převody mezi číselnýma soustavama, zobrazení výsledku, editování atd.*/
public class Convertor {
	
	private static final Convertor instance = new Convertor(10); 
	/**Číslo ve dvojkové soustavě, slouží pro výpočty i zobrazení.*/
	private String stringBIN = "";
	/**Číslo ve decimální soustavě, slouží pro výpočty i zobrazení.*/
	private String stringDEC = "";
	/**Číslo ve hexadecimální soustavě, slouží pro výpočty i zobrazení.*/
	private String stringHEX = "";
	/**Číslo ve osmičkové soustavě, slouží pro výpočty i zobrazení.*/
	private String stringOCT = "";
	/**Určuje vzhled kurzoru.*/
	private String stringCursor = "⎜";
	/**Mód, který určuje, ve které číselné soustavě se právě zadává výraz ke zpracování.*/
	private int mode;
	/**Index kurzoru.*/
	private int cursorIndex;
	
	private Convertor(int mode) {
		this.mode = mode;
		switch (this.mode) {
		case 2: {
			this.stringBIN = stringCursor;
			break;
		}
		case 8: {
			this.stringOCT = stringCursor;
			break;
		}		
		case 10: {
			this.stringDEC = stringCursor;
			break;
		}
		case 16: {
			this.stringHEX = stringCursor;
			break;
		}	
		default:
			throw new IllegalArgumentException("Unexpected value: " + mode);
		}	
		cursorIndex = 0;
		update();
	}
	
	/**@return Vrací odkaz na instanci třídy Convertor.*/
	public static Convertor getInstance() {
		return instance;
	}
	
	/**Updatuje atributy stringBIN, stringOCT, stringDEC, stringHEX.*/
	public void update() {
		this.stringBIN = this.stringBIN.replace(stringCursor, "");
		this.stringOCT = this.stringOCT.replace(stringCursor, "");
		this.stringDEC = this.stringDEC.replace(stringCursor, "");
		this.stringHEX = this.stringHEX.replace(stringCursor, "");
		
		switch (this.mode) {
		case 2: {
			String stringToCnvrt = this.stringBIN;
			if(stringToCnvrt!="" && stringToCnvrt!=null) {
			this.stringDEC = BINtoDEC(stringToCnvrt);
			this.stringHEX = DECtoHEX(BINtoDEC(stringToCnvrt));
			this.stringOCT = DECtoOCT(BINtoDEC(stringToCnvrt));
			}
			this.stringBIN = insertToString(stringToCnvrt, stringCursor, cursorIndex);
			break;
		}
		case 8: {
			String stringToCnvrt = this.stringOCT;
			if(stringToCnvrt!="" && stringToCnvrt!=null) {
			this.stringBIN = DECtoBIN(OCTtoDEC(stringToCnvrt));
			this.stringDEC = OCTtoDEC(stringToCnvrt);
			this.stringHEX = DECtoHEX(OCTtoDEC(stringToCnvrt));
			}
			this.stringOCT = insertToString(stringToCnvrt, stringCursor, cursorIndex);
			break;
		}		
		case 10: {
			String stringToCnvrt = this.stringDEC;
			if(stringToCnvrt!="" && stringToCnvrt!=null) {
			this.stringBIN = DECtoBIN(stringToCnvrt);
			this.stringHEX = DECtoHEX(stringToCnvrt);
			this.stringOCT = DECtoOCT(stringToCnvrt);
			}
			this.stringDEC = insertToString(stringToCnvrt, stringCursor, cursorIndex);
			break;
		}	
		case 16: {
			String stringToCnvrt = this.stringHEX;
			if(stringToCnvrt!="" && stringToCnvrt!=null) {
			this.stringBIN = DECtoBIN(HEXtoDEC(stringToCnvrt));
			this.stringDEC = HEXtoDEC(stringToCnvrt);
			this.stringOCT = DECtoOCT(HEXtoDEC(stringToCnvrt));
			}
			this.stringHEX = insertToString(stringToCnvrt, stringCursor, cursorIndex);
			break;
		}	
		default:
			throw new IllegalArgumentException("Unexpected value: " + this.mode);
		}
	}

	/**Převede číslo v DEC do OCT
	 * @param stringDEC Číslo v DEC pro převedení.
	 * @return Výsledek v OCT*/
	static private String DECtoOCT(String stringDEC){
		String stringOCT;
		try {
			stringOCT=Integer.toOctalString(Integer.parseInt(stringDEC));			
		} catch (Exception e) {
			stringOCT = "Error";
		}
	    return stringOCT;
	}	

	/**Převede číslo v DEC do BIN
	 * @param stringDEC Číslo v DEC pro převedení 
	 * @return Výsledek v BIN*/
	static private String DECtoBIN(String stringDEC){
		String stringBIN;
		try {
			stringBIN=Integer.toBinaryString(Integer.parseInt(stringDEC));			
		} catch (Exception e) {
			stringBIN = "Error";
		}
	    return stringBIN;
	}	

	/**Převede číslo v DEC do HEX
	 * @param stringDEC Číslo v DEC pro převedení.
	 * @return Výsledek v HEX*/
	static private String DECtoHEX(String stringDEC){
		String stringHEX;
		try {
			stringHEX=Integer.toHexString(Integer.parseInt(stringDEC)).toUpperCase();			
		} catch (Exception e) {
			stringHEX = "Error";
		}
	    return stringHEX;
	}	

	/**Převede číslo v HEX do DEC
	 * @param stringHEX Číslo v HEX pro převedení.
	 * @return Výsledek v DEC*/
	static private String HEXtoDEC(String stringHEX){
		String stringDEC;
		try {
			stringDEC=Integer.parseInt(stringHEX, 16) + "";				
		} catch (Exception e) {
			stringDEC = "Error";
		}
	    return stringDEC;
	}

	/**Převede číslo v BIN do DEC
	 * @param stringBIN Číslo v BIN pro převedení.
	 * @return Výsledek v DEC*/
	static private String BINtoDEC(String stringBIN){
		String stringDEC;
		try {
			stringDEC=Integer.parseInt(stringBIN, 2) + "";				
		} catch (Exception e) {
			stringDEC = "Error";
		}
	    return stringDEC;
	}

	/**Převede číslo v OCT do DEC
	 * @param stringOCT Číslo v OCT pro převedení.
	 * @return Výsledek v DEC*/
	static private String OCTtoDEC(String stringOCT){
		String stringDEC;
		try {
			stringDEC=Integer.parseInt(stringOCT, 8) + "";	
		} catch (Exception e) {
			stringDEC = "Error";
		}
	    return stringDEC;
	}

	@Override
	public String toString() {
		return "Convertor [stringBIN=" + stringBIN + ", stringDEC=" + stringDEC + ", stringHEX=" + stringHEX
				+ ", stringOCT=" + stringOCT + "]";
	}

	public String getStringBIN() {
		return stringBIN;
	}

	public String getStringDEC() {
		return stringDEC;
	}

	public String getStringHEX() {
		return stringHEX;
	}

	public String getStringOCT() {
		return stringOCT;
	}

	public void setStringBIN(String stringBIN) {
		this.stringBIN = stringBIN;
		this.mode = 2;
		update();
	}

	public void setStringDEC(String stringDEC) {
		this.stringDEC = stringDEC;
		this.mode = 10;
		update();
	}

	public void setStringHEX(String stringHEX) {
		this.stringHEX = stringHEX;
		this.mode = 16;
		update();
	}

	public void setStringOCT(String stringOCT) {
		this.stringOCT = stringOCT;
		this.mode = 8;
		update();
	}
	
	/**@return Vrací string pro zobrazení na displeji kalkulačky.*/
	public String getViewString() {
		String viewString;
		String stringHEX;
		String stringDEC;
		String stringOCT;		
		String stringBIN;
		String emptyPrefix = "   ";
		String markerPrefix = " * ";
		switch (this.mode) {
		case 2: {
			stringHEX = emptyPrefix;
			stringDEC = emptyPrefix;
			stringOCT = emptyPrefix;		
			stringBIN = markerPrefix;
			break;
		}
		case 8: {
			stringHEX = emptyPrefix;
			stringDEC = emptyPrefix;
			stringOCT = markerPrefix;		
			stringBIN = emptyPrefix;
			break;
		}
		case 10: {
			stringHEX = emptyPrefix;
			stringDEC = markerPrefix;
			stringOCT = emptyPrefix;		
			stringBIN = emptyPrefix;
			break;
		}
		case 16: {
			stringHEX = markerPrefix;
			stringDEC = emptyPrefix;
			stringOCT = emptyPrefix;		
			stringBIN = emptyPrefix;
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + this.mode);
		}
	
		stringHEX = stringHEX +"HEX: "+ this.stringHEX;
		stringDEC = stringDEC +"DEC: "+ this.stringDEC;
	    stringOCT = stringOCT +"OCT: "+ this.stringOCT;		
		stringBIN = stringBIN +"BIN: "+ this.stringBIN;
		viewString = stringHEX + "\n" + stringDEC + "\n" + stringOCT + "\n" + stringBIN;
		return viewString;
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
	
	/**Smaže jeden znak v právě editovaném stringu (určeno modem) před kurzorem.*/
	public void removeCharBeforeCursor() {
		switch (this.mode){
		case 2: {
			if (this.cursorIndex>0) {
				this.stringBIN = removeFromString(this.stringBIN.replace(stringCursor, ""), this.cursorIndex-1, 1);
				this.cursorIndex = this.cursorIndex - 1;
			}
			break;
		}
		case 8: {
			if (this.cursorIndex>0) {
				this.stringOCT = removeFromString(this.stringOCT.replace(stringCursor, ""), this.cursorIndex-1, 1);
				this.cursorIndex = this.cursorIndex - 1;
			}
			break;
		}
		case 10: {
			if (this.cursorIndex>0) {
				this.stringDEC = removeFromString(this.stringDEC.replace(stringCursor, ""), this.cursorIndex-1, 1);
				this.cursorIndex = this.cursorIndex - 1;
			}
			break;
		}
		case 16: {
			if (this.cursorIndex>0) {
				this.stringHEX = removeFromString(this.stringHEX.replace(stringCursor, ""), this.cursorIndex-1, 1);
				this.cursorIndex = this.cursorIndex - 1;
			}
			break; 
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + this.mode);
		}
		this.update();
	}
	
	/**Vrací vše to původního stavu jako po zapnutí kalkulačky.*/
	public void allClear() {
		this.stringHEX = "";
		this.stringDEC = "";
		this.stringOCT = "";
		this.stringBIN = "";
		this.cursorIndex = 0;
		this.update();
	}
	
	/**Posune kurzor doleva.*/
	public void cursorLeft() {
		if (this.cursorIndex>0) {
		this.cursorIndex = this.cursorIndex - 1;
		}
		this.update();
	}
	
	/**Posune kurzor doprava.*/
	public void cursorRight() {
		int actStringLen;
		switch (this.mode){
		case 2: {
			actStringLen = this.stringBIN.length();
			break;
		}
		case 8: {
			actStringLen = this.stringOCT.length();
			break;
		}
		case 10: {
			actStringLen = this.stringDEC.length();
			break;
		}
		case 16: {
			actStringLen = this.stringHEX.length();
			break; 
		}
		default:
			actStringLen = 0;
			throw new IllegalArgumentException("Unexpected value: " + this.mode);
		}
		
		if(this.cursorIndex<actStringLen-1) {
			this.cursorIndex = this.cursorIndex + 1;
			System.out.println("Index byl navýšen");
		}
		this.update();
	}

	public int getMode() {
		return mode;
	}

	/**Nastaví mód v jaké soustavě je zadáváno číslo.
	 * @param mode 2 for BIN, 8 for OCT, 10 for DEC, 16 for HEX*/
	public void setMode(int mode) {
		this.mode = mode;
		int actStringLen;
		this.stringBIN = this.stringBIN.replace(stringCursor, "");
		this.stringOCT = this.stringOCT.replace(stringCursor, "");
		this.stringDEC = this.stringDEC.replace(stringCursor, "");
		this.stringHEX = this.stringHEX.replace(stringCursor, "");
		switch (this.mode){
		case 2: {
			actStringLen = this.stringBIN.length();
			break;
		}
		case 8: {
			actStringLen = this.stringOCT.length();
			break;
		}
		case 10: {
			actStringLen = this.stringDEC.length();
			break;
		}
		case 16: {
			actStringLen = this.stringHEX.length();
			break;
		}
		default:
			actStringLen = 0;
			throw new IllegalArgumentException("Unexpected value: " + this.mode);
		}
		this.cursorIndex = actStringLen;
		this.update();
	}
	
	/**Přidá před kurzor zadaný string.
	 *@param stringToAdd String, který má být přidán..*/
	public void addBeforeCursor(String stringToAdd) {
		switch (this.mode) {
		case 2: {
			this.stringBIN = insertToString(this.stringBIN, stringToAdd, this.cursorIndex);
			break;
		}
		case 8: {
			this.stringOCT = insertToString(this.stringOCT, stringToAdd, this.cursorIndex);
			break;
		}		
		case 10: {
			this.stringDEC = insertToString(this.stringDEC, stringToAdd, this.cursorIndex);
			break;
		}
		case 16: {
			this.stringHEX = insertToString(this.stringHEX, stringToAdd, this.cursorIndex);
			break;
		}	
		default:
			throw new IllegalArgumentException("Unexpected value: " + mode);
		}	
		this.cursorIndex = this.cursorIndex+1;
		this.update();
	}
	
}
