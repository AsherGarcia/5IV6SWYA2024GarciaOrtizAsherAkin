package cifrado;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Cipher;

public class Cifrado {
    public Cifrado(){
        
    }
    
    public static String cifrar(String claveSecreta, String ruta) throws Exception{
        if(claveSecreta.length() < 8){
            while(claveSecreta.length() < 8){
                claveSecreta += " ";
            }
        }
        else if(claveSecreta.length() > 8){
            claveSecreta = ""+claveSecreta.charAt(0)+claveSecreta.charAt(1)+claveSecreta.charAt(2)+claveSecreta.charAt(3)+claveSecreta.charAt(4)+claveSecreta.charAt(5)+claveSecreta.charAt(6)+claveSecreta.charAt(7);
        }
        
        byte[] llave1 = claveSecreta.getBytes();
        SecretKeySpec clave = new SecretKeySpec(llave1, 0, llave1.length, "DES");
        Cipher cifrado = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cifrado.init(Cipher.ENCRYPT_MODE, clave);
        byte[] buffer = new byte[1000];
        byte[] bufferCifrado;
        FileInputStream in = new FileInputStream(ruta);
        FileOutputStream out = new FileOutputStream(ruta+".cifrado");
        
        int bytesleidos = in.read(buffer, 0, 1000);
        while(bytesleidos != -1){
            bufferCifrado = cifrado.update(buffer, 0, bytesleidos);
            out.write(bufferCifrado);
            bytesleidos = in.read(buffer, 0, 1000);
        }
        bufferCifrado = cifrado.doFinal();
        out.write(bufferCifrado);
        
        in.close();
        out.close();
        
        return ruta+".cifrado";
    }
    
    public static String descifrar(String claveSecreta, String ruta) throws Exception{
        if(claveSecreta.length() < 8){
            while(claveSecreta.length() < 8){
                claveSecreta += " ";
            }
        }
        else if(claveSecreta.length() > 8){
            claveSecreta = ""+claveSecreta.charAt(0)+claveSecreta.charAt(1)+claveSecreta.charAt(2)+claveSecreta.charAt(3)+claveSecreta.charAt(4)+claveSecreta.charAt(5)+claveSecreta.charAt(6)+claveSecreta.charAt(7);
        }
        
        byte[] llave1 = claveSecreta.getBytes();
        
        SecretKeySpec clave = new SecretKeySpec(llave1, 0, llave1.length, "DES");
        Cipher cifrado = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cifrado.init(Cipher.DECRYPT_MODE, clave);
        
        byte[] bufferPlano;
        byte[] buffer = new byte[1000];
        
        FileInputStream in = new FileInputStream(ruta);
        FileOutputStream out = new FileOutputStream(ruta.replace(".cifrado",".descifrado"));
        int bytesleidos = in.read(buffer, 0, 1000);
        
        while(bytesleidos != -1){
            bufferPlano = cifrado.update(buffer, 0, bytesleidos);
            out.write(bufferPlano);
            bytesleidos = in.read(buffer, 0, 1000);
        }
        
        bufferPlano = cifrado.doFinal();
        
        out.write(bufferPlano);
        
        in.close();
        out.close();
        
        return ruta.replace(".cifrado",".descifrado");
    }
}
