// Essa classe serve de recurso compartilhado para as threads 

public class Buffer {
    // cria vetores
    private final int[] vetor;
    private final Object[] locks;

    public Buffer() {
        this.vetor = new int[5]; // vetor de 5 posições
        this.locks = new Object[5];// locks que serve de "chave/trava" para as movimentações das threads
        
        // percorrer todas as posições do vetor de locks
        for (int i = 0; i < locks.length; i++) {
            locks[i] = new Object(); // cria um novo objeto(lock) para cada posição do vetor, ou seja, cada posição tem sua chave/trava
        }
    }

    // método para retornar o tamanho do vetor
    public int getTamanho() {
        return vetor.length;
    }

    // método para retornar o lock de alguma posição específica
    public Object getLock(int posicao) {
        return locks[posicao]; // permite que sicronize apenas essa parte no vetor
    }

    // método para ler e retornar o valor da posição 
    public int lerPosicao(int posicao) {
        return vetor[posicao];
    }

    // método que escreve valores em posições no vetor
    public void escreverPosicao(int posicao, int valor) {
        vetor[posicao] = valor; // altera de um valor para outro
    }
}