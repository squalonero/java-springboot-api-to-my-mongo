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
    System.out.println(" 🧹🧹🧹 Empty trash 🧹🧹🧹 \n");
    bookService.deleteAllBooks();
    System.out.println(" ✳️✳️✳️ Create some Books ✳️✳️✳️ \n");
    bookService.createBook();
    System.out.println("\n 📖 List all Books 📖 \n");
    bookService.showAllBooks();
    System.out.println("\n 🔍 Get item By title 🔍 \n");
    bookService.getBookByTitle("The Way of the Kings");
    System.out.println("\n 🔎 Get Books By genre 🔎 \n");
    bookService.getItemsByGenre("horror");
    System.out.println("\n 👾 Update genre name of fantasy name 👾 \n");
    bookService.updateGenreTitle("fantasy");
    System.out.println("\n ❌ Delete a Book ❌ \n");
    bookService.deleteBook("630670ea730c4cac165437ab");
    System.out.println("\n 🏁 Final Count of Books 🏁 \n");
    bookService.findCountOfBooks();
    System.out.println("\n RIP 💀");
  }


}