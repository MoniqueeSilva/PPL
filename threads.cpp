#include <iostream>
#include <pthread.h> // Biblioteca para trabalhar com threads
#include <unistd.h>  // Para usar sleep()
#include <chrono> // Para medir tempo
using namespace std;

// Função para executar a primeira thread
void* tarefa1(void* arg) {
    cout << "thread 1: imprimindo números...\n";
    for (int i = 0; i <= 3; i++) {
        cout << "thread 1: " << i << "\n";
        sleep(1); // Pausa de 1 segundo
    }
    pthread_exit(NULL); // Finaliza a thread
}

// Função que a segunda thread
void* tarefa2(void* arg) {
    cout << "thread 2: imprimindo letras...\n";
    for (char letra = 'A'; letra <= 'C'; letra++) {
        cout << "thread 2: " << letra << "\n";
        sleep(1); // Pausa de 1 segundo
    }
    pthread_exit(NULL); // Finaliza a thread
}

int main() {
    auto inicio = chrono::high_resolution_clock::now(); // Marca o tempo inicial

    pthread_t thread1, thread2; // Declaração das threads

    // Criação da primeira thread
    if (pthread_create(&thread1, NULL, tarefa1, NULL) != 0) {
        cout << "Erro ao criar a Thread 1\n";
        return 1;
    }

    // Criação da segunda thread
    if (pthread_create(&thread2, NULL, tarefa2, NULL) != 0) {
        cout << "Erro ao criar a Thread 2\n";
        return 1;
    }

    // Aguarda a conclusão das threads
    pthread_join(thread1, NULL);
    pthread_join(thread2, NULL);

    cout << "fim das threads.\n";

    auto fim = chrono::high_resolution_clock::now(); // Marca o tempo final

    chrono::duration<double> duracao = fim - inicio;
    cout << "Tempo de execução: " << duracao.count() << " segundos\n";
    
    return 0;
}