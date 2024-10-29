import java.util.Random;

public class Hash {
    //declaração de tabelas
    private Random random;
    Node[] tabela1;
    Node[] tabela2;
    Node[] tabela3;
    
    //tamanho das tabelas
    int tamanhoTabela1 = 1000;
    int tamanhoTabela2 = 10000;
    int tamanhoTabela3 = 100000;
    
    //contador auxiliar
    int contador = 0;
    
    public Hash(long seed) {
        // iniciador das tabelas
        this.random = new Random(seed);
        tabela1 = new Node[tamanhoTabela1];
        tabela2 = new Node[tamanhoTabela2];
        tabela3 = new Node[tamanhoTabela3];
    
    }

    //definição dos hash's
    private int h(int tipo, int chave, int tamanhoTabela) {
        if (tipo == 1) {
            return chave % tamanhoTabela; // primeiro tipo de hash, divisão
        } else if (tipo == 2) { //segundo tipo de hash, multiplicação
            double A = 0.6180339887; // uma constante fracionária para diminuir ocorrencia de colissões
            return (int) (tamanhoTabela * ((chave * A) % 1));
        } else if (tipo == 3) { //terceiro tipo de hash, XOR 
            chave ^= (chave >>> 20) ^ (chave >>> 12); // desloca bits e aplica XOR
            chave ^= (chave >>> 7) ^ (chave >>> 4);
            return chave % tamanhoTabela;
}
        return -1; 
    }

    // Função para exibir tabelas
    public void exibirTabela(Node[] tabela) {
        for (int i = 0; i < 50; i++) {
            Node atual = tabela[i];
            System.out.println("----------------------------------------------------------------");
            System.out.print("Posição " + i + ":");
            while (atual != null) {
                System.out.print("-> "+ atual.registro.id);
                atual = atual.proximo;
            }
            System.out.println("");
        }
    }

    //Função de busca
    public void buscar(Node[] tabela, int id, int tipo, int tamanhoTabela) {
        // Contador auxiliar para numero de comparações
        int comparacao = 0;
        //atribui um parãmetro ao indice
        int indice = h(tipo, id, tamanhoTabela);
            Node atual = tabela[indice];
            while (atual != null) {
                comparacao++; //Incrementa contador auxiliar
                if (atual.registro.id == id) {
                    //Imprime valor de comparações caso seja encontrado o valor
                    System.out.println("Chave " + id + "encontrada após " + comparacao + " comparações.");
                    return;//Encerra busca caso seja encontrado o valor (break)
                }
                atual = atual.proximo;
            }
        //Imprime numero de comparações caso não seja encontrado o valor
        System.out.println("Chave " + id + " não encontrada após " + comparacao + " comparações.");
    }

    //Insere valores
    public void inserirVarios(int tipoH, int tipoR, int numero, Node[] tabela, int tamanhoTabela) {
        for (int i = 0; i < numero; i++) {
            int chave = random.nextInt(100000000) + 1;// Gera numero aleatório e garante que o mesmo tenha 9 digitos
            int a = h(tipoH, chave, tamanhoTabela);
            
            Node novo = new Node(chave);
            
            //Determina a maneira de lidar com colisões
            if (tipoR == 1) { // Encadeamento 
                if (tabela[a] == null) {
                    tabela[a] = novo;
                } else { 
                    contador = contador + 1 ;
                    Node atual = tabela[a];
                    while (atual.proximo != null) {
                        atual = atual.proximo;
                    }
                    atual.proximo = novo;
                }
            } else if (tipoR == 2) {// rehasing simples
                while (tabela[a] != null) {
                    contador += 1;
                    a = (a + 1) % tamanhoTabela;
                }
                tabela[a] = novo;
            }
        }
    }
    //função principal do código
    public static void main(String[] args) {
        Hash hash = new Hash(123456);//inserir semente para testes
        
        //Insere elementos e faz uma busca, conta o tempo que cada ação leva,
        //imprime tempo de inserção, tempo de busca e numero de colisões
        System.out.println("Hash1");
        long comecoinserir1 = System.currentTimeMillis();
        hash.inserirVarios(1 ,1, 20000000, hash.tabela1, hash.tamanhoTabela1); 
        long finalinserir1 = System.currentTimeMillis();
        long comecobuscar1 = System.currentTimeMillis();
        hash.buscar(hash.tabela1, 81310003, 1, hash.tamanhoTabela1);
        long finalbuscar1 = System.currentTimeMillis();
        System.out.println("Tempo de busca: " + (finalbuscar1 - comecobuscar1) + " ms");
        System.out.println("Tempo de inserção: " + (finalinserir1 - comecoinserir1) + " ms");
        System.out.println("Numero de colisões:" + hash.contador);
        
        //Insere elementos e faz uma busca, conta o tempo que cada ação leva,
        //imprime tempo de inserção, tempo de busca e numero de colisões
        System.out.println("Hash2");
        long comecoinserir2 = System.currentTimeMillis();
        hash.inserirVarios(2 ,1, 20000000, hash.tabela2, hash.tamanhoTabela2); 
        long finalinserir2 = System.currentTimeMillis();
        long comecobuscar2 = System.currentTimeMillis();
        hash.buscar(hash.tabela2, 48130043, 2, hash.tamanhoTabela2);
        long finalbuscar2 = System.currentTimeMillis();
        System.out.println("Tempo de busca: " + (finalbuscar2 - comecobuscar2) + " ms");
        System.out.println("Tempo de inserção: " + (finalinserir2 - comecoinserir2) + " ms");
        System.out.println("Numero de colisões:" + hash.contador);

        //Insere elementos e faz uma busca, conta o tempo que cada ação leva,
        //imprime tempo de inserção, tempo de busca e numero de colisões
        System.out.println("Hash3");
        long comecoinserir3 = System.currentTimeMillis();
        hash.inserirVarios(3, 1, 20000000, hash.tabela3, hash.tamanhoTabela3);
        long finalinserir3 = System.currentTimeMillis();
        long comecobuscar3 = System.currentTimeMillis();
        hash.buscar(hash.tabela3, 16823237, 3, hash.tamanhoTabela3);
        long finalbuscar3 = System.currentTimeMillis();
        System.out.println("Tempo de busca: " + (finalbuscar3 - comecobuscar3) + " ms");
        System.out.println("Tempo de inserção: " + (finalinserir3 - comecoinserir3) + " ms");
        System.out.println("Numero de colisões:" + hash.contador);
        
        //Modo de usar
        //tipoH é o tipo de hash que deseja 1 para divisão, 2 para multiplicação e 3 para XOR.
        //tipoR é o tipo de tratamento de colisão que deseja 1 para encadeamento e 2 para rehashing linear
        //numero é o número de elementos a serem inseridos
        //tabela é a tabela hash que deseja manipular
        //tamanhoTabela é Tamanho da tabela 1 para 100000 2 para 10000 e 3 para 1000
    }
}