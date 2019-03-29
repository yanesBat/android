package com.karman.ebcard.service.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;
import com.karman.ebcard.domain.enumeration.PaidPlan;

/**
 * A DTO for the EBCard entity.
 */
@ApiModel(description = "The EBCard entity.")
public class EBCardDTO implements Serializable {

    private Long id;

    /**
     * name
     */
    @ApiModelProperty(value = "name")
    private String firstName;

    /**
     * lastName
     */
    @ApiModelProperty(value = "lastName")
    private String lastName;

    /**
     * title
     */
    @ApiModelProperty(value = "title")
    private String title;

    /**
     * subtitle
     */
    @ApiModelProperty(value = "subtitle")
    private String subtitle;

    /**
     * bussinesName
     */
    @ApiModelProperty(value = "bussinesName")
    private String bussinesName;

    /**
     * occupation
     */
    @ApiModelProperty(value = "occupation")
    private String occupation;

    /**
     * email
     */
    @ApiModelProperty(value = "email")
    private String email;

    /**
     * rating
     */
    @ApiModelProperty(value = "rating")
    private Integer rating;

    /**
     * paidPlan
     */
    @ApiModelProperty(value = "paidPlan")
    private PaidPlan paidPlan;

    /**
     * imageUrl
     */
    @ApiModelProperty(value = "imageUrl")
    private String imageUrl;


    private Long categoryId;

    private Long userPropioId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getBussinesName() {
        return bussinesName;
    }

    public void setBussinesName(String bussinesName) {
        this.bussinesName = bussinesName;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public PaidPlan getPaidPlan() {
        return paidPlan;
    }

    public void setPaidPlan(PaidPlan paidPlan) {
        this.paidPlan = paidPlan;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getUserPropioId() {
        return userPropioId;
    }

    public void setUserPropioId(Long userPropioId) {
        this.userPropioId = userPropioId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EBCardDTO eBCardDTO = (EBCardDTO) o;
        if (eBCardDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), eBCardDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EBCardDTO{" +
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
            ", category=" + getCategoryId() +
            ", userPropio=" + getUserPropioId() +
            "}";
    }
}
