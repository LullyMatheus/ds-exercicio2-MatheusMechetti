const API_URL = "http://localhost:8888/api";

// Recupera o usuário logado
let usuarioLogado = JSON.parse(sessionStorage.getItem("usuarioLogado"));
// Fallback para testes
if (!usuarioLogado) usuarioLogado = { id: 1, nome: "Teste da Silva" };

$(document).ready(function() {
    
    // 1. Carrega a lista assim que abre a página
    carregarContas();

    // 2. Configura o botão de criar conta
    $("#btnCriarConta").click(function() {
        criarNovaConta();
    });
});

// --- FUNÇÃO 1: LISTAR CONTAS ---
async function carregarContas() {
    try {
        const resposta = await fetch(`${API_URL}/contas/cliente/${usuarioLogado.id}`);
        const lista = await resposta.json();

        // Limpa a tabela antes de desenhar
        $("#corpoTabelaContas").empty();

        // Para cada conta, cria uma linha (tr) na tabela
        lista.forEach(conta => {
            const linhaHTML = `
                <tr>
                    <td>${conta.numero}</td>
                    <td>R$ ${conta.saldo.toFixed(2)}</td>
                </tr>
            `;
            $("#corpoTabelaContas").append(linhaHTML);
        });

    } catch (erro) {
        console.error("Erro ao listar contas:", erro);
        alert("Erro ao carregar sua lista de contas.");
    }
}

// --- FUNÇÃO 2: CRIAR NOVA CONTA (Lógica Complexa do RF-04) ---
async function criarNovaConta() {
    
    // Passo A: Gerar o número no formato AA-999999
    // Pega as 2 primeiras letras do nome e joga pra maiúsculo
    const iniciais = usuarioLogado.nome.substring(0, 2).toUpperCase();
    
    let numeroUnico = false;
    let numeroGerado = "";

    // Loop para garantir que o número é único (Requisito do professor)
    // Tenta gerar até achar um que não exista
    while (!numeroUnico) {
        const aleatorio = Math.floor(Math.random() * 900000) + 100000;
        numeroGerado = `${iniciais}-${aleatorio}`;

        try {
            // Pergunta pra API se esse número já existe
            const check = await fetch(`${API_URL}/contas/exists?numero=${numeroGerado}`);
            const existe = await check.json(); // Retorna true ou false
            
            if (!existe) {
                numeroUnico = true; // Achamos um número válido! Sai do loop.
            }
        } catch (erro) {
            console.error("Erro ao verificar numero:", erro);
            return; // Para tudo se der erro na verificação
        }
    }

    // Passo B: Montar o objeto da conta
    const novaConta = {
        numero: numeroGerado,
        saldo: 0,
        idCliente: usuarioLogado.id
    };

    // Passo C: Salvar a conta
    try {
        const resposta = await fetch(`${API_URL}/contas`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(novaConta)
        });

        if (resposta.ok) {
            alert(`Conta ${numeroGerado} criada com sucesso!`);
            carregarContas(); // Recarrega a tabela para mostrar a nova conta
        } else {
            alert("Erro ao salvar a conta.");
        }

    } catch (erro) {
        console.error("Erro ao criar conta:", erro);
    }
}