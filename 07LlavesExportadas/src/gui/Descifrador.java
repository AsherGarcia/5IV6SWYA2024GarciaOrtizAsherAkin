package gui;

import javax.swing.JOptionPane;
import logica.Descifrar;
import logica.EscribirArchivo;
import logica.Llaves;

public class Descifrador extends javax.swing.JFrame {
    public Descifrador() {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        descifrar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        texto = new javax.swing.JTextPane();
        cancelar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        descifrar.setText("Descifrar");
        descifrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                descifrarActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(texto);

        cancelar.setText("Regresar");
        cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarActionPerformed(evt);
            }
        });

        jLabel1.setText("Texto a Cifrar:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(descifrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(descifrar)
                        .addGap(18, 18, 18)
                        .addComponent(cancelar))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void descifrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_descifrarActionPerformed
        descifrar();
    }//GEN-LAST:event_descifrarActionPerformed

    private void cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarActionPerformed
        new principal().setVisible(true);
        dispose();
    }//GEN-LAST:event_cancelarActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Descifrador().setVisible(true);
            }
        });
    }
    
    public void descifrar(){
        try{
            String cifrado = EscribirArchivo.leerArchivo();

            String[] cont = cifrado.split("\n");

            String firma = "", mensaje = "";
            int o = 0;
            for(String s : cont){
                if(s.indexOf("Firma: ") != -1){
                    o = 1;
                }            
                else if(s.indexOf("Texto: ") != -1){
                    o = 2;
                }

                if(o == 1){
                    firma += s.trim().replace("Firma: ", "")+"\n";
                }

                else if(o == 2){
                    if(!s.trim().equals(""))
                       mensaje += s.trim().replace("Texto: ", "")+"\n";
                }
            }
        
            String descifrado = Descifrar.descifrar(firma);  
            if(descifrado.trim().replace("\r\n", "").replace("\n", "").equals(mensaje.trim().replace("\r\n", "").replace("\n", ""))){
                JOptionPane.showMessageDialog(null, "El mensaje original NO ha sido cambiado");                
                texto.setText("Mensaje Descifrado: \n"+descifrado);
            }
            else{                
                texto.setText("Mensaje Obtenido del Archivo: \n"+mensaje+"\nMensaje Original: \n"+descifrado);
                JOptionPane.showMessageDialog(null, "El mensaje original ha sido cambiado, se muestran aqui ambas versiones del mensaje");
            }
        }
        catch(Exception e){
            if(e.getMessage().indexOf("out of bounds for length") != -1 && e.getMessage().indexOf("Index") != -1){
                JOptionPane.showMessageDialog(null, "Incapaz de realizar la peticion por modificacion incorrecta de firma");                
            }
            else if(e.getMessage().indexOf("No installed provider supports this key: (null)") != -1){
                JOptionPane.showMessageDialog(null, "No hay una clave publica para descifrar el mensaje");                
            }
            else if(e.getMessage().indexOf("Decryption error") != -1){
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, "No se encontro el archivo a descifrar");                
            }
            else{
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
            System.out.println(e.getMessage());
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelar;
    private javax.swing.JButton descifrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane texto;
    // End of variables declaration//GEN-END:variables
}
