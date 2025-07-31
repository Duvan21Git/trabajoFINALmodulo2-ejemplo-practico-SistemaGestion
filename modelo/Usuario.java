package modelo;

public class Usuario {
    private String nombreCompleto;
    private String idUsuario;
    private String username;
    private String contrasena;
    private Rol rol;
    private Accion[] historial;
    private int contadorAcciones;

    public Usuario(String nombreCompleto, String idUsuario, String username, String contrasena, Rol rol) {
        this.nombreCompleto = nombreCompleto;
        this.idUsuario = idUsuario;
        this.username = username;
        this.contrasena = contrasena;
        this.rol = rol;
        this.historial = new Accion[100];
        this.contadorAcciones = 0;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public String getUsername() {
        return username;
    }

    public Rol getRol() {
        return rol;
    }

    public boolean cambiarContrasena(String actual, String nueva) {
        if (this.contrasena.equals(actual)) {
            this.contrasena = nueva;
            registrarAccion("Cambió su contraseña");
            return true;
        }
        return false;
    }

    public void setNombreCompleto(String nuevoNombre) {
        this.nombreCompleto = nuevoNombre;
        registrarAccion("Actualizó su nombre");
    }

    public boolean validarCredenciales(String contrasena) {
        return this.contrasena.equals(contrasena);
    }

    public void registrarAccion(String descripcion) {
        if (contadorAcciones < historial.length) {
            historial[contadorAcciones++] = new Accion(descripcion);
        }
    }

    public void mostrarHistorial() {
        System.out.println("Historial de acciones de " + username + ":");
        for (int i = 0; i < contadorAcciones; i++) {
            System.out.println(historial[i]);
        }
    }

    @Override
    public String toString() {
        return "Usuario: " + nombreCompleto + " | ID: " + idUsuario + " | Rol: " + rol;
    }
}
