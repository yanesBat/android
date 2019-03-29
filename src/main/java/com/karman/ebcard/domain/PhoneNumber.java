package com.karman.ebcard.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.karman.ebcard.domain.enumeration.NetworkOperator;

/**
 * The PhoneNumber entity.
 * @author A true hipster
 */
@Entity
@Table(name = "phone_number")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PhoneNumber implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * fieldName
     */
    @Column(name = "jhi_number")
    private String number;

    /**
     * networkOperator
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "network_operator")
    private NetworkOperator networkOperator;

    /**
     * asWhatsapp
     */
    @Column(name = "has_whatsapp")
    private Boolean hasWhatsapp;

    @ManyToOne
    @JsonIgnoreProperties("phoneNumbers")
    private EBCard eBCard;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public PhoneNumber number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public NetworkOperator getNetworkOperator() {
        return networkOperator;
    }

    public PhoneNumber networkOperator(NetworkOperator networkOperator) {
        this.networkOperator = networkOperator;
        return this;
    }

    public void setNetworkOperator(NetworkOperator networkOperator) {
        this.networkOperator = networkOperator;
    }

    public Boolean isHasWhatsapp() {
        return hasWhatsapp;
    }

    public PhoneNumber hasWhatsapp(Boolean hasWhatsapp) {
        this.hasWhatsapp = hasWhatsapp;
        return this;
    }

    public void setHasWhatsapp(Boolean hasWhatsapp) {
        this.hasWhatsapp = hasWhatsapp;
    }

    public EBCard getEBCard() {
        return eBCard;
    }

    public PhoneNumber eBCard(EBCard eBCard) {
        this.eBCard = eBCard;
        return this;
    }

    public void setEBCard(EBCard eBCard) {
        this.eBCard = eBCard;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PhoneNumber phoneNumber = (PhoneNumber) o;
        if (phoneNumber.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), phoneNumber.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PhoneNumber{" +
            "id=" + getId() +
            ", number='" + getNumber() + "'" +
            ", networkOperator='" + getNetworkOperator() + "'" +
            ", hasWhatsapp='" + isHasWhatsapp() + "'" +
            "}";
    }
}
