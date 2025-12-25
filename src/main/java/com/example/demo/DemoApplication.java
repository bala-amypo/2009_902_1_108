package com.example.demo;

import com.example.demo.servlet.HelloServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DynamicEventTicketPricingEngineApplicationTests {

    private HelloServlet servlet;

    @BeforeEach
    public void setUp() {
        servlet = new HelloServlet();
    }

    @Test
    public void testDoGet() throws Exception {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        Mockito.when(response.getWriter()).thenReturn(writer);

        servlet.doGet(request, response);

        writer.flush();
        String output = stringWriter.toString();
        assertTrue(output.contains("Hello from HelloServlet!"));
    }
}
