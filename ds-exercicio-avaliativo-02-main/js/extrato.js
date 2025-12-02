const API_URL = "http://localhost:8888/api";
let usuarioLogado = JSON.parse(sessionStorage.getItem("usuarioLogado"));

// Se não tiver usuário (para testes), cria um fake
if (!usuarioLogado) usuarioLogado = { id: 1, nome: "Teste" };

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