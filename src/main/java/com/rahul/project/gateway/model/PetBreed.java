package com.rahul.project.gateway.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * @author rahul malhotra
 * date 2019-05-21
 */
@Data
@Entity
@Table(name = "pet_breed")
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetBreed implements Serializable {
    @Id
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "image_name")
    private String imageName;
    @Basic
    @Column(name = "status", columnDefinition = "boolean default true")
    private Boolean status;
    @ManyToOne
    @JoinColumn(name = "pt_id", referencedColumnName = "id")
    private PetType petType;
    @ManyToOne
    @JoinColumn(name = "pbg_id", referencedColumnName = "id")
    private PetBreedGroup petBreedGroup;
    //need the gender of pet -- mange in pet table
    @ManyToOne
    @JoinColumn(name = "pbl_id", referencedColumnName = "id")
    private PetBreedLifespan petBreedLifespan;
    @JoinTable(
            name = "breed_type_mp",
            joinColumns = @JoinColumn(
                    name = "breed_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "type_id",
                    referencedColumnName = "id"
            )
    )
    @ManyToMany
    private Set<PetBreedType> petBreedTypes;
    @JoinTable(
            name = "breed_color",
            joinColumns = @JoinColumn(
                    name = "breed_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "color_id",
                    referencedColumnName = "id"
            )
    )
    @ManyToMany
    private Set<PetColor> petColor;
    @JoinTable(
            name = "breed_desc_mp",
            joinColumns = @JoinColumn(
                    name = "breed_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "pet_desc_id",
                    referencedColumnName = "id"
            )
    )
    @ManyToMany
    private Set<PetDescription> petDescriptions;
    @ManyToOne
    @JoinColumn(name = "pet_adult_range_breed_id", referencedColumnName = "id")
    private PetAdultAgeRange petAdultAgeRange;
    @ManyToOne
    @JoinColumn(name = "pet_puppy_range_breed_id", referencedColumnName = "id")
    private PetPuppyAgeRange petPuppyAgeRange;
    @JoinTable(
            name = "breed_title_mp",
            joinColumns = @JoinColumn(
                    name = "breed_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "title_id",
                    referencedColumnName = "id"
            )
    )
    @ManyToMany
    private Set<PetBreedTitle> petBreedTitles;
    @ManyToMany()
    @JoinTable(name = "breed_personality", joinColumns = @JoinColumn(name = "breed_id"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    private Set<PetPersonalityTraits> petPersonalityTraits;
    @ManyToMany()
    @JoinTable(name = "breed_temperament", joinColumns = @JoinColumn(name = "breed_id"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    private Set<PetTemperament> petTemperaments;
    @ManyToMany()
    @JoinTable(name = "breed_physical_attributes", joinColumns = @JoinColumn(name = "breed_id"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    private Set<PetPhysicalAttributes> petPhysicalAttributes;
    @ManyToMany()
    @JoinTable(name = "breed_training", joinColumns = @JoinColumn(name = "breed_id"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    private Set<PetTraining> petTrainings;
    @ManyToMany()
    @JoinTable(name = "breed_grooming", joinColumns = @JoinColumn(name = "breed_id"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    private Set<PetGrooming> petGrooming;
    @ManyToMany()
    @JoinTable(name = "breed_health_issues", joinColumns = @JoinColumn(name = "breed_id"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    private Set<PetHealthIssues> petHealthIssues;
    @ManyToMany()
    @JoinTable(name = "breed_exercise", joinColumns = @JoinColumn(name = "breed_id"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    private Set<PetExercise> petExercises;
    @ManyToMany()
    @JoinTable(name = "breed_behaviour", joinColumns = @JoinColumn(name = "breed_id"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    private Set<PetBehaviour> petBehaviours;
    @ManyToMany()
    @JoinTable(name = "breed_quick_facts", joinColumns = @JoinColumn(name = "breed_id"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    private Set<PetQuickFacts> petQuickFacts;

    public PetBreed(Long id) {
        this.id = id;
    }
}
