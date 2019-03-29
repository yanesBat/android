package com.karman.ebcard.service.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;
import com.karman.ebcard.domain.enumeration.NetworkOperator;

/**
 * A DTO for the PhoneNumber entity.
 */
@ApiModel(description = "The PhoneNumber entity. @author A true hipster")
public class PhoneNumberDTO implements Serializable {

    private Long id;

    /**
     * fieldName
     */
    @ApiModelProperty(value = "fieldName")
    private String number;

    /**
     * networkOperator
     */
    @ApiModelProperty(value = "networkOperator")
    private NetworkOperator networkOperator;

    /**
     * asWhatsapp
     */
    @ApiModelProperty(value = "asWhatsapp")
    private Boolean hasWhatsapp;


    private Long eBCardId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public NetworkOperator getNetworkOperator() {
        return networkOperator;
    }

    public void setNetworkOperator(NetworkOperator networkOperator) {
        this.networkOperator = networkOperator;
    }

    public Boolean isHasWhatsapp() {
        return hasWhatsapp;
    }

    public void setHasWhatsapp(Boolean hasWhatsapp) {
        this.hasWhatsapp = hasWhatsapp;
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

        PhoneNumberDTO phoneNumberDTO = (PhoneNumberDTO) o;
        if (phoneNumberDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), phoneNumberDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PhoneNumberDTO{" +
            "id=" + getId() +
            ", number='" + getNumber() + "'" +
            ", networkOperator='" + getNetworkOperator() + "'" +
            ", hasWhatsapp='" + isHasWhatsapp() + "'" +
            ", eBCard=" + getEBCardId() +
            "}";
    }
}
