package application;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class AboutWindow extends JFrame{

    public AboutWindow() throws HeadlessException {
        super("Acerca de la aplicacion");
        deployUI();
    }

    private void deployUI() {
        getContentPane().add(informationText(), BorderLayout.CENTER);
        getContentPane().add(closeButton(), BorderLayout.SOUTH);
        setCollocation();
    }

    private JTextArea informationText() {
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setWrapStyleWord(true);
        textArea.setText(aboutText());
        return textArea;
    }

    private JButton closeButton() {
        JButton closeButton = new JButton("Cerrar");
        closeButton.addActionListener(closeButtonListener());
        return closeButton;
    }
    
    private void setCollocation() {
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private String aboutText() {
        return "Aplicacion creada por Alberto Cárdenes Pérez \n"
                + "Para la asignatura de IS2 \n"
                + "2015/2016";
    }

    private ActionListener closeButtonListener() {
        return new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                AboutWindow.this.dispose();
            }
        };
    }

    
    
}
