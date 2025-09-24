function manejarErrorLogin() {
    const urlParams = new URLSearchParams(window.location.search);
    const error = urlParams.get('error');
    const errorDiv = document.getElementById('errorMessage');
    
    if (error === 'invalid') {
        errorDiv.textContent = 'Usuario o contraseña incorrectos';
        errorDiv.style.display = 'block';
        
        // Ocultar mensaje después de 5 segundos
        setTimeout(() => {
            errorDiv.style.display = 'none';
        }, 5000);
    }
}

// Agregar listener cuando el DOM esté cargado
document.addEventListener('DOMContentLoaded', manejarErrorLogin);