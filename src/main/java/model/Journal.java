package model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the journal database table.
 * 
 */
@Entity
@NamedQueries({
@NamedQuery(name="Journal.findAll", query="SELECT j FROM Journal j"),
@NamedQuery(name = "Journal.findlog", query = "SELECT j FROM Journal j WHERE j.IDlog=?1")
})
public class Journal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int IDlog;

	@Temporal(TemporalType.DATE)
	private Date dateLog;

	private BigDecimal montant;

	private String typeService;

	//bi-directional many-to-one association to Compt
	@ManyToOne
	@JoinColumn(name="NumCompt")
	private Compt compt;

	public Journal() {
	}

	public int getIDlog() {
		return this.IDlog;
	}

	public void setIDlog(int IDlog) {
		this.IDlog = IDlog;
	}

	public Date getDateLog() {
		return this.dateLog;
	}

	public void setDateLog(Date dateLog) {
		this.dateLog = dateLog;
	}

	public BigDecimal getMontant() {
		return this.montant;
	}

	public void setMontant(BigDecimal montant) {
		this.montant = montant;
	}

	public String getTypeService() {
		return this.typeService;
	}

	public void setTypeService(String typeService) {
		this.typeService = typeService;
	}

	public Compt getCompt() {
		return this.compt;
	}

	public void setCompt(Compt compt) {
		this.compt = compt;
	}

}