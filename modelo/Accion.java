package modelo;

public class Accion {
    private String descripcion;
    private long timestamp;

    public Accion(String descripcion) {
        this.descripcion = descripcion;
        this.timestamp = System.currentTimeMillis();
    }

    public String getDescripcion() {
        return descripcion;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "[" + timestamp + "] " + descripcion;
    }
}
