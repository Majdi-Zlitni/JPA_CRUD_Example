package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the client database table.
 * 
 */
@Entity
@NamedQueries({ @NamedQuery(name = "Client.findAll", query = "SELECT c FROM Client c"),
		@NamedQuery(name = "Client.finCin", query = "SELECT c FROM Client c WHERE c.cin=?1"),
		@NamedQuery(name = "Client.Delete", query = "DELETE FROM Client c WHERE c.cin=?1"),
		@NamedQuery(name = "Client.Update", query = "UPDATE Client c SET c.cin=cin,c.adresse=adresse,c.nom=nom,c.prenom=prenom,c.tel=tel")
})

public class Client implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id

	private int cin;

	private String adresse;

	private String nom;

	private String prenom;

	private int tel;

	// bi-directional many-to-one association to Compt
	@OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
	private List<Compt> compts;

	public Client() {
	}

	public Client(int cin, String adresse, String nom, String prenom, int tel) {
		this.cin = cin;
		this.adresse = adresse;
		this.nom = nom;
		this.prenom = prenom;
		this.tel = tel;
	}

	public int getCin() {
		return this.cin;
	}

	public void setCin(int cin) {
		this.cin = cin;
	}

	public String getAdresse() {
		return this.adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return this.prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public int getTel() {
		return this.tel;
	}

	public void setTel(int tel) {
		this.tel = tel;
	}

	public List<Compt> getCompts() {
		return this.compts;
	}

	public void setCompts(List<Compt> compts) {
		this.compts = compts;
	}

	public Compt addCompt(Compt compt) {
		getCompts().add(compt);
		compt.setClient(this);

		return compt;
	}

	public Compt removeCompt(Compt compt) {
		getCompts().remove(compt);
		compt.setClient(null);

		return compt;
	}

}