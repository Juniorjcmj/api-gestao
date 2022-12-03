package com.jcmj.core.modulos.financeiro.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

@Entity
public class Documento extends PanacheEntity {

    public long size;

    @Column(length = 512, nullable = false, unique = true)
    public String name;

    @Column(name="uploadtime")

    public Date uploadTime;

    public byte[] content;

}
