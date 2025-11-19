$(document).ready(function () {
    if (!localStorage.clienteAutenticado) {
        alert("Acesso negado.");
        window.location.href = "login.html";
    } else {
        var cliente = JSON.parse(localStorage.getItem('clienteAutenticado'));
        var primeiroNome = cliente.nome.substr(0, cliente.nome.indexOf(' '));
        $("#nome").text(primeiroNome);
        atualizarSaldo(cliente.id);
    }

})

async function atualizarSaldo(idCliente) {
    let saldo = 0;

    try {
        let resposta = await fetch("http://18.229.132.2:8888/api/contas/cliente/" + idCliente);
        let contas = await resposta.json();
        contas.forEach(conta => {
            saldo += conta.saldo;
        });

    } catch (erro) {
        console.error("Erro:", erro);
        alert("Erro ao consultar contas.");
    }

    // formata para pt-BR (ex.: 5.150,00) — com símbolo de moeda opcional
    const formatter = new Intl.NumberFormat('pt-BR', {
        minimumFractionDigits: 2,
        maximumFractionDigits: 2
    });
    const texto = 'R$ ' + formatter.format(saldo);
    $("#saldo").text(saldo);

}

