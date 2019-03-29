package com.karman.ebcard.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.karman.ebcard.domain.enumeration.SocialNetwork;

/**
 * The SocialContact entity.
 * @author A true hipster
 */
@Entity
@Table(name = "social_contact")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SocialContact implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * socialNetwork
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "social_network")
    private SocialNetwork socialNetwork;

    /**
     * Link
     */
    @Column(name = "jhi_link")
    private String link;

    @ManyToOne
    @JsonIgnoreProperties("socialContacts")
    private EBCard eBCard;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SocialNetwork getSocialNetwork() {
        return socialNetwork;
    }

    public SocialContact socialNetwork(SocialNetwork socialNetwork) {
        this.socialNetwork = socialNetwork;
        return this;
    }

    public void setSocialNetwork(SocialNetwork socialNetwork) {
        this.socialNetwork = socialNetwork;
    }

    public String getLink() {
        return link;
    }

    public SocialContact link(String link) {
        this.link = link;
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public EBCard getEBCard() {
        return eBCard;
    }

    public SocialContact eBCard(EBCard eBCard) {
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
        SocialContact socialContact = (SocialContact) o;
        if (socialContact.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), socialContact.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SocialContact{" +
            "id=" + getId() +
            ", socialNetwork='" + getSocialNetwork() + "'" +
            ", link='" + getLink() + "'" +
            "}";
    }
}
