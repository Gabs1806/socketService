# 💬 Sistema de Chat Multi-cliente com Sockets TCP (Java)

Este repositório contém a implementação de um sistema de chat em tempo real baseado na arquitetura Cliente-Servidor. O projeto foi desenvolvido como trabalho acadêmico para demonstrar a aplicação prática de conceitos de redes de computadores, especificamente a comunicação via Sockets TCP.

**Instituição:** Universidade do Estado do Pará (UEPA)  
**Curso:** Engenharia de Software  
**Disciplina:** Fundamentos de Redes  
**Desenvolvedores:** Gabriel Silveira e Oslain Gabriel  

---

## 📌 Sobre o Projeto

O sistema consiste em duas aplicações em Java: um **Servidor** que gerencia as conexões e distribui as mensagens, e um **Cliente** que permite aos usuários enviarem e receberem mensagens em uma sala de chat compartilhada. 

Para suportar múltiplos usuários simultaneamente sem que a aplicação trave, o servidor utiliza **Threads**. Cada novo cliente que se conecta recebe uma thread exclusiva no servidor para gerenciar a sua comunicação.

### ✨ Funcionalidades
* **Multi-cliente:** Suporta vários usuários conectados simultaneamente.
* **Descoberta de IP:** O servidor identifica e exibe automaticamente o seu IP local na rede para facilitar a conexão dos clientes.
* **Broadcast de Mensagens:** Qualquer mensagem enviada por um cliente é retransmitida para todos os outros na sala.
* **Notificações de Sistema:** Alertas automatizados quando um usuário entra ou sai (`/sair`) do chat.
* **Encerramento Automático:** O servidor é encerrado de forma limpa quando o último cliente sai da sala.

---

## 🛠️ Tecnologias e Conceitos Utilizados

* **Linguagem:** Java (JDK 23+)
* **API de Redes (`java.net`):** Uso de `Socket` (lado do cliente) e `ServerSocket` (lado do servidor) para estabelecer a comunicação TCP. `DatagramSocket` é usado de forma auxiliar para descobrir o IP local.
* **API de Entrada/Saída (`java.io`):** Uso de `DataInputStream` e `DataOutputStream` para envio e recebimento dos dados (mensagens de texto) pela rede.
* **Concorrência (Threads):** Implementação da interface `Runnable` para permitir o processamento assíncrono das mensagens de cada cliente.

---

## 🚀 Como Executar

Você pode executar este projeto utilizando a sua IDE de preferência (IntelliJ IDEA, Eclipse, etc.) ou diretamente pelo terminal.

### Pré-requisitos
* Java Development Kit (JDK) instalado na máquina.

### Passo 1: Iniciar o Servidor
O servidor **deve ser iniciado primeiro** para que a porta de comunicação seja aberta.
1. Navegue até o diretório onde os arquivos `.java` estão localizados.
2. Compile e execute a classe do servidor:
   ```bash
   javac ServidorChat.java
   java ServidorChat

3. O terminal exibirá o IP local da máquina e a Porta (padrão: 3389). Anote essas informações.

Passo 2: Iniciar os Clientes
Abra novos terminais para cada cliente que deseja conectar (podem estar na mesma máquina ou em computadores diferentes na mesma rede local).

1. Compile e execute a classe do cliente:

javac ClienteChat.java
java ClienteChat
2. O sistema pedirá as seguintes informações:

IP do servidor: (Digite o IP fornecido pelo terminal do Servidor).

Porta: (Digite 3389).

Nome: (Escolha o seu nickname para o chat).

3. Comece a conversar! Para sair da sala, basta digitar o comando /sair.

📂 Estrutura de Arquivos
ServidorChat.java: Classe responsável por inicializar o servidor na porta 3389, aceitar conexões via ServerSocket e delegar cada conexão para a classe interna ClienteHandler.

ClienteChat.java: Classe que estabelece a conexão Socket com o servidor, possui um loop principal para captura de digitação (envio de mensagens) e uma Thread secundária rodando em paralelo para escutar as mensagens recebidas.

Arquivos .xml e .iml (ignorados pelo Git): Configurações de ambiente geradas pela IDE (IntelliJ IDEA).
