/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

// Variables del juego
var palabra = '';
var letras = [];
var vidas = 7;
var tiempoRestante = 230;
var estaJugando = false;
var temporizador;
var estaPausado = false;
var mensaje = "ESTAS LISTRO PARA ADIVINAR LA PARABRA?";
var pistaActual = "";

// aqui se agregan las imagenes para el ahorcado 
const imagenes = [
    'Images/hangman0.jpg', 
    'Images/hangman1.jpg', 
    'Images/hangman2.jpg', 
    'Images/hangman3.jpg', 
    'Images/hangman4.jpg', 
    'Images/hangman5.jpg', 
    'Images/hangman6.jpg', 
    'Images/hangman7.jpg'  
];

// con esta funcion que hacemos la peticion al servidor para obtener la palabra y la pista
function obtenerPalabra() {
    // Hacer petición al servidor
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "Controlador?menu=Palabras&accion=ObtenerPalabra", true);
    
    xhr.onreadystatechange = function() {
        if(xhr.readyState == 4 && xhr.status == 200) {
            var respuesta = JSON.parse(xhr.responseText);
            palabra = respuesta.palabra;
            pistaActual = respuesta.pista;
            // Poner guiones
            letras = [];
            for(var i = 0; i < palabra.length; i++) {
                letras[i] = "_";
            }
            ponerPalabra();
            document.querySelector('.hint-text').textContent = pistaActual;
            // Iniciar juego
            estaJugando = true;
            estaPausado = false;
            empezarTiempo();
            // Activar teclado
            var botonesTeclado = document.querySelectorAll('.key');
            for(var i = 0; i < botonesTeclado.length; i++) {
                botonesTeclado[i].disabled = false;
            }
        }
    };
    
    xhr.send();
}

// Empieza el juego
function empezarJuego() {
    // Reiniciar todo
    vidas = 7;
    tiempoRestante = 230;
    estaJugando = false;
    estaPausado = true;
    clearInterval(temporizador);
    
    // Mostrar mensaje inicial
    document.querySelector('.hint-text').textContent = mensaje;
    document.querySelector('.hangman-display').textContent = "Vidas: 7";
    
    // Limpiar palabra
    palabra = '';
    letras = [];
    ponerPalabra();
    
    // Apagar teclado
    var botonesTeclado = document.querySelectorAll('.key');
    for(var i = 0; i < botonesTeclado.length; i++) {
        botonesTeclado[i].disabled = true;
    }
    // Poner reloj a 0
    actualizarTiempo();

    // Mostrar imagen inicial
    actualizarImagenAhorcado(0);
}

// aqui se pone la funcion para actualizar el tiempo
function actualizarTiempo() {
    var mins = Math.floor(tiempoRestante / 60);
    var segs = tiempoRestante % 60;
    
    if(mins < 10) mins = "0" + mins;
    if(segs < 10) segs = "0" + segs;
    
    document.querySelector('.time').textContent = mins + ":" + segs;
}

// Hacer funcionar el reloj
function empezarTiempo() {
    clearInterval(temporizador);
    temporizador = setInterval(function() {
        if(!estaPausado) {
            tiempoRestante--;
            actualizarTiempo();
            
            if(tiempoRestante <= 0) {
                terminar(false);
            }
        }
    }, 1000);
}

// Revisar si la letra está
function buscarLetra(letra) {
    if(!estaJugando || estaPausado) return;
    var encontro = false;
    // Buscar la letra en la palabra
    for(var i = 0; i < palabra.length; i++) {
        if(palabra[i] == letra) {
            letras[i] = letra;
            encontro = true;
        }
    }
    
    // Si no encontró, quitar vida y actualizar imagen
    if(!encontro) {
        vidas = vidas - 1;
        actualizarImagenAhorcado(7 - vidas); // Actualizar imagen según errores
    }
    
    // Actualizar palabra en pantalla
    ponerPalabra();
    
    // Ver si ganó o perdió
    revisarSiGano();
}

// Poner la palabra en pantalla
function ponerPalabra() {
    var dondeVaLaPalabra = document.querySelector('.word-display');
    dondeVaLaPalabra.innerHTML = '';
    
    for(var i = 0; i < letras.length; i++) {
        var cajita = document.createElement('div');
        cajita.className = 'letter-slot';
        cajita.textContent = letras[i];
        dondeVaLaPalabra.appendChild(cajita);
    }
}

// Ver si ganó o perdió
function revisarSiGano() {
    var gano = true;
    
    // Ver si completó la palabra
    for(var i = 0; i < palabra.length; i++) {
        if(letras[i] != palabra[i]) {
            gano = false;
            break;
        }
    }
    
    if(gano) {
        terminar(true);
        return;
    }
    
    // Ver si perdió
    if(vidas <= 0) {
        terminar(false);
    }
}

// Función para actualizar la imagen del ahorcado
function actualizarImagenAhorcado(indice) {
    const hangmanDisplay = document.querySelector('.hangman-display');
    hangmanDisplay.innerHTML = `<img src="${imagenes[indice]}" alt="Ahorcado estado ${indice}">`;
}

// Terminar el juego
function terminar(gano) {
    estaJugando = false;
    estaPausado = true;
    clearInterval(temporizador);
    
    // Apagar teclado
    var botonesTeclado = document.querySelectorAll('.key');
    for(var i = 0; i < botonesTeclado.length; i++) {
        botonesTeclado[i].disabled = true;
    }
    
    // Decir si ganó o perdió
    if(gano) {
        alert('¡Muy bien! ¡Ganaste!');
    } else {
        alert('¡Oh no! Perdiste... La palabra era: ' + palabra);
    }
    
    // Poner mensaje inicial
    setTimeout(function() {
        document.querySelector('.hint-text').textContent = mensaje;
        actualizarImagenAhorcado(0); // Volver a la imagen principal
    }, 1000);
}

// Pausar el juego
function pausar() {
    if(estaJugando) {
        estaPausado = !estaPausado;
        var botonPausar = document.querySelector('.game-button');
        
        if(estaPausado) {
            // Pausar juego
            botonPausar.textContent = "CONTINUAR";
            var botonesTeclado = document.querySelectorAll('.key');
            for(var i = 0; i < botonesTeclado.length; i++) {
                botonesTeclado[i].disabled = true;
            }
        } else {
            // Continuar juego
            botonPausar.textContent = "PAUSAR";
            var botonesTeclado = document.querySelectorAll('.key');
            for(var i = 0; i < botonesTeclado.length; i++) {
                botonesTeclado[i].disabled = false;
            }
        }
    }
}

// Volver a empezar
function reiniciar() {
    empezarJuego();
    document.querySelector('.hint-text').textContent = mensaje;
}

// Salir del juego
function salir() {
    var quiereSalir = confirm("¿Seguro que quieres salir?");
    if(quiereSalir) {
        window.location.href = "Controlador?menu=Index&accion=Salir";
    }
}

// Cuando carga la página
window.onload = function() {
    // Configurar mensaje inicial
    document.querySelector('.hint-text').textContent = mensaje;
    
    document.getElementById('btnComenzar').addEventListener('click', function() {
        obtenerPalabra();
    });

    document.getElementById('btnPausar').addEventListener('click', function() {
        pausar();
    });

    document.getElementById('btnReiniciar').addEventListener('click', function() {
        reiniciar();
    });

    document.getElementById('btnSalir').addEventListener('click', function() {
        salir();
    });

    // Hacer que funcionen las letras
    var botonesTeclado = document.querySelectorAll('.key');
    for(var i = 0; i < botonesTeclado.length; i++) {
        botonesTeclado[i].addEventListener('click', function() {
            if(!estaPausado && estaJugando) {
                buscarLetra(this.textContent);
                this.disabled = true;
            }
        });
    }
    
    // Inicializar juego
    empezarJuego();
};

// Modificar la función obtenerPalabra para manejar errores
function obtenerPalabra() {
    fetch('Controlador?menu=Palabras&accion=ObtenerRandom')
        .then(response => response.json())
        .then(data => {
            palabra = data.palabra;
            pistaActual = data.pista;
            
            // Inicializar letras
            letras = Array(palabra.length).fill('_');
            
            ponerPalabra();
            document.querySelector('.hint-text').textContent = pistaActual;
            
            // Iniciar juego
            estaJugando = true;
            estaPausado = false;
            vidas = 7;
            tiempoRestante = 230;
            empezarTiempo();
            
            // Activar teclado
            document.querySelectorAll('.key').forEach(btn => {
                btn.disabled = false;
            });
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Error al obtener la palabra. Por favor, intente de nuevo.');
        });
}

