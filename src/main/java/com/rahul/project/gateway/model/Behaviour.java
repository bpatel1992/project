package com.rahul.project.gateway.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.WhitespaceTokenizerFactory;
import org.apache.lucene.analysis.ngram.EdgeNGramFilterFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.*;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Indexed(index = "behaviour_index")
@Entity
@Data
@Table(name = "behaviour_m")
//@Analyzer(impl = CustomLowerCaseAnalyzer.class)
@AnalyzerDef(name = "behaviour_custom_analyzer", tokenizer = @TokenizerDef(factory = WhitespaceTokenizerFactory.class), filters = {
        @TokenFilterDef(factory = LowerCaseFilterFactory.class),
        @TokenFilterDef(factory = SnowballPorterFilterFactory.class, params =
                {@Parameter(name = "language", value = "English")}),
        @TokenFilterDef(factory = EdgeNGramFilterFactory.class, params = {@Parameter(name = "maxGramSize", value = "15")})

})
public class Behaviour implements Serializable {

    @Id
    @NumericField
    @SequenceGenerator(name = "behaviour_gen", allocationSize = 1, sequenceName = "behaviour_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "behaviour_gen")
    private Long id;

    @Field(store = Store.YES, analyzer = @Analyzer(definition = "custom_analyzer"/*impl = CustomLowerCaseAnalyzer.class*/))
    @Column(name = "behaviour_name", length = 100)
    private String behaviourName;
    @Column(name = "code", length = 50)
    private String code;
    @Basic
    @Column(name = "image_name")
    private String imageName;
    @ManyToOne
    @JoinColumn(name = "behaviour_node")
    @JsonIgnoreProperties({"behaviourNode", "behaviourNodeAssessments"})
    private SymptomNode behaviourNode;

    @JsonIgnore
    @OneToMany(mappedBy = "behaviourEntity", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @MapKey(name = "localizedId.locale")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    private Map<String, LocalizedBehaviour> localizations = new HashMap<>();

    @JsonInclude()
    @Transient
    private String label;

    public String getLabel() {
        return localizations.get(LocaleContextHolder.getLocale().getLanguage()) != null
                ? localizations.get(LocaleContextHolder.getLocale().getLanguage()).getLabel() : null;
    }

    /*@IndexedEmbedded
    @ManyToMany // owner side: it doesn'data have mappedBy, and can decide how the association is mapped: with a join table
    @JoinTable(name = "behaviour_pet_type_mp",
            joinColumns = {@JoinColumn(name = "behaviour_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "pet_type_id", referencedColumnName = "id")})
    private Set<PetType> petTypes;*/

}
