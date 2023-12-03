package logica;

import javax.crypto.Cipher;

public class Descifrar {
    private static Cipher rsa;
    
    public Descifrar(){
        
    }
    
    public static String descifrar(String cifrado) throws Exception{
        rsa = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        new Llaves().cargarLlaves();
        rsa.init(Cipher.DECRYPT_MODE, new Llaves().getLlavepublica());
        byte[] bytesdescifrados = rsa.doFinal(hexStringABytes(cifrado));
        String textodescifrado = new String(bytesdescifrados);
        
        return textodescifrado;
    }
    
    private static byte[] hexStringABytes(String hexString) {
        hexString = hexString.replaceAll("\\s", "");
        int longitud = hexString.length();
        byte[] bytes = new byte[longitud / 2];

        for (int i = 0; i < longitud; i += 2) {
            bytes[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }

        return bytes;
    }
}
