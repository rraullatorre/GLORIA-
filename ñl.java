import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ñl {
    private String[] productos = {"leche gloria", "yogur gloria", "queso gloria"};
    private double[] precios = {2.50, 3.50, 10.00};
    private int[] carrito = new int[productos.length];
    private double monto_inicial = 1000;
    private Scanner pepe = new Scanner(System.in);
    private ArrayList<String> historialCompras = new ArrayList<>();
    public void menu_principal() {
        while (true) {
            System.out.println("--------- BIENVENIDO A LA TIENDA LECHES GLORIA ---------");
            System.out.println("1. Comprar productos");
            System.out.println("2. Realizar pago");
            System.out.println("4. Ver historial de compras");
            System.out.println("5. Modificar carrito");
            System.out.println("6. Salir");

            int opcion = pepe.nextInt();
            pepe.nextLine();
            switch (opcion) {
                case 1:
                    this.comprar_producto();
                    break;
                case 2:
                    this.mostrar_pago();
                    break;

                case 4:
                    this.ver_hisorial_de_compra();
                    break;
                case 5:
                    this.modificar_carrito();
                    break;
                case 6:
                    System.out.println("gracias por visitar la tienda");
                    return;
                default:
                    System.out.println("opcion no valida");
            }
        }
    }

    public void comprar_producto() {
        System.out.println("------ BIENVENIDO A LECHES GLORIA -----------");
        System.out.println("seleccione su producto de preferencia:");
        for (int i = 0; i < productos.length; i++) {
            System.out.println("opicion " + (i + 1) + " : " + productos[i]);
        }
        int opcion = pepe.nextInt();
        pepe.nextLine();
        int cantidad;
        switch (opcion) {
            case 1:
                System.out.println("cualtos productos deseas ");
                cantidad = pepe.nextInt();
                carrito[0] += cantidad;
                System.out.println("Ud ha pedido " + carrito[0] + " de " + productos[0]);
                break;
            case 2:
                System.out.println("cualtos productos deseas");
                cantidad = pepe.nextInt();
                carrito[1] += cantidad;
                System.out.println("Ud ha pedido " + carrito[1] + " de " + productos[1]);
                break;
            case 3:
                System.out.println("cualtos productos deseas");
                cantidad = pepe.nextInt();
                carrito[2] += cantidad;
                System.out.println("Ud ha pedido " + carrito[2] + " de " + productos[2]);
                break;
            default:
                System.out.println("opcion no valida");
                break;
        }

        System.out.println(" desea comprar otro producto");
        System.out.println(" 1. si");
        System.out.println(" 2. no");
        opcion = pepe.nextInt();
        if (opcion == 1) {
            this.comprar_producto();
        } else {
            this.mostrar_pago();
        }
    }

    public void mostrar_pago() {
        System.out.println("Seleccione el tipo de pago:");
        System.out.println("1. Efectivo");
        System.out.println("2. Tarjeta de débito");
        System.out.println("0. Volver al menú principal");

        int opcion = pepe.nextInt();
        pepe.nextLine();

        switch (opcion) {
            case 0:
                return;
            case 1:
                this.pago_en_efectivo();
                break;
            case 2:
                this.realizar_pago_por_tarjeta_de_debito();
                break;
            default:
                System.out.println("Opción no válida");
        }
    }

    public void pago_en_efectivo() {
        double totalPagar = calcular_total_a_pagar();
        System.out.println("Total a pagar (incluye IGV): " + totalPagar);
        int intentos = 3;

        while (intentos > 0) {
            System.out.println("Ingrese el monto que usted tiene:");
            double montoRecibido = pepe.nextDouble();
            pepe.nextLine();

            if (montoRecibido >= totalPagar) {
                double cambio = montoRecibido - totalPagar;
                System.out.println("Pago recibido: " + montoRecibido);
                System.out.println("Cambio: " + cambio);
                System.out.println("gracias por su compra");
                historial_de_compra();
                limpiar_carrito();
                return;
            } else {
                System.out.println("moto insuficiente " + (totalPagar - montoRecibido));
                intentos--;
                if (intentos > 0) {
                    System.out.println("Intentos restantes: " + intentos);
                }
            }
        }
        System.out.println("Se acabaron los intentos. Seleccione otro método de pago.");
        this.mostrar_pago();
    }

    public void realizar_pago_por_tarjeta_de_debito() {
        double totalPagar = calcular_total_a_pagar();
        System.out.println("Total a pagar incluye el igv" + totalPagar);

        System.out.println("Ingrese el número de tarjeta de débito:");
        String numeroTarjeta = pepe.nextLine();

        if (numeroTarjeta.trim().length() < 8) {
            System.out.println("Número de tarjeta inválido");
            return;
        }

        System.out.println("Realizando pago con tarjeta de débito número: " + numeroTarjeta.substring(0, 4) + "****" + numeroTarjeta.substring(numeroTarjeta.length() - 4));

        if (totalPagar <= monto_inicial) {
            monto_inicial -= totalPagar;
            System.out.println("Pago exitoso con tarjeta de débito.");
            System.out.println("Nuevo saldo de tarjeta: " + monto_inicial);
            System.out.println("gracias por su compra");
            historial_de_compra();
            limpiar_carrito();
        } else {
            System.out.println("Saldo insuficiente en la tarjeta de débito. Intente con otro método de pago.");
            this.mostrar_pago();
        }
    }

    private void limpiar_carrito() {
        Arrays.fill(carrito, 0);
    }

    public double calcular_total_a_pagar() {
        double subtotal = 0;
        for (int i = 0; i < productos.length; i++) {
            subtotal += carrito[i] * precios[i];
        }
        return subtotal * 1.18;
    }

    public void historial_de_compra() {
        StringBuilder compra = new StringBuilder("Compra realizada:");
        double subtotalCompra = 0;

        for (int i = 0; i < productos.length; i++) {
            if (carrito[i] > 0) {
                double subtotalProducto = carrito[i] * precios[i];
                compra.append(productos[i]).append(" x ").append(carrito[i]).append(" = S/." + subtotalProducto).append("\n");
                subtotalCompra += subtotalProducto;
            }
        }

        double igvCompra = subtotalCompra * 0.18;
        double totalCompra = subtotalCompra + igvCompra;

        System.out.println("Subtotal: " + subtotalCompra);
        System.out.println("IGV : " + igvCompra);
        System.out.println("Total: " + totalCompra);

        historialCompras.add(compra.toString());
    }

    public void ver_hisorial_de_compra() {
        if (historialCompras.isEmpty()) {
            System.out.println("no hay compras en el historial.");
        } else {
            System.out.println("historial de compras:");
            for (String compra : historialCompras) {
                System.out.println(compra);
                System.out.println("-----------------------------------");
            }
        }
    }
    public void modificar_carrito() {
        System.out.println("------ MODIFICAR CARRITO ---------");
        System.out.println("seleccione el producto que desea modificar:");
        for (int i = 0; i < productos.length; i++) {
            System.out.println("opcion " + (i + 1) + " : " + productos[i] + " - cantidad: " + carrito[i]);
        }

        int opcion = pepe.nextInt();
        pepe.nextLine();

        if (opcion >= 1 && opcion <= productos.length) {
            System.out.println("cunatos desea agregar o eliminar");
            int cantidad = pepe.nextInt();
            pepe.nextLine();

            if (carrito[opcion - 1] + cantidad < 0) {
                System.out.println("no puede tener cantidad negativa de productos.");
            } else {
                carrito[opcion - 1] += cantidad;
                System.out.println("La cantidad de " + productos[opcion - 1] + " ha sido modificada. Ahora tiene " + carrito[opcion - 1]);
            }
        } else {
            System.out.println("opcion no valida.");
        }
        System.out.println(" desea comprar otro producto");
        System.out.println(" 1. si");
        System.out.println(" 2. no");

        int opcionMod = pepe.nextInt();
        if (opcionMod == 1) {
            modificar_carrito();
        } else {
            menu_principal();
        }
    }


    public static void main(String[] args) {
        ñl tienda = new ñl();
        tienda.menu_principal();
    }
}
