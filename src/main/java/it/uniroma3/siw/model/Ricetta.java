package it.uniroma3.siw.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Ricetta {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	private String nome;
	@Column(length = 2000)
	private String descrizione;
	private List<String> urlsImages;
	
	@OneToMany(mappedBy = "ricetta")
	private List<RigaRicetta> righeRicetta;
	
    @ManyToOne 
	private Cuoco cuoco; 

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Cuoco getCuoco() {
		return cuoco;
	}
	public void setCuoco(Cuoco cuoco) {
		this.cuoco = cuoco;
	}

	public List<RigaRicetta> getRigheRicetta() {
		return righeRicetta;
	}
	public void setRigheRicetta(List<RigaRicetta> righeRicetta) {
		this.righeRicetta = righeRicetta;
	}
	public List<String> getUrlsImages() {
		return urlsImages;
	}
	public void setUrlsImages(List<String> urlsImages) {
		this.urlsImages = urlsImages;
	} 
}
