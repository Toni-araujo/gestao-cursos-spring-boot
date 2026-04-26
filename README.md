# 🎓 Sistema de Gestão de Cursos

Projeto de desenvolvimento Web full-stack construído para a 2ª Avaliação da disciplina de Programação III do IFPB. O sistema permite a gestão de cursos, com áreas públicas para visitantes e áreas restritas para administradores, protegido por segurança de rotas e perfis de acesso.

## 🚀 Tecnologias Utilizadas
- **Linguagem:** Java 21
- **Framework:** Spring Boot 3
- **Módulos Spring:** Spring MVC, Spring Data JPA, Spring Security, Spring Validation
- **Front-end:** HTML5, CSS3 animado, Thymeleaf, Bootstrap 5 e Font Awesome
- **Banco de Dados:** MySQL (via Docker)
- **Migração de Banco:** Hibernate (DDL Auto)

## ✨ Funcionalidades Extras (Além dos Requisitos Básicos)
Visando enriquecer a experiência do usuário e demonstrar domínio sobre relacionamentos de entidades e UI/UX, foram adicionadas as seguintes *features*:
- **Sistema de Favoritos (Many-to-Many):** Alunos autenticados podem salvar/remover cursos de sua lista pessoal.
- **Sistema de Comentários (One-to-Many):** Área de avaliação e feedback na página de detalhes de cada curso exclusivo para usuários logados.
- **UI/UX Premium:** Interface animada com efeitos em cascata, hover responsivo, badges dinâmicas e design adaptativo.
- **Visualização de Preços Dinâmica:** Aplicação automática de desconto visual de 10% exclusivo para alunos autenticados.
- **Página 403 Customizada:** Tratamento de erros de acesso negado amigável.

## 🔐 Credenciais de Teste
Para avaliar o sistema, utilize as seguintes credenciais pré-cadastradas no banco de dados:

**🛡️ Administrador (Acesso Total: Adicionar, Editar, Excluir)**
- **Usuário:** `admin`
- **Senha:** `54321`

**👤 Aluno / Visitante Logado (Acesso Restrito: Favoritar e Comentar)**
- **Usuário:** `meuusuario`
- **Senha:** `12345`

## ⚙️ Como Executar o Projeto
1. Certifique-se de ter o Docker rodando em sua máquina e execute o `compose.yaml` para subir o contêiner do MySQL.
2. Inicie a aplicação via IntelliJ executando a classe principal.
3. O Hibernate se encarregará de criar as tabelas automáticas (`tbl_user`, `tbl_role`, `course`, `tbl_comment`, `favorite`).
4. Acesse a aplicação no navegador em: `http://localhost:8080/`

---
*Desenvolvido por ToniJosué Barbosa de Araújo* ☕