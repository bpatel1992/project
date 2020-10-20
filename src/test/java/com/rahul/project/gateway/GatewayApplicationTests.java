//package com.rahul.project.gateway;
//
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//
//@SpringBootTest
//public class GatewayApplicationTests {
//
//    @Mock
//    List<String> mockedList;
//
//    @Test
//    public void whenUseMockAnnotation_thenMockIsInjected() {
//        mockedList.add("one");
//        Mockito.verify(mockedList).add("one");
//        assertEquals(0, mockedList.size());
//
//        Mockito.when(mockedList.size()).thenReturn(100);
//        assertEquals(100, mockedList.size());
//    }
//
//}
//
