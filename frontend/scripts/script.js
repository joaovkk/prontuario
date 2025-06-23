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
            form.reset();
            pacienteEditandoId = null;
            formTitle.textContent = 'Cadastrar Paciente';
            submitButton.textContent = 'Salvar';
            form.classList.remove('editing');
            carregarPacientes();
        } else {
            const erro = await response.text();
            alert("Erro: " + erro);
        }
    } catch (err) {
        alert("Erro de conexão com a API.");
    }
});

// Carregar lista de pacientes
async function carregarPacientes() {
    list.innerHTML = '';
    try {
        const response = await fetch(apiUrl);
        if (!response.ok) throw new Error("Erro ao carregar pacientes");

        const pacientes = await response.json();

        pacientes.forEach(pac => {
            const item = document.createElement('li');
            item.innerHTML = `
                <strong>${pac.nome}</strong> - CPF: ${pac.cpf} - Nascimento: ${pac.dataNascimento}
                <button id="editButton" onclick="editarPaciente(${pac.id})">Editar</button>
                <button id="deleteButton" onclick="deletarPaciente(${pac.id})">Deletar</button>
            `;
            list.appendChild(item);
        });
    } catch (err) {
        console.error(err);
        list.innerHTML = '<li>Erro ao carregar pacientes</li>';
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
                carregarPacientes();
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

// Cancelar edição
function limpar() {
    pacienteEditandoId = null;
    form.reset();
    formTitle.textContent = 'Cadastrar Paciente';
    submitButton.textContent = 'Cadastrar';
    submitButton.style.backgroundColor = '#78D237';
    limparButton.textContent = 'Limpar';
    form.classList.remove('editing');
}

carregarPacientes();
