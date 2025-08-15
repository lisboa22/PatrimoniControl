# Sistema de Controle Patrimonial 

Este é um sistema desktop desenvolvido em Java para gerenciamento de patrimônio, criado como projeto da disciplina de Programação Orientada a Objetos (POO) no Instituto Federal da Bahia (IFBA).

## 📋 Descrição do Projeto

O sistema realiza o controle completo de patrimônio, incluindo cadastro de equipamentos, gestão de usuários, controle de permissões, movimentações de bens e geração de relatórios.

## 🛠️ Tecnologias e Ferramentas

- **Linguagem:** Java (JDK 8+)
- **Interface Gráfica:** Java Swing
- **Banco de Dados:** MySQL
- **Padrões de Projeto:**
  - MVC (Model-View-Controller)
  - DAO (Data Access Object)
  - Factory
- **IDE Recomendada:** NetBeans

## 📁 Estrutura do Projeto

```plaintext
src/
├── aplicacao/
│   ├── frmPrincipal.java          # Tela principal do sistema
│   ├── frmGenerico.java           # Classe base para formulários
│   ├── frmUsuario.java            # Gestão de usuários
│   ├── frmPermissao.java          # Controle de permissões
│   ├── frmEquipamento.java        # Gestão de equipamentos
│   ├── frmUnidade.java            # Gestão de unidades
│   ├── frmFabricante.java         # Gestão de fabricantes
│   ├── frmModulo.java             # Gestão de módulos
│   ├── frmMovimentacao.java       # Registro de movimentações
│   └── frmConfiguracao.java       # Configurações do sistema
│
├── modelo/
│   ├── Equipamento.java           # Entidade de equipamento
│   ├── Usuario.java               # Entidade de usuário
│   ├── Unidade.java               # Entidade de unidade
│   ├── Fabricante.java            # Entidade de fabricante
│   ├── Permissao.java            # Entidade de permissão
│   ├── Permissaomodulo.java      # Entidade de permissão por módulo
│   ├── Modulo.java               # Entidade de módulo
│   ├── Movimentacao.java         # Entidade de movimentação
│   └── Log.java                  # Entidade de log do sistema
│
├── dao/
│   ├── DAOGenerico.java          # Conexão com banco de dados
│   ├── DAOFactory.java           # Fábrica de DAOs
│   ├── UsuarioDAO.java           # Interface DAO de usuário
│   ├── UsuarioDAOJDBC.java       # Implementação JDBC de UsuarioDAO
│   ├── EquipamentoDAO.java       # Interface DAO de equipamento
│   ├── EquipamentoDAOJDBC.java   # Implementação JDBC de EquipamentoDAO
│   └── ... (demais DAOs)
│
└── recurso/
    ├── configuracao/
    │   └── database.properties    # Configurações do banco de dados
    └── imagens/                   # Ícones e imagens do sistema
```

## 🔨 Funcionalidades

### 1. Gestão de Usuários
- Cadastro de usuários
- Controle de permissões
- Alteração de senha
- Níveis de acesso

### 2. Controle Patrimonial
- Cadastro de equipamentos
- Registro de fabricantes
- Cadastro de unidades
- Movimentação de bens
- Histórico de movimentações

### 3. Controle de Acesso
- Módulos de permissões
- Níveis de acesso por funcionalidade
- Log de ações do sistema

### 4. Relatórios e Consultas
- Consulta de bens por unidade
- Histórico de movimentações
- Relatórios gerenciais

## 🚀 Configuração e Instalação

### Pré-requisitos
- Java JDK 8 ou superior
- MySQL
- NetBeans IDE (recomendado)

### Configuração do Banco de Dados
1. Crie um banco de dados MySQL
2. Configure o arquivo `src/recurso/configuracao/database.properties`:
```properties
db.url=jdbc:mysql://localhost:3306/seu_banco
db.user=seu_usuario
db.senha=sua_senha
db.driver=com.mysql.jdbc.Driver
```

### Executando o Projeto
1. Clone o repositório:
```bash
git clone https://github.com/lisboa22/PatrimonioControl.git
```

2. Abra o projeto no NetBeans

3. Configure as dependências do projeto

4. Execute a classe `src/aplicacao/frmPrincipal.java`

## 🗃️ Estrutura do Banco de Dados

### Principais Tabelas
- `usuarios` - Armazena dados dos usuários
- `equipamentos` - Cadastro de bens patrimoniais
- `unidades` - Registro de unidades/departamentos
- `fabricantes` - Cadastro de fabricantes
- `movimentacoes` - Registro de movimentações
- `permissoes` - Controle de permissões
- `modulos` - Módulos do sistema
- `permissao_modulo` - Relacionamento entre permissões e módulos
- `log` - Registro de ações no sistema

## 👥 Padrões de Projeto Utilizados

1. **MVC (Model-View-Controller)**
   - Model: Classes em `modelo/`
   - View: Forms em `aplicacao/`
   - Controller: Lógica de negócios

2. **DAO (Data Access Object)**
   - Interfaces DAO para cada entidade
   - Implementações JDBC
   - DAOFactory para instanciação

3. **Factory**
   - DAOFactory para criar instâncias DAO
   - Isolamento da criação de objetos

## 🔒 Segurança

- Controle de acesso por usuário
- Diferentes níveis de permissão
- Log de todas as operações
- Criptografia de senhas
- Validação de dados

## 📝 Contribuição

1. Faça um fork do projeto
2. Crie uma branch para sua feature
3. Faça commit das alterações
4. Faça push para a branch
5. Abra um Pull Request

## 🎓 Contexto Acadêmico

Projeto desenvolvido para a disciplina de POO no IFBA, aplicando conceitos como:
- Encapsulamento
- Herança
- Polimorfismo
- Interfaces
- Tratamento de Exceções
- Persistência de Dados
- Interface Gráfica

---
