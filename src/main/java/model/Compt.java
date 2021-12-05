package model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the compt database table.
 * 
 */
@Entity
@NamedQuery(name="Compt.findAll", query="SELECT c FROM Compt c")
public class Compt implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int numCompt;

	@Temporal(TemporalType.DATE)
	private Date dateOuverture;

	@Temporal(TemporalType.DATE)
	private Date dateValidite;

	private BigDecimal sold;

	//bi-directional many-to-one association to Client
	@ManyToOne
	@JoinColumn(name="Cin")
	private Client client;

	//bi-directional many-to-one association to Journal
	@OneToMany(mappedBy="compt", fetch=FetchType.EAGER)
	private List<Journal> journals;

	public Compt() {
	}

	public int getNumCompt() {
		return this.numCompt;
	}

	public void setNumCompt(int numCompt) {
		this.numCompt = numCompt;
	}

	public Date getDateOuverture() {
		return this.dateOuverture;
	}

	public void setDateOuverture(Date dateOuverture) {
		this.dateOuverture = dateOuverture;
	}

	public Date getDateValidite() {
		return this.dateValidite;
	}

	public void setDateValidite(Date dateValidite) {
		this.dateValidite = dateValidite;
	}

	public BigDecimal getSold() {
		return this.sold;
	}

	public void setSold(BigDecimal sold) {
		this.sold = sold;
	}

	public Client getClient() {
		return this.client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public List<Journal> getJournals() {
		return this.journals;
	}

	public void setJournals(List<Journal> journals) {
		this.journals = journals;
	}

	public Journal addJournal(Journal journal) {
		getJournals().add(journal);
		journal.setCompt(this);

		return journal;
	}

	public Journal removeJournal(Journal journal) {
		getJournals().remove(journal);
		journal.setCompt(null);

		return journal;
	}

}