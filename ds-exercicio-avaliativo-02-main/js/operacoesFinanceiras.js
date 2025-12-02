const API_URL = "http://localhost:8888/api";
let usuarioLogado = JSON.parse(localStorage.getItem("usuarioLogado"));

// usuario que pode ser usado para testes
if (!usuarioLogado) usuarioLogado = { id: 1, nome: "Teste" };


//eu copiei o codigo abaixo do arquivo menu.js
$(document).ready(function () {
    if (!localStorage.clienteAutenticado) {
        alert("Acesso negado.");
        window.location.href = "login.html";
    } else {
        var cliente = JSON.parse(localStorage.getItem('clienteAutenticado'));
        var primeiroNome = cliente.nome.substr(0, cliente.nome.indexOf(' '));
        $("#nome").text(primeiroNome);
    }

})

async function carregarContasNoSelect() {
    try {
        // 1. Busca as contas do cliente na API
        const resposta = await fetch(`${API_URL}/contas/cliente/${usuarioLogado.id}`);
        const listaContas = await resposta.json();

        // 2. Limpa o select e adiciona a opção padrão
        const select = $("#selectConta");
        select.empty();
        select.append('<option value="" disabled selected>Selecione uma conta</option>');

        // 3. Percorre a lista e cria as opções
        listaContas.forEach(conta => {
            // O value guarda o ID (importante para o envio depois)
            // O texto mostra o Número e o Saldo (para o usuário ver)
            const htmlOpcao = `
                <option value="${conta.id}">
                    ${conta.numero} (Saldo: R$ ${conta.saldo})
                </option>
            `;
            
            select.append(htmlOpcao);
        });

    } catch (erro) {
        console.error("Erro ao buscar contas:", erro);
        alert("Não foi possível carregar as contas.");
    }
}

// Chama a função assim que a tela abre
$(document).ready(function() {
    carregarContasNoSelect();
});

$(document).ready(function() {
    
    // 1. Carrega as contas assim que a página abre
    carregarContas();

    // 2. Intercepta o envio do formulário (Botão Enviar)
    $("#formOperacao").submit(async function(event) {
        // A linha abaixo IMPEDE que a página recarregue (padrão do HTML)
        event.preventDefault(); 
        
        await realizarOperacao();
    });
});

// --- FUNÇÃO PARA CARREGAR AS CONTAS NO SELECT ---
async function carregarContas() {
    try {
        const resposta = await fetch(`${API_URL}/contas/cliente/${usuarioLogado.id}`);
        const listaContas = await resposta.json();

        const select = $("#selectConta");
        select.empty();
        select.append('<option value="" disabled selected>Selecione uma conta</option>');

        listaContas.forEach(conta => {
            // TRUQUE: Salvamos o saldo num atributo invisível 'data-saldo'
            // O value é o ID da conta (necessário para a API)
            const html = `
                <option value="${conta.id}" data-saldo="${conta.saldo}">
                    Conta: ${conta.numero} | Saldo: R$ ${conta.saldo}
                </option>
            `;
            select.append(html);
        });

    } catch (erro) {
        console.error("Erro ao carregar contas:", erro);
        alert("Erro ao buscar contas do usuário.");
    }
}

// --- FUNÇÃO PARA REALIZAR O SAQUE OU DEPÓSITO ---
async function realizarOperacao() {
    
    // 1. Pegar os dados do HTML
    const idContaSelecionada = $("#selectConta").val();
    const tipoOperacao = $("#selectTipo").val(); // SAQUE ou DEPOSITO
    const valorDigitado = $("#campoValor").val();

    // Converte o texto para número decimal (Float)
    const valor = parseFloat(valorDigitado);

    // 2. Validações Básicas
    if (!idContaSelecionada) {
        alert("Por favor, selecione uma conta!");
        return;
    }
    
    if (isNaN(valor) || valor <= 0) {
        alert("Por favor, digite um valor válido maior que zero.");
        return;
    }

    // 3. Validação Específica de SAQUE (Regra de Negócio)
    if (tipoOperacao === "SAQUE") {
        // Recupera aquele saldo que escondemos no 'data-saldo' da opção selecionada
        const saldoAtual = parseFloat($("#selectConta option:selected").data("saldo"));
        
        if (valor > saldoAtual) {
            alert(`Saldo insuficiente! Você tem R$ ${saldoAtual} e tentou sacar R$ ${valor}`);
            return; // Para a função aqui. Não envia nada para o servidor.
        }
    }

    // 4. Montar o Objeto para a API
    // Atenção: A API de lançamentos espera este formato exato
    const dadosOperacao = {
        idConta: parseInt(idContaSelecionada),
        tipo: tipoOperacao,
        valor: valor
    };

    // 5. Enviar para o Servidor (POST)
    try {
        const resposta = await fetch(`${API_URL}/lancamentos`, { // Confirme se a URL é /lancamentos ou /contas
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(dadosOperacao)
        });

        if (resposta.ok) {
            alert("Operação realizada com sucesso!");
            
            // Limpa o campo de valor
            $("#campoValor").val("");
            
            // Recarrega a lista de contas para atualizar o saldo visualmente no select
            carregarContas();
            
        } else {
            alert("Erro ao realizar operação. O servidor recusou.");
        }

    } catch (erro) {
        console.error("Erro na requisição:", erro);
        alert("Falha de comunicação com o servidor.");
    }
}