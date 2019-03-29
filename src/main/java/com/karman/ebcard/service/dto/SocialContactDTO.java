package com.karman.ebcard.service.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;
import com.karman.ebcard.domain.enumeration.SocialNetwork;

/**
 * A DTO for the SocialContact entity.
 */
@ApiModel(description = "The SocialContact entity. @author A true hipster")
public class SocialContactDTO implements Serializable {

    private Long id;

    /**
     * socialNetwork
     */
    @ApiModelProperty(value = "socialNetwork")
    private SocialNetwork socialNetwork;

    /**
     * Link
     */
    @ApiModelProperty(value = "Link")
    private String link;


    private Long eBCardId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SocialNetwork getSocialNetwork() {
        return socialNetwork;
    }

    public void setSocialNetwork(SocialNetwork socialNetwork) {
        this.socialNetwork = socialNetwork;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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

        SocialContactDTO socialContactDTO = (SocialContactDTO) o;
        if (socialContactDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), socialContactDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SocialContactDTO{" +
            "id=" + getId() +
            ", socialNetwork='" + getSocialNetwork() + "'" +
            ", link='" + getLink() + "'" +
            ", eBCard=" + getEBCardId() +
            "}";
    }
}
