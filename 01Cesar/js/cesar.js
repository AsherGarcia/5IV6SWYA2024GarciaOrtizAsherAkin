const desplazamiento = document.getElementById("desplazamiento");
const texto = document.getElementById("texto");
const textoCifrado = document.getElementById("cifrado");

function cifrado() {
    const textoIngresado = texto.value;
    textoCifrado.value = textoIngresado.split('').map(
        (c) => {
            let mayusc = (c === c.toUpperCase()) ? true : false;
            let valorEntero = c.toLowerCase().charCodeAt(0);

            if (valorEntero >= 97 && valorEntero <= 122) {
                let valorDelDesplazamiento = 1;
                if ((desplazamiento.value === desplazamiento.value.toUpperCase()) ? true : false) {
                    valorDelDesplazamiento = (parseInt(desplazamiento.value)) % 26;
                }
                else {
                    valorDelDesplazamiento = (desplazamiento.value.toLowerCase().charCodeAt(0) - 96) % 26;
                }

                if (valorEntero + valorDelDesplazamiento > 122) {
                    valorEntero = 97 + (valorEntero - 122) + valorDelDesplazamiento - 1;
                }
                else {
                    valorEntero += valorDelDesplazamiento;
                }
            }
            else if (valorEntero >= 48 && valorEntero <= 57) {
                let valorDelDesplazamiento = 1;
                if ((desplazamiento.value === desplazamiento.value.toUpperCase()) ? true : false) {
                    valorDelDesplazamiento = (parseInt(desplazamiento.value)) % 10;
                }
                else {
                    valorDelDesplazamiento = (desplazamiento.value.toLowerCase().charCodeAt(0) - 96) % 10;
                }
                if (valorEntero + valorDelDesplazamiento > 57) {
                    valorEntero = 48 + (valorEntero - 57) + valorDelDesplazamiento - 1;
                }
                else {
                    valorEntero += valorDelDesplazamiento;
                }
            }

            let cifrado = String.fromCharCode(valorEntero);

            return mayusc ? cifrado.toUpperCase() : cifrado;
        }
    ).join('');

}

function descifrar() {
    const textoIngresado = texto.value;
    textoCifrado.value = textoIngresado.split('').map(
        (c) => {
            let mayusc = (c === c.toUpperCase()) ? true : false;
            let valorEntero = c.toLowerCase().charCodeAt(0);
            if (valorEntero >= 97 && valorEntero <= 122) {
                let valorDelDesplazamiento = 1;
                if ((desplazamiento.value === desplazamiento.value.toUpperCase()) ? true : false) {
                    valorDelDesplazamiento = (parseInt(desplazamiento.value)) % 26;
                }
                else {
                    valorDelDesplazamiento = (desplazamiento.value.toLowerCase().charCodeAt(0) - 96) % 26;
                }
                if (valorEntero - valorDelDesplazamiento < 97) {
                    valorEntero = 122 - (valorEntero - 97) - valorDelDesplazamiento + 1;
                }
                else {
                    valorEntero -= valorDelDesplazamiento;
                }
            }
            else if (valorEntero >= 48 && valorEntero <= 57) {
                let valorDelDesplazamiento = 1;
                if (desplazamiento.value === desplazamiento.value.toUpperCase()) {
                    valorDelDesplazamiento = parseInt(desplazamiento.value) % 10;
                }
                else {
                    valorDelDesplazamiento = (desplazamiento.value.toLowerCase().charCodeAt(0) - 96) % 10;
                }
                if (valorEntero - valorDelDesplazamiento < 48) {
                    valorEntero = 57 - (47 - valorEntero) - valorDelDesplazamiento;
                }
                else {
                    valorEntero -= valorDelDesplazamiento;
                }
            }

            let descifrado = String.fromCharCode(valorEntero);

            return mayusc ? descifrado.toUpperCase() : descifrado;
        }
    ).join('');
}

function decidir() {
    if (document.getElementById("oculto").value === "cifrar") {
        cifrado();
    }
    else {
        descifrar();
    }
}
texto.addEventListener("keyup", decidir);
desplazamiento.addEventListener("change", decidir);

document.getElementById("cifrar").addEventListener('click', () => {
    document.getElementById("oculto").value = "cifrar";
    cifrado();
});
document.getElementById("descifrar").addEventListener('click', () => {
    document.getElementById("oculto").value = "descifrar";
    descifrar();
});

document.getElementById("regresar").addEventListener('click', ()=>{
    window.location.href="../5IV6SWYA2024GarciaOrtizAsherAkin/../index.html";
});