package com.rahul.project.gateway.service;

import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.configuration.hibernate.search.analyzer.CustomLowerCaseAnalyzer;
import com.rahul.project.gateway.dao.AbstractDao;
import com.rahul.project.gateway.dto.SymptomDTO;
import com.rahul.project.gateway.model.Symptom;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@TransactionalService
public class SymptomCheckerService {

    @Autowired
    AbstractDao abstractDao;

    @PersistenceContext
    private EntityManager entityManager;


    public List<Symptom> getSymptoms(SymptomDTO symptomDTO) throws Exception {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        // fields we are interested in
        String[] fields = {"symptomName"/*, "petTypes.name"*/};
        Analyzer analyzer = new CustomLowerCaseAnalyzer();
        // to set MUST for all fields we need an instance of the parser
        MultiFieldQueryParser mfqp = new MultiFieldQueryParser(fields, analyzer);
        mfqp.setDefaultOperator(QueryParser.Operator.AND);
        org.apache.lucene.search.Query luceneQuery = mfqp.parse(symptomDTO.getPetType() + " " + symptomDTO.getSymptom());
        javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Symptom.class);
        return jpaQuery.getResultList();
    }

    /*public List<Symptom> getSymptomsByPetType(String petType) {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Symptom.class).get();

        Query luceneQuery = qb.keyword()
                .onFields("petTypes.name").matching(petType).createQuery();

        javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Symptom.class);
        return jpaQuery.getResultList();
    }*/

    public List<Symptom> getSymptoms(String symptom) {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Symptom.class).get();

        /*Query luceneQuery = qb.keyword().fuzzy().withEditDistanceUpTo(2).withPrefixLength(10)
                .onFields("symptomName").matching(symptom).createQuery();*/
        Query luceneQuery = qb.keyword()
                .onFields("symptomName").matching(symptom).createQuery();
        javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Symptom.class);
        return jpaQuery.getResultList();
    }
}
