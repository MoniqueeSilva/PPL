// mostrando o uso de mutex para garantir o controle de acesso a um recurso compartilhado entre múltiplas threads

import java.util.concurrent.locks.ReentrantLock;

public class Mutex {
    private static ReentrantLock mutex = new ReentrantLock();
    private static int contador = 0;
    private static int vez = 1;

    public static void main(String[] args) {

        Thread thread1 = new Thread(() -> {
            while (true) {
                mutex.lock();
                try {
                    if (vez == 1) {
                        for (int i = 0; i < 25; i++) {
                            contador++;
                        }
                        System.out.println("Thread 1 terminou com = " + contador);
                        vez = 2;
                        break;
                    }
                } finally {
                    mutex.unlock();
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            while (true) {
                mutex.lock();
                try {
                    if (vez == 2) {
                        for (int i = 0; i < 25; i++) {
                            contador++;
                        }
                        System.out.println("Thread 2 terminou com = " + contador);
                        vez = 3;
                        break;
                    }
                } finally {
                    mutex.unlock();
                }
            }
        });

        Thread thread3 = new Thread(() -> {
            while (true) {
                mutex.lock();
                try {
                    if (vez == 3) {
                        for (int i = 0; i < 25; i++) {
                            contador++;
                        }
                        System.out.println("Thread 3 terminou com = " + contador);
                        vez = 4;
                        break;
                    }
                } finally {
                    mutex.unlock();
                }
            }
        });

        Thread thread4 = new Thread(() -> {
            while (true) {
                mutex.lock();
                try {
                    if (vez == 4) {
                        for (int i = 0; i < 25; i++) {
                            contador++;
                        }
                        System.out.println("Thread 4 terminou com = " + contador);
                        break;
                    }
                } finally {
                    mutex.unlock();
                }
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\nValor final: " + contador);
    }
}
