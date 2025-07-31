package servicio;

import modelo.Usuario;
import modelo.Rol;

public class ServicioUsuario {
    private Usuario[] usuarios;
    private int contadorUsuarios;

    public ServicioUsuario() {
        usuarios = new Usuario[50];
        contadorUsuarios = 0;
    }

    public boolean crearUsuario(Usuario actual, Usuario nuevo) {
        if (actual.getRol() != Rol.ADMINISTRADOR) {
            System.out.println("Permiso denegado: solo administradores pueden crear usuarios.");
            return false;
        }

        if (buscarPorID(nuevo.getIdUsuario()) != null || buscarPorUsername(nuevo.getUsername()) != null) {
            System.out.println("Error: ID o Username ya existen.");
            return false;
        }

        if (contadorUsuarios < usuarios.length) {
            usuarios[contadorUsuarios++] = nuevo;
            actual.registrarAccion("Creó al usuario " + nuevo.getUsername());
            return true;
        }

        System.out.println("No se puede crear más usuarios.");
        return false;
    }

    public Usuario buscarPorID(String id) {
        for (int i = 0; i < contadorUsuarios; i++) {
            if (usuarios[i].getIdUsuario().equals(id)) {
                return usuarios[i];
            }
        }
        return null;
    }

    public Usuario buscarPorUsername(String username) {
        for (int i = 0; i < contadorUsuarios; i++) {
            if (usuarios[i].getUsername().equals(username)) {
                return usuarios[i];
            }
        }
        return null;
    }

    public boolean eliminarUsuario(Usuario actual, String username) {
        if (actual.getRol() != Rol.ADMINISTRADOR) {
            System.out.println("Permiso denegado: solo administradores pueden eliminar usuarios.");
            return false;
        }

        for (int i = 0; i < contadorUsuarios; i++) {
            if (usuarios[i].getUsername().equals(username)) {
                actual.registrarAccion("Eliminó al usuario " + username);
                usuarios[i] = usuarios[--contadorUsuarios];
                usuarios[contadorUsuarios] = null;
                return true;
            }
        }

        System.out.println("Usuario no encontrado.");
        return false;
    }

    public boolean actualizarUsuario(Usuario actual, String nuevoNombre, String contrasenaActual, String nuevaContrasena) {
        if (nuevoNombre != null && !nuevoNombre.isBlank()) {
            actual.setNombreCompleto(nuevoNombre);
        }

        if (nuevaContrasena != null && !nuevaContrasena.isBlank()) {
            if (!actual.cambiarContrasena(contrasenaActual, nuevaContrasena)) {
                System.out.println("Contraseña actual incorrecta.");
                return false;
            }
        }

        actual.registrarAccion("Actualizó su perfil.");
        return true;
    }

    public Usuario iniciarSesion(String username, String contrasena) {
        Usuario usuario = buscarPorUsername(username);
        if (usuario != null && usuario.validarCredenciales(contrasena)) {
            usuario.registrarAccion("Inició sesión.");
            return usuario;
        }
        return null;
    }

    public void mostrarUsuarios(Usuario actual) {
        if (actual.getRol() != Rol.ADMINISTRADOR) {
            System.out.println("Permiso denegado: solo administradores pueden ver la lista.");
            return;
        }

        for (int i = 0; i < contadorUsuarios; i++) {
            System.out.println(usuarios[i]);
        }
    }
}
