package application;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

class Application extends JFrame{
    private Map<String, JTextField> fields = new HashMap<>();
    
    public static void main(String[] args) {
        new Application();
    }
    
    public Application() {
        deployUI();
        this.setVisible(true);
    }

    private void deployUI() {
        this.setTitle("BuscaMinas");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(340, 200);
        this.setResizable(false);
        this.getContentPane().add(panelWithQuestions(), BorderLayout.CENTER);
        this.add(startButton(), BorderLayout.SOUTH);
        this.setLocationRelativeTo(null);
    }

    private JPanel panelWithTagAndField(String name) {
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(new JLabel("Introduce el numero de " + name + ":"));
        panel.add(field(name));
        return panel;
    }

    private JTextField field(String name) {
        JTextField field = new JTextField(4);
        fields.put(name, field);
        return field;
    }

    private JButton startButton() {
        JButton button = new JButton("Comenzar");
        button.addActionListener(start());
        return button;
    }

    private ActionListener start() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = toNumber("filas");
                int column = toNumber("columnas");
                int mine = toNumber("minas");
                if(checkAreValid(row, column, mine)) {
                    new SwingGameBoardDisplay(row,column,mine).execute();
                    Application.this.setVisible(false);
                }
            }

            private int toNumber(String field) {
                String text = fields.get(field).getText().trim();
                try{
                    return Integer.parseInt(text);
                }catch(NumberFormatException e){
                    return -1;
                }
            }

            private boolean checkAreValid(int row, int column, int mine) {
                if(row<1) {
                    showErrorMessage("filas");
                    return false;
                }
                if(column<1) {
                    showErrorMessage("columnas");
                    return false;
                }
                if(mine>=(row*column)) {
                    showErrorMessage("minas");
                    return false;
                }
                return true;
            }
            
            private void showErrorMessage(String field) {
                    JOptionPane.showMessageDialog(Application.this, "El campo de " 
                            + field + " tiene un valor incorrecto", "Valor invalido", 
                            JOptionPane.ERROR_MESSAGE);
            }
        };
    }

    private JPanel panelWithQuestions() {
        JPanel jPanel = new JPanel();
        jPanel.add(panelWithTagAndField("filas"));
        jPanel.add(panelWithTagAndField("columnas"));
        jPanel.add(panelWithTagAndField("minas"));
        jPanel.add(new JLabel("El numero de minas ha de ser menor que el de casillas"), BorderLayout.SOUTH);
        return jPanel;
    }
    
}
