import java.awt.EventQueue;

/**Used to start the calculator.
 * @author Jakub KVAPIL
 * @version 1.03 
 * */
public class Main {

static private CalculatorGUI calculatiorGUI;

	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					calculatiorGUI = new CalculatorGUI();
					calculatiorGUI.setVisible(true);
					calculatiorGUI.setSize(1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	
	}

}
