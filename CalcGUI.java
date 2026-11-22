package ProjectJava;
import java.awt.*;// Library for Graphics & event handling
import java.awt.event.*;// Library for Graphics & event handling
import java.util.Arrays;// Library listing the buttons
import javax.swing.*;// Library Modifying the border of the buttons
import javax.swing.border.LineBorder;// Library Modifying the border of the buttons
public class CalcGUI {
    int boardWidth = 360;// window width
    int boardHeight = 540;// window height

    // Color for the window backgrouds and buttons
    Color customDarkGray = new Color(51, 63, 71);
    Color customLightGray = new Color(212, 212, 210);
    Color customDeepNavy = new Color(30, 58, 95);
    Color customWhite = new Color(255, 255, 255);
    Color customOffBlack = new Color(20, 20, 20);

    // Array for the button symbols
    String[] buttonValues ={
        "AC", "+/-", "%", "÷",
        "7", "8", "9", "×",
        "4", "5", "6", "-",
        "1", "2", "3", "+",
        "0", ".", "√", "="
    };
    // divide the symbols to make easier 
    String[] rightSymbols ={"÷", "×", "-", "+", "="};
    String[] topSymbols ={ "AC", "+/-", "%"};

    JFrame frame = new JFrame("Calculator Project");// Text on top of the window
    JLabel displayLabel = new JLabel();
    JPanel displayPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();

    // for sqrt
    double numA;
    double numB;
    // A+B, A-B, A*B, A/B
    String A ="0";
    String operator = null;
    String B = null;

    CalcGUI(){// Constructor
        
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);// center the window frame
        frame.setResizable(false);// make the size Absolute/ Unchangeable
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// X terminate button
        frame.setLayout(new BorderLayout());// make a component like East Or South ETC in the window
        
        
        displayLabel.setBackground(customOffBlack);// background calc
        displayLabel.setForeground(customWhite);// text color
        displayLabel.setFont(new Font("Arial", Font.PLAIN, 60));// display text font and sizes
        displayLabel.setHorizontalAlignment(JLabel.RIGHT);// display text to the right
        displayLabel.setText("0");// default text
        displayLabel.setOpaque(true);


        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(displayLabel);// put the Label in the Panel
        frame.add(displayPanel, BorderLayout.NORTH);// put the Panel in the window and the "0" to the top


        buttonsPanel.setLayout(new GridLayout(5, 4));// 5 row and 4 columns
        buttonsPanel.setBackground(customDarkGray);
        frame.add(buttonsPanel);

        for(int i=0; i< buttonValues.length; i++){
            JButton button = new JButton();
            String buttonValue = buttonValues[i];
            button.setFont(new Font("Arial", Font.PLAIN, 30));// font and sizes
            button.setText(buttonValue);
            button.setFocusable(false); // remove the rectangle on the numb or symbols
            button.setBorder(new LineBorder(customOffBlack));// outline color each buttons
            if(Arrays.asList(topSymbols).contains(buttonValue)){// top color
                button.setBackground(customDeepNavy);
                button.setForeground(customLightGray);
            }
            else if(Arrays.asList(rightSymbols).contains(buttonValue)){// right color
                button.setBackground(customDarkGray);
                button.setForeground(customWhite);
            }
            else{// numb color
                button.setBackground(customLightGray);
                button.setForeground(customDarkGray);
            }
            buttonsPanel.add(button);// add the button to the window

            // for button action/click
            button.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    JButton button = (JButton)e.getSource();// e action event and source is where the events comes from
                    String buttonValue = button.getText();
                    if(Arrays.asList(rightSymbols).contains(buttonValue)){
                        if(buttonValue == "="){
                            if(displayLabel.getText().contains("√") && operator ==null){// show the sqrt value if the user press the equal button without using any operator
                                    String text = displayLabel.getText().replace("√", "").trim();
                                    double valueSQRT = Double.parseDouble(text);
                                    double sqrt = Math.sqrt(valueSQRT);
                                    A = String.valueOf(sqrt);
                                    displayLabel.setText(A);
                                }
                            if(A != null){
                                if(displayLabel.getText().contains("√")){// remove sqrt symbols and convert the value
                                    String text = displayLabel.getText().replace("√", "").trim();
                                    double valueSQRT = Double.parseDouble(text);
                                    double sqrt = Math.sqrt(valueSQRT);
                                    B = String.valueOf(sqrt);
                                    numA = Double.parseDouble(A);
                                    numB = Double.parseDouble(B);
                                }
                                else{
                                B = displayLabel.getText();
                                numA = Double.parseDouble(A);
                                numB = Double.parseDouble(B);
                                }
                                
                                if(operator == "+"){
                                    displayLabel.setText(removeZeroDecimal(numA+numB));
                                }
                                else if(operator == "-"){
                                    displayLabel.setText(removeZeroDecimal(numA-numB));
                                } 
                                else if(operator == "×"){
                                    displayLabel.setText(removeZeroDecimal(numA*numB));
                                }
                                else if(operator == "÷"){
                                    displayLabel.setText(removeZeroDecimal(numA/numB));
                                }
                                clearAll();
                            }
                        }
                        else if("÷×-+".contains(buttonValue)){
                            if(operator==null){// prevent the user from using the operator twice
                                if(displayLabel.getText().contains("√")){// remove sqrt symbols and convert the value
                                    String text = displayLabel.getText().replace("√", "").trim();
                                    double valueSQRT = Double.parseDouble(text);
                                    double sqrt = Math.sqrt(valueSQRT);
                                    A = String.valueOf(sqrt);
                                    displayLabel.setText("0");
                                    B ="0";
                                }else{
                                A = displayLabel.getText();
                                displayLabel.setText("0");
                                B = "0";
                                }
                            }
                            operator = buttonValue;
                        }
                    }
                    else if(Arrays.asList(topSymbols).contains(buttonValue)){
                        if(buttonValue == "AC"){//clear all the numb and set to zero
                            clearAll();
                            displayLabel.setText("0");
                        }
                        else if(buttonValue == "+/-"){//convert the numb into negative
                            double numbDisplay = Double.parseDouble(displayLabel.getText());
                            numbDisplay*=-1;
                            displayLabel.setText(removeZeroDecimal(numbDisplay));
                        }
                        else if(buttonValue == "%"){
                            double numbDisplay = Double.parseDouble(displayLabel.getText());
                            numbDisplay/=100;
                            displayLabel.setText(removeZeroDecimal(numbDisplay));

                        }
                    }
                    else{// digits or .
                        if(buttonValue == "."){
                            if(!displayLabel.getText().contains(buttonValue)){// to make the decimal symbols once if there wasn't
                                displayLabel.setText(displayLabel.getText() + buttonValue);
                            }
                        }
                        else if(buttonValue == "√"){
                            if(displayLabel.getText() == "0"){
                                displayLabel.setText(buttonValue);
                            }
                            else{
                                displayLabel.setText(displayLabel.getText() + buttonValue);
                            }
                        }
                        else if("0123456789".contains(buttonValue)){
                            if(displayLabel.getText() == "0"){
                                displayLabel.setText(buttonValue);// 5 instead of 05
                            }
                            else{
                                displayLabel.setText(displayLabel.getText() + buttonValue);
                            }
                        }
                    }
                }
            });
            frame.setVisible(true);
        }

    }
    void clearAll(){
        A ="0";
        operator = null;
        B = null;
    }
    String removeZeroDecimal(double numDisplay){// remove the extra zeros from the process into (-)
        if(numDisplay%1==0){
            return Integer.toString((int)numDisplay);
        }
        return Double.toString(numDisplay);
    }

    public static void main(String[] args) {
    }
}

