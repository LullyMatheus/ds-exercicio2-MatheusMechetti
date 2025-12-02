const API_URL = "http://localhost:8888/api";



$(document).ready(function() {
    

    let usuarioLogado = JSON.parse(localStorage.getItem("clienteAutenticado"));

    if (!localStorage.clienteAutenticado) {
        alert("Acesso negado.");
        window.location.href = "login.html";
    } else {
        var cliente = JSON.parse(localStorage.getItem('clienteAutenticado'));
        var primeiroNome = cliente.nome.substr(0, cliente.nome.indexOf(' '));
        $("#nome").text(primeiroNome);
    }
    
    // 2. Carrega as contas no Select assim que abre a tela
    carregarContas(usuarioLogado.id);

    // 3. Configura o botão "Gerar"
    $("input[type='submit']").click(function() {
        const idConta = $("#selectConta").val();
        
        if(idConta) {
            carregarExtrato(idConta);
        } else {
            alert("Por favor, selecione uma conta primeiro.");
        }
    });
});

// --- FUNÇÃO 1: CARREGAR CONTAS (Igual às outras páginas) ---
async function carregarContas(id) {
    
    try {
        const resposta = await fetch(`${API_URL}/contas/cliente/${id}`);
        const lista = await resposta.json();

        const select = $("#selectConta");
        select.empty();
        select.append('<option value="" disabled selected>Selecione uma conta</option>');

        lista.forEach(conta => {
            select.append(`<option value="${conta.id}">Conta: ${conta.numero}</option>`);
        });

    } catch (erro) {
        console.error("Erro ao carregar contas:", erro);
        alert("Erro ao buscar as contas.");
    }
}

// --- FUNÇÃO 2: GERAR O EXTRATO ---
async function carregarExtrato(idConta) {
    try {
        // Limpa a tabela e avisa que está buscando
        $("#corpoTabelaContas").html('<tr><td colspan="3">Carregando...</td></tr>');

        // Chama a API de lançamentos
        const resposta = await fetch(`${API_URL}/lancamentos/conta/${idConta}`);
        
        if (!resposta.ok) throw new Error("Erro na API");

        const lancamentos = await resposta.json();

        // Limpa a mensagem de carregando
        $("#corpoTabelaContas").empty();

        if (lancamentos.length === 0) {
            $("#corpoTabelaContas").html('<tr><td colspan="3">Nenhum lançamento encontrado.</td></tr>');
            return;
        }

        // Preenche a tabela
        lancamentos.forEach(item => {
            // Define a cor (Vermelho para Saque, Verde para Depósito)
            const cor = item.tipo === "SAQUE" ? "red" : "green";
            
            // Tratamento da data (caso venha vazia ou precise formatar)
            // Se a API retornar array [2023, 11, 25], o JS mostra vírgulas. 
            // Se retornar string "2023-11-25", aparece normal.
            const dataExibicao = item.data || "Data não inf.";

            const linha = `
                <tr>
                    <td>${dataExibicao}</td>
                    <td style="color: ${cor}; font-weight: bold;">
                        R$ ${item.valor.toFixed(2)}
                    </td>
                    <td>${item.tipo}</td>
                </tr>
            `;
            $("#corpoTabelaContas").append(linha);
        });

    } catch (erro) {
        console.error("Erro ao carregar extrato:", erro);
        $("#corpoTabelaContas").html('<tr><td colspan="3" style="color:red">Erro ao carregar dados.</td></tr>');
    }
}