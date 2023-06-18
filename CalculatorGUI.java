import javax.swing.text.DefaultCaret;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.Point;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**Vytváří GUI pro vědeckou kalkulačku.*/
public class CalculatorGUI extends JFrame {
	private JPanel contentPane;
	DefaultCaret caret;
	JButton btnMoreLess;
	JButton btnAbs;
	JButton btnLn;
	JButton btnLog;
	JButton btnE;
	JButton btnPi;
	JButton btnDeg;
	JScrollPane scrollDisplayPanel ;
	static private CalculationList instanceArray = CalculationList.getInstance();
    static private Convertor convertor = Convertor.getInstance();
    /**Displej kalkulačky.*/
	JTextArea mainDisplay;
	/**Prostor pro zobrazení historie výsledů.*/
	JTextArea historyDisplay;
	/**Text, který bude zobrazen v historyDisplay.*/
	String historyString = "";
	/**Mód kalkulačky, 0 značí kalkulačku, 1 značí převodník číselných soustav.*/
	int mode = 0; 
	/**Podoba malého kurzoru.*/
	private String smallCursor = CalculationList.CURSOR_STRING;
	/**Označuje, kolik znaků se vejde do na jeden řádek v mainDisplay v základních staženém stavu.*/
	static protected int lenViewMem = 71; //35
	/**Stav zobrazení kalkulačky. 1 značí základní malé zobrazení kalkulačky, 2 značí vědecké zobrazení kalkulačky.*/
	static protected int size = 1; //1 - small, 2 - medium, 3 - medium with history

	public CalculatorGUI() {
		setTitle("Calculator");
		setResizable(false);
		CalculationList instanceArray = CalculationList.getInstance();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 695, 432);
		contentPane = new JPanel();
		contentPane.setBackground(UIManager.getColor("Button.background"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnToLeft = new JButton("<");
		btnToLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (mode==0) {
					boolean multiArgMode = instanceArray.getMultiArgModeState();
					if(!multiArgMode) {
					instanceArray.cursorLeft();
					} else if(multiArgMode) {
					instanceArray.argStringCursorLeft();
					}
				} else if(mode==1) {
					convertor.cursorLeft();;
				}
				updateDisplay();
			}
		});
		btnToLeft.setBounds(5, 100, 65, 50);
		contentPane.add(btnToLeft);
		
		JButton btnToRight = new JButton(">");
		btnToRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (mode==0) {
				boolean multiArgMode = instanceArray.getMultiArgModeState();
				if(!multiArgMode) {
				instanceArray.cursorRight();
				}else if(multiArgMode) {
				instanceArray.argStringCursorRight();
				}
			} else if(mode==1) {
				convertor.cursorRight();;
			}
			updateDisplay();
			}
		});
		btnToRight.setBounds(70, 100, 65, 50); 
		contentPane.add(btnToRight);
		
		JButton btnTwo = new JButton("2");
		btnTwo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (mode==0) {	
					boolean multiArgMode = instanceArray.getMultiArgModeState();
					if(multiArgMode) {
		            instanceArray.addStringAfterCursorCurrentlyEditedArg("2");
					}else {
					instanceArray.addAfterCursor(new MemberDigit("2")); 
					}
				} else if(mode==1) {
					convertor.addBeforeCursor("2");;
				}
				updateDisplay();	
			}
		});
		btnTwo.setBounds(70, 300, 65, 50);
		contentPane.add(btnTwo);
		
		JButton btnOne = new JButton("1");
		btnOne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (mode==0) {	
					boolean multiArgMode = instanceArray.getMultiArgModeState();
					if(multiArgMode) {
		            instanceArray.addStringAfterCursorCurrentlyEditedArg("1");
					}else {
					instanceArray.addAfterCursor(new MemberDigit("1")); 
					}
				} else if(mode==1) {
					convertor.addBeforeCursor("1");;
				}
				updateDisplay();
			}
		});
		btnOne.setBounds(5, 300, 65, 50);
		contentPane.add(btnOne);
		
		JButton btnThree = new JButton("3");
		btnThree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (mode==0) {	
					boolean multiArgMode = instanceArray.getMultiArgModeState();
					if(multiArgMode) {
		            instanceArray.addStringAfterCursorCurrentlyEditedArg("3");
					}else {
					instanceArray.addAfterCursor(new MemberDigit("3")); 
					}
				} else if(mode==1) {
					convertor.addBeforeCursor("3");;
				}
				updateDisplay();
			}
		});
		btnThree.setBounds(135, 300, 65, 50);
		contentPane.add(btnThree);
		
		scrollDisplayPanel = new JScrollPane();
		scrollDisplayPanel.setFocusable(false);
		scrollDisplayPanel.setFocusTraversalKeysEnabled(false);
		scrollDisplayPanel.setBounds(5, 5, 685, 93);
		contentPane.add(scrollDisplayPanel);
		
		mainDisplay = new JTextArea();
		caret = (DefaultCaret)mainDisplay.getCaret();
		caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		mainDisplay.setAutoscrolls(false);
		mainDisplay.setFocusTraversalKeysEnabled(false);
		mainDisplay.setEditable(false);
		scrollDisplayPanel.setViewportView(mainDisplay);
		mainDisplay.setBorder(null);
		mainDisplay.setFont(new Font("Courier New", Font.PLAIN, 15));
		mainDisplay.setText("⎜\n⎜\n⎜");
		
		JButton btnFour = new JButton("4");
		btnFour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (mode==0) {	
					boolean multiArgMode = instanceArray.getMultiArgModeState();
					if(multiArgMode) {
		            instanceArray.addStringAfterCursorCurrentlyEditedArg("4");
					}else {
					instanceArray.addAfterCursor(new MemberDigit("4")); 
					}
				} else if(mode==1) {
					convertor.addBeforeCursor("4");;
				}
				updateDisplay();
			}
		});
		btnFour.setBounds(5, 250, 65, 50);
		contentPane.add(btnFour);
		
		JButton btnFive = new JButton("5");
		btnFive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (mode==0) {	
					boolean multiArgMode = instanceArray.getMultiArgModeState();
					if(multiArgMode) {
		            instanceArray.addStringAfterCursorCurrentlyEditedArg("5");
					}else {
					instanceArray.addAfterCursor(new MemberDigit("5")); 
					}
				} else if(mode==1) {
					convertor.addBeforeCursor("5");;
				}
				updateDisplay();
			}
		});
		btnFive.setBounds(70, 250, 65, 50);
		contentPane.add(btnFive);
		
		JButton btnSix = new JButton("6");
		btnSix.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (mode==0) {	
					boolean multiArgMode = instanceArray.getMultiArgModeState();
					if(multiArgMode) {
		            instanceArray.addStringAfterCursorCurrentlyEditedArg("6");
					}else {
					instanceArray.addAfterCursor(new MemberDigit("6")); 
					}
				} else if(mode==1) {
					convertor.addBeforeCursor("6");;
				}
				updateDisplay();
			}
		});
		btnSix.setBounds(135, 250, 65, 50);
		contentPane.add(btnSix);
		
		JButton btnSeven = new JButton("7");
		btnSeven.setBackground(SystemColor.control);
		btnSeven.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (mode==0) {	
					boolean multiArgMode = instanceArray.getMultiArgModeState();
					if(multiArgMode) {
		            instanceArray.addStringAfterCursorCurrentlyEditedArg("7");
					}else {
					instanceArray.addAfterCursor(new MemberDigit("7")); 
					}
				} else if(mode==1) {
					convertor.addBeforeCursor("7");;
				}
				updateDisplay();
			}
		});
		btnSeven.setBounds(5, 200, 65, 50);
		contentPane.add(btnSeven);
		
		JButton btnEight = new JButton("8");
		btnEight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (mode==0) {	
					boolean multiArgMode = instanceArray.getMultiArgModeState();
					if(multiArgMode) {
		            instanceArray.addStringAfterCursorCurrentlyEditedArg("8");
					}else {
					instanceArray.addAfterCursor(new MemberDigit("8")); 
					}
				} else if(mode==1) {
					convertor.addBeforeCursor("8");;
				}
				updateDisplay();
			}
		});
		btnEight.setBounds(70, 200, 65, 50);
		contentPane.add(btnEight);
		
		JButton btnNine = new JButton("9");
		btnNine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {;
			if (mode==0) {	
				boolean multiArgMode = instanceArray.getMultiArgModeState();
				if(multiArgMode) {
	            instanceArray.addStringAfterCursorCurrentlyEditedArg("9");
				}else {
				instanceArray.addAfterCursor(new MemberDigit("9")); 
				}
			} else if(mode==1) {
				convertor.addBeforeCursor("9");;
			}
			updateDisplay();
			}
		});
		btnNine.setBounds(135, 200, 65, 50);
		contentPane.add(btnNine);
		
		JButton btnZero = new JButton("0");
		btnZero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (mode==0) {
					boolean multiArgMode = instanceArray.getMultiArgModeState();
					if(multiArgMode) {
		            instanceArray.addStringAfterCursorCurrentlyEditedArg("0");
					}else {
					instanceArray.addAfterCursor(new MemberDigit("0")); 
					}
					updateDisplay();
				} else if(mode==1) {
					convertor.addBeforeCursor("0");;
				}
				updateDisplay();
			}
		});
		btnZero.setBounds(5, 350, 65, 50);
		contentPane.add(btnZero);
		
		JButton btnEqualsB = new JButton("=");
		btnEqualsB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateDisplayWithEquals();
			}
		});
		btnEqualsB.setBounds(135, 350, 65, 50);
		contentPane.add(btnEqualsB);
		
		JButton btnPointA = new JButton(".");
		btnPointA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (mode==0) {
					boolean multiArgMode = instanceArray.getMultiArgModeState();
					if(multiArgMode) {
		            instanceArray.addStringAfterCursorCurrentlyEditedArg(".");
					}else {
						instanceArray.addAfterCursor(new MemberSymbol(".", ".")); 
					}
				} else if(mode==1) {
					convertor.addBeforeCursor("A");;
				}
				updateDisplay();	
			}
		});
		btnPointA.setBounds(70, 350, 65, 50);
		contentPane.add(btnPointA);
		
		JButton btnMultiplyF = new JButton("×");
		btnMultiplyF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (mode==0) {	
				boolean multiArgMode = instanceArray.getMultiArgModeState();
				if(multiArgMode) {
	            instanceArray.addStringAfterCursorCurrentlyEditedArg("·");
				}else {
					instanceArray.addAfterCursor(new MemberSymbol("·", "*")); 
				}
				} else if(mode==1) {
					convertor.addBeforeCursor("F");
				}
				updateDisplay();
			}
		});
		btnMultiplyF.setBounds(200, 200, 65, 50);
		contentPane.add(btnMultiplyF);
		
		JButton btnDivideE = new JButton("÷");
		btnDivideE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (mode==0) {
					boolean multiArgMode = instanceArray.getMultiArgModeState();
					if(multiArgMode) {
		            instanceArray.addStringAfterCursorCurrentlyEditedArg("/");
				}else {
					instanceArray.addAfterCursor(new MemberSymbol("/", "/")); 
				}		
				} else if(mode==1) {
					convertor.addBeforeCursor("E");
				}
				updateDisplay();
			}
		});
		btnDivideE.setBounds(200, 250, 65, 50);
		contentPane.add(btnDivideE);
		
		JButton btnMinusD = new JButton("−");
		btnMinusD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (mode==0) {	
					boolean multiArgMode = instanceArray.getMultiArgModeState();
					if(multiArgMode) {
		            instanceArray.addStringAfterCursorCurrentlyEditedArg("-");
				}else {
					instanceArray.addAfterCursor(new MemberSymbol("−", "-")); 
				}
				} else if(mode==1) {
					convertor.addBeforeCursor("D");
				}
					updateDisplay();
			}
		});
		btnMinusD.setBounds(200, 300, 65, 50);
		contentPane.add(btnMinusD);
		
		JButton btnPlusC = new JButton("+");
		btnPlusC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (mode==0) {
					boolean multiArgMode = instanceArray.getMultiArgModeState();
					if(multiArgMode) {
		            instanceArray.addStringAfterCursorCurrentlyEditedArg("+");
				}else {
					instanceArray.addAfterCursor(new MemberSymbol("+", "+")); 
				}
				} else if(mode==1) {
					convertor.addBeforeCursor("C");
				}
				updateDisplay();
			}
		});
		btnPlusC.setBounds(200, 350, 65, 50);
		contentPane.add(btnPlusC);
		
		
		JButton btnDEL = new JButton("DEL");
		btnDEL.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (mode==0) {
					boolean multiArgMode = instanceArray.getMultiArgModeState();
					if(!multiArgMode) {
					instanceArray.deleteBeforeCursor();;
					} else {
					instanceArray.removeLastMemberFromCurrentlyEditedStringBefCursor();
					}
				} else if(mode==1) {
					convertor.removeCharBeforeCursor();;
				}
				updateDisplay();
			}
		});
		btnDEL.setBounds(135, 100, 65, 50);
		contentPane.add(btnDEL);
		
		btnPi = new JButton("π");
		btnPi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean multiArgMode = instanceArray.getMultiArgModeState();
				if(multiArgMode) {
	            instanceArray.addStringAfterCursorCurrentlyEditedArg("π");
				}else {
					instanceArray.addAfterCursor(new MemberSymbol("π", "pi")); 
				}
				updateDisplay();
			}
		});
		btnPi.setBounds(265, 300, 65, 50);
		contentPane.add(btnPi);
		
		btnE = new JButton("e");
		btnE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean multiArgMode = instanceArray.getMultiArgModeState();
				if(multiArgMode) {
	            instanceArray.addStringAfterCursorCurrentlyEditedArg("e");
				}else {
					instanceArray.addAfterCursor(new MemberSymbol("e", "e")); 
				}
				updateDisplay();
			}
		});
		btnE.setActionCommand("");
		btnE.setBounds(265, 250, 65, 50);
		contentPane.add(btnE);
		
		JButton btnAllClear = new JButton("AC");
		btnAllClear.setOpaque(true);
		btnAllClear.setForeground(Color.BLACK);
		btnAllClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				if (mode==0) {	
					instanceArray.allClear();
				} else if(mode==1) {
					convertor.allClear();;
				}
				updateDisplay();
			}
		});
		btnAllClear.setBounds(200, 100, 65, 50);
		contentPane.add(btnAllClear);
		
		JButton btnLeftParenthesis = new JButton("(");
		btnLeftParenthesis.setForeground(new Color(0, 0, 0));
		btnLeftParenthesis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (mode==0) {
					boolean multiArgMode = instanceArray.getMultiArgModeState();
					if(multiArgMode) {
		            instanceArray.addStringAfterCursorCurrentlyEditedArg("(");
					}else {
						instanceArray.addAfterCursor(new MemberSymbol("(", "(")); 
					}
				} else if(mode==1) {
					convertor.setMode(2);
					btnZero.setEnabled(true);
					btnOne.setEnabled(true);
					btnTwo.setEnabled(false);
					btnThree.setEnabled(false);
					btnFour.setEnabled(false);
					btnFive.setEnabled(false);
					btnSix.setEnabled(false);
					btnSeven.setEnabled(false);
					btnEight.setEnabled(false);
					btnNine.setEnabled(false);
					btnPointA.setEnabled(false);
					btnEqualsB.setEnabled(false);
					btnPlusC.setEnabled(false);
					btnMinusD.setEnabled(false);
					btnDivideE.setEnabled(false);
					btnMultiplyF.setEnabled(false);
					repaint();
				}
				updateDisplay();	
			}
		});
		btnLeftParenthesis.setBounds(5, 150, 65, 50);
		contentPane.add(btnLeftParenthesis);
		
		JButton btnRightParenthesis = new JButton(")");
		btnRightParenthesis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			if (mode==0) {
				boolean multiArgMode = instanceArray.getMultiArgModeState();
				if(multiArgMode) {
	            instanceArray.addStringAfterCursorCurrentlyEditedArg(")");
				}else {
					instanceArray.addAfterCursor(new MemberSymbol(")", ")")); 
				}
			} else if(mode==1) {
				convertor.setMode(8);
				btnZero.setEnabled(true);
				btnOne.setEnabled(true);
				btnTwo.setEnabled(true);
				btnThree.setEnabled(true);
				btnFour.setEnabled(true);
				btnFive.setEnabled(true);
				btnSix.setEnabled(true);
				btnSeven.setEnabled(true);
				btnEight.setEnabled(false);
				btnNine.setEnabled(false);
				btnPointA.setEnabled(false);
				btnEqualsB.setEnabled(false);
				btnPlusC.setEnabled(false);
				btnMinusD.setEnabled(false);
				btnDivideE.setEnabled(false);
				btnMultiplyF.setEnabled(false);
				repaint();
			}
			updateDisplay();
			}
		});
		btnRightParenthesis.setContentAreaFilled(false);
		btnRightParenthesis.setBounds(70, 150, 65, 50);
		contentPane.add(btnRightParenthesis);
		
		JButton btnNthRoot = new JButton("ⁿ√");
		btnNthRoot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
			     	boolean multiArgMode = instanceArray.getMultiArgModeState();
					if(multiArgMode) {
		            instanceArray.addStringAfterCursorCurrentlyEditedArg("^(1/");
					}else {
						int addedMemberIndex = instanceArray.addAfterCursor(new MemberRoot("", smallCursor));
						instanceArray.setCurrentlyEditedCalculationListMemberIndex(addedMemberIndex);
						instanceArray.setCurrentlyEditedStringCursorIndex(0);
		            	instanceArray.setMultiArgModeState(true);
		            	instanceArray.setCurrentlyEditedArgOrder(1);
		            	instanceArray.incrementCurrentlyEditedArgOrder();
				     	updateDisplay();
					}
					updateDisplay(); 	
			}
		});
		btnNthRoot.setActionCommand("");
		btnNthRoot.setBounds(460, 150, 65, 50);
		contentPane.add(btnNthRoot);
		
		JButton btnFraction = new JButton("FRAC");
		btnFraction.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		btnFraction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			if (mode==0) {     	
					boolean multiArgMode = instanceArray.getMultiArgModeState();
					if(multiArgMode) {
		            instanceArray.addStringAfterCursorCurrentlyEditedArg("/");
					}else {
						int addedMemberIndex = instanceArray.addAfterCursor(new MemberFraction(smallCursor,""));
						instanceArray.setCurrentlyEditedCalculationListMemberIndex(addedMemberIndex);
						instanceArray.setCurrentlyEditedStringCursorIndex(0);
		            	instanceArray.setMultiArgModeState(true);
		            	instanceArray.setCurrentlyEditedArgOrder(1);
					}
			} else if(mode==1) {
				convertor.setMode(10);
				btnZero.setEnabled(true);
				btnOne.setEnabled(true);
				btnTwo.setEnabled(true);
				btnThree.setEnabled(true);
				btnFour.setEnabled(true);
				btnFive.setEnabled(true);
				btnSix.setEnabled(true);
				btnSeven.setEnabled(true);
				btnEight.setEnabled(true);
				btnNine.setEnabled(true);
				btnPointA.setEnabled(false);
				btnEqualsB.setEnabled(false);
				btnPlusC.setEnabled(false);
				btnMinusD.setEnabled(false);
				btnDivideE.setEnabled(false);
				btnMultiplyF.setEnabled(false);
				repaint();
			}
			updateDisplay();	
			}
		});
		btnFraction.setBounds(135, 150, 65, 50);
		contentPane.add(btnFraction);
		
		JButton btnNthPower = new JButton("xⁿ");
		btnNthPower.setIgnoreRepaint(true);
		btnNthPower.setFocusCycleRoot(true);
		btnNthPower.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean multiArgMode = instanceArray.getMultiArgModeState();
				if(multiArgMode) {
	            instanceArray.addStringAfterCursorCurrentlyEditedArg("^");
				}else {
					int addedMemberIndex = instanceArray.addAfterCursor(new MemberExponent(smallCursor, ""));
					instanceArray.setCurrentlyEditedCalculationListMemberIndex(addedMemberIndex);
					instanceArray.setCurrentlyEditedStringCursorIndex(0);
	            	instanceArray.setMultiArgModeState(true);
	            	instanceArray.setCurrentlyEditedArgOrder(1);
				}
				updateDisplay();
			}
		});
		btnNthPower.setBounds(460, 200, 65, 50);
		contentPane.add(btnNthPower);
		
		JButton btnSquareRoot = new JButton("²√");
		btnSquareRoot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean multiArgMode = instanceArray.getMultiArgModeState();
				if(multiArgMode) {
	            instanceArray.addStringAfterCursorCurrentlyEditedArg("^(1/2)");
				}else {
					int addedMemberIndex = instanceArray.addAfterCursor(new MemberRoot("", "2"+smallCursor));
					instanceArray.setCurrentlyEditedCalculationListMemberIndex(addedMemberIndex);
					instanceArray.setCurrentlyEditedStringCursorIndex(0);
	            	instanceArray.setMultiArgModeState(true);
	            	instanceArray.setCurrentlyEditedArgOrder(1);
	            	instanceArray.incrementCurrentlyEditedArgOrder();
			     	updateDisplay();
				}
				updateDisplay();	
			}
		});
		btnSquareRoot.setActionCommand("");
		btnSquareRoot.setBounds(330, 150, 65, 50);
		contentPane.add(btnSquareRoot);
		
		JButton btn3rdRoot = new JButton("³√");
		btn3rdRoot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	     	
		     	boolean multiArgMode = instanceArray.getMultiArgModeState();
				if(multiArgMode) {
	            instanceArray.addStringAfterCursorCurrentlyEditedArg("^(1/3)");
				}else {
					int addedMemberIndex = instanceArray.addAfterCursor(new MemberRoot("", "3"+smallCursor));
					instanceArray.setCurrentlyEditedCalculationListMemberIndex(addedMemberIndex);
					instanceArray.setCurrentlyEditedStringCursorIndex(0);
	            	instanceArray.setMultiArgModeState(true);
	            	instanceArray.setCurrentlyEditedArgOrder(1);
	            	instanceArray.incrementCurrentlyEditedArgOrder();
			     	updateDisplay();
				}
				updateDisplay();
			}
		});
		btn3rdRoot.setActionCommand("");
		btn3rdRoot.setBounds(395, 150, 65, 50);
		contentPane.add(btn3rdRoot);
		
		JButton btn2thPower = new JButton("x²");
		btn2thPower.setIgnoreRepaint(true);
		btn2thPower.setFocusCycleRoot(true);
		btn2thPower.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean multiArgMode = instanceArray.getMultiArgModeState();
				if(multiArgMode) {
	            instanceArray.addStringAfterCursorCurrentlyEditedArg("^2");
				}else {
					int addedMemberIndex = instanceArray.addAfterCursor(new MemberExponent(smallCursor, "2"));
					instanceArray.setCurrentlyEditedCalculationListMemberIndex(addedMemberIndex);
					instanceArray.setCurrentlyEditedStringCursorIndex(0);
	            	instanceArray.setMultiArgModeState(true);
	            	instanceArray.setCurrentlyEditedArgOrder(1);
				}
				updateDisplay();
			}
		});
		btn2thPower.setBounds(330, 200, 65, 50);
		contentPane.add(btn2thPower);
		
		JButton btn3rdPower = new JButton("x³");
		btn3rdPower.setIgnoreRepaint(true);
		btn3rdPower.setFocusCycleRoot(true);
		btn3rdPower.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean multiArgMode = instanceArray.getMultiArgModeState();
				if(multiArgMode) {
	            instanceArray.addStringAfterCursorCurrentlyEditedArg("^3");
				}else {
					int addedMemberIndex = instanceArray.addAfterCursor(new MemberExponent(smallCursor, "3"));
					instanceArray.setCurrentlyEditedCalculationListMemberIndex(addedMemberIndex);
					instanceArray.setCurrentlyEditedStringCursorIndex(0);
	            	instanceArray.setMultiArgModeState(true);
	            	instanceArray.setCurrentlyEditedArgOrder(1);
				}
				updateDisplay();
			}
		});
		btn3rdPower.setBounds(395, 200, 65, 50);
		contentPane.add(btn3rdPower);
		
		JButton btnSinX = new JButton("sin(x)");
		btnSinX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean multiArgMode = instanceArray.getMultiArgModeState();
				if(multiArgMode) {
	            instanceArray.addStringAfterCursorCurrentlyEditedArg("sin(");
				}else {
				instanceArray.addAfterCursor(new MemberTrigonometricAbs("sin")); 
				}
				updateDisplay();
			}
		});
		btnSinX.setBounds(330, 250, 65, 50);
		contentPane.add(btnSinX);
		
		JButton btnCosX = new JButton("cos(x)");
		btnCosX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean multiArgMode = instanceArray.getMultiArgModeState();
				if(multiArgMode) {
	            instanceArray.addStringAfterCursorCurrentlyEditedArg("cos(");
				}else {
				instanceArray.addAfterCursor(new MemberTrigonometricAbs("cos")); 
				}
				updateDisplay();
			}
		});
		btnCosX.setBounds(395, 250, 65, 50);
		contentPane.add(btnCosX);
		
		JButton btnTanX = new JButton("tan(x)");
		btnTanX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean multiArgMode = instanceArray.getMultiArgModeState();
				if(multiArgMode) {
	            instanceArray.addStringAfterCursorCurrentlyEditedArg("tan(");
				}else {
				instanceArray.addAfterCursor(new MemberTrigonometricAbs("tan")); 
				}
				updateDisplay();				
			}
		});
		btnTanX.setBounds(460, 250, 65, 50);
		contentPane.add(btnTanX);
		
		JButton btnAsinX = new JButton("sin⁻¹(x)");
		btnAsinX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean multiArgMode = instanceArray.getMultiArgModeState();
				if(multiArgMode) {
	            instanceArray.addStringAfterCursorCurrentlyEditedArg("asin(");
				}else {
				instanceArray.addAfterCursor(new MemberTrigonometricAbs("asin")); 
				}
				updateDisplay();
			}
		});
		btnAsinX.setActionCommand("");
		btnAsinX.setBounds(330, 300, 65, 50);
		contentPane.add(btnAsinX);
		
		JButton btnAcosX = new JButton("cos⁻¹(x)");
		btnAcosX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean multiArgMode = instanceArray.getMultiArgModeState();
				if(multiArgMode) {
	            instanceArray.addStringAfterCursorCurrentlyEditedArg("acos(");
				}else {
				instanceArray.addAfterCursor(new MemberTrigonometricAbs("acos")); 
				}
				updateDisplay();
			}
		});
		btnAcosX.setActionCommand("");
		btnAcosX.setBounds(395, 300, 65, 50);
		contentPane.add(btnAcosX);
		
		JButton btnAtanX = new JButton("tan⁻¹(x)");
		btnAtanX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean multiArgMode = instanceArray.getMultiArgModeState();
				if(multiArgMode) {
	            instanceArray.addStringAfterCursorCurrentlyEditedArg("atan(");
				}else {
				instanceArray.addAfterCursor(new MemberTrigonometricAbs("atan")); 
				}
				updateDisplay();
			}
		});
		btnAtanX.setActionCommand("");
		btnAtanX.setBounds(460, 300, 65, 50);
		contentPane.add(btnAtanX);
		
		btnAbs = new JButton("abs(x)");
		btnAbs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean multiArgMode = instanceArray.getMultiArgModeState();
				if(multiArgMode) {
	            instanceArray.addStringAfterCursorCurrentlyEditedArg("abs(");
				}else {
				instanceArray.addAfterCursor(new MemberTrigonometricAbs("abs")); 
				}
				updateDisplay();
			}
		});
		btnAbs.setBounds(265, 100, 65, 50);
		contentPane.add(btnAbs);
		
		JButton btnSinhX = new JButton("sinh(x)");
		btnSinhX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean multiArgMode = instanceArray.getMultiArgModeState();
				if(multiArgMode) {
	            instanceArray.addStringAfterCursorCurrentlyEditedArg("sinh(");
				}else {
				instanceArray.addAfterCursor(new MemberTrigonometricAbs("sinh")); 
				}
				updateDisplay();
			}
		});
		btnSinhX.setActionCommand("");
		btnSinhX.setBounds(330, 350, 65, 50);
		contentPane.add(btnSinhX);
		
		JButton btnCoshX = new JButton("cosh(x)");
		btnCoshX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean multiArgMode = instanceArray.getMultiArgModeState();
				if(multiArgMode) {
	            instanceArray.addStringAfterCursorCurrentlyEditedArg("cosh(");
				}else {
				instanceArray.addAfterCursor(new MemberTrigonometricAbs("cosh")); 
				}
				updateDisplay();
			}			
		});
		btnCoshX.setActionCommand("");
		btnCoshX.setBounds(395, 350, 65, 50);
		contentPane.add(btnCoshX);
		
		JButton btnTanhX = new JButton("tanh(x)");
		btnTanhX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean multiArgMode = instanceArray.getMultiArgModeState();
				if(multiArgMode) {
	            instanceArray.addStringAfterCursorCurrentlyEditedArg("tanh(");
				}else {
				instanceArray.addAfterCursor(new MemberTrigonometricAbs("tanh")); 
				}
				updateDisplay();
			}
		});
		btnTanhX.setActionCommand("");
		btnTanhX.setBounds(460, 350, 65, 50);
		contentPane.add(btnTanhX);
		
		JScrollPane scrollHistoryPanel = new JScrollPane();
		scrollHistoryPanel.setBackground(UIManager.getColor("Button.background"));
		scrollHistoryPanel.setBorder(null);
		scrollHistoryPanel.setBounds(537, 119, 153, 276);
		contentPane.add(scrollHistoryPanel);
		
		historyDisplay = new JTextArea();
		scrollHistoryPanel.setViewportView(historyDisplay);
		historyDisplay.setForeground(UIManager.getColor("Button.darkShadow"));
		historyDisplay.setBorder(null);
		historyDisplay.setBackground(UIManager.getColor("Button.background"));
		historyDisplay.setFont(new Font("Courier New", Font.PLAIN, 12));
		historyDisplay.setEditable(false);
		historyDisplay.setText(this.historyString);
		
		btnLog = new JButton("log(x)");
		btnLog.setIgnoreRepaint(true);
		btnLog.setFocusCycleRoot(true);
		btnLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean multiArgMode = instanceArray.getMultiArgModeState();
				if(multiArgMode) {
	            instanceArray.addStringToCurrentlyEditedArg("log(");
				}else {
				instanceArray.addAfterCursor(new MemberTrigonometricAbs("log")); 
				}
				updateDisplay();
			}
		});
		btnLog.setBounds(265, 200, 65, 50);
		contentPane.add(btnLog);
		
		btnLn = new JButton("ln(x)");
		btnLn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean multiArgMode = instanceArray.getMultiArgModeState();
				if(multiArgMode) {
	            instanceArray.addStringToCurrentlyEditedArg("ln(");
				}else {
				instanceArray.addAfterCursor(new MemberTrigonometricAbs("ln")); 
				}
				updateDisplay();
			}
		});
		btnLn.setBounds(265, 150, 65, 50);
		contentPane.add(btnLn);
		
		btnDeg = new JButton("°");	
		btnDeg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				boolean multiArgMode = instanceArray.getMultiArgModeState();
				if(multiArgMode) {
	            instanceArray.addStringAfterCursorCurrentlyEditedArg("°");
				}else {
					instanceArray.addAfterCursor(new MemberSymbol("°")); 
				}
				updateDisplay();
			}
		});
		btnDeg.setBounds(265, 350, 65, 50);
		contentPane.add(btnDeg);
		
		JButton btnHistory = new JButton("HISTORY");
		btnHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (CalculatorGUI.size == 3) {
					setSize(2);
				} else if (CalculatorGUI.size == 2 || CalculatorGUI.size==1) {
					setSize(3);
				}
				repaint();
				updateDisplay();
			}
		});
		btnHistory.setBounds(460, 100, 65, 50);
		contentPane.add(btnHistory);
		
		
		JLabel lblNewLabel = new JLabel("History");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(583, 100, 51, 16);
		contentPane.add(lblNewLabel);
		
		JButton btnRound = new JButton("ROUND");
		btnRound.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean multiArgMode = instanceArray.getMultiArgModeState();
				if(multiArgMode) {
	            instanceArray.addStringAfterCursorCurrentlyEditedArg("round(");
				}else {
				instanceArray.addAfterCursor(new MemberTrigonometricAbs("round")); 
				}
				updateDisplay();
			}
		});
		btnRound.setBounds(330, 100, 65, 50);
		contentPane.add(btnRound);
		
		btnMoreLess = new JButton("MORE");
		btnMoreLess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		
			if (mode==0) {
					if (CalculatorGUI.size == 1) {
						setSize(2);
					} else if (CalculatorGUI.size == 2 || CalculatorGUI.size==3) {
						setSize(1);
					}
						
			} else if(mode==1) {
				convertor.setMode(16);
				btnZero.setEnabled(true);
				btnOne.setEnabled(true);
				btnTwo.setEnabled(true);
				btnThree.setEnabled(true);
				btnFour.setEnabled(true);
				btnFive.setEnabled(true);
				btnSix.setEnabled(true);
				btnSeven.setEnabled(true);
				btnEight.setEnabled(true);
				btnNine.setEnabled(true);
				btnPointA.setEnabled(true);
				btnEqualsB.setEnabled(true);
				btnPlusC.setEnabled(true);
				btnMinusD.setEnabled(true);
				btnDivideE.setEnabled(true);
				btnMultiplyF.setEnabled(true);
			}
			repaint();
			updateDisplay();
				
			}
		});
		btnMoreLess.setBounds(200, 150, 65, 50);
		contentPane.add(btnMoreLess);
		
		
		JButton btnConvert = new JButton("CONV");
		btnConvert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (mode==0) {
					 mode = 1;
					setSize(2);
					 convertor.setMode(10);
					 btnRound.setEnabled(false);
					 btnAbs.setEnabled(false);
					 btnAbs.setEnabled(false);
					 btnLn.setEnabled(false);
					 btnSquareRoot.setEnabled(false);
					 btn3rdRoot.setEnabled(false);
					 btnNthRoot.setEnabled(false);
					 btnLog.setEnabled(false);
					 btn2thPower.setEnabled(false);
					 btn3rdPower.setEnabled(false);
					 btnNthPower.setEnabled(false);
					 btnE.setEnabled(false);
					 btnSinX.setEnabled(false);
					 btnCosX.setEnabled(false);
					 btnTanX.setEnabled(false);
					 btnPi.setEnabled(false);
					 btnAsinX.setEnabled(false);
					 btnAcosX.setEnabled(false);
					 btnAtanX.setEnabled(false);
					 btnDeg.setEnabled(false);
					 btnSinhX.setEnabled(false);
					 btnCoshX.setEnabled(false);
					 btnTanhX.setEnabled(false);
					 btnZero.setEnabled(true);
				  	 btnOne.setEnabled(true);
					 btnTwo.setEnabled(true);
					 btnThree.setEnabled(true);
					 btnFour.setEnabled(true);
					 btnFive.setEnabled(true);
					 btnSix.setEnabled(true);
					 btnSeven.setEnabled(true);
					 btnEight.setEnabled(true);
					 btnNine.setEnabled(true);
					 btnPointA.setEnabled(false);
					 btnEqualsB.setEnabled(false);
					 btnPlusC.setEnabled(false);
					 btnMinusD.setEnabled(false);
					 btnDivideE.setEnabled(false);
					 btnMultiplyF.setEnabled(false);
					 btnHistory.setEnabled(false);
					 
					 btnConvert.setText("CALC");
					 btnLeftParenthesis.setText("BIN");
					 btnRightParenthesis.setText("OCT");	
					 btnFraction.setText("DEC");
					 btnMoreLess.setText("HEX");
					 btnPointA.setText("A");
					 btnEqualsB.setText("B");
					 btnPlusC.setText("C");
					 btnMinusD.setText("D");
					 btnDivideE.setText("E");
					 btnMultiplyF.setText("F");
					 
				} else if (mode==1) {
				     mode = 0;
				     setSize(2);
				     btnAbs.setEnabled(true);
					 btnLn.setEnabled(true);
					 btnRound.setEnabled(true);
					 btnSquareRoot.setEnabled(true);
					 btn3rdRoot.setEnabled(true);
					 btnNthRoot.setEnabled(true);
					 btnLog.setEnabled(true);
					 btn2thPower.setEnabled(true);
					 btn3rdPower.setEnabled(true);
					 btnNthPower.setEnabled(true);
					 btnE.setEnabled(true);
					 btnSinX.setEnabled(true);
					 btnCosX.setEnabled(true);
					 btnTanX.setEnabled(true);
					 btnPi.setEnabled(true);
					 btnAsinX.setEnabled(true);
					 btnAcosX.setEnabled(true);
					 btnAtanX.setEnabled(true);
					 btnDeg.setEnabled(true);
					 btnSinhX.setEnabled(true);
					 btnCoshX.setEnabled(true);
					 btnTanhX.setEnabled(true);
					 btnZero.setEnabled(true);
					 btnOne.setEnabled(true);
					 btnTwo.setEnabled(true);
					 btnThree.setEnabled(true);
					 btnFour.setEnabled(true);
					 btnFive.setEnabled(true);
					 btnSix.setEnabled(true);
					 btnSeven.setEnabled(true);
					 btnEight.setEnabled(true);
					 btnNine.setEnabled(true);
					 btnPointA.setEnabled(true);
					 btnEqualsB.setEnabled(true);
					 btnPlusC.setEnabled(true);
					 btnMinusD.setEnabled(true);
					 btnDivideE.setEnabled(true);
					 btnMultiplyF.setEnabled(true);
					 btnHistory.setEnabled(true);
					 
					 btnConvert.setText("CONVERT");
					 btnLeftParenthesis.setText("(");
					 btnRightParenthesis.setText(")");
					 btnFraction.setText("FRAC");
					 btnMoreLess.setText("LESS");
					 btnPointA.setText(",");
					 btnEqualsB.setText("=");
					 btnPlusC.setText("+");
					 btnMinusD.setText("−");
					 btnDivideE.setText("÷");
					 btnMultiplyF.setText("×");
				}
				updateDisplay();
				//Bylo problemové renderovani, tak byl přidán repaint
				repaint();
			}
		});
		btnConvert.setBounds(395, 100, 65, 50);
		contentPane.add(btnConvert);
	}

	public void setSize(int size) {
		Point location = getLocationOnScreen();
		switch (size) {
		case 1: {	
			setBounds(location.x, location.y, 270, 432);
			scrollDisplayPanel.setBounds(5, 5, 260, 93);
			CalculatorGUI.lenViewMem = 27;
			CalculatorGUI.size = 1;
			btnMoreLess.setText("MORE");	
			btnAbs.setVisible(false);
			btnLn.setVisible(false);
			btnLog.setVisible(false);
			btnE.setVisible(false);
			btnPi.setVisible(false);
			btnDeg.setVisible(false);
			repaint();
			break;
		}
		case 2: {
			CalculatorGUI.size = size;
			setBounds(location.x, location.y, 530, 432);
			scrollDisplayPanel.setBounds(5, 5, 520, 93);
			CalculatorGUI.lenViewMem = 56;
			CalculatorGUI.size = 2;
			btnMoreLess.setText("LESS");
			btnAbs.setVisible(true);
			btnLn.setVisible(true);
			btnLog.setVisible(true);
			btnE.setVisible(true);
			btnPi.setVisible(true);
			btnDeg.setVisible(true);
			repaint();
			break;
		}
		case 3: {
			CalculatorGUI.size = size;
			setBounds(location.x, location.y, 695, 432);
			scrollDisplayPanel.setBounds(5, 5, 685, 93);
			CalculatorGUI.lenViewMem = 74;
			CalculatorGUI.size = 3;
			btnMoreLess.setText("LESS");
			btnAbs.setVisible(true);
			btnLn.setVisible(true);
			btnLog.setVisible(true);
			btnE.setVisible(true);
			btnPi.setVisible(true);
			btnDeg.setVisible(true);
			repaint();
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + size);
		}
	}

	public void updateDisplay() {	
		if (this.mode==0) {
		mainDisplay.setText(instanceArray.getViewString());
		} else if (this.mode==1) {
		mainDisplay.setText(convertor.getViewString());
		}
	}
	
	public void updateDisplayWithEquals() {
		if (mode==0) {	
			String result = instanceArray.getEvaluatedValue();
			int lenResult = result.length()+1;
			historyString  = historyString + instanceArray.getViewString().replace(smallCursor, " ") +"\n"+
							 "="+instanceArray.getEvaluatedValue().replace(".",",")+ "\n" + "\n";	
			mainDisplay.setText(instanceArray.getViewString().replace(smallCursor, " ") + 
					           "\n"+ CalculationListMember.spaces(CalculatorGUI.lenViewMem-lenResult) +"= "+instanceArray.getEvaluatedValue());
			historyDisplay.setText(historyString);
		} else if(mode==1) {
			convertor.addBeforeCursor("B");;
			updateDisplay();
		}
	}
}
