 API - Prontuário de Pacientes 

## 📍 Base URL
```
/api/pacientes
```

---

##  Endpoints

###  1. Listar todos os pacientes

- **Método:** `GET`
- **URL:** `/api/pacientes`
- **Descrição:** Retorna todos os pacientes cadastrados.

####  Respostas:

- **200 OK**
```json
[
  {
    "id": 1,
    "nome": "João Silva",
    "dataNascimento": "01/01/1990",
    "cpf": "12345678901",
    "telefone": "11999999999",
    "genero": "Masculino",
    "tipoSanguineo": "O+",
    "peso": 70.5,
    "altura": 1.75,
    "limitacao": "Nenhuma",
    "historicoMedico": "Sem histórico relevante",
    "alergia": "Nenhuma"
  }
]
```

- **204 No Content**
  Sem corpo na resposta.

---

###  2. Buscar paciente por ID

- **Método:** `GET`
- **URL:** `/api/pacientes/{id}`
- **Parâmetros de caminho:**
    - `id` (Long): ID do paciente

####  Respostas:

- **200 OK**
```json
{
  "id": 1,
  "nome": "João Silva",
  "dataNascimento": "01/01/1990",
  "cpf": "12345678901",
  "telefone": "11999999999",
  "genero": "Masculino",
  "tipoSanguineo": "O+",
  "peso": 70.5,
  "altura": 1.75,
  "limitacao": "Nenhuma",
  "historicoMedico": "Sem histórico relevante",
  "alergia": "Nenhuma"
}
```

- **404 Not Found**
```
Paciente com id 1 não encontrado.
```

---

###  3. Buscar pacientes por nome

- **Método:** `GET`
- **URL:** `/api/pacientes/buscar`
- **Query Parameter:**
    - `nome` (String): Nome (ou parte do nome) para busca

####  Exemplo de requisição:
```
GET /api/pacientes/buscar?nome=Maria
```

####  Respostas:

- **200 OK**
```json
[
  {
    "id": 2,
    "nome": "Maria Silva",
    "dataNascimento": "05/03/1985",
    "cpf": "12345678902",
    "telefone": "11988888888",
    "genero": "Feminino",
    "tipoSanguineo": "A+",
    "peso": 60.0,
    "altura": 1.65,
    "limitacao": "Nenhuma",
    "historicoMedico": "Asma leve",
    "alergia": "Poeira"
  }
]
```

- **404 Not Found**
```
Nenhum paciente encontrado com o nome: Maria
```

---

###  4. Criar paciente

- **Método:** `POST`
- **URL:** `/api/pacientes`
- **Body esperado (JSON):**
```json
{
  "nome": "João Silva",
  "dataNascimento": "01/01/1990",
  "cpf": "12345678901",
  "telefone": "11999999999",
  "genero": "Masculino",
  "tipoSanguineo": "O+",
  "peso": 70.5,
  "altura": 1.75,
  "limitacao": "Nenhuma",
  "historicoMedico": "Sem histórico relevante",
  "alergia": "Nenhuma"
}
```

####  Regras de validação dos campos:

| Campo            | Validação                                                                                          |
|------------------|----------------------------------------------------------------------------------------------------|
| nome             | Obrigatório, apenas letras e espaços, entre 2 e 100 caracteres                                      |
| dataNascimento   | Obrigatório, formato `dd/MM/yyyy`, não pode ser no futuro                                           |
| cpf              | Obrigatório, exatamente 11 dígitos numéricos, CPF único                                             |
| telefone         | Obrigatório, exatamente 11 dígitos numéricos                                                        |
| genero           | Obrigatório, apenas letras e espaços                                                                |
| tipoSanguineo    | Obrigatório, um dos seguintes: `A+`, `A-`, `B+`, `B-`, `AB+`, `AB-`, `O+`, `O-`                    |
| peso             | Obrigatório, entre 2.0 e 300.0 kg                                                                   |
| altura           | Obrigatório, entre 0.30 e 2.50 metros                                                               |
| limitacao        | Obrigatório, máximo 500 caracteres                                                                  |
| historicoMedico  | Obrigatório, máximo 500 caracteres                                                                  |
| alergia          | Obrigatório, máximo 300 caracteres                                                                  |

####  Respostas:

- **201 Created**
```json
{
  "id": 3,
  "nome": "João Silva",
  "dataNascimento": "01/01/1990",
  "cpf": "12345678901",
  "telefone": "11999999999",
  "genero": "Masculino",
  "tipoSanguineo": "O+",
  "peso": 70.5,
  "altura": 1.75,
  "limitacao": "Nenhuma",
  "historicoMedico": "Sem histórico relevante",
  "alergia": "Nenhuma"
}
```

- **400 Bad Request**
  Motivos:
- Qualquer violação nas regras de validação acima
- CPF duplicado

---

###  5. Atualizar paciente

- **Método:** `PUT`
- **URL:** `/api/pacientes/{id}`
- **Parâmetros de caminho:**
    - `id` (Long): ID do paciente a ser atualizado
- **Body esperado:** Mesmo formato do POST (criação)

####  Respostas:

- **200 OK**
```json
{
  "id": 3,
  "nome": "João Silva Atualizado",
  "dataNascimento": "01/01/1990",
  "cpf": "12345678901",
  "telefone": "11999999999",
  "genero": "Masculino",
  "tipoSanguineo": "O+",
  "peso": 75.0,
  "altura": 1.75,
  "limitacao": "Nenhuma",
  "historicoMedico": "Sem histórico relevante",
  "alergia": "Nenhuma"
}
```

- **400 Bad Request**
  Motivos: Mesmas regras de validação do POST.

- **404 Not Found**
```
Paciente com id 3 não encontrado.
```

---

###  6. Deletar paciente

- **Método:** `DELETE`
- **URL:** `/api/pacientes/{id}`
- **Parâmetros de caminho:**
    - `id` (Long): ID do paciente a ser deletado

####  Respostas:

- **204 No Content**
  Sem corpo na resposta.

- **404 Not Found**
```
Paciente com id 3 não encontrado.
```

