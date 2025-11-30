#include <iostream>
#include <pthread.h>
#include <unistd.h>   // sleep()
#include <chrono>     // medir tempo

using namespace std;

#define NUM_THREADS 5

// Função auxiliar (baseada no exemplo do professor)
void* funcaoHelper(void* threadid) {
    long tid = (long)threadid;

    cout << "Thread " << tid << " iniciada!" << endl;

    if (tid == 0) {
        cout << "Thread 0 (números): imprimindo números..." << endl;
        for (int i = 0; i <= 3; i++) {
            cout << "Thread 0 -> número: " << i << endl;
            sleep(1);
        }
    } else if (tid == 1) {
        cout << "Thread 1 (letras): imprimindo letras..." << endl;
        for (char letra = 'A'; letra <= 'C'; letra++) {
            cout << "Thread 1 -> letra: " << letra << endl;
            sleep(1);
        }
    }

    cout << "Thread " << tid << " finalizada!" << endl;

    pthread_exit(NULL);
}

int main() {
    auto inicio = chrono::high_resolution_clock::now(); // marca tempo inicial

    pthread_t threads[NUM_THREADS];
    int statusRetorno;

    cout << "== Iniciando criação das threads ==" << endl;

    for (long t = 0; t < NUM_THREADS; t++) {
        cout << "Criando thread: " << t << endl;
        statusRetorno = pthread_create(&threads[t], NULL, funcaoHelper, (void*)t);

        if (statusRetorno) {
            cout << "Erro ao criar a thread " << t << " código: " << statusRetorno << endl;
            exit(-1);
        }
    }

    cout << "== Aguardando finalização das threads ==" << endl;

    for (int t = 0; t < NUM_THREADS; t++) {
        pthread_join(threads[t], NULL);
        cout << "Thread " << t << " finalizada com sucesso!" << endl;
    }

    cout << "Fim das threads." << endl;

    auto fim = chrono::high_resolution_clock::now();
    chrono::duration<double> duracao = fim - inicio;
    cout << "Tempo total de execução: " << duracao.count() << " segundos." << endl;

    pthread_exit(NULL);
}
