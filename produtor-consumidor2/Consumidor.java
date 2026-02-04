// Essa classe serve para remover um valor diferente de zero de uma posição por vez

import java.util.Random;

public class Consumidor implements Runnable {
    private final Buffer buffer;
    private final int qtdOperacoes;

    public Consumidor(Buffer buffer, int qtdOperacoes) {
        this.buffer = buffer;
        this.qtdOperacoes = qtdOperacoes;
    }

    public void run() {
        Random gerador = new Random();
        String nome = Thread.currentThread().getName(); // exibir a thread atual

        // loop para a quantidade de operações que vai tentar consumir
        for (int i = 0; i < qtdOperacoes; i++) {
            boolean consumiu = false; // evitar que a thread consuma mais de um valor por vez 

            // percorre todas as posições do buffer
            for (int pos = 0; pos < buffer.getTamanho(); pos++) {
                

                synchronized (buffer.getLock(pos)) { // só uma thread pode mexer na posição específica
                    int valor = buffer.lerPosicao(pos); // lê o valor daquela posição 

                    // verifica se o valor não é zero, se != de 0, pode consumir
                    if (valor != 0) {
                        buffer.escreverPosicao(pos, 0); 
                        System.out.println(nome + " REMOVEU <- " + valor + " da Pos " + pos);
                        consumiu = true; // se já consumiu algo, marca que já consumiu
                    }
                }

                if (consumiu) break; // depois de consumido para de procurar
            }

            // evitar execução previsível e simula concorrência 
            try { Thread.sleep(gerador.nextInt(10)); } catch (Exception e) {}
        }
    }
}