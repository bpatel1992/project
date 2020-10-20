package com.rahul.project.gateway.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author rahul malhotra
 * date 2019-05-21
 */
@Embeddable
@NoArgsConstructor
@Getter
@Setter
public class UserPetRelationMpPK implements Serializable {

    @ManyToOne
    @JoinColumn(name = "from_user_id", referencedColumnName = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "pet_id", referencedColumnName = "id")
    Pet pet;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPetRelationMpPK that = (UserPetRelationMpPK) o;
        return Objects.equals(user.getId(), that.user.getId()) &&
                Objects.equals(pet.getId(), that.pet.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, pet);
    }
}
