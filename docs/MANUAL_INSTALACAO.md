# Manual de Instalação – MultiGestão Hub

Este manual descreve os passos necessários para instalar, configurar e executar o sistema **MultiGestão Hub** em um ambiente de desenvolvimento Windows/Linux/Mac.

---

## 📌 Pré‑requisitos

Antes de começar, certifique‑se de que os seguintes softwares estão instalados no seu computador:

| Software                                | Versão Mínima | Download                                                                                                          |
| --------------------------------------- | ------------- | ----------------------------------------------------------------------------------------------------------------- |
| **Java JDK**                            | 11 (LTS)      | [Oracle](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) / [OpenJDK](https://adoptium.net/) |
| **MySQL Server**                        | 8.0           | [MySQL Community Server](https://dev.mysql.com/downloads/mysql/)                                                  |
| **MySQL Workbench**                     | 8.0           | [MySQL Workbench](https://dev.mysql.com/downloads/workbench/) (opcional, mas recomendado)                         |
| **IntelliJ IDEA** (ou Eclipse/NetBeans) | 2024+         | [IntelliJ IDEA Community](https://www.jetbrains.com/idea/download/)                                               |
| **Maven** (opcional)                    | 3.6+          | [Apache Maven](https://maven.apache.org/download.cgi)                                                             |

---

## 1️⃣ Instalação do Java (JDK 11)

### Windows

1. Faça o download do **JDK 11** (ex: Eclipse Temurin, Oracle).
2. Execute o instalador e siga as instruções padrão.
3. Após instalar, configure a variável de ambiente `JAVA_HOME`:
   - Painel de Controle → Sistema → Configurações avançadas → Variáveis de ambiente.
   - Adicione `JAVA_HOME` apontando para o diretório do JDK (ex: `C:\Program Files\Java\jdk-11`).
   - Adicione `%JAVA_HOME%\bin` ao `PATH`.
4. Verifique no terminal: `java -version`

### Linux (Ubuntu/Debian)

```bash
sudo apt update
sudo apt install openjdk-11-jdk
java -version
```



### MacOS

```bash
brew install openjdk@11
echo 'export PATH="/usr/local/opt/openjdk@11/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc
java -version
```

2️⃣ Instalação do MySQL
-----------------------

### Windows

1. Baixe o **MySQL Installer** do site oficial.

2. Execute e escolha **Developer Default**.

3. Durante a instalação, defina uma senha para o usuário `root`.

4. Anote a senha (será usada na configuração do sistema).

5. Finalizada a instalação, abra o **MySQL Workbench** e teste a conexão.
   
   

### Linux

```bash
sudo apt update
sudo apt install mysql-server
sudo mysql_secure_installation   # Defina a senha do root
sudo systemctl status mysql
```



### MacOS

```bash
brew install mysql
brew services start mysql
mysql_secure_installation
```

3️⃣ Configuração do Banco de Dados
----------------------------------

1. Abra o **MySQL Workbench** (ou terminal MySQL).

2. Conecte-se com o usuário `root` e a senha definida.

3. Execute o script DDL completo fornecido no projeto: <link_aqui>
   
   

4. Verifique se as tabelas foram criadas:
   
   ```sql
   SHOW TABLES;
   ```
   
   

4️⃣ Configuração do Projeto na IDE
----------------------------------

### Importar o projeto no IntelliJ IDEA

1. Abra o IntelliJ.

2. Clique em **File → New → Project from Existing Sources**.

3. Selecione a pasta raiz do projeto (onde está o `pom.xml`).

4. Escolha **Import project from external model → Maven**.

5. Aguarde o download das dependências (JavaFX, mysql-connector, etc.).
   
   

### Estrutura esperada

```textile
projeto/
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/lj1_bd_2sem/
│   │   │       ├── controller/
│   │   │       ├── dao/
│   │   │       ├── model/
│   │   │       ├── dto/
│   │   │       ├── util/
│   │   │       ├── Main.java
│   │   │       └── Launcher.java
│   │   └── resources/
│   │       ├── videos/          (coloque aqui seus GIFs)
│   │       ├── img/             (imagens de botões)
│   │       ├── *.fxml
│   │       └── db.properties
│   └── test/
└── README.md
```

5️⃣ Configuração do Arquivo `db.properties`
-------------------------------------------

Crie o arquivo `db.properties` dentro de `src/main/resources/` (ou na raiz do projeto) com o seguinte conteúdo:

```properties
db.name = ljbd
db.url = jdbc:mysql://localhost:3306/
db.user = o_seu_usuario_aqui
db.password = a_sua_senha_aqui
```



**Atenção:** Substitua `a_sua_senha_aqui` e `o_seu_usuario_aqui` pela senha e usuario que você definiu para o MySQL.

Se o MySQL estiver rodando em outra porta ou servidor, altere a URL:

```properties
db.url = jdbc:mysql://localhost:<sua_porta_aqui>/
```

Atenção:** Substitua `<sua_porta_aqui>` pela porta que está utilizando.

6️⃣ Executando o Sistema
------------------------

### Via IntelliJ IDEA

1. Localize a classe `Launcher.java` (ou `Main.java`).

2. Clique com o botão direito e escolha **Run 'Launcher.main()'**.

3. A tela de login será exibida.

### Via linha de comando (Maven)

1. Abra um terminal na raiz do projeto.

2. Compile e empacote:
   
   ```bash
   mvn clean compile
   mvn javafx:run
   ```

**Nota:** O projeto usa o plugin `javafx-maven-plugin`. Se houver erro de módulos, adicione a VM option: `--module-path "caminho_do_javafx/lib" --add-modules javafx.controls,javafx.fxml,javafx.media`

7️⃣ Testando com Duas Instâncias (Polling)
------------------------------------------

Para simular a sincronização cliente‑profissional:

1. **Execute a primeira instância** (cliente) – faça login com perfil **CLIENTE**.

2. **Execute a segunda instância** – no IntelliJ, vá em **Run → Edit Configurations** e marque **Allow parallel run**. Rode novamente.

3. Na segunda instância, faça login com perfil **PROFISSIONAL** (tipo SALAO).

4. Realize um agendamento na primeira instância (cliente) e altere o status na segunda (profissional) para **EM ANDAMENTO**.

5. O cliente será redirecionado automaticamente (polling a cada 3 segundos).
   
   

8️⃣ Diretórios de Mídia e Recursos
----------------------------------

* **GIFs dos serviços:** Coloque os arquivos `.gif` na pasta `src/main/resources/videos/`.
  
  * Nome do arquivo deve corresponder ao nome do serviço (minúsculas, espaços viram `_`).
  
  * Exemplo: Serviço `"Corte de Cabelo"` → `corte_de_cabelo.gif`

* **Imagens de botões:** Coloque em `src/main/resources/img/`.

Caso não encontre, o sistema exibirá mensagem no label “GIF não encontrado”.

9️⃣ Possíveis Erros e Soluções
------------------------------

| Erro                                                               | Causa                                                    | Solução                                                                                                                                                                                                               |
| ------------------------------------------------------------------ | -------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `ClassNotFoundException: com.mysql.cj.jdbc.Driver`                 | Biblioteca MySQL Connector ausente                       | No Maven, verifique se a dependência está no `pom.xml`. Execute `mvn dependency:resolve`.                                                                                                                             |
| `java.sql.SQLException: Access denied for user 'root'@'localhost'` | Senha incorreta no `db.properties`                       | Verifique o arquivo e a senha do MySQL.                                                                                                                                                                               |
| `javafx.fxml.LoadException: ...`                                   | Arquivo FXML não encontrado                              | Certifique‑se de que os `.fxml` estão em `src/main/resources/` e que o caminho no `FXMLLoader` começa com `/`.                                                                                                        |
| Polling não redireciona cliente                                    | Cliente não está na tela de agendamento                  | O polling global do `NotificationService` funciona em qualquer tela, mas a tela de execução só é aberta se o cliente logar depois. Verifique se o `NotificationService.startPolling(stage)` foi chamado após o login. |
| GIF não aparece                                                    | Arquivo não está em `resources/videos` ou nome incorreto | Use o método `getClass().getResource()` (conforme manual). Prefira colocar os GIFs no classpath.                                                                                                                      |

* * *

🔧 Dependências Maven (`pom.xml`)
---------------------------------

Caso seu `pom.xml` não tenha as dependências, adicione:

xml

<dependencies>
    <!-- MySQL Connector -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.33</version>
    </dependency>

    <!-- JavaFX Controls, FXML, Media -->
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-controls</artifactId>
        <version>11</version>
    </dependency>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-fxml</artifactId>
        <version>11</version>
    </dependency>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-media</artifactId>
        <version>11</version>
    </dependency>

</dependencies>

* * *

🧪 Verificação Final
--------------------

* O MySQL está rodando e o banco `ljbd` foi criado.

* O arquivo `db.properties` está preenchido e no local correto.

* O projeto compila sem erros.

* A tela de login aparece e permite criar novos usuários.

* Um cliente consegue recarregar saldo, agendar serviço e comprar produtos.

* Um profissional consegue alterar status e finalizar pagamentos.

* O polling funciona entre duas instâncias.

* * *

📞 Suporte
----------

Para problemas não listados, consulte o **Manual do Usuário** ou abra uma issue no repositório do projeto.

* * *

> **Última atualização:** Junho de 2026  
> **Versão do sistema:** 1.0
