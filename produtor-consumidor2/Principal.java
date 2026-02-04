// Classe onde tudo funciona
import java.util.Scanner;

public class Principal {

    public static void main(String[] args) throws InterruptedException {
        Scanner leitor = new Scanner(System.in);
        
        // ESSE É o recurso compartilhado entre as threads (Buffer)
        Buffer bufferCompartilhado = new Buffer();

        System.out.println("PRODUTOR-CONSUMIDOR");

        int qtdProdutores = lerInteiro(leitor, "Digite o número de Produtores (2 a 5): ");
        int qtdConsumidores = lerInteiro(leitor, "Digite o número de Consumidores (2 a 5): ");

        // criação dos vetores de threads um para produtora e outro para consumidora
        Thread[] threadsProdutoras = new Thread[qtdProdutores];
        Thread[] threadsConsumidoras = new Thread[qtdConsumidores];

        // define quantas operações cada thread vai fazer
        int opsProdutor = 100 / qtdProdutores; 
        int opsConsumidor = 100 / qtdConsumidores; 

        // loop para criar cada produtor
        for (int i = 0; i < qtdProdutores; i++) {
            Produtor tarefa = new Produtor(bufferCompartilhado, opsProdutor); // cria a tarefa do produtor
            threadsProdutoras[i] = new Thread(tarefa, "(Produtor-" + (i+1) + ")"); // cria a thread
            threadsProdutoras[i].start(); // começa a rodar em paralelo
        }

        // loop para criar cada consumidor 
        for (int i = 0; i < qtdConsumidores; i++) {
            Consumidor tarefa = new Consumidor(bufferCompartilhado, opsConsumidor); // cria a tarefa do consumidor
            threadsConsumidoras[i] = new Thread(tarefa, "(Consumidor-" + (i+1) + ")"); // cria a thread
            threadsConsumidoras[i].start(); // começa a rodar em paralelo
        }

        // espera todas as threads terminarem
        for (Thread t : threadsProdutoras) t.join();
        for (Thread t : threadsConsumidoras) t.join();

        System.out.println("\nTRABALHO FINALIZADO");
        leitor.close();
    }

    // método para ler e validar números de entrada do usuário 
    private static int lerInteiro(Scanner leitor, String mensagem) {
        int valor = 0;
        // repete até o usuário digitar correto
        do {
            System.out.print(mensagem);
            // verifica se o que foi digitado é número
            if (leitor.hasNextInt()) {
                valor = leitor.nextInt(); // lê o número
                if (valor < 2 || valor > 5) { // de 2 até 5 para ter briga por recurso
                    System.out.println("Erro: O valor deve ser entre 2 e 5.");
                }
            } else {
                System.out.println("Erro: Digite apenas números inteiros.");
                leitor.next();
            }
        } while (valor < 2 || valor > 5); // sai quando tiver válido
        return valor; // retorna o número 
    }
}