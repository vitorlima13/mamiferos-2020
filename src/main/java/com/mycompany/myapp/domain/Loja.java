package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Loja.
 */
@Entity
@Table(name = "loja")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Loja implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @OneToMany(mappedBy = "loja")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Mamifero> mamiferos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Loja nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Mamifero> getMamiferos() {
        return mamiferos;
    }

    public Loja mamiferos(Set<Mamifero> mamiferos) {
        this.mamiferos = mamiferos;
        return this;
    }

    public Loja addMamifero(Mamifero mamifero) {
        this.mamiferos.add(mamifero);
        mamifero.setLoja(this);
        return this;
    }

    public Loja removeMamifero(Mamifero mamifero) {
        this.mamiferos.remove(mamifero);
        mamifero.setLoja(null);
        return this;
    }

    public void setMamiferos(Set<Mamifero> mamiferos) {
        this.mamiferos = mamiferos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Loja)) {
            return false;
        }
        return id != null && id.equals(((Loja) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Loja{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
