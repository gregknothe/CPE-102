 
 
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import java.lang.StringBuilder;
 
/**
 * Encrypts and Decrypts text using the Caesar Cihper algorithm.
 * @author Invisible Computer, JTN
 *
 */
public class CaesarGUI extends JFrame implements ActionListener {
 
        private static final long serialVersionUID = 1L;
        private static String alphabet = "abcdefghijklmnopqrstuvwxyz .!@#$%^&*()_+-=/,;:0123456789";
        private static String alphabet2 = "ㄱㄴㄷㄹㅁㅂㅅㅇㅈㅊㅋㅌㅍㅎㄲㅏㅑㅔ가간감게겐겔겜광 옴앙억여완왈왕왜외워원월위유육윤율융윷기꼿꾜꿰끕낡넓녔눅늉";
        private JTextField shiftFactor;
        private JTextArea inputTA;
        private JTextArea outputTA;
 
        
        /**
         * @param args
         */
        public static void main(String[] args) {
                new CaesarGUI().setVisible(true);
        }
       
        public int type(char x){
                if (alphabet.indexOf(x) != -1) {
                    return 1; 
                }
                else if (alphabet2.indexOf(x) != -1 ) {
                    return 2;
                }
                else {
                    return 0; 
                }
        }
        
        public void encryptText() throws InterruptedException {
                //Create a HashMap
                //A hash map takes keys and values, which are both Characters in this case.
                HashMap<Character, Character> alphaMap = new HashMap<Character, Character>();
                HashMap<Character, Character> alphaMap2 = new HashMap<Character, Character>();
                int shift;
                //Get the text from the app and store it in a String variable.
                String textNum = this.shiftFactor.getText();
                //Check to see if a "Shift Factor" value was entered.
                //If there wasn't, set shift to zero,
                //Otherwise parse the input value to an integer so we can use it.
                if(!textNum.equals("")){
                        shift = Integer.parseInt(textNum)%56;
                }
                else{
                        shift = 0;
                }
                //Map every letter of the alphabet to another letter in the alphabet, shifted by x places.
                for(int i=0; i<alphabet.length(); i++){
                        alphaMap.put(alphabet.charAt(i), alphabet2.charAt((i+shift+56)%56));
                }
                for(int i=0; i<alphabet2.length(); i++){
                        alphaMap2.put(alphabet2.charAt(i), alphabet.charAt((i+shift+56)%56));
                }
                //Get input text and put it all to lower-case so it's easy to convert
                String inputText = inputTA.getText().toLowerCase();
                String outputText = "";
                
                int wordType;
                //Go to each letter and switch it with it's shifted counterpart
                for(int j=0; j<inputText.length(); j++){
                        wordType = type(inputText.charAt(j));
                        if(wordType == 2){
                            //System.out.println("KR->EN translation occurs");
                            outputText = outputText.concat(alphaMap2.get(inputText.charAt(j)).toString()); 
                        }
                        else if(wordType == 1){
                            //System.out.println("EN->KR translation occurs");
                            outputText = outputText.concat(alphaMap.get(inputText.charAt(j)).toString());
                        }
                        else {
                            outputText = outputText.concat("X");
                        }
                }
                //Output the encrypted text
                outputTA.setText(outputText);
        }
       
        public void decryptText() throws InterruptedException{
                HashMap<Character, Character> alphaMap = new HashMap<Character, Character>();
                HashMap<Character, Character> alphaMap2 = new HashMap<Character, Character>();
                int shift;
                String textNum = this.shiftFactor.getText();
                if(!textNum.equals("")){
                        shift = Integer.parseInt(textNum)%56;
                }
                else{
                        shift = 0;
                }

                for(int i=0; i<alphabet.length(); i++){
                        alphaMap.put(alphabet.charAt(i), alphabet2.charAt((i-shift+56)%56));
                }
                for(int i=0; i<alphabet2.length(); i++){
                        alphaMap2.put(alphabet2.charAt(i), alphabet.charAt((i-shift+56)%56));
                }
                String inputText = inputTA.getText().toLowerCase();
                String outputText = "";

                int wordType;
                for(int j=0; j<inputText.length(); j++){
                        wordType = type(inputText.charAt(j));
                        if(wordType == 2){
                            //System.out.println("KR->EN translation occurs");
                            outputText = outputText.concat(alphaMap2.get(inputText.charAt(j)).toString());
                        }
                        else if(wordType == 1){
                            //System.out.println("EN->KR translation occurs");
                            outputText = outputText.concat(alphaMap.get(inputText.charAt(j)).toString());
                        }
                        else {
                            outputText = outputText.concat("X");
                        }
                }
                outputTA.setText(outputText);
        }
       
        public CaesarGUI(){
                setTitle("Caesar Cipher");
            setVisible(true);
            setDefaultCloseOperation(3);
 
            Container content = getContentPane();
            GridLayout layout = new GridLayout(3, 0, 0, 10);
            content.setLayout(layout);
 
            inputTA = new JTextArea("Insert the text to be encrypted/decrypted here, then press the appropriate button.", 12, 40);
            inputTA.setLineWrap(true);
            inputTA.setWrapStyleWord(true);
            inputTA.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
            JScrollPane scroller = new JScrollPane(inputTA);
            scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            content.add(scroller);
           
            outputTA = new JTextArea("Output text.",12, 40);
            outputTA.setLineWrap(true);
            outputTA.setWrapStyleWord(true);
            outputTA.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
            JScrollPane scroller2 = new JScrollPane(outputTA);
            scroller2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            content.add(scroller2);
           
            JPanel box1 = new JPanel();
            box1.setLayout(new FlowLayout());
            JButton decryptButton = new JButton("Decrypt");
            JButton encryptButton = new JButton("Encrypt");
            decryptButton.addActionListener(this);
            encryptButton.addActionListener(this);
            box1.add(decryptButton);
            box1.add(encryptButton);
            box1.add(new JLabel("Shift Factor"));
            box1.add(this.shiftFactor = new JTextField(20));
            content.add(box1);
           
            pack();
        }
 
        @Override
        public void actionPerformed(ActionEvent e) {
                if(e.getActionCommand().equals("Encrypt")){
                        try{
                                encryptText();
                        }
                        catch(InterruptedException e1){
                                e1.printStackTrace();
                        }
                }
                if (e.getActionCommand().equals("Decrypt"))
                      try {
                        decryptText();
                      } catch (InterruptedException e1) {
                        e1.printStackTrace();
                      }
        } 
}
