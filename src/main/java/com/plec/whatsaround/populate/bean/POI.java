package com.plec.whatsaround.populate.bean;

import java.util.Date;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.emul.org.bson.types.ObjectId;

@Entity("poi")
public class POI {
	/** POI's id */
	private String sourceId;
	/** POI's name */
	private String name;
	/** POI's Localisation */
	private LatLng latlng = new LatLng();
	/** POI's type */
	private String type;
	/** POI's description */
	private String description;
	/** POI's periode ouverture */
	private String periodeOuverture;
	/** POI's periode fermeture */
	private String periodeFermeture;
	/** POI's horaires */
	private String horaires;
	/** POI's note */
	private String note;
	/** POI's ville */
	private String ville;
	/** POI's adresse */
	private String adresse;
	/** POI's formatted adresse */
	private String formattedAddress;
	/** POI's cp */
	private String cp;
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LatLng getLatlng() {
		return latlng;
	}
	public void setLatlng(LatLng latlng) {
		this.latlng = latlng;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPeriodeOuverture() {
		return periodeOuverture;
	}
	public void setPeriodeOuverture(String periodeOuverture) {
		this.periodeOuverture = periodeOuverture;
	}
	public String getPeriodeFermeture() {
		return periodeFermeture;
	}
	public void setPeriodeFermeture(String periodeFermeture) {
		this.periodeFermeture = periodeFermeture;
	}
	public String getHoraires() {
		return horaires;
	}
	public void setHoraires(String horaires) {
		this.horaires = horaires;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getFormattedAddress() {
		return formattedAddress;
	}
	public void setFormattedAddress(String formattedAddress) {
		this.formattedAddress = formattedAddress;
	}
	public String getCp() {
		return cp;
	}
	public void setCp(String cp) {
		this.cp = cp;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
