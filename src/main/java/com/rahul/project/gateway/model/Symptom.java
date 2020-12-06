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

@Indexed(index = "symptom_index")
@Entity
@Data
@Table(name = "symptom_m")
//@Analyzer(impl = CustomLowerCaseAnalyzer.class)
@AnalyzerDef(name = "custom_analyzer", tokenizer = @TokenizerDef(factory = WhitespaceTokenizerFactory.class), filters = {
        @TokenFilterDef(factory = LowerCaseFilterFactory.class),
        @TokenFilterDef(factory = SnowballPorterFilterFactory.class, params =
                {@Parameter(name = "language", value = "English")}),
        @TokenFilterDef(factory = EdgeNGramFilterFactory.class, params = {@Parameter(name = "maxGramSize", value = "15")})

})
public class Symptom implements Serializable {

    @Id
    @NumericField
    @SequenceGenerator(name = "symptom_gen", allocationSize = 1, sequenceName = "symptom_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "symptom_gen")
    private Long id;

    @Field(store = Store.YES, analyzer = @Analyzer(definition = "custom_analyzer"/*impl = CustomLowerCaseAnalyzer.class*/))
    @Column(name = "symptom_name", length = 100)
    private String symptomName;
    @Column(name = "code", length = 50)
    private String code;
    @Basic
    @Column(name = "image_name")
    private String imageName;
    @ManyToOne
    @JoinColumn(name = "symptom_node")
    @JsonIgnoreProperties({"symptomNode", "symptomNodeAssessments"})
    private SymptomNode symptomNode;

    @JsonIgnore
    @OneToMany(mappedBy = "symptomEntity", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @MapKey(name = "localizedId.locale")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    private Map<String, LocalizedSymptom> localizations = new HashMap<>();

    @JsonInclude()
    @Transient
    private String label;

    public String getLabel() {
        return localizations.get(LocaleContextHolder.getLocale().getLanguage()) != null
                ? localizations.get(LocaleContextHolder.getLocale().getLanguage()).getLabel() : "";
    }

    /*@IndexedEmbedded
    @ManyToMany // owner side: it doesn't have mappedBy, and can decide how the association is mapped: with a join table
    @JoinTable(name = "symptom_pet_type_mp",
            joinColumns = {@JoinColumn(name = "symptom_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "pet_type_id", referencedColumnName = "id")})
    private Set<PetType> petTypes;*/

}
