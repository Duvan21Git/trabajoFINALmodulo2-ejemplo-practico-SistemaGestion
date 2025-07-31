package vista;

import modelo.Rol;
import modelo.Usuario;
import servicio.ServicioUsuario;

import java.util.Scanner;

public class Controlador {
    private Scanner scanner = new Scanner(System.in);
    private ServicioUsuario servicio = new ServicioUsuario();
    private Usuario usuarioActual;

    public void iniciar() {
        System.out.println("=== SISTEMA DE GESTIÓN DE CLIENTES ===");

        Usuario admin = new Usuario("Admin General", "1", "admin", "admin123", Rol.ADMINISTRADOR);
        servicio.crearUsuario(admin, admin);

        while (true) {
            System.out.println("\nIniciar sesión:");
            System.out.print("Usuario: ");
            String username = scanner.nextLine();
            System.out.print("Contraseña: ");
            String contrasena = scanner.nextLine();

            usuarioActual = servicio.iniciarSesion(username, contrasena);
            if (usuarioActual != null) {
                System.out.println("Inicio de sesión exitoso. Bienvenido, " + usuarioActual.getNombreCompleto());
                mostrarMenu();
            } else {
                System.out.println("Credenciales incorrectas. Intenta nuevamente.");
            }
        }
    }

    private void mostrarMenu() {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n===== MENÚ PRINCIPAL =====");
            System.out.println("1. Ver mi perfil");
            System.out.println("2. Actualizar mi información");
            System.out.println("3. Ver historial de acciones");

            if (usuarioActual.getRol() == Rol.ADMINISTRADOR) {
                System.out.println("4. Crear nuevo usuario");
                System.out.println("5. Eliminar usuario");
                System.out.println("6. Ver todos los usuarios");
            }

            System.out.println("0. Cerrar sesión");
            System.out.print("Opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    System.out.println(usuarioActual);
                    break;
                case "2":
                    actualizarPerfil();
                    break;
                case "3":
                    usuarioActual.mostrarHistorial();
                    break;
                case "4":
                    if (esAdmin()) crearUsuario();
                    break;
                case "5":
                    if (esAdmin()) eliminarUsuario();
                    break;
                case "6":
                    if (esAdmin()) servicio.mostrarUsuarios(usuarioActual);
                    break;
                case "0":
                    usuarioActual.registrarAccion("Cerró sesión.");
                    usuarioActual = null;
                    salir = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private boolean esAdmin() {
        return usuarioActual.getRol() == Rol.ADMINISTRADOR;
    }

    private void actualizarPerfil() {
        System.out.print("Nuevo nombre (enter para no cambiar): ");
        String nuevoNombre = scanner.nextLine();

        System.out.print("¿Deseas cambiar la contraseña? (s/n): ");
        String cambiar = scanner.nextLine();
        String actual = "", nueva = "";

        if (cambiar.equalsIgnoreCase("s")) {
            System.out.print("Contraseña actual: ");
            actual = scanner.nextLine();
            System.out.print("Nueva contraseña: ");
            nueva = scanner.nextLine();
        }

        servicio.actualizarUsuario(usuarioActual, nuevoNombre, actual, nueva);
    }

    private void crearUsuario() {
        System.out.print("Nombre completo: ");
        String nombre = scanner.nextLine();

        System.out.print("ID de usuario: ");
        String id = scanner.nextLine();

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Contraseña: ");
        String contrasena = scanner.nextLine();

        System.out.print("Rol (ADMINISTRADOR/ESTANDAR): ");
        String rolInput = scanner.nextLine().toUpperCase();
        Rol rol = Rol.valueOf(rolInput);

        Usuario nuevo = new Usuario(nombre, id, username, contrasena, rol);
        servicio.crearUsuario(usuarioActual, nuevo);
    }

    private void eliminarUsuario() {
        System.out.print("Username del usuario a eliminar: ");
        String username = scanner.nextLine();
        servicio.eliminarUsuario(usuarioActual, username);
    }
}
