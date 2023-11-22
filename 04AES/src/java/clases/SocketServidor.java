package clases;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.nio.ByteBuffer;
import cifrado.Cifrado;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@ServerEndpoint("/index")
public class SocketServidor{

    private Session sesion;
    private AtomicInteger id = new AtomicInteger(0);
    private ArrayList<SocketServidor> conexiones = new ArrayList<>();
    private static FileOutputStream fileOutputStream = null;
    private static File archivo = null;
    private static String ruta = "";
    private static String accion = "";
    private static String clave = "";
    private static String nombreArchivo = "";

    public Session getSesion() {
        return sesion;
    }

    public void setSesion(Session sesion) {
        this.sesion = sesion;
    }

    public AtomicInteger getId() {
        return id;
    }

    public void setId(AtomicInteger id) {
        this.id = id;
    }

    public ArrayList<SocketServidor> getConexiones() {
        return conexiones;
    }

    public void setConexiones(ArrayList<SocketServidor> conexiones) {
        this.conexiones = conexiones;
    }

    public SocketServidor() {
        id.getAndIncrement();
    }

    @OnOpen
    public void iniciarConexion(Session sesion) {
        this.sesion = sesion;
        conexiones.add(this);
    }

    @OnClose
    public void close(Session sesion) {
        conexiones.remove(this);

    }

    @OnError
    public void onError(Throwable error) throws Throwable {
        System.out.println("Error en el servidor: " + error.toString());
    }
    
    @OnMessage
    public void handleMessage(String mensaje, Session session) {
        if (mensaje.startsWith("file:") && fileOutputStream == null) {
            String fileName = mensaje.substring(6);
            try {
                fileOutputStream = new FileOutputStream(ruta + fileName);
                archivo = new File(ruta + fileName);
                nombreArchivo = fileName;
            } 
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(mensaje.startsWith("ruta:")){
            this.ruta = mensaje.substring(6);
        }
        else if(mensaje.startsWith("accion:")){
            this.accion = mensaje.substring(8);
        }
        else if(mensaje.startsWith("clave:")){
            this.clave = mensaje.substring(7);
        }
        else {
           System.out.println(mensaje);
        }
    }

    @OnMessage
    public void handleMessage(ByteBuffer data, Session session) {
        if (fileOutputStream != null && !accion.equals("") && !ruta.equals("") && archivo != null) {
            byte[] bytes = data.array();
            try {
                boolean operacion = (archivo.isFile())? archivo.delete(): false;
                fileOutputStream.write(bytes);
                if(clave.length() <= 8){
                    try{
                        if(accion.equals("Cifrar")){
                            String cifrado = Cifrado.cifrar(clave, ruta+nombreArchivo);
                            session.getBasicRemote().sendText("result: "+nombreArchivo+".cifrado");
                            Path filePath = Paths.get(cifrado);
                            byte[] fileData = Files.readAllBytes(filePath);
                            session.getBasicRemote().sendBinary(ByteBuffer.wrap(fileData));
                        }
                        else if(accion.equals("Descifrar")){
                            String descifrado = Cifrado.descifrar(clave, ruta+nombreArchivo);
                            System.out.println("s"+clave);
                            session.getBasicRemote().sendText("result: "+nombreArchivo.replace(".cifrado",".descifrado"));
                            Path filePath = Paths.get(descifrado);
                            byte[] fileData = Files.readAllBytes(filePath);
                            session.getBasicRemote().sendBinary(ByteBuffer.wrap(fileData));
                        }
                    }
                    catch(Exception e){
                        System.out.println(e.getMessage());
                        session.getBasicRemote().sendText("keywrong: clave incorrecta");
                    }
                    finally{
                        fileOutputStream = null;
                        archivo = null;
                        ruta = "";
                        accion = "";
                        clave = "";
                        nombreArchivo = "";
                    }
                }
            } 
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void mandarMensajesGlobales(String mensaje) {
        for (SocketServidor conexion : conexiones) {
            try {
                synchronized (conexion) {
                    if (conexion.sesion.isOpen()) {
                        conexion.sesion.getBasicRemote().sendText(mensaje);
                    }
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void mandarMensajePrivado(String mensaje, Session sesion, AtomicInteger id) {
        for (SocketServidor conexion : conexiones) {
            try {
                synchronized (conexion) {
                    if (conexion.sesion.isOpen() && this.id.get() == id.get()) {
                        conexion.sesion.getBasicRemote().sendText(mensaje);
                    }
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
