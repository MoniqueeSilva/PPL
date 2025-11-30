// mostrando na prática, que a exclusão mútua funciona e evita erro em programas com várias threads.

import java.util.Random;

public class ExclusaoMutua01 {
    static int[] vetor = new int[6];

    // sicronização que protege um par de posições do vetor
    static final Object lock01 = new Object();
    static final Object lock23 = new Object();
    static final Object lock45 = new Object();

    public static void main(String[] args) throws InterruptedException {

        // criação de threads para manipular cada par de posições
        Thread t1 = new Thread(new Responsavel(0, 1, lock01));
        Thread t2 = new Thread(new Responsavel(0, 1, lock01));

        Thread t3 = new Thread(new Responsavel(2, 3, lock23));
        Thread t4 = new Thread(new Responsavel(2, 3, lock23));

        Thread t5 = new Thread(new Responsavel(4, 5, lock45));
        Thread t6 = new Thread(new Responsavel(4, 5, lock45));

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();
        t5.join();
        t6.join();

        System.out.println("\nVetor final:");
        for (int i = 0; i < vetor.length; i++) {
            System.out.println("Posição " + i + " = " + vetor[i]);
        }

        System.out.println("\nSomas por par (cada par deve somar 40):");
        System.out.println("0 + 1 = " + (vetor[0] + vetor[1]));
        System.out.println("2 + 3 = " + (vetor[2] + vetor[3]));
        System.out.println("4 + 5 = " + (vetor[4] + vetor[5]));
    }

    static class Responsavel implements Runnable {

        int a, b; // posições do vetor que este thread manipula
        Object lock; // objeto de sincronização para proteger as posições
        Random random = new Random(); // gerador de números aleatórios

        public Responsavel(int a, int b, Object lock) {
            this.a = a;
            this.b = b;
            this.lock = lock;
        }

        public void run() {
            for (int i = 0; i < 20; i++) {

                // exclusão mútua: garantir que apenas uma thread por vez possa acessar o par de posições protegido pelo mesmo lock e alterar uma delas para incrementar seu valor
                synchronized (lock) {
                    int posicao = random.nextBoolean() ? a : b;
                    vetor[posicao]++;
                }

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
