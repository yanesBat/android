package com.karman.ebcard.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * The Category entity.
 * @author A true hipster
 */
@Entity
@Table(name = "category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * name
     */
    @Column(name = "name")
    private String name;

    /**
     * description
     */
    @Column(name = "description")
    private String description;

    @ManyToOne
    @JsonIgnoreProperties("subCategories")
    private Category category;

    @OneToMany(mappedBy = "category")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Category> subCategories = new HashSet<>();
    @OneToMany(mappedBy = "category")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EBCard> eBCards = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Category name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Category description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public Category category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<Category> getSubCategories() {
        return subCategories;
    }

    public Category subCategories(Set<Category> categories) {
        this.subCategories = categories;
        return this;
    }

    public Category addSubCategory(Category category) {
        this.subCategories.add(category);
        category.setCategory(this);
        return this;
    }

    public Category removeSubCategory(Category category) {
        this.subCategories.remove(category);
        category.setCategory(null);
        return this;
    }

    public void setSubCategories(Set<Category> categories) {
        this.subCategories = categories;
    }

    public Set<EBCard> getEBCards() {
        return eBCards;
    }

    public Category eBCards(Set<EBCard> eBCards) {
        this.eBCards = eBCards;
        return this;
    }

    public Category addEBCard(EBCard eBCard) {
        this.eBCards.add(eBCard);
        eBCard.setCategory(this);
        return this;
    }

    public Category removeEBCard(EBCard eBCard) {
        this.eBCards.remove(eBCard);
        eBCard.setCategory(null);
        return this;
    }

    public void setEBCards(Set<EBCard> eBCards) {
        this.eBCards = eBCards;
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
        Category category = (Category) o;
        if (category.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), category.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Category{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
