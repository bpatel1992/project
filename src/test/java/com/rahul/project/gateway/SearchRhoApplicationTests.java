//package com.rahul.project.gateway;
//
//import com.rahul.project.gateway.model.Card;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//
//@SpringBootTest
//public class SearchRhoApplicationTests {
//
//
//    @Autowired
//    private CardService cardService;
//
//    @Autowired
//    private HibernateSearchService hibernateSearchServiceImpl;
//
//
//    @Test
//    public void searcMultipleTermWithTypos() {
//
//        cardService.addCards();
//        List<Card> cardList = hibernateSearchServiceImpl.magicSearch("Bob Gibson");
//        assert (cardList.size() == 1);
//        assert (cardList.get(0).getHolderName().equals("Bob Gibson"));
//
//        cardList = hibernateSearchServiceImpl.magicSearch("Bob Giibsssson");
//        assert (cardList.size() == 1);
//        assert (cardList.get(0).getHolderName().equals("Bob Gibson"));
//
//
//        cardList = hibernateSearchServiceImpl.magicSearch("Romero");
//        assert (cardList.size() == 1);
//        assert (cardList.get(0).getHolderSurname().equals("Romero"));
//
//
//        cardList = hibernateSearchServiceImpl.magicSearch("Romerro");
//        assert (cardList.size() == 1);
//        assert (cardList.get(0).getHolderSurname().equals("Romero"));
//
//
//        cardList = hibernateSearchServiceImpl.magicSearch("black");
//        assert (cardList.size() == 2);
//
//    }
//
//}
