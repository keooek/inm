package inmjava;

import java.util.ArrayList;

public class Inmueble {

	private String id = "vacio";
	private String direccion = "vacio";
	private String fechaPublicacion = "vacio";
	private String zona = "vacio";
	private String descripcion = "vacio";
	private String vendedor = "vacio";
	private String url = "vacio";
	private String origen = "vacio";
	private ArrayList<String> telUrls = new ArrayList<>();
	private Integer metrosCuadrados = 0;
	private Integer telefono = 0;
	private Integer precio = 0;

	public Integer getPrecio() {
		return precio;
	}

	public void setPrecio(Integer precio) {
		this.precio = precio;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(String fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getTelefono() {
		return telefono;
	}

	public void setTelefono(Integer telefono) {
		this.telefono = telefono;
	}

	public String getVendedor() {
		return vendedor;
	}

	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getMetrosCuadrados() {
		return metrosCuadrados;
	}

	public void setMetrosCuadrados(Integer metrosCuadrados) {
		this.metrosCuadrados = metrosCuadrados;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Inmueble other = (Inmueble) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public ArrayList<String> getTelUrls() {
		return telUrls;
	}

	// public void setTelUrls(ArrayList<String> telUrls) {
	// this.telUrls = telUrls;
	// }

	public void setTelUrls(String attr) {
		// TODO Auto-generated method stub
		System.out.println(attr);
		this.telUrls.add(attr);
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

}
