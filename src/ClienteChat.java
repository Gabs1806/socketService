import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClienteChat {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Pede IP e Porta ao usuário
            System.out.print("Digite o IP do servidor: ");
            String ip = scanner.nextLine();

            System.out.print("Digite a porta do servidor: ");
            int porta = scanner.nextInt();
            scanner.nextLine(); // consumir quebra de linha

            Socket conexao = new Socket(ip, porta);
            DataOutputStream saida = new DataOutputStream(conexao.getOutputStream());
            DataInputStream entrada = new DataInputStream(conexao.getInputStream());

            // Nome do usuário
            System.out.print("Digite seu nome: ");
            String nome = scanner.nextLine();
            saida.writeUTF(nome);

            // Thread para receber mensagens
            new Thread(() -> {
                try {
                    while (true) {
                        String resposta = entrada.readUTF();
                        System.out.println(resposta);
                    }
                } catch (IOException e) {
                    System.out.println("❌ Conexão encerrada.");
                }
            }).start();

            // Loop para enviar mensagens
            while (true) {
                String msg = scanner.nextLine();
                saida.writeUTF(msg);
                if (msg.equalsIgnoreCase("/sair")) break;
            }

            conexao.close();
            scanner.close();

        } catch (IOException e) {
            System.out.println("⚠ Não foi possível conectar ao servidor.");
            e.printStackTrace();
        }
    }
}

