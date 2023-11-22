<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script type="text/javascript">
            let conexion = {};
            let nombre = "";
            try{
                conexion.socket = new WebSocket("ws://192.168.20.101:8080/WebSocket/index");
            }
            catch(err){
                conexion.socket = new WebSocket("ws://localhost:8080/WebSocket/index");
            }

            function enviarMensaje(mensaje){
                conexion.socket.send(mensaje);
            }

            conexion.atenderMensaje = function(e){
                
            }

            conexion.socket.onopen = ()=>{
                console.log("Conexion Iniciada");
                conexion.socket.send("Conexion Inciada");
            }

            conexion.socket.onclose = ()=>{

            }

            conexion.socket.onmessage = (event)=>{
                if (event.data instanceof Blob) {
                    const enlaceDescarga = document.getElementById('descarga');
                    enlaceDescarga.href = URL.createObjectURL(event.data);
                    enlaceDescarga.download = nombre;
                    enlaceDescarga.textContent = 'Descargar Archivo';
                    nombre = "";
                } 
                else {
                    if(event.data.startsWith("result: ")){
                        nombre = event.data.replace("result: ","")
                        console.log(nombre)
                    }
                    else if(event.data.startsWith("keywrong: ")){
                        alert(event.data.replace("keywrong: ", ""))
                    }
                }
            }
            
            function sendFileCifrar(){
                let secretKey = document.getElementById("clave");
                let ruta = document.getElementById("ruta");
                conexion.socket.send("accion: Cifrar");
                conexion.socket.send("clave: "+secretKey.value);
                conexion.socket.send("ruta: "+ruta.value);
                
                let fileInput = document.getElementById("fileInput");
                let file = fileInput.files[0];
                
                if (file){
                    conexion.socket.send("file: " + file.name);

                    let reader = new FileReader();
                    reader.onload = event => {
                        let data = event.target.result;
                        conexion.socket.send(data);
                    }

                    reader.readAsArrayBuffer(file);
                }
                secretKey.value = "";
                fileInput.value = "";
            }
            
            function sendFileDescifrar(){                
                let secretKey = document.getElementById("clave");
                let ruta = document.getElementById("ruta");
                conexion.socket.send("accion: Descifrar");
                conexion.socket.send("clave: "+secretKey.value);
                conexion.socket.send("ruta: "+ruta.value);
                
                let fileInput = document.getElementById("fileInput");
                let file = fileInput.files[0];
                
                if (file){
                    conexion.socket.send("file: " + file.name);

                    let reader = new FileReader();
                    reader.onload = event => {
                        let data = event.target.result;
                        conexion.socket.send(data);
                    }

                    reader.readAsArrayBuffer(file);
                }
                secretKey.value = "";
                fileInput.value = "";
            }
            
            function enviarMensaje(){
                let texto = document.getElementById("mensaje").value;
                if(texto !== ""){
                    console.log(texto);
                    conexion.socket.send(texto);
                    document.getElementById("mensaje").value = "";
                }
                else{
                    console.log("Mensaje Vacio");
                }
            }
        </script>
    </head>
    <body>
        <div>
            <input type="text" id="clave" required placeholder="Clave Secreta" maxlength="8">
            <br>
            <input type="file" id="fileInput" required>
            <br>
            <input type="hidden" value="<%=application.getRealPath("/").replace("\\", "/")+"Archivos/"%>" id="ruta">
            <input type="button" id="mensaje" value="Cifrar" onclick="sendFileCifrar()">
            <input type="button" id="mensaje" value="Descfrar" onclick="sendFileDescifrar()">
        </div>
            <div id="liga">
                <a href="" id="descarga" download></a>
            </div>
    </body>
</html>
