public class Threads {
    static class FuncaoHelper extends Thread {
        private final int id;

        // CONSTRUTOR
        public FuncaoHelper(int id) {
            this.id = id;
            System.out.println("THREAD " + id + " CRIADA"); // <-- print ao criar
        }
        
        public void run() { // ONDE A THREAD EXECUTA SUA TAREFA
            System.out.println("THREAD " + id + " INICIOU"); // <-- início da execução
            try {
                if (id == 0) {
                    for (int i = 0; i <= 3; i++) {
                        System.out.println("THREAD 0 -> NÚMERO: " + i);
                        Thread.sleep(1000);
                    }
                } else if (id == 1) {
                    for (char letra = 'A'; letra <= 'C'; letra++) {
                        System.out.println("THREAD 1 -> LETRA: " + letra);
                        Thread.sleep(1000);
                    }
                } else {
                    System.out.println("THREAD " + id + " EM ATIVIDADE MISTERIOSA...");
                    Thread.sleep(1500);
                }
            } catch (InterruptedException e) {
                System.out.println("ERRO! THREAD " + id + " INTERROMPIDA!");
            }
            System.out.println("THREAD " + id + " TERMINOU"); // <-- print ao finalizar
        }
    }

    public static void main(String[] args) {
        int NUM_THREADS = 4;
        FuncaoHelper[] threads = new FuncaoHelper[NUM_THREADS];

        System.out.println("~ INICIANDO EXECUÇÃO DAS THREADS ~");

        for (int i = 0; i < NUM_THREADS; i++) {
            threads[i] = new FuncaoHelper(i); // CRIAÇÃO
            threads[i].start(); // INICIA EM PARALELO
        }

        System.out.println("~ TODAS AS THREADS FORAM CRIADAS ~");

        for (int i = 0; i < NUM_THREADS; i++) {
            try {
                threads[i].join(); // AGUARDA FINALIZAÇÃO
            } catch (InterruptedException e) {
                System.out.println("ERRO AO AGUARDAR THREAD " + i);
            }
        }

        System.out.println("~ TODAS AS THREADS FINALIZARAM ~");
    }
}