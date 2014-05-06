package com.plec.whatsaround.populate.bean;

import com.google.code.morphia.annotations.Entity;

@Entity("type")
public enum POIType {
	Musee, MH, Bibliotheque, Event, other
}