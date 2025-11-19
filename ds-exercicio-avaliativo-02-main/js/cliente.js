$(document).ready(function () {
    if (localStorage.clienteAutenticado) {
        localStorage.removeItem('clienteAutenticado');
    }

    $('#cpf').mask('999.999.999-99');
})


async  function cadastrar() {
    //verifica se o formulário atende as regras de validação do jQuery Validation.
    if ($("#formulario").valid()) {
        
        let cliente = new Object();
        cliente.nome = $("#nome").val();
        cliente.cpf = $("#cpf").val();
        cliente.senha = $("#senha").val();

        try {
            await fetch("http://18.229.132.2:8888/api/clientes", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(cliente)
            });
   
            alert("Cliente cadastrado com sucesso!");
            window.location.href = "login.html";

        } catch (erro) {
            console.error("Erro:", erro);
            alert("Erro ao cadastrar o cliente.");
        }


    }
}

$("#formulario").validate({
    rules: {
        nome: {
            required: true
        },
        cpf: {
            required: true,
            minlength: 14,
            maxlength: 14,
            remote: {
                url: "http://18.229.132.2:8888/api/clientes/exists",
                type: "GET",
                data: {
                    cpf: function () {
                        return $("#cpf").val(); 
                    }
                },
                dataFilter: function (response) {
                    // backend retorna true se CPF já existe → invalida
                    return response === "false"; // CPF ainda não existe → OK para cadastrar
                }
            }
        },
        senha: {
            required: true,
            minlength: 3
        }
    },
    messages: {
        nome: {
            required: "Campo obrigatório"
        },
        cpf: {
            required: "Campo obrigatório",
            minlength: "CPF deve ter 14 caracteres",
            maxlength: "CPF deve ter 14 caracteres",
            remote: "CPF já cadastrado"
        },
        senha: {
            required: "Campo obrigatório",
            minlength: "A senha deve ter no mínimo 3 caracteres"
        }
    }
});
