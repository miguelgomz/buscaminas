package miguelgomez;

public class Juego {
    public static void main(String[] args) {
        System.out.println("Bienvenido al juego de Buscaminas 🧨\n");

        Buscaminas juego = new Buscaminas();
        juego.iniciarJuego();
    }
}
