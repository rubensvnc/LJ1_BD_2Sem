# Manual do Usuário – MultiGestão Hub

Sistema integrado de gestão para **Salão de Beleza**, **Mercado** e **Farmácia**.

Este manual orienta os diferentes perfis de usuário (CLIENTE, PROFISSIONAL e ADM) sobre como utilizar o sistema.

---

## 📋 Índice

1. <a href ="#primeiros-passos"> Primerios Passos </a>
2. <a href ="#perfil-cliente"> Perfil Cliente</a>
   - 2.1. Cadastro e Login
   - 2.2. Recarga de Saldo
   - 2.3. Agendamento de Serviços (Salão)
   - 2.4. Execução de Serviço
   - 2.5. Compra em Mercado ou Farmácia
   - 2.6. Pagamento com Saldo
3. [Perfil PROFISSIONAL](#perfil-profissional)
   - 3.1. Tipos de Profissional
   - 3.2. Menu Profissional
   - 3.3. Para Profissionais do Salão
   - 3.4. Para Profissionais de Mercado/Farmácia
4. [Perfil ADM](#perfil-adm)
5. [Funcionalidades Transversais](#funcionalidades-transversais)
   - 5.1. Sincronização em Tempo Real (Polling)
   - 5.2. Controle de Saldo e Pagamentos
6. <a href ="#perguntas-frequentes"> Perguntas Frequentes </a>
   

---

## 1. Primeiros Passos <a id="primeiros-passos"></a>

Ao executar o sistema, a tela de **Login** será exibida.

- Se você já possui cadastro, insira seu **Login** e **Senha** e clique em **Entrar**.
- Se é novo usuário, clique em **Faça cadastro aqui** e preencha os dados (nome, login, senha, perfil e informações adicionais conforme o perfil escolhido).

---

## 2. Perfil CLIENTE <a id="perfil-cliente"></a>

### 2.1. Cadastro e Login

- No cadastro, escolha o perfil **CLIENTE** e informe o saldo inicial (ex: R$ 100,00).
- Após login, você será direcionado para a tela **Home**.

### 2.2. Recarga de Saldo

Na tela **Home**, você verá seu saldo atual e um botão **Recarregar**.

- Clique em **Recarregar**, digite o valor desejado (use ponto ou vírgula) e confirme.
- O saldo será atualizado instantaneamente e poderá ser usado em qualquer estabelecimento.

### 2.3. Agendamento de Serviços (Salão)

1. Na **Home**, clique no botão **Salon**.
2. Preencha:
   - **Serviço** desejado (ex: Corte de Cabelo)
   - **Profissional** de sua preferência
   - **Data** e **Horário** (formato HH:mm:ss)
3. Clique em **Confirmar Horário**.
4. O agendamento aparecerá na tabela **Meus Agendamentos** com status `PENDENTE`.

### 2.4. Execução de Serviço

Quando o profissional alterar o status do seu agendamento para **EM ANDAMENTO**, você será **automaticamente redirecionado** (após no máximo 3 segundos) para a tela de execução, independentemente da tela em que estiver.

Na tela de execução:

- Será exibido um **GIF animado** relacionado ao serviço (se disponível).
- Um **cronômetro** em segundos contará o tempo restante do serviço.
- Uma barra de progresso indicará o andamento.

Ao final do tempo, você será levado para a tela **Aguardando Pagamento**.

- **Se houver saldo suficiente:** o sistema aguarda a confirmação do pagamento pelo profissional.
- **Se o saldo for insuficiente:** aparecerá o botão **"LIMPAR CABELOS DO CHÃO"**. Clique nele para que o sistema ajuste o saldo automaticamente (adicionando o valor faltante e debitando o serviço) e finalize o atendimento.

Após o pagamento, você retornará à **Home**.

### 2.5. Compra em Mercado ou Farmácia

1. Na **Home**, clique em **Market** (Mercado) ou **Pharmacy** (Farmácia).
2. Selecione um produto da lista (clique sobre ele para preencher o código).
3. Informe a **quantidade** desejada e clique em **Adicionar Item**.
   - Na Farmácia, se o medicamento exigir receita, será solicitado o CRM do médico.
4. Repita para outros produtos. O carrinho exibirá os itens e o total.
5. Escolha a **forma de pagamento** (apenas informativa).
6. Clique em **Finalizar Venda**. O sistema verificará seu saldo e, se suficiente, debitará o valor automaticamente.
7. Você pode remover itens do carrinho a qualquer momento.

### 2.6. Pagamento com Saldo

- Seu saldo é usado para pagar serviços e produtos.
- Se o saldo for insuficiente, a compra ou agendamento não será concluído (apenas serviços em execução permitem a opção de "limpar chão").
- Recarregue o saldo sempre que necessário.

---

## 3. Perfil PROFISSIONAL <a id="perfil-profissional"></a>

### 3.1. Tipos de Profissional

Ao cadastrar um profissional, você deve escolher o **tipo**:

- **SALAO** – Atende no Salão de Beleza.
- **MERCADO** – Gerencia produtos e vendas no Mercado.
- **FARMACIA** – Gerencia produtos e vendas na Farmácia.

Cada tipo tem acesso a funcionalidades específicas.

### 3.2. Menu Profissional

Após login, o profissional é direcionado ao **Menu Profissional**, que exibe apenas as opções compatíveis com seu tipo.

### 3.3. Para Profissionais do Salão

**Opções disponíveis:**

- **Cadastrar Serviço** – Define nome, valor e duração (em segundos) de novos serviços. A duração determina o cronômetro na execução.
- **Agenda do Salão** – Visualiza todos os agendamentos. É possível:
  - Alterar o status (PENDENTE → EM ANDAMENTO → CONCLUIDO → CANCELADO).
  - Quando alterar para **EM ANDAMENTO**, o cliente é automaticamente redirecionado (polling).
  - Quando o serviço for concluído pelo cliente, o profissional será redirecionado para a tela de **Pagamento de Atendimento**.
- **Pagamento de Atendimento** – Lista serviços concluídos e não pagos. Permite:
  - Selecionar um atendimento.
  - Ver o saldo do cliente e o valor do serviço.
  - Finalizar o pagamento (debita do saldo e marca como pago).

### 3.4. Para Profissionais de Mercado/Farmácia

**Opções disponíveis:**

- **Cadastrar Produto** – Informa código de barras, nome, preço, quantidade, validade e, no caso da Farmácia, se exige receita.
- **Realizar Vendas** – Funciona como o PDV:
  - Exibe todos os produtos do estoque.
  - Permite adicionar itens ao carrinho (com validação de receita na Farmácia).
  - Finaliza a venda debitando o saldo do cliente (o cliente deve estar logado em outra instância ou já ter saldo cadastrado).

---

## 4. Perfil ADM <a id="perfil-adm"></a>

- O administrador tem acesso ao **Dashboard**.
- No dashboard, é possível:
  - Selecionar o tipo de negócio (Mercado, Farmácia ou Salão).
  - Visualizar um gráfico de barras com o **faturamento total** daquele estabelecimento (baseado nas vendas/serviços concluídos e pagos).
  - Acessar os módulos (opcional – os botões de navegação podem ser implementados).

> **Importante:** O ADM não realiza vendas ou agendamentos, apenas monitora os resultados financeiros.

---

## 5. Funcionalidades Transversais <a id="funcionalidades-transversais"></a>

### 5.1. Sincronização em Tempo Real (Polling)

- O sistema utiliza **polling** (consultas periódicas ao banco de dados a cada 3 segundos) para manter cliente e profissional sincronizados.
- **Exemplo:** Quando o profissional altera o status de um agendamento para "EM ANDAMENTO", o cliente é redirecionado automaticamente para a tela de execução, mesmo que esteja em outra tela (Home, Mercado, etc.).
- O mesmo ocorre para pagamentos: após o profissional finalizar um pagamento, o cliente sai da tela de aguardando pagamento e volta para a Home.

### 5.2. Controle de Saldo e Pagamentos

- Todo cliente possui uma **balança** (saldo) que pode ser recarregada a qualquer momento.
- Ao comprar produtos ou pagar serviços, o valor é **debitado automaticamente** do saldo.
- Caso o saldo seja insuficiente para um serviço já executado, o cliente pode utilizar o botão **"LIMPAR CABELOS DO CHÃO"**, que:
  - Adiciona o valor faltante ao saldo.
  - Debitar o valor total do serviço.
  - Marca o atendimento como pago e concluído.
- Essa funcionalidade evita que o cliente fique com saldo negativo e permite finalizar o atendimento sem intervenção manual do profissional.

---

## 6. Perguntas Frequentes <a id="perguntas-frequentes"></a>

**1. O que fazer se o GIF do serviço não aparecer?**  
Verifique se o arquivo `.gif` está na pasta `videos` dentro dos recursos do sistema e se o nome corresponde ao serviço (minúsculas, espaços substituídos por `_`). O sistema exibirá uma mensagem de erro caso o arquivo não seja encontrado.

**2. Como testar o polling com duas instâncias?**  
Execute o programa duas vezes (no IntelliJ, ative **Allow parallel run**). Na primeira, logue como CLIENTE. Na segunda, logue como PROFISSIONAL SALAO. Faça um agendamento no cliente e depois altere o status no profissional – o cliente será redirecionado em até 3 segundos.

**3. Por que meu saldo não foi atualizado após recarga?**  
Feche e reabra a tela atual ou clique em "Recarregar" novamente. O sistema atualiza o label de saldo automaticamente, mas se houver erro, verifique no banco de dados se a tabela `cliente` foi realmente alterada.

**4. Consigo pagar um serviço com saldo insuficiente usando o botão "LIMPAR CABELOS DO CHÃO"?**  
Sim. Essa opção surge apenas quando o saldo é menor que o valor do serviço. Ela adiciona a diferença ao saldo e debita o valor total, finalizando o atendimento.

**5. O administrador pode fazer vendas ou agendamentos?**  
Não. O perfil ADM tem acesso restrito ao dashboard de faturamento. Para operações comerciais, utilize perfis CLIENTE ou PROFISSIONAL.

**6. Como definir a duração de um serviço?**  
No cadastro de serviços (apenas profissional SALAO), o campo **Duração** deve ser preenchido em **segundos**. Exemplo: 1800 segundos = 30 minutos.

**7. O sistema funciona com vídeos MP4?**  
A versão atual utiliza GIFs animados. Se desejar MP4, é necessário modificar o `ExecutandoServicoController` para usar `MediaPlayer` novamente.

---

> **Em caso de dúvidas não resolvidas**, entre em contato com o suporte técnico ou consulte o **Manual de Instalação** para verificar configurações do ambiente.

--- 

**Documentação atualizada em:** Junho de 2026  
**Versão do sistema:** 1.0
