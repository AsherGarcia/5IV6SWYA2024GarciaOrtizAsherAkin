package cifrado;

import java.math.BigInteger;
import java.util.Random;

public class Cifrador {
    private static int tamprimo = 100;
    private static BigInteger p, q, n;
    private static BigInteger fi;
    private static BigInteger e, d;
    
    public Cifrador(){
        
    }
    
    public void generarPrimos(){
        p = new BigInteger(tamprimo, 10, new Random());
        do{
            q = new BigInteger(tamprimo, 10, new Random());
        }while(q.compareTo(p) == 0);
    }
    
    public void generarClaves(){
        n = p.multiply(q);
        fi = p.subtract(BigInteger.valueOf(1));
        fi = fi.multiply(q.subtract(BigInteger.valueOf(1)));
        
        do{
            e = new BigInteger(2*tamprimo, new Random());
        }while((e.compareTo(fi) != 1) || (e.gcd(fi).compareTo(BigInteger.valueOf(1)) != 0));
        
        d = e.modInverse(fi);
    }
    
    public BigInteger[] cifrar(String mensaje){
        int i;
        byte[] temp = new byte[1];
        byte[] digitos = mensaje.getBytes();
        
        BigInteger[] bigdigitos = new BigInteger[digitos.length];
        
        for(i = 0; i < bigdigitos.length; i++){
            temp[0] = digitos[i];
            bigdigitos[i] = new BigInteger(temp);
        }
        
        BigInteger[] cifrado = new BigInteger[bigdigitos.length];
        for(i = 0; i < bigdigitos.length; i++){
            cifrado[i] = bigdigitos[i].modPow(e, n);
        }
        
        return cifrado;
    }
    
    public String descifrar(BigInteger[] cifrado){
        int i;
        
        BigInteger[] descifrado = new BigInteger[cifrado.length];
        for(i = 0; i < descifrado.length; i++){
            descifrado[i] = cifrado[i].modPow(d, n);
        }
        
        char[] charArray = new char[descifrado.length];
        
        for(i = 0; i < charArray.length; i++){
            charArray[i] = (char)(descifrado[i].intValue());
        }
        return (new String(charArray));
    }

    public static int getTamprimo() {
        return tamprimo;
    }

    public static void setTamprimo(int tamprimo) {
        Cifrador.tamprimo = tamprimo;
    }

    public static BigInteger getP() {
        return p;
    }

    public static void setP(BigInteger p) {
        Cifrador.p = p;
    }

    public static BigInteger getQ() {
        return q;
    }

    public static void setQ(BigInteger q) {
        Cifrador.q = q;
    }

    public static BigInteger getN() {
        return n;
    }

    public static void setN(BigInteger n) {
        Cifrador.n = n;
    }

    public static BigInteger getFi() {
        return fi;
    }

    public static void setFi(BigInteger fi) {
        Cifrador.fi = fi;
    }

    public static BigInteger getE() {
        return e;
    }

    public static void setE(BigInteger e) {
        Cifrador.e = e;
    }

    public static BigInteger getD() {
        return d;
    }

    public static void setD(BigInteger d) {
        Cifrador.d = d;
    }
}
