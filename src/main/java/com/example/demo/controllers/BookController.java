package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class BookController {

    // @RequestMapping("/ciao")
    // public String saluta() {
    // return "Welcome";
    // }

    // @RequestMapping("/items") // path endpoint
    // public ArrayList<String> list() {
    // ArrayList<String> list = new ArrayList<String>();
    // list.add("Porco");
    // list.add("Dio");
    // return list;
    // }

    // @RequestMapping("/object")
    // public Map<String, Object> obj() throws JsonProcessingException {
    // Persona persona = new Persona("Gesù", "Cristo", 33, "Gesù@PadreTerno.inf",
    // "Paradiso",
    // "/Users/p.piras/Documents/progetti/spring-demo/src/image/maxresdefault.jpeg");

    // ObjectMapper ow = new ObjectMapper();

    // Map<String, Object> obj = ow.convertValue(persona, Map.class);

    // return obj;
    // }
}
