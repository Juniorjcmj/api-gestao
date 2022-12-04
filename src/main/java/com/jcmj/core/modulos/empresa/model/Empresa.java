package com.jcmj.core.modulos.empresa.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Empresa extends PanacheEntity {
    public String nome;
    public String cnpj;
    public String endereco;
    @Column(name = "tel_fixo")
    public String telFixo;
    @Column(name = "tel_movel")
    public String telMovel;
    public String email;

    public Empresa() {
    }

    public Empresa(Long idEmpresa) {
        id = idEmpresa;
    }
    public static Empresa findByNome(String nome) {
        return find("nome", nome).firstResult();
    }
    public static Empresa findBycnpj(String cnpj) {
        return find("cnpj", cnpj).firstResult();
    }
    public static Empresa findByEmail(String email) {
        return find("email", email).firstResult();
    }

}
