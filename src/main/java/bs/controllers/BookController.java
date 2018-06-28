package bs.controllers;

import bs.entities.BookEntity;
import bs.entities.VocabEntity;
import bs.models.*;
import bs.repositories.BookRepository;
import bs.repositories.UserRepository;
import bs.repositories.VocabRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final VocabRepository vocabRepository;

    @Autowired
    public BookController(UserRepository userRepository, BookRepository bookRepository,
                          VocabRepository vocabRepository){
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.vocabRepository = vocabRepository;
    }

    @GetMapping()
    @ResponseStatus(value = HttpStatus.OK)
    public List<Book> getAllBooks(){
        List<BookEntity> bookEntityList = null;
        try {
            bookEntityList = bookRepository.findAll();
        }
        catch (Exception e){}
        if(bookEntityList == null)
            return  null;
        List<Book> bookList = new ArrayList<Book>();
        for(BookEntity bookEntity: bookEntityList)
        {
            Book book = new Book(bookEntity);
            bookList.add(book);
        }
        return bookList;
    }

    @GetMapping("/vocabs/{bookId}")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Vocab> getAllVocabs(@PathVariable Integer bookId){
        BookEntity bookEntity = null;
        try {
            bookEntity = bookRepository.findById(bookId).get();
        }catch (Exception e){}
        if(bookEntity == null)
            return null;
        List<VocabEntity> vocabEntityList = bookEntity.getVocabs();
        List<Vocab> vocabList = new ArrayList<>();
        for(VocabEntity vocabEntity: vocabEntityList)
        {
            Vocab vocab = new Vocab(vocabEntity);
            vocabList.add(vocab);
        }

        return vocabList;
    }

    @PostMapping("/add")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void addBook(@RequestBody Book book){
        BookEntity bookEntity = null;
        bookEntity = new BookEntity(book.getBookName(),book.getBookIntro());
        bookRepository.save(bookEntity);
        return ;
    }

    @PostMapping("/addVocab")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void addVocabToBook(@RequestBody VocabBook vocabBook) {
        VocabEntity vocabEntity = null;
        try {
            vocabEntity = vocabRepository.findById(vocabBook.getVocabId()).get();
        } catch (Exception e) {
        }
        if (vocabEntity == null)
            return;
        BookEntity bookEntity = null;
        try {
            bookEntity = bookRepository.findById(vocabBook.getBookId()).get();
        }catch (Exception e){}
        if(bookEntity == null)
            return;
        List<BookEntity> bookEntityList = vocabEntity.getBooks();
        for(BookEntity bookEntity1: bookEntityList)
            if(bookEntity1.getId() == bookEntity.getId())
                return;

        vocabEntity.addToBook(bookEntity);
        vocabRepository.save(vocabEntity);
        return;
    }

    @DeleteMapping("/deleteVocab")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteVocabToBook(@RequestBody VocabBook vocabBook) {
        VocabEntity vocabEntity = null;
        try {
            vocabEntity = vocabRepository.findById(vocabBook.getVocabId()).get();
        } catch (Exception e) {
        }
        if (vocabEntity == null)
            return;
        BookEntity bookEntity = null;
        try {
            bookEntity = bookRepository.findById(vocabBook.getBookId()).get();
        }catch (Exception e){}
        if(bookEntity == null)
            return;
        vocabEntity.deleteFromBook(bookEntity);
        vocabRepository.save(vocabEntity);
        return;
    }

}

