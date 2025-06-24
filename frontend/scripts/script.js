const apiUrl = 'http://localhost:8080/api/pacientes';
const form = document.getElementById('pacienteForm');
const list = document.getElementById('pacienteList');
const formTitle = document.getElementById('formTitle');
const submitButton = document.getElementById('submitButton');
const limparButton = document.getElementById('limparButton');

let pacienteEditandoId = null;

// Submissão do formulário (Cadastrar ou Atualizar)
form.addEventListener('submit', async (event) => {
    event.preventDefault();

    const formData = new FormData(form);
    const paciente = {};
    formData.forEach((value, key) => paciente[key] = value);

    try {
        let response;

        if (pacienteEditandoId) {
            // Atualizar
            response = await fetch(`${apiUrl}/${pacienteEditandoId}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(paciente)
            });
        } else {
            // Novo cadastro
            response = await fetch(apiUrl, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(paciente)
            });
        }

        if (response.ok) {
            alert("Operação realizada com sucesso!");
            limpar();
            carregarListaPacientes();
        } else {
            const erro = await response.text();
            alert("Erro: " + erro);
        }
    } catch (err) {
        alert("Erro de conexão com a API.");
    }
});

// Carregar lista de pacientes
async function carregarListaPacientes() {
    list.innerHTML = '';
    try {
        const response = await fetch(apiUrl);
        if (!response.ok) throw new Error("Erro ao carregar pacientes");

        const pacientes = await response.json();

        pacientes.forEach(pac => {
            const item = document.createElement('li');
            item.innerHTML = `
                <strong>ID: ${pac.id}</strong> - ${pac.nome} - CPF: ${pac.cpf} - Nascimento: ${pac.dataNascimento}
                <button id="editButton" onclick="editarPaciente(${pac.id})">Editar</button>
                <button id="deleteButton" onclick="deletarPaciente(${pac.id})">Deletar</button>
            `;
            list.appendChild(item);
        });
    } catch (err) {
        console.error(err);
        list.innerHTML = '<li>Lista de pacientes vazia</li>';
    }
}

// Deletar paciente
async function deletarPaciente(id) {
    if (confirm("Deseja realmente deletar este paciente?")) {
        try {
            const response = await fetch(`${apiUrl}/${id}`, {
                method: 'DELETE'
            });

            if (response.ok) {
                alert("Paciente deletado com sucesso!");
                limpar();
                carregarListaPacientes();
            } else {
                const erro = await response.text();
                alert("Erro ao deletar: " + erro);
            }
        } catch (err) {
            alert("Erro de conexão com a API.");
        }
    }
}

// Editar paciente
async function editarPaciente(id) {
    try {
        const response = await fetch(`${apiUrl}/${id}`);
        if (!response.ok) throw new Error("Paciente não encontrado");

        const paciente = await response.json();

        // Preencher os campos do formulário
        form.nome.value = paciente.nome;
        form.dataNascimento.value = paciente.dataNascimento;
        form.cpf.value = paciente.cpf;
        form.telefone.value = paciente.telefone;
        form.genero.value = paciente.genero;
        form.tipoSanguineo.value = paciente.tipoSanguineo;
        form.peso.value = paciente.peso;
        form.altura.value = paciente.altura;
        form.limitacao.value = paciente.limitacao;
        form.historicoMedico.value = paciente.historicoMedico;
        form.alergia.value = paciente.alergia;

        pacienteEditandoId = id;

        // Ajuste visual
        formTitle.textContent = 'Editar Paciente - "' + form.nome.value + '"';
        submitButton.textContent = 'Atualizar';
        submitButton.style.backgroundColor = '#007BFF';
        limparButton.textContent = 'Cancelar edição';
        form.classList.add('editing');
    } catch (err) {
        alert("Erro ao buscar paciente.");
    }
}

// Limpar formulário
function limpar() {
    pacienteEditandoId = null;
    form.reset();
    formTitle.textContent = 'Cadastrar Paciente';
    submitButton.textContent = 'Cadastrar';
    submitButton.style.backgroundColor = '#78D237';
    limparButton.textContent = 'Limpar';
    form.classList.remove('editing');
}

async function buscarPacientePorId() {
    const id = document.getElementById('buscaId').value.trim();
    const resultadoDiv = document.getElementById('resultadoBusca');

    if (!id) {
        alert("Digite um ID válido.");
        return;
    }

    try {
        const response = await fetch(`${apiUrl}/${id}`);

        if (response.ok) {
            const paciente = await response.json();
            resultadoDiv.innerHTML = `
                <h3>Resultado da Busca:</h3>
                <p><strong>Nome:</strong> ${paciente.nome}</p>
                <p><strong>CPF:</strong> ${paciente.cpf}</p>
                <p><strong>Data de Nascimento:</strong> ${paciente.dataNascimento}</p>
                <p><strong>Telefone:</strong> ${paciente.telefone}</p>
                <p><strong>Gênero:</strong> ${paciente.genero}</p>
                <p><strong>Tipo Sanguíneo:</strong> ${paciente.tipoSanguineo}</p>
                <p><strong>Peso:</strong> ${paciente.peso}</p>
                <p><strong>Altura:</strong> ${paciente.altura}</p>
                <p><strong>Limitação:</strong> ${paciente.limitacao}</p>
                <p><strong>Histórico Médico:</strong> ${paciente.historicoMedico}</p>
                <p><strong>Alergia:</strong> ${paciente.alergia}</p>
            `;
        } else if (response.status === 404) {
            resultadoDiv.innerHTML = `<p style="color:red;">Paciente não encontrado.</p>`;
        } else {
            const erro = await response.text();
            resultadoDiv.innerHTML = `<p style="color:red;">Erro: ${erro}</p>`;
        }
    } catch (err) {
        resultadoDiv.innerHTML = `<p style="color:red;">Erro de conexão com a API.</p>`;
    }
}

async function buscarPacientePorNome() {
    const nome = document.getElementById('buscaNome').value.trim().toLowerCase();
    const resultadoDiv = document.getElementById('resultadoBusca');

    if (!nome) {
        alert("Digite um nome para buscar.");
        return;
    }

    try {
        const response = await fetch(apiUrl);

        if (!response.ok) throw new Error("Erro ao buscar pacientes.");

        const pacientes = await response.json();

        const encontrados = pacientes.filter(p =>
            p.nome.toLowerCase().includes(nome)
        );

        if (encontrados.length === 0) {
            resultadoDiv.innerHTML = `<p style="color:red;">Nenhum paciente encontrado com esse nome.</p>`;
            return;
        }

        const resultadoHTML = encontrados.map(p => `
            <div style="margin-bottom:10px;">
                <h3>Paciente Encontrado:</h3>
                <p><strong>ID:</strong> ${p.id}</p>
                <p><strong>Nome:</strong> ${p.nome}</p>
                <p><strong>CPF:</strong> ${p.cpf}</p>
                <p><strong>Data de Nascimento:</strong> ${p.dataNascimento}</p>
                <p><strong>Telefone:</strong> ${p.telefone}</p>
                <p><strong>Gênero:</strong> ${p.genero}</p>
                <p><strong>Tipo Sanguíneo:</strong> ${p.tipoSanguineo}</p>
                <p><strong>Peso:</strong> ${p.peso}</p>
                <p><strong>Altura:</strong> ${p.altura}</p>
                <p><strong>Limitação:</strong> ${p.limitacao}</p>
                <p><strong>Histórico Médico:</strong> ${p.historicoMedico}</p>
                <p><strong>Alergia:</strong> ${p.alergia}</p>
                <hr>
            </div>
        `).join('');

        resultadoDiv.innerHTML = resultadoHTML;
    } catch (err) {
        resultadoDiv.innerHTML = `<p style="color:red;">Erro de conexão com a API.</p>`;
    }
}


carregarListaPacientes();

