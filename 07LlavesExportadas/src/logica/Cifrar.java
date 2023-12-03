package logica;

import javax.crypto.Cipher;

public class Cifrar {
    private static Cipher rsa;
    
    public Cifrar(){
        
    }
    
    public static String cifrar(String text) throws Exception{      
        String encriptado = "";
        new Llaves().cargarLlaves();
        rsa = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        rsa.init(Cipher.ENCRYPT_MODE, new Llaves().getLlaveprivada());
        
        byte[] cifrado = rsa.doFinal(text.getBytes());
        
        for(byte b : cifrado){
            encriptado += String.format("%02X", b);
        }
        
        return encriptado;
    }
}
