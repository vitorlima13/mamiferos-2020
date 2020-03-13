package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

import com.mycompany.myapp.domain.enumeration.TipoMamifero;

/**
 * A Mamifero.
 */
@Entity
@Table(name = "mamifero")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Mamifero implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoMamifero tipo;

    @Column(name = "apelido")
    private String apelido;

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "altura")
    private Double altura;

    @Column(name = "peso")
    private Double peso;

    @Column(name = "comprimento")
    private Double comprimento;

    @OneToMany(mappedBy = "progenitora")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Mamifero> filhotes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("filhotes")
    private Mamifero progenitora;

    @ManyToOne
    @JsonIgnoreProperties("mamiferos")
    private Loja loja;

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

    public Mamifero nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoMamifero getTipo() {
        return tipo;
    }

    public Mamifero tipo(TipoMamifero tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(TipoMamifero tipo) {
        this.tipo = tipo;
    }

    public String getApelido() {
        return apelido;
    }

    public Mamifero apelido(String apelido) {
        this.apelido = apelido;
        return this;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public Integer getNumero() {
        return numero;
    }

    public Mamifero numero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Double getAltura() {
        return altura;
    }

    public Mamifero altura(Double altura) {
        this.altura = altura;
        return this;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public Double getPeso() {
        return peso;
    }

    public Mamifero peso(Double peso) {
        this.peso = peso;
        return this;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getComprimento() {
        return comprimento;
    }

    public Mamifero comprimento(Double comprimento) {
        this.comprimento = comprimento;
        return this;
    }

    public void setComprimento(Double comprimento) {
        this.comprimento = comprimento;
    }

    public Set<Mamifero> getFilhotes() {
        return filhotes;
    }

    public Mamifero filhotes(Set<Mamifero> mamiferos) {
        this.filhotes = mamiferos;
        return this;
    }

    public Mamifero addFilhotes(Mamifero mamifero) {
        this.filhotes.add(mamifero);
        mamifero.setProgenitora(this);
        return this;
    }

    public Mamifero removeFilhotes(Mamifero mamifero) {
        this.filhotes.remove(mamifero);
        mamifero.setProgenitora(null);
        return this;
    }

    public void setFilhotes(Set<Mamifero> mamiferos) {
        this.filhotes = mamiferos;
    }

    public Mamifero getProgenitora() {
        return progenitora;
    }

    public Mamifero progenitora(Mamifero mamifero) {
        this.progenitora = mamifero;
        return this;
    }

    public void setProgenitora(Mamifero mamifero) {
        this.progenitora = mamifero;
    }

    public Loja getLoja() {
        return loja;
    }

    public Mamifero loja(Loja loja) {
        this.loja = loja;
        return this;
    }

    public void setLoja(Loja loja) {
        this.loja = loja;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Mamifero)) {
            return false;
        }
        return id != null && id.equals(((Mamifero) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Mamifero{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", apelido='" + getApelido() + "'" +
            ", numero=" + getNumero() +
            ", altura=" + getAltura() +
            ", peso=" + getPeso() +
            ", comprimento=" + getComprimento() +
            "}";
    }
}
