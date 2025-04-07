package miguelgomez;
import java.util.Random;
import java.util.Scanner;

public class Buscaminas {
    private final int TAMANIO = 6;
    private final int CANTIDAD_MINAS = 6;
    private Celda[][] tablero;
    private Scanner entrada;

    public Buscaminas() {
        tablero = new Celda[TAMANIO][TAMANIO];
        entrada = new Scanner(System.in);
        inicializarTablero();
        distribuirMinas();
    }

    public void iniciarJuego() {
        boolean terminado = false;

        while (!terminado) {
            mostrarTablero(false);
            String accion = "";

           
            while (!accion.equals("D") && !accion.equals("M")) {
                System.out.print("[D]espejar o [M]arcar mina?: ");
                accion = entrada.nextLine().toUpperCase();

                if (!accion.equals("D") && !accion.equals("M")) {
                    System.out.println("Opción no válida. Usa solo D o M en mayúscula.");
                }
            }

            
            System.out.print("> Fila: ");
            int fila = entrada.nextInt() - 1;
            System.out.print("> Columna: ");
            int columna = entrada.nextInt() - 1;
            entrada.nextLine(); 

            if (esCoordenadaValida(fila, columna)) {
                Celda celda = tablero[fila][columna];

                if (accion.equals("D")) {
                    if (celda.tieneMina) {
                        mostrarTablero(true);
                        System.out.println("¡Boom! Has pisado una mina. Fin del juego.");
                        terminado = true;
                    } else {
                        celda.estaDescubierta = true;
                        int minasCercanas = contarMinasCercanas(fila, columna);
                        System.out.println("Casilla despejada (" + (fila + 1) + ", " + (columna + 1) + "). Minas cercanas: " + minasCercanas);

                        if (esVictoria()) {
                            mostrarTablero(true);
                            System.out.println("¡Felicidades! Has ganado.");
                            terminado = true;
                        }
                    }
                } else { 
                    celda.estaMarcada = !celda.estaMarcada;
                }
            } else {
                System.out.println("Coordenadas fuera del tablero.");
            }
        }
    }

    private void inicializarTablero() {
        for (int i = 0; i < TAMANIO; i++) {
            for (int j = 0; j < TAMANIO; j++) {
                tablero[i][j] = new Celda();
            }
        }
    }

    private void distribuirMinas() {
        Random random = new Random();
        int minasColocadas = 0;

        while (minasColocadas < CANTIDAD_MINAS) {
            int fila = random.nextInt(TAMANIO);
            int columna = random.nextInt(TAMANIO);

            if (!tablero[fila][columna].tieneMina) {
                tablero[fila][columna].tieneMina = true;
                minasColocadas++;
            }
        }
    }

    private void mostrarTablero(boolean revelarTodo) {
        System.out.println("\nBUSCAMINAS");
        System.out.print("  ");
        for (int i = 1; i <= TAMANIO; i++) System.out.print(i + " ");
        System.out.println();

        for (int i = 0; i < TAMANIO; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < TAMANIO; j++) {
                Celda celda = tablero[i][j];

                if (celda.estaDescubierta || revelarTodo) {
                    System.out.print(celda.tieneMina ? "* " : "D ");
                } else if (celda.estaMarcada) {
                    System.out.print("M ");
                } else {
                    System.out.print("_ ");
                }
            }
            System.out.println();
        }
    }
}