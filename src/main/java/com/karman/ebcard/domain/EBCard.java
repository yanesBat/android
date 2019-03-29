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

import com.karman.ebcard.domain.enumeration.PaidPlan;

/**
 * The EBCard entity.
 */
@Entity
@Table(name = "eb_card")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EBCard implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * name
     */
    @Column(name = "first_name")
    private String firstName;

    /**
     * lastName
     */
    @Column(name = "last_name")
    private String lastName;

    /**
     * title
     */
    @Column(name = "title")
    private String title;

    /**
     * subtitle
     */
    @Column(name = "subtitle")
    private String subtitle;

    /**
     * bussinesName
     */
    @Column(name = "bussines_name")
    private String bussinesName;

    /**
     * occupation
     */
    @Column(name = "occupation")
    private String occupation;

    /**
     * email
     */
    @Column(name = "email")
    private String email;

    /**
     * rating
     */
    @Column(name = "rating")
    private Integer rating;

    /**
     * paidPlan
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "paid_plan")
    private PaidPlan paidPlan;

    /**
     * imageUrl
     */
    @Column(name = "image_url")
    private String imageUrl;

    @OneToMany(mappedBy = "eBCard")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Review> reviews = new HashSet<>();
    @OneToMany(mappedBy = "eBCard")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SocialContact> socialContacts = new HashSet<>();
    @OneToMany(mappedBy = "eBCard")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PhoneNumber> phoneNumbers = new HashSet<>();
    @OneToMany(mappedBy = "eBCard")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Address> addresses = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("eBCards")
    private Category category;

    @ManyToMany(mappedBy = "wallets")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<User> users = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("personals")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public EBCard firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public EBCard lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTitle() {
        return title;
    }

    public EBCard title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public EBCard subtitle(String subtitle) {
        this.subtitle = subtitle;
        return this;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getBussinesName() {
        return bussinesName;
    }

    public EBCard bussinesName(String bussinesName) {
        this.bussinesName = bussinesName;
        return this;
    }

    public void setBussinesName(String bussinesName) {
        this.bussinesName = bussinesName;
    }

    public String getOccupation() {
        return occupation;
    }

    public EBCard occupation(String occupation) {
        this.occupation = occupation;
        return this;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getEmail() {
        return email;
    }

    public EBCard email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getRating() {
        return rating;
    }

    public EBCard rating(Integer rating) {
        this.rating = rating;
        return this;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public PaidPlan getPaidPlan() {
        return paidPlan;
    }

    public EBCard paidPlan(PaidPlan paidPlan) {
        this.paidPlan = paidPlan;
        return this;
    }

    public void setPaidPlan(PaidPlan paidPlan) {
        this.paidPlan = paidPlan;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public EBCard imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public EBCard reviews(Set<Review> reviews) {
        this.reviews = reviews;
        return this;
    }

    public EBCard addReview(Review review) {
        this.reviews.add(review);
        review.setEBCard(this);
        return this;
    }

    public EBCard removeReview(Review review) {
        this.reviews.remove(review);
        review.setEBCard(null);
        return this;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public Set<SocialContact> getSocialContacts() {
        return socialContacts;
    }

    public EBCard socialContacts(Set<SocialContact> socialContacts) {
        this.socialContacts = socialContacts;
        return this;
    }

    public EBCard addSocialContact(SocialContact socialContact) {
        this.socialContacts.add(socialContact);
        socialContact.setEBCard(this);
        return this;
    }

    public EBCard removeSocialContact(SocialContact socialContact) {
        this.socialContacts.remove(socialContact);
        socialContact.setEBCard(null);
        return this;
    }

    public void setSocialContacts(Set<SocialContact> socialContacts) {
        this.socialContacts = socialContacts;
    }

    public Set<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public EBCard phoneNumbers(Set<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
        return this;
    }

    public EBCard addPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumbers.add(phoneNumber);
        phoneNumber.setEBCard(this);
        return this;
    }

    public EBCard removePhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumbers.remove(phoneNumber);
        phoneNumber.setEBCard(null);
        return this;
    }

    public void setPhoneNumbers(Set<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public EBCard addresses(Set<Address> addresses) {
        this.addresses = addresses;
        return this;
    }

    public EBCard addAddress(Address address) {
        this.addresses.add(address);
        address.setEBCard(this);
        return this;
    }

    public EBCard removeAddress(Address address) {
        this.addresses.remove(address);
        address.setEBCard(null);
        return this;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public Category getCategory() {
        return category;
    }

    public EBCard category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<User> getUsers() {
        return users;
    }

    public EBCard users(Set<User> users) {
        this.users = users;
        return this;
    }

    public EBCard addUser(User user) {
        this.users.add(user);
        user.getWallets().add(this);
        return this;
    }

    public EBCard removeUser(User user) {
        this.users.remove(user);
        user.getWallets().remove(this);
        return this;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public User getUser() {
        return user;
    }

    public EBCard user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
        EBCard eBCard = (EBCard) o;
        if (eBCard.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), eBCard.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EBCard{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", title='" + getTitle() + "'" +
            ", subtitle='" + getSubtitle() + "'" +
            ", bussinesName='" + getBussinesName() + "'" +
            ", occupation='" + getOccupation() + "'" +
            ", email='" + getEmail() + "'" +
            ", rating=" + getRating() +
            ", paidPlan='" + getPaidPlan() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            "}";
    }
}
