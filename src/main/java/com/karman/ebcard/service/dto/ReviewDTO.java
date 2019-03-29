package com.karman.ebcard.service.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Review entity.
 */
@ApiModel(description = "The Review entity.")
public class ReviewDTO implements Serializable {

    private Long id;

    /**
     * rating
     */
    @ApiModelProperty(value = "rating")
    private Integer rating;

    /**
     * comment
     */
    @ApiModelProperty(value = "comment")
    private String comment;


    private Long userPropioId;

    private Long eBCardId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getUserPropioId() {
        return userPropioId;
    }

    public void setUserPropioId(Long userPropioId) {
        this.userPropioId = userPropioId;
    }

    public Long getEBCardId() {
        return eBCardId;
    }

    public void setEBCardId(Long eBCardId) {
        this.eBCardId = eBCardId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReviewDTO reviewDTO = (ReviewDTO) o;
        if (reviewDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reviewDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReviewDTO{" +
            "id=" + getId() +
            ", rating=" + getRating() +
            ", comment='" + getComment() + "'" +
            ", userPropio=" + getUserPropioId() +
            ", eBCard=" + getEBCardId() +
            "}";
    }
}
