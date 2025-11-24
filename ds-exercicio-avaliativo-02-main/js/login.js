$(document).ready(function(){
    if (localStorage.clienteAutenticado) {
        localStorage.removeItem('clienteAutenticado');
    }
    
    $('#cpf').mask('999.999.999-99');
})


async function autenticar() {
    //verifica se o formulário atende as regras de validação do jQuery Validation.
    if ($("#formulario").valid()) {

        let cliente = new Object();
        cliente.cpf = $("#cpf").val();
        cliente.senha = $("#senha").val();

        try {
            let resposta = await fetch("http://54.233.183.126:8888/api/clientes/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(cliente)
            });

            if (!resposta.ok) {
                alert("CPF ou senha inválidos.");
            }else{
                let retorno = await resposta.json();
                localStorage.setItem('clienteAutenticado', JSON.stringify(retorno));
                window.location.href = "menu.html";
            }

        } catch (erro) {
            console.log(erro)
        }    
    }
}

$("#formulario").validate(
    {
        rules: {
            cpf: {
                required: true,
                minlength: 14,
                maxlength: 14
            },
            senha: {
                required: true,
                minlength: 3
            }
        },
        messages: {
            cpf: {
                required: "Campo obrigatório",
                minlength: "CPF deve ter 14 caracteres",
                maxlength: "CPF deve ter 14 caracteres"                
            },
            senha: {
                required: "Campo obrigatório",
                minlength: "A senha deve ter no mínimo 3 caracteres"
            }
        }
    }
);
