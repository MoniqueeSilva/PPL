import java.util.Random;

public class ProdutorConsumidor {

    static int[] vetor = new int[5]; 
    static final Object lock = new Object(); 

    public static void main(String[] args) throws InterruptedException {
        
        Thread produtor = new Thread(new Produtor()); // adiciona números no vetor
        Thread consumidor = new Thread(new Consumidor()); // remove números do vetor

        produtor.start();
        consumidor.start();

        produtor.join();
        consumidor.join();

        System.out.println("\nExecução finalizada.");
    }

    // --- PRODUTOR ---
    static class Produtor implements Runnable {
        public void run() {
            Random gerador = new Random();

            for (int i = 0; i < 100; i++) { // tenta inserir 100 valores
                int valorAleatorio = gerador.nextInt(100) + 1; // gera valor aleatório entre 1 e 100
                boolean inseriu = false;

                synchronized (lock) {
                    //procurando a primeira posição vaga (== 0). Quando acha, coloca o número ali.
                    for (int pos = 0; pos < vetor.length; pos++) {
                        if (vetor[pos] == 0) {
                            vetor[pos] = valorAleatorio;
                            System.out.println("Produtor [" + i + "]: Inseriu " + valorAleatorio + " na posição " + pos);
                            inseriu = true;
                            break; 
                        }
                    }

                    if (!inseriu) {
                        System.out.println("Produtor [" + i + "]: Descartou " + valorAleatorio + " (Vetor CHEIO)");
                    }
                }
                try {
                    Thread.sleep(gerador.nextInt(10)); // sllep aleatório para evitar efeito robô
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // --- CONSUMIDOR ---
    static class Consumidor implements Runnable {
        public void run() {
            Random gerador = new Random();

            for (int i = 0; i < 100; i++) {
                
                boolean consumiu = false;

                synchronized (lock) {
                    for (int pos = 0; pos < vetor.length; pos++) {
                        if (vetor[pos] != 0) {
                            int valorRemovido = vetor[pos];
                            vetor[pos] = 0; 
                            System.out.println("Consumidor [" + i + "]: Removeu " + valorRemovido + " da posição " + pos);
                            consumiu = true;
                            break; 
                        }
                    }
                    if (!consumiu) {
                    }
                }

                try {
                    Thread.sleep(gerador.nextInt(10)); 
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}