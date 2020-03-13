package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.mycompany.myapp.domain.enumeration.TipoInseto;

/**
 * A Inseto.
 */
@Entity
@Table(name = "inseto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Inseto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoInseto tipo;

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "altura")
    private Double altura;

    @Column(name = "peso")
    private Double peso;

    @Column(name = "comprimento")
    private Double comprimento;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoInseto getTipo() {
        return tipo;
    }

    public Inseto tipo(TipoInseto tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(TipoInseto tipo) {
        this.tipo = tipo;
    }

    public Integer getNumero() {
        return numero;
    }

    public Inseto numero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Double getAltura() {
        return altura;
    }

    public Inseto altura(Double altura) {
        this.altura = altura;
        return this;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public Double getPeso() {
        return peso;
    }

    public Inseto peso(Double peso) {
        this.peso = peso;
        return this;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getComprimento() {
        return comprimento;
    }

    public Inseto comprimento(Double comprimento) {
        this.comprimento = comprimento;
        return this;
    }

    public void setComprimento(Double comprimento) {
        this.comprimento = comprimento;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Inseto)) {
            return false;
        }
        return id != null && id.equals(((Inseto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Inseto{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", numero=" + getNumero() +
            ", altura=" + getAltura() +
            ", peso=" + getPeso() +
            ", comprimento=" + getComprimento() +
            "}";
    }
}
