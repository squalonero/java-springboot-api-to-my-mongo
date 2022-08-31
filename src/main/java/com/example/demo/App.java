package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//required for MongoDb operations
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.example.demo.service.BookService;


@SpringBootApplication
@EnableMongoRepositories
public class App implements CommandLineRunner {

  @Autowired
  private BookService bookService;

  public static void main(String[] args) {
    try {
      SpringApplication.run(App.class, args);
    } catch (Exception e) {
      System.out.println("Errore");
    }
  }

  @Override
  public void run(String... args) {
    System.out.println("-------------EMPTY TRASH-------------------------------\n");
    bookService.deleteAllBooks();
    System.out.println("-------------CREATE BOOKS-------------------------------\n");
    bookService.createBook();
    System.out.println("\n----------------SHOW ALL BOOKS---------------------------\n");
    bookService.showAllBooks();
    System.out.println("\n--------------GET ITEM BY NAME-----------------------------------\n");
    bookService.getBookByTitle("The Way of the Kings");
    System.out.println("\n-----------GET ITEMS BY GENRE---------------------------------\n");
    bookService.getItemsByGenre("horror");
    System.out.println("\n-----------UPDATE GENRE NAME OF FANTASY GENRE----------------\n");
    bookService.updateGenreTitle("fantasy");
    System.out.println("\n----------DELETE A BOOK----------------------------------\n");
    bookService.deleteBook("630670ea730c4cac165437ab");
    System.out.println("\n------------FINAL COUNT OF BOOKS-------------------------\n");
    bookService.findCountOfBooks();
    System.out.println("\n-------------------THANK YOU---------------------------");
  }


}