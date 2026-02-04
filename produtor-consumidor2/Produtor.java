// Essa classe serve para inserir valores aleatórios no vetor

import java.util.Random;

public class Produtor implements Runnable {
    private final Buffer buffer;
    private final int qtdOperacoes;

    public Produtor(Buffer buffer, int qtdOperacoes) {
        this.buffer = buffer;
        this.qtdOperacoes = qtdOperacoes;
    }

    public void run() {
        Random gerador = new Random();
        String nome = Thread.currentThread().getName(); // exibir a thread atual

        // loop para a quantidade de operações que vai tentar produzir
        for (int i = 0; i < qtdOperacoes; i++) {
            int valorAleatorio = gerador.nextInt(100) + 1; // gerar números aleatório de 1 a 100
            boolean inseriu = false; // indica que o valor já foi inserido em alguma posição

            // percorre o vetor procurando posições vazias 
            for (int pos = 0; pos < buffer.getTamanho(); pos++) {
                
                // trava apenas uma posição 
                synchronized (buffer.getLock(pos)) {
                    if (buffer.lerPosicao(pos) == 0) { // se a posição for 0 insere valor
                        buffer.escreverPosicao(pos, valorAleatorio); // coloca um valor aleatório nessa posição
                        System.out.println(nome + " INSERIU -> " + valorAleatorio + " na Pos " + pos);
                        inseriu = true; // marca que já inseriu 
                    }
                }
                
                if (inseriu) break; // se já inseriu, para o loop
            }

            // se percorreu todo o vetor e encontrou ele cheio, descarta o valor que seria inserido
            if (!inseriu) {
                System.out.println(nome + " ( X ) Falha: VETOR CHEIO - DESCARTOU " + valorAleatorio);
            }

            // simula concorrência e bagunça a ordem de execução
            try { Thread.sleep(gerador.nextInt(10)); } catch (Exception e) {}
        }
    }
}