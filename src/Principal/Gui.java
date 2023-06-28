package Principal;

import ProcesarConsulta.Transformador;
import java.awt.Color;
import java.awt.Event;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.text.*;
import lexico.Lexer;
import lexico.Tokens;

public class Gui extends JFrame implements ActionListener {

    JMenu menuAyuda;
    JMenuItem item1M1, item2M1, item1M2, item2M2, item3M2, item4M2, item5M2, item6M2, item7M2, item8M2, item9M2, itemAy1, itemAy2;
    String QUERYact;
    int posJTPA = 0;

    public Gui(String[] colores) {
        initComponents();
        setLocationRelativeTo(null);

        setSize(750, 500);

        menuAyuda = new JMenu("Ayuda");
        setResizable(false);
        jCBaccion.removeAllItems();

        jCBaccion.addItem("Π"/*proyección*/);
        jCBaccion.addItem("σ" /*selección*/);
        jCBaccion.addItem("∪");
        jCBaccion.addItem("∪b");
        jCBaccion.addItem("∩");
        jCBaccion.addItem("*");
        jCBaccion.addItem("-");
        jCBaccion.addItem("join");
        jCBaccion.setVisible(false);
        jLabel1.setVisible(false);

        jmenubar();
        jTPalgebra.setText("");
        jTPalgebra.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                posJTPA = jTPalgebra.getCaretPosition();
            }
        });
    }

    public void jmenubar() { //La barra de menu que muestra atajos

        AbstractAction accProy = new AbstractAction("Π") {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultStyledDocument document = (DefaultStyledDocument) jTPalgebra.getDocument();
                try {
                    document.insertString(jTPalgebra.getCaretPosition(), "Πcampos(RELACION)", null);
                } catch (BadLocationException ex) {
                    Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
                }

                jTPalgebra.setCaretPosition(jTPalgebra.getCaretPosition() - 8);
            }
        };
        accProy.putValue(Action.ACCELERATOR_KEY, KeyStroke.getAWTKeyStroke('P', Event.CTRL_MASK));

        AbstractAction accSelect = new AbstractAction("σ") {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultStyledDocument document = (DefaultStyledDocument) jTPalgebra.getDocument();
                try {
                    document.insertString(jTPalgebra.getCaretPosition(), "σcondicion(RELACION)", null);
                } catch (BadLocationException ex) {
                    Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
                }

                jTPalgebra.setCaretPosition(jTPalgebra.getCaretPosition() - 8);
            }
        };
        accSelect.putValue(Action.ACCELERATOR_KEY, KeyStroke.getAWTKeyStroke('S', Event.CTRL_MASK));

        AbstractAction accUnion = new AbstractAction("∪") {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultStyledDocument document = (DefaultStyledDocument) jTPalgebra.getDocument();
                try {
                    document.insertString(jTPalgebra.getCaretPosition(), "RELACION ∪  RELACION", null);
                } catch (BadLocationException ex) {
                    Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        };
        accUnion.putValue(Action.ACCELERATOR_KEY, KeyStroke.getAWTKeyStroke('U', Event.CTRL_MASK));

        AbstractAction accInter = new AbstractAction("∩") {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultStyledDocument document = (DefaultStyledDocument) jTPalgebra.getDocument();
                try {
                    document.insertString(jTPalgebra.getCaretPosition(), "RELACION ∩ RELACION", null);
                } catch (BadLocationException ex) {
                    Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
                }

                jTPalgebra.setCaretPosition(jTPalgebra.getCaretPosition() - 14);
            }
        };
        accInter.putValue(Action.ACCELERATOR_KEY, KeyStroke.getAWTKeyStroke('I', Event.CTRL_MASK));

        AbstractAction accCruz = new AbstractAction("*") {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultStyledDocument document = (DefaultStyledDocument) jTPalgebra.getDocument();
                try {
                    document.insertString(jTPalgebra.getCaretPosition(), "*", null);
                } catch (BadLocationException ex) {
                    Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        };
        accCruz.putValue(Action.ACCELERATOR_KEY, KeyStroke.getAWTKeyStroke('K', Event.CTRL_MASK));

        AbstractAction accDif = new AbstractAction("-") {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultStyledDocument document = (DefaultStyledDocument) jTPalgebra.getDocument();
                try {
                    document.insertString(jTPalgebra.getCaretPosition(), "RELACION - RELACION", null);
                } catch (BadLocationException ex) {
                    Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
                }
//              jTPalgebra.setText(jTPalgebra.getText()+"reg1 - reg2 ( cond )");
                jTPalgebra.setCaretPosition(jTPalgebra.getCaretPosition() - 17);
            }
        };
        accDif.putValue(Action.ACCELERATOR_KEY, KeyStroke.getAWTKeyStroke('D', Event.CTRL_MASK));

        AbstractAction accJoin = new AbstractAction("JOIN") {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultStyledDocument document = (DefaultStyledDocument) jTPalgebra.getDocument();
                try {
                    document.insertString(jTPalgebra.getCaretPosition(), "JOIN", null);
                } catch (BadLocationException ex) {
                    Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
                }
//              jTPalgebra.setText(jTPalgebra.getText()+"JOIN(reg1, reg2)");
                jTPalgebra.setCaretPosition(jTPalgebra.getCaretPosition() - 7);
            }
        };
        accJoin.putValue(Action.ACCELERATOR_KEY, KeyStroke.getAWTKeyStroke('J', Event.CTRL_MASK));

        AbstractAction accUnionBag = new AbstractAction("∪B") {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultStyledDocument document = (DefaultStyledDocument) jTPalgebra.getDocument();
                try {
                    document.insertString(jTPalgebra.getCaretPosition(), "RELACION ∪B RELACION", null);
                } catch (BadLocationException ex) {
                    Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
                }
//              jTPalgebra.setText(jTPalgebra.getText()+"JOIN(reg1, reg2)");
                jTPalgebra.setCaretPosition(jTPalgebra.getCaretPosition() - 7);
            }
        };
        accUnionBag.putValue(Action.ACCELERATOR_KEY, KeyStroke.getAWTKeyStroke('B', Event.CTRL_MASK));

        //itemAy1 = new JMenuItem("Gramatica");
        //itemAy2 = new JMenuItem("Info");
        //itemAy1.addActionListener(this);
        //itemAy2.addActionListener(this);
        item1M2 = new JMenuItem(accProy);
        item2M2 = new JMenuItem(accSelect);
        item3M2 = new JMenuItem(accUnion);
        item4M2 = new JMenuItem(accInter);
        item5M2 = new JMenuItem(accCruz);
        item6M2 = new JMenuItem(accDif);
        item7M2 = new JMenuItem(accJoin);
        item8M2 = new JMenuItem(accUnionBag);

        item1M2.addActionListener(this);
        item2M2.addActionListener(this);
        item3M2.addActionListener(this);
        item4M2.addActionListener(this);
        item5M2.addActionListener(this);
        item6M2.addActionListener(this);
        item7M2.addActionListener(this);
        item8M2.addActionListener(this);

        jMenu2.add(new JSeparator());
        jMenu2.add(item1M2);
        jMenu2.add(item2M2);
        jMenu2.add(item3M2);
        jMenu2.add(item8M2);
        jMenu2.add(item4M2);
        jMenu2.add(item5M2);
        jMenu2.add(item6M2);
        jMenu2.add(item7M2);

        jMenuBar.add(jMenu2);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jCBaccion = new javax.swing.JComboBox();
        jBtrans = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTPalgebra = new javax.swing.JTextPane();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTPsentencia = new javax.swing.JTextPane();
        jLabel7 = new javax.swing.JLabel();
        jMenuBar = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Acción:");

        jCBaccion.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));
        jCBaccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //  jCBaccionActionPerformed(evt);
            }
        });

        jBtrans.setText("Transformar");
        jBtrans.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    jBtransActionPerformed(evt);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        jScrollPane4.setViewportView(jTPalgebra);

        jLabel6.setText("Algebra Relacional:");

        jScrollPane5.setViewportView(jTPsentencia);

        jLabel7.setText("Sentencia SQL:");

        jMenu2.setText("Edit");
        jMenuBar.add(jMenu2);

        setJMenuBar(jMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(136, Short.MAX_VALUE)
                                .addComponent(jCBaccion, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(jBtrans, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(264, 264, 264))
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel6)
                                        .addComponent(jLabel7))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane5)
                                        .addComponent(jScrollPane4))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(56, 56, 56)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jBtrans, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jCBaccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel7)
                                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        

    //Para cuando se da clic en el boton de transformar
    private void jBtransActionPerformed(java.awt.event.ActionEvent evt) throws FileNotFoundException, IOException, Exception {
        //Se crea el archivo
        File archivo = new File("Programa.txt");
        PrintWriter esc;
        //Se escribe en el archivo
        esc = new PrintWriter(archivo);
        esc.print(jTPalgebra.getText());
        esc.close();

        //Se lee el archivo y se realiza el analisis lexico
        Reader leer = new BufferedReader(new FileReader(archivo));
        Lexer al = new Lexer(leer);
        String resultado = " ";
        //Este arrayList contiene las palabras clave (Tokens) del lexer
        ArrayList<String> query = new ArrayList();
        while (true) {
            //Obtiene el token en cada iteracion
            Tokens token = al.yylex();
            //Si el token es null significa que ya llego al final y sale del while
            if (token == null) {
                resultado += "FIN";
                jTPsentencia.setText(resultado);
                break;
            }

            switch (token) { //Dependiento el token, se agrega al string de resultado
                case Reservada:
                    resultado += "\nreservada  " + al.lexeme;

                    break;
                case PROY:
                    resultado += "\nPROY  " + al.lexeme;
                    query.add(al.lexeme);
                    break;
                case SELECT:
                    resultado += "\nSELECT " + al.lexeme;
                    query.add(al.lexeme);

                    break;
                case INTERSECCION:
                    resultado += "\nINTERSECCION  " + al.lexeme;
                    query.add(al.lexeme);

                    break;
                case UNION:
                    resultado += "\nUNION  " + al.lexeme;
                    query.add(al.lexeme);

                    break;
                case UNIONBAG:
                    resultado += "\nUNIONBAG " + al.lexeme;
                    query.add(al.lexeme);

                    break;
                case CRUZ:
                    resultado += "\nCRUZ  " + al.lexeme;
                    query.add(al.lexeme);

                    break;
                case DIFERENCIA:
                    resultado += "\nDIFERENCIA  " + al.lexeme;
                    query.add(al.lexeme);

                    break;
                case JOIN:
                    resultado += "\nJOIN  " + al.lexeme;
                    query.add(al.lexeme);

                    break;
                case COMA:
                    resultado += "\nCOMA  " + al.lexeme;
                    query.add(al.lexeme);

                    break;
                case PUNTO:
                    resultado += "\nPalabra  " + al.lexeme;

                    break;
                case PAR_I:
                    resultado += "\nPARI  " + al.lexeme;
                    query.add(al.lexeme);

                    break;
                case PAR_II:
                    resultado += "\nPARII  " + al.lexeme;
                    query.add(al.lexeme);

                    break;
                case PAR_D:
                    resultado += "\nPARD  " + al.lexeme;
                    query.add(al.lexeme);

                    break;
                case PAR_DD:
                    resultado += "\nPARDD  " + al.lexeme;
                    query.add(al.lexeme);

                    break;
                case AND:
                    resultado += "\nAND  " + al.lexeme;
                    query.add(al.lexeme);

                    break;
                case OR:
                    resultado += "\nOR  " + al.lexeme;
                    query.add(al.lexeme);

                    break;
                case NOT:
                    resultado += "\nNOT  " + al.lexeme;
                    query.add(al.lexeme);

                    break;
                case MAYOR:
                    resultado += "\nMAYOR  " + al.lexeme;
                    query.add(al.lexeme);

                    break;
                case MAYORIGUAL:
                    resultado += "\nMAYORIGUAL  " + al.lexeme;
                    query.add(al.lexeme);

                    break;
                case MENOR:
                    resultado += "\nMENOR  " + al.lexeme;
                    query.add(al.lexeme);

                    break;
                case MENORIGUAL:
                    resultado += "\nMENORIGUAL  " + al.lexeme;
                    query.add(al.lexeme);

                    break;
                case IGUAL:
                    resultado += "\nPalabra  " + al.lexeme;
                    query.add(al.lexeme);

                    break;
                case DIFERENTE:
                    resultado += "\nDIFERENTE  " + al.lexeme;
                    query.add(al.lexeme);

                    break;
                case COMO:
                    resultado += "\nPalabra  " + al.lexeme;
                    query.add(al.lexeme);

                    break;
                case TABLA:
                    resultado += "\nTABLA  " + al.lexeme;
                    query.add(al.lexeme);

                    break;
                case IDENTIFICADOR:
                    resultado += "\nIDENTIFICADOR  " + al.lexeme;
                    query.add(al.lexeme);

                    break;
                case NUMERO:
                    resultado += "\nNUMERO  " + al.lexeme;
                    query.add(al.lexeme);

                    break;
                case CADENA:
                    resultado += "\nCADENA  " + al.lexeme;
                    query.add(al.lexeme);

                    break;
                case CAMPO:
                    resultado += "\nCAMPO  " + al.lexeme;
                    query.add(al.lexeme);

                    break;

                default:
                    resultado += "\nPalabra ERROR " + al.lexeme;
                    break;
            }

        }

        //Se crea e inicializa Transformador para transformar a SQL
        Transformador traducir = new Transformador(query); //Se le pasa el arrayList como parametro
        String sql = traducir.transformar(); //Se almacena la traduccion de SQL
        System.out.println(traducir.tamaño + "∪_b");
        System.out.println(sql);
        jTPsentencia.setText(sql);

    }

    public static void main(final String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Gui(args).setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    private javax.swing.JButton jBtrans;
    private javax.swing.JComboBox jCBaccion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextPane jTPalgebra;
    private javax.swing.JTextPane jTPsentencia;

    public void escribir(String direccion) {
        //metodo que guarda lo que esta escrito en el TextArea en un archivo de texto
        try {
            FileWriter writer = new FileWriter(direccion);
            PrintWriter print = new PrintWriter(writer);
            print.print(jTPalgebra.getText());
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void appendTexto(Color color, String texto) { //agrega el texto a jTPalgebra con cierto color
        StyledDocument doc = jTPalgebra.getStyledDocument();
        Style syle = doc.addStyle("txt", null);
        StyleConstants.setForeground(syle, color);
        try {
            doc.insertString(this.posJTPA, texto, syle);
            jTPalgebra.setCaretPosition(doc.getLength());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error:", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

}
