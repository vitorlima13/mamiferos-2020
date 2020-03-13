package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.ZonedDateTime;

/**
 * A ObservacaoAnimal.
 */
@Entity
@Table(name = "observacao_animal")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ObservacaoAnimal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data")
    private ZonedDateTime data;

    @Column(name = "local")
    private String local;

    @Column(name = "comprimento")
    private Double comprimento;

    @Column(name = "altura")
    private Double altura;

    @Column(name = "peso")
    private Double peso;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public ObservacaoAnimal data(ZonedDateTime data) {
        this.data = data;
        return this;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public String getLocal() {
        return local;
    }

    public ObservacaoAnimal local(String local) {
        this.local = local;
        return this;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public Double getComprimento() {
        return comprimento;
    }

    public ObservacaoAnimal comprimento(Double comprimento) {
        this.comprimento = comprimento;
        return this;
    }

    public void setComprimento(Double comprimento) {
        this.comprimento = comprimento;
    }

    public Double getAltura() {
        return altura;
    }

    public ObservacaoAnimal altura(Double altura) {
        this.altura = altura;
        return this;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public Double getPeso() {
        return peso;
    }

    public ObservacaoAnimal peso(Double peso) {
        this.peso = peso;
        return this;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ObservacaoAnimal)) {
            return false;
        }
        return id != null && id.equals(((ObservacaoAnimal) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ObservacaoAnimal{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", local='" + getLocal() + "'" +
            ", comprimento=" + getComprimento() +
            ", altura=" + getAltura() +
            ", peso=" + getPeso() +
            "}";
    }
}
