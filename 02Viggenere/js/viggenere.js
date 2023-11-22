var viggenere = viggenere || (function () {

    var doStaff = function (txt, desp, action) {
        var replace = (function () {
            var abc = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'];
            var l = abc.length;

            return function (c) {
                var i = abc.indexOf(c.toLowerCase());
                if (i != -1) {
                    var pos = i;
                    if (action) {
                        pos += desp;
                        pos = (pos >= l) ? pos - l : pos;
                    } 
                    else {
                        pos -= desp;
                        pos = (pos < 0) ? l + pos : pos;
                    }
                    return abc[pos];
                }
                return c;
            };
        })();

        var re = (/([a-z])/ig);

        return String(txt).replace(re, function (match) {
            return replace(match);
        });
    };
    return {
        encode: function (txt, desp) {
            return doStaff(txt, desp, true);
        },
        decode: function (txt, desp) {
            return doStaff(txt, desp, false);
        }
    };
})();

function longitudCifrar() {
    camposVacios();
    var texto = document.getElementById("txt").value;
    var clave = document.getElementById("txtClave").value;

    if (clave.length > texto.length) {
        alert("La clave no puede ser mas larga que el texto a cifrar");
    } else {
        codificar(texto, clave);
    }
}


function longitudDescifrar() {
    camposVacios();
    var texto = document.getElementById("txt").value;
    var clave = document.getElementById("txtClave").value;

    if (clave.length > texto.length) {
        alert("La clave no puede ser mas larga que el texto a cifrar");
    } else {
        decodificar(texto, clave);
    }
}

function codificar(texto, clave) {

    var resultado = "";
    var indiceClave = 0;
    var charArTexto = texto.split('');

    for (var i = 0; i < charArTexto.length; i++) {

        var des = obindiceClave(clave.charAt(indiceClave));
        var charTexto = charArTexto[i];

        resultado += viggenere.encode(charTexto, (des >= 26) ? des % 26 : des);
        indiceClave++;

        if (indiceClave >= clave.length) {
            indiceClave = 0;
        }

        document.getElementById("res").value = resultado;
    }
}


function decodificar(texto, clave) {

    var resultado = "";
    var indiceClave = 0;
    var charArTexto = texto.split('');

    for (var i = 0; i < charArTexto.length; i++) {

        var des = obindiceClave(clave.charAt(indiceClave));
        var charTexto = charArTexto[i];

        resultado += viggenere.decode(charTexto, (des >= 26) ? des % 26 : des);
        indiceClave++;

        if (indiceClave >= clave.length) {
            indiceClave = 0;
        }

        document.getElementById("res").value = resultado;
    }
}

function obindiceClave(reco) {
    var abc = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'];
    return abc.indexOf(reco.toLowerCase());
}

function camposVacios() {
    var texto = document.getElementById("txt").value;
    var clave = document.getElementById("txtClave").value;

    if (texto == "") {
        alert("El texto a cifrar no puede estar vacio");
    } if (clave == "") {
        alert("La clave no puede estar vacia");
    }
}

function colocar() {
    var copiado = document.getElementById("res").value;
    document.getElementById("txt").value = copiado;
}

function reiniciar() {
    document.getElementById("txt").value = "";
    document.getElementById("txtClave").value = "";
    document.getElementById("res").value = "";
}

document.getElementById("regresar").addEventListener('click', ()=>{
    window.location.href="../5IV6SWYA2024GarciaOrtizAsherAkin/../index.html";
});