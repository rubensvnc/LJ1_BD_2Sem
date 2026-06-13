# 🚀 MultiGestão Hub

Sistema integrado de gestão para **Salão de Beleza**, **Mercado** e **Farmácia**, desenvolvido em Java com JavaFX. Permite a operação simultânea de múltiplas instâncias (cliente, profissional, administrador) com sincronização em **tempo real via polling**, controle de saldo do cliente, cadastro de produtos/serviços e fluxo completo de pagamentos.

[📘 Manual do Usuário](./MANUAL_USUARIO.md)  
[🛠️ Manual de Instalação](./MANUAL_INSTALACAO.md)

---

## 📋 Índice

- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Técnicas e Padrões de Projeto](#técnicas-e-padrões-de-projeto)
- [Visão Geral do Sistema](#visão-geral-do-sistema)
- [Funcionalidades Principais](#funcionalidades-principais)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Como Executar](#como-executar)
- [Contribuição](#contribuição)
- [Licença](#licença)

---

## 🧰 Tecnologias Utilizadas

| Tecnologia       | Versão | Finalidade                          |
|-----------------|--------|--------------------------------------|
| Java             | 11+    | Linguagem principal                  |
| JavaFX           | 11+    | Interface gráfica (FXML, Controllers)|
| MySQL            | 8.0+   | Banco de dados relacional            |
| Maven            | 3.6+   | Gerenciamento de dependências        |
| JDBC             | -      | Conexão com o banco de dados         |

---

## 🧠 Técnicas e Padrões de Projeto

- **Programação Orientada a Objetos (OOP)** – Classes bem definidas (`Usuario`, `Cliente`, `Servico`, `Agenda`, `Produto`, `Venda`), encapsulamento, herança (embora não usada diretamente) e polimorfismo.
- **Padrão DAO (Data Access Object)** – Separação da lógica de persistência (ex: `UsuarioDAO`, `AgendaDAO`).
- **Padrão MVC (Model-View-Controller)** – Models (dados), Views (arquivos `.fxml`) e Controllers (lógica de interface).
- **Lambda Expressions** – Usadas em eventos JavaFX (ex: `setOnAction(e -> {...})`) e no `Timeline` do polling.
- **Polling** – Sincronização entre instâncias cliente/profissional via consultas periódicas ao banco de dados (intervalo de 3 segundos).
- **Singleton** – `NotificationService` para gerenciar o polling global do cliente.
- **Observer (implementado via polling)** – Cliente “observa” mudanças no status do agendamento.

---

## 📖 Visão Geral do Sistema

O **MultiGestão Hub** é uma aplicação desktop multi‑instância que atende três tipos de negócio:

1. **Salão de Beleza** – Agendamento de serviços, execução com cronômetro e GIFs/vídeos, pagamento via saldo do cliente.
2. **Mercado** – Venda de produtos, controle de estoque, pagamento com saldo.
3. **Farmácia** – Venda de medicamentos (com validação de receita médica), controle de estoque, pagamento com saldo.

**Perfis de usuário:**

- **CLIENTE** – Recarrega saldo, agenda serviços, compra produtos, acompanha execução de serviços.
- **PROFISSIONAL** – Atende conforme seu tipo (Salão, Mercado ou Farmácia), altera status de atendimentos, finaliza pagamentos.
- **ADM** – Visualiza dashboards de faturamento por unidade.

**Diferencial:** Duas instâncias rodando simultaneamente (cliente e profissional) se comunicam via **polling**, permitindo que o cliente seja redirecionado automaticamente para a tela de execução quando o profissional inicia o serviço.

---

## ✨ Funcionalidades Principais

### Para o Cliente
- Cadastro/login com perfil CLIENTE.
- Recarga de saldo (valores com suporte a vírgula/ponto).
- Agendamento de serviços no Salão (com escolha de profissional e horário).
- Compra de produtos no Mercado e Farmácia (com validação de receita).
- Visualização do saldo em todas as telas.
- Polling automático: ao ter um serviço marcado como "EM ANDAMENTO", é levado à tela de execução.
- Ao fim do serviço, aguarda pagamento (ou usa a opção "LIMPAR CABELOS DO CHÃO" se saldo insuficiente).

### Para o Profissional
- Cadastro/login com perfil PROFISSIONAL e tipo (MERCADO, FARMACIA, SALAO).
- Menu específico por tipo:
  - **Salão**: Cadastrar serviços, gerenciar agenda, realizar pagamento de atendimentos.
  - **Mercado/Farmácia**: Cadastrar produtos, realizar vendas.
- Polling na agenda e na tela de pagamento para detectar novos serviços concluídos.

### Para o Administrador
- Login com perfil ADM.
- Dashboard com gráfico de faturamento por tipo de negócio (dados reais do banco).

---

## ⚙️ Como Executar

### Pré‑requisitos
- Java 11 ou superior
- MySQL 8.0+
- Maven (opcional, mas recomendado)

### Passos
1. **Clone o repositório** (ou copie os arquivos).
2. **Crie o banco de dados** executando o script `DDL.sql` (enviado junto).
3. **Configure a conexão** no arquivo `db.properties` (em `src/main/resources` ou na raiz):
   ```properties
   db.name = ljbd
   db.user = root
   db.url = jdbc:mysql://localhost:3306/
   db.password = sua_senha
