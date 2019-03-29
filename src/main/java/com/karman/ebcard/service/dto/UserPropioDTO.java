package com.karman.ebcard.service.dto;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the UserPropio entity.
 */
public class UserPropioDTO implements Serializable {

    private Long id;

    private String name;


    private Set<EBCardDTO> wallets = new HashSet<>();

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

    public Set<EBCardDTO> getWallets() {
        return wallets;
    }

    public void setWallets(Set<EBCardDTO> eBCards) {
        this.wallets = eBCards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserPropioDTO userPropioDTO = (UserPropioDTO) o;
        if (userPropioDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userPropioDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserPropioDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
