import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Calculator extends JFrame {
    private double firstNumber = 0;
    private double secondNumber = 0;
    private String currentOperation = null;
    private boolean expectingSecondNumber = false;
    private JTextField display = new JTextField();

    public Calculator() {
        setTitle("Calculator");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        display.setEditable(false);
        display.setFont(new Font("SansSerif", Font.BOLD, 24));
        display.setPreferredSize(new Dimension(display.getWidth(), 60));
        add(display, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(5, 4));
        String[] buttons = {
            "AC", "C", "%", "/", 
            "7", "8", "9", "*", 
            "4", "5", "6", "-", 
            "1", "2", "3", "+", 
            "0", ".0", ".00", "="
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.addActionListener(createButtonListener(text));
            panel.add(button);
        }

        add(panel, BorderLayout.CENTER);
    }

    private ActionListener createButtonListener(String text) {
        return e -> {
            if ("AC".equals(text)) {
                display.setText("");
                firstNumber = 0;
                secondNumber = 0;
                currentOperation = null;
            } else if ("C".equals(text)) {
                String currentText = display.getText();
                if (!currentText.isEmpty()) {
                    display.setText(currentText.substring(0, currentText.length() - 1));
                }
            } else if ("=".equals(text)) {
                	 calculateAndDisplayResult();
            } else if (text.matches("[0-9]")) {
                if (expectingSecondNumber) {
                    display.setText(""); // Clear display if expecting a second number
                    expectingSecondNumber = false;
                }
                display.setText(display.getText() + text); // Append number or decimal
            }else if(".0".equals(text)) {
            	
            	String currentText = display.getText();
            	if(!currentText.isEmpty() && !currentText.endsWith(".0") && !currentText.endsWith(".00")) {
            		display.setText(currentText + ".0"); 
            	}
            } 
			else if(".00".equals(text)) {
			            	
            	String currentText = display.getText();
            	if(!currentText.isEmpty() && !currentText.endsWith(".0") && !currentText.endsWith(".00")) {
            		display.setText(currentText + ".00"); 
            	}
            }
            
            else {
                // Handle operation buttons (+, -, *, /, %)
                if (!expectingSecondNumber) {
                    firstNumber = Double.parseDouble(display.getText());
                }
                expectingSecondNumber = true;
                currentOperation = text;
                display.setText("");
            }
        };
    }

    private double performOperation(double firstNum, double secondNum, String operation) {
        switch (operation) {
            case "+": return firstNum + secondNum;
            case "-": return firstNum - secondNum;
            case "*": return firstNum * secondNum;
            case "/": 
                if (secondNum == 0) {
                    JOptionPane.showMessageDialog(this, "Cannot divide by zero.", "Error", JOptionPane.ERROR_MESSAGE);
                    return 0;
                }
                return firstNum / secondNum;
            case "%": return firstNum % secondNum;
            default: return 0;
        }
    }
    
    private void calculateAndDisplayResult() {
            secondNumber = Double.parseDouble(display.getText());
            double result = performOperation(firstNumber, secondNumber, currentOperation);
            display.setText(String.valueOf(result));
            System.out.println(result);
            firstNumber = result; // For chaining operations
            currentOperation = null; // Reset current operation
            expectingSecondNumber = false; // Not expecting a second number immediately
        
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Calculator calculator = new Calculator();
            calculator.setVisible(true);
        });
    }
}
