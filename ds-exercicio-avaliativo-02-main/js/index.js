$(document).ready(function(){
    if (!localStorage.clienteAutenticado) {
        window.location.href = "login.html";
    }else{
        window.location.href = "menu.html";
    }
    
})

