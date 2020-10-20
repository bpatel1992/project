package com.rahul.project.gateway.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * This class defines the access privilege of a User while performing actions
 * <p>
 * ACCESS_DENIED("No Access", 0),
 * <p>
 * VIEW("View", 1),
 * <p>
 * CREATE("Create",2),
 * <p>
 * UPDATE("Update",3),
 * <p>
 * DELETE("Delete",4);
 *
 * @author Rahul Malhotra
 */
@Getter
@Setter
@Entity
@Table(name = "privilege_m")
public class Privilege implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "privilege_id")
    private Long id;

    @Column(name = "privilege_name")
    private String privilege;

    @Column(name = "privilege_desc")
    private String privilegeDesc;

    @Column(name = "status", columnDefinition = "boolean default true", nullable = false)
    private boolean status;

    @JsonIgnore
    @OneToMany(mappedBy = "privilegeEntity", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @MapKey(name = "localizedId.locale")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    private Map<String, LocalizedPrivilege> localizations = new HashMap<>();

    @JsonInclude()
    @Transient
    private String label;

    public String getLabel() {
        return localizations.get(LocaleContextHolder.getLocale().getLanguage()) != null
                ? localizations.get(LocaleContextHolder.getLocale().getLanguage()).getLabel() : "";
    }
}
