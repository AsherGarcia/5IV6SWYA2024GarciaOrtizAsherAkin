package logica;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class Llaves {
    private static PublicKey llavepublica;
    private static PrivateKey llaveprivada;
    
    public Llaves(){
        
    }
    
    public static void generarLLaves() throws Exception{
        KeyPairGenerator generadorllaves =  KeyPairGenerator.getInstance("RSA");
        KeyPair llavesrsa = generadorllaves.generateKeyPair();
        
        llavepublica = llavesrsa.getPublic();
        llaveprivada = llavesrsa.getPrivate(); 
        saveKey(llavepublica, "public.key"); 
        saveKey(llaveprivada, "private.key");        
    }
    
    public static void saveKey(Key llave, String archivo) throws Exception{
        byte[] llavesPubPriv = llave.getEncoded();
        
        FileOutputStream fos = new FileOutputStream(archivo);
        fos.write(llavesPubPriv);
        fos.close();
    }

    public static PublicKey loadpublickey(String archivo) throws Exception{
        FileInputStream fis = new FileInputStream(archivo);
        int numbytes = fis.available();
        byte[] bytes = new byte[numbytes];
        fis.read(bytes);
        fis.close();
        
        KeyFactory fabricallaves = KeyFactory.getInstance("RSA");
        KeySpec keyspec = new X509EncodedKeySpec(bytes);
        PublicKey llavedelarchivo = fabricallaves.generatePublic(keyspec);
        return llavedelarchivo;
    }

    public static PrivateKey loadprivatekey(String archivo) throws Exception{
        FileInputStream fis = new FileInputStream(archivo);
        int numbytes = fis.available();
        byte[] bytes = new byte[numbytes];
        fis.read(bytes);
        fis.close();
        
        KeyFactory fabricallaves = KeyFactory.getInstance("RSA");
        KeySpec keyspec = new PKCS8EncodedKeySpec(bytes);
        PrivateKey llavedelarchivopriv = fabricallaves.generatePrivate(keyspec);
        return llavedelarchivopriv;
    }
    
    public static void cargarLlaves(){
        try{
            llaveprivada = loadprivatekey("private.key");  
            llavepublica = loadpublickey("public.key");
        }
        catch(Exception e){
            
        }        
    }

    public PublicKey getLlavepublica() {
        return llavepublica;
    }

    public void setLlavepublica(PublicKey llavepublica) {
        this.llavepublica = llavepublica;
    }

    public PrivateKey getLlaveprivada() {
        return llaveprivada;
    }

    public void setLlaveprivada(PrivateKey llaveprivada) {
        this.llaveprivada = llaveprivada;
    }
}
