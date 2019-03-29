package com.karman.ebcard.service.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Address entity.
 */
@ApiModel(description = "The Address entity. @author A true hipster")
public class AddressDTO implements Serializable {

    private Long id;

    /**
     * name
     */
    @ApiModelProperty(value = "name")
    private String name;

    /**
     * address
     */
    @ApiModelProperty(value = "address")
    private String address;

    /**
     * latitude
     */
    @ApiModelProperty(value = "latitude")
    private String latitude;

    /**
     * longitud
     */
    @ApiModelProperty(value = "longitud")
    private String longitud;


    private Long eBCardId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
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

        AddressDTO addressDTO = (AddressDTO) o;
        if (addressDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), addressDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AddressDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            ", latitude='" + getLatitude() + "'" +
            ", longitud='" + getLongitud() + "'" +
            ", eBCard=" + getEBCardId() +
            "}";
    }
}
