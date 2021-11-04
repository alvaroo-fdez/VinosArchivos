package org.izv.archivosdevinos.data;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Vino implements Serializable, Parcelable {

    private long id;
    private String nombre;
    private String bodega;
    private String color;
    private String origen;
    private double graduacion;
    private int fecha;

    public Vino(long id, String nombre, String bodega, String color, String origen, double graduacion, int fecha) {
        this.id = id;
        this.nombre = nombre;
        this.bodega = bodega;
        this.color = color;
        this.origen = origen;
        this.graduacion = graduacion;
        this.fecha = fecha;
    }

    public Vino() {
        this(0, null, null, null, null, 0.0, 0);
    }

    protected Vino(Parcel in) {
        id = in.readLong();
        nombre = in.readString();
        bodega = in.readString();
        color = in.readString();
        origen = in.readString();
        graduacion = in.readDouble();
        fecha = in.readInt();
    }

    public static final Creator<Vino> CREATOR = new Creator<Vino>() {
        @Override
        public Vino createFromParcel(Parcel in) {
            return new Vino(in);
        }

        @Override
        public Vino[] newArray(int size) {
            return new Vino[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getBodega() {
        return bodega;
    }

    public void setBodega(String bodega) {
        this.bodega = bodega;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public double getGraduacion() {
        return graduacion;
    }

    public void setGraduacion(double graduacion) {
        this.graduacion = graduacion;
    }

    public int getFecha() {
        return fecha;
    }

    public void setFecha(int fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Vino{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", bodega='" + bodega + '\'' +
                ", color='" + color + '\'' +
                ", origen='" + origen + '\'' +
                ", graduacion=" + graduacion +
                ", fecha=" + fecha +
                '}';
    }

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vino vino = (Vino) o;
        return fecha == vino.fecha && Objects.equals(nombre, vino.nombre) && Objects.equals(bodega, vino.bodega);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, bodega, fecha);
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vino vino = (Vino) o;
        if (fecha != vino.fecha) return false;
        if (nombre != null ? !nombre.equals(vino.nombre) : vino.nombre != null) return false;
        return bodega != null ? bodega.equals(vino.bodega) : vino.bodega == null;
    }

    @Override
    public int hashCode() {
        int result = nombre != null ? nombre.hashCode() : 0;
        result = 31 * result + (bodega != null ? bodega.hashCode() : 0);
        result = 31 * result + fecha;
        return result;
    }

    //equals - hashCode
    //Java: si dos objetos son iguales su hashCode tiene que ser igual
    //pero si el hashCode de dos objetos es el mismo no significa que los objetos sean iguales
    //pero si el hashCode es distinto no son iguales
    //o1, o2 -> if(o1.equals(o2))

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(nombre);
        parcel.writeString(bodega);
        parcel.writeString(color);
        parcel.writeString(origen);
        parcel.writeDouble(graduacion);
        parcel.writeInt(fecha);
    }
}
