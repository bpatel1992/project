package com.rahul.project.gateway.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * It is the pet type : dog or cat
 *
 * @author rahul malhotra
 * date 2019-05-21
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "pet_type_m")
public class PetType implements Serializable {
    @Id
    @Column(name = "id")
    private Long id;
    //    @Field(store = Store.YES, analyzer = @Analyzer(impl = CustomLowerCaseAnalyzer.class))
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "image_name")
    private String imageName;
    @Basic
    @Column(name = "status", columnDefinition = "boolean default false", nullable = false)
    private boolean status;
    @JsonIgnore
    @OneToMany(mappedBy = "petTypeEntity", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @MapKey(name = "localizedId.locale")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    private Map<String, LocalizedPetType> localizations = new HashMap<>();
    @JsonInclude()
    @Transient
    private String label;

    public PetType(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return localizations.get(LocaleContextHolder.getLocale().getLanguage()) != null
                ? localizations.get(LocaleContextHolder.getLocale().getLanguage()).getLabel() : null;
    }

  /*  @JsonIgnoreProperties("petTypes")
    @JsonIgnore
    @ContainedIn
    @ManyToMany // owner side: it doesn'data have mappedBy, and can decide how the association is mapped: with a join table
    @JoinTable(name = "symptom_pet_type_mp",
            joinColumns = {@JoinColumn(name = "pet_type_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "symptom_id", referencedColumnName = "id")})
    private Set<Symptom> symptoms;*/

}
