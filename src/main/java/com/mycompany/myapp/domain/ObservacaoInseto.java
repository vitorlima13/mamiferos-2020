package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.ZonedDateTime;

/**
 * A ObservacaoInseto.
 */
@Entity
@Table(name = "observacao_inseto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ObservacaoInseto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data")
    private ZonedDateTime data;

    @Column(name = "local")
    private String local;

    @Column(name = "quantidade")
    private Integer quantidade;

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

    public ObservacaoInseto data(ZonedDateTime data) {
        this.data = data;
        return this;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public String getLocal() {
        return local;
    }

    public ObservacaoInseto local(String local) {
        this.local = local;
        return this;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public ObservacaoInseto quantidade(Integer quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ObservacaoInseto)) {
            return false;
        }
        return id != null && id.equals(((ObservacaoInseto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ObservacaoInseto{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", local='" + getLocal() + "'" +
            ", quantidade=" + getQuantidade() +
            "}";
    }
}
