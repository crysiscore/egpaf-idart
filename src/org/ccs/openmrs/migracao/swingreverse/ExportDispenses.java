/*
 * Decompiled with CFR 0_114.
 */
package org.ccs.openmrs.migracao.swingreverse;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.PrintStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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

public class ExportDispenses extends JPanel implements Runnable{
    private final JProgressBar progress1;
    private final JProgressBar progress2;
    private static Boolean stop = false;
    
 final ExecutorService executor = Executors.newFixedThreadPool(1);
    public ExportDispenses() {
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
        this.add((Component)new JButton(new AbstractAction("Exportar Dispensas"){

            @Override
            public void actionPerformed(ActionEvent e) {
                final JButton b = (JButton)e.getSource();
                b.setEnabled(false);
                Task1 worker = new Task1(){

                    @Override
                    public void done() {
                        if (b.isDisplayable()) {
                            b.setEnabled(true);
                            b.setLabel("Fechar");
                        }
                    }
                };
                worker.addPropertyChangeListener(new ProgressListener(ExportDispenses.this.progress2));
                worker.execute();
                if (b.getLabel().equalsIgnoreCase("Fechar")) {
                     ExportDispenses.this.setStop(true);
                    ExportDispenses.this.setVisible(false);
                }
            }

        }), "South");
        JPanel p = new JPanel(new GridLayout(1, 2));
        p.add(new JScrollPane(textArea));
        p.add(this.progress2);
        this.add(p);
        this.setPreferredSize(new Dimension(920, 440));
    }

    public static void main(String ... args) {
        ExportDispenses exportDispenses = new ExportDispenses();
        exportDispenses.run();
//        EventQueue.invokeLater(
//                new Runnable(){
//            @Override
//            public void run() {
//            }
//        });
    }

    public static void createAndShowGUIExport() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        JFrame frame = new JFrame("Export Dispenses to OpenMRS");
        frame.setDefaultCloseOperation(1);
        frame.getContentPane().add(new ExportDispenses());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void run() {
         while(!stop){
            ExportDispenses.createAndShowGUIExport();
             ExportDispenses.this.setStop(true);
         }
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

     public Boolean getStop() {
                return stop;
               }

              public void setStop(Boolean stop) {
                this.stop = stop;
              }  
    
}

