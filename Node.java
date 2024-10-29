class Node {
    Registro registro;
    Node proximo;

    public Node(int id) {
        this.registro = new Registro(id);
        this.proximo = null;
    }
}