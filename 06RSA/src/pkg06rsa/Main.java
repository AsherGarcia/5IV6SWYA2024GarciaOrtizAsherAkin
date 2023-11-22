/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pkg06rsa;

/**
 *
 * @author Alumno
 */

import java.io.InputStream;
import java.security.*;
import javax.crypto.*;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

/*
Esta libreria sirve para poder realizar el calculo de
primos mas grandes pero a costo de que el bloque
debe de ser constante 
BC es un provider el cual maneja CBC para el calculo de
los bloques 
*/


public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        // TODO code application logic here
        //tenemos que añadir el nuevo proveedor
        Security.addProvider(new BouncyCastleProvider());
        
        System.out.println("1.- Vamos a crear las llaves "
                + "publica y privada");
        
        
        //tenemos que inicializar la clase KeyPairGenerator
        KeyPairGenerator generadorclaves = 
                KeyPairGenerator.getInstance(
                        "RSA", "BC");
        
        //tenemos que definir el tamaño de la llave
        generadorclaves.initialize(4096);
        
        //creamos las llaves publica y privada
        KeyPair clavesRSA = generadorclaves.generateKeyPair();
        //privada
        PrivateKey clavePrivada = clavesRSA.getPrivate();
        //publica
        PublicKey clavePublica = clavesRSA.getPublic();
        
        System.out.println("2.- Escribe el texto que quieres cifrar");
        
        byte[] bufferplano = leerLinea(System.in);
        
        /*
        BC no funciona en modo ECB no divide el mensaje en bloques
        nosotros tenemos que realizar esa operacion 
        y solo puede cifrar maximo 64 caracteres el bloque 
        es constante
        */
        
        //vamos a cifrar
        
        Cipher cifrador = Cipher.getInstance(
                "RSA", "BC");
        
        //cifrar con publica
        
        cifrador.init(
                Cipher.ENCRYPT_MODE, clavePublica);
        
        //tenemos que leer el buffer
        System.out.println("Ciframos con publica");
        byte[] bufferCifrado = cifrador.doFinal(
                bufferplano);
        
        System.out.println("Texto cifrado");
        //crear un metodo para leer el bloque cifrado
        mostrarBytes(bufferCifrado);
        System.out.println("\n*****************");
        
        //desciframos con privada
        cifrador.init(
                Cipher.DECRYPT_MODE, clavePrivada);
        
        byte[] bufferDescifrado = cifrador.doFinal(
                bufferCifrado);
        System.out.println("Texto Descifrado");
        mostrarBytes(bufferDescifrado);
        System.out.println("\n*****************");
        
        
        //invertimos
        //cifrar con privada
        
        cifrador.init(
                Cipher.ENCRYPT_MODE, clavePrivada);
        
        //tenemos que leer el buffer
        System.out.println("Ciframos con privada");
        bufferCifrado = cifrador.doFinal(
                bufferplano);
        
        System.out.println("Texto cifrado");
        //crear un metodo para leer el bloque cifrado
        mostrarBytes(bufferCifrado);
        System.out.println("\n*****************");
        
        //desciframos con publica
        cifrador.init(
                Cipher.DECRYPT_MODE, clavePublica);
        
        bufferDescifrado = cifrador.doFinal(
                bufferCifrado);
        System.out.println("Texto Descifrado");
        mostrarBytes(bufferDescifrado);
        System.out.println("\n*****************");
        
        
        
    }

    public static byte[] leerLinea(InputStream in) throws Exception{
        //un buffer
        byte[] buffer1 = new byte[1000];
        
        int i = 0;
        
        byte c;
        
        c = (byte)in.read();
        
        while((c!= '\n') && (i < 1000)){
            buffer1[i] = c;
            c = (byte)in.read();
            i++;
        }
        
        //asignar
        byte[] buffer2 = new byte[i];
        for(int j = 0; j < i; j++){
            buffer2[j] = buffer1[j];
        }
        return (buffer2);
    }

    public static void mostrarBytes(byte[] buffer) {
        System.out.write(buffer, 0, buffer.length);
    }
    
}
