package logica;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class EscribirArchivo {
    public static void generarArchivo(String contenido) throws Exception{
        String rutaArchivo = "documento.txt";

        File archivo = new File(rutaArchivo);

        try (FileWriter escritor = new FileWriter(archivo)) 
        {
            escritor.write(contenido.replace("\n","\r\n"));
        } 
        catch(FileNotFoundException ed){
            ed.printStackTrace();            
        }
        catch (IOException  e) {
            e.printStackTrace();
        }
    }
    
    public static String leerArchivo() throws Exception{
        String rutaArchivo = "documento.txt";

        File archivo = new File(rutaArchivo);
        String contenido = "";
        
        try (Scanner scanner = new Scanner(archivo)) {
            while (scanner.hasNextLine()) {
                contenido += scanner.nextLine()+"\n";
            }
        } 
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        return contenido;
    }
}
