import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.net.*;

public class ServidorChat {
    private static final int PORTA = 3389;
    private static List<ClienteHandler> clientes = new ArrayList<>();

    public static void main(String[] args) {
        try (ServerSocket servidor = new ServerSocket(PORTA)) {
        String ipLocal;
        try (final DatagramSocket socket = new DatagramSocket()) {
          socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
          ipLocal = socket.getLocalAddress().getHostAddress();
      }

        System.out.println("✅ Servidor de chat iniciado.");
        System.out.println("📡 Endereço: " + ipLocal + " | Porta: " + PORTA);


            while (true) {
                Socket conexao = servidor.accept();
                System.out.println("🔗 Novo cliente conectado: " + conexao.getInetAddress().getHostAddress());

                ClienteHandler cliente = new ClienteHandler(conexao);
                synchronized (clientes) {
                    clientes.add(cliente);
                }
                new Thread(cliente).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ClienteHandler implements Runnable {
        private Socket socket;
        private DataInputStream entrada;
        private DataOutputStream saida;
        private String nome;

        public ClienteHandler(Socket socket) {
            this.socket = socket;
            try {
                entrada = new DataInputStream(socket.getInputStream());
                saida = new DataOutputStream(socket.getOutputStream());
                this.nome = entrada.readUTF(); 
                broadcast("🔵 " + nome + " entrou no chat.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                String mensagem;
                while (true) {
                    mensagem = entrada.readUTF();
                    if (mensagem.equalsIgnoreCase("/sair")) break;
                    broadcast("💬 " + nome + ": " + mensagem);
                }
            } catch (IOException e) {
                System.out.println("Cliente " + nome + " desconectado.");
            } finally {
                encerrar();
            }
        }

        private void broadcast(String mensagem) {
            synchronized (clientes) {
                for (ClienteHandler cliente : clientes) {
                    try {
                        cliente.saida.writeUTF(mensagem);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        private void encerrar() {
            try {
                synchronized (clientes) {
                    clientes.remove(this);
                    broadcast("🔴 " + nome + " saiu do chat.");
                    if (clientes.isEmpty()) {
                        System.out.println("Todos os clientes saíram. Encerrando servidor...");
                        System.exit(0);
                    }
                }
                socket.close();
                entrada.close();
                saida.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

