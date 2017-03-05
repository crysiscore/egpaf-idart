/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccs.openmrs.migracao.swingreverse;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.PrintStream;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author ColacoVM
 */
public class UnirNids extends JPanel {
    private final JProgressBar progress1;
    private final JProgressBar progress2;

    public UnirNids() {
        super(new BorderLayout());
        this.progress1 = new JProgressBar(){

            @Override
            public void updateUI() {
                super.updateUI();
                this.setUI(new ProgressCircleUI());
                this.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
            }
        };
        this.progress2 = new JProgressBar(){

            @Override
            public void updateUI() {
                super.updateUI();
                this.setUI(new ProgressCircleUI());
                this.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
            }
        };
        this.progress1.setForeground(new Color(-1426085206));
        this.progress2.setStringPainted(true);
        this.progress2.setFont(this.progress2.getFont().deriveFont(24.0f));
        JSlider slider = new JSlider();
        slider.putClientProperty("Slider.paintThumbArrowShape", Boolean.TRUE);
        this.progress1.setModel(slider.getModel());
        JTextArea textArea = new JTextArea(50, 10);
        textArea.setEditable(false);
        PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));
        PrintStream standardOut = System.out;
        System.setErr(printStream);
        this.add((Component)new JButton(new AbstractAction("Unir NIDS dos Pacientes do OpenMRS para o IDART"){

            @Override
            public void actionPerformed(ActionEvent e) {
                final JButton b = (JButton)e.getSource();
                b.setEnabled(false);
                Task2 worker = new Task2(){

                    @Override
                    public void done() {
                        if (b.isDisplayable()) {
                            b.setEnabled(true);
                            b.setLabel("Fechar");
                        }
                    }
                };
                worker.addPropertyChangeListener(new ProgressListener(UnirNids.this.progress2));
                worker.execute();
                if (b.getLabel().equalsIgnoreCase("Fechar")) {
                    UnirNids.this.setVisible(false);
                }
            }

        }), "South");
        JPanel p = new JPanel(new GridLayout(1, 2));
        p.add(new JScrollPane(textArea));
        p.add(this.progress2);
        this.add(p);
        this.setPreferredSize(new Dimension(920, 440));
    }

    public static /* varargs */ void main(String ... args) {
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                UnirNids.createAndShowGUI();
            }
        });
    }

    public static void createAndShowGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        JFrame frame = new JFrame("Unir NIDS dos Pacientes do OpenMRS para o IDART");
        frame.setDefaultCloseOperation(1);
        frame.getContentPane().add(new UnirNids());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}

