package com.lin.cms.demo.api.v1;

import com.lin.cms.core.annotation.GroupRequired;
import com.lin.cms.core.annotation.RouteMeta;
import com.lin.cms.core.exception.NotFound;
import com.lin.cms.core.result.Result;
import com.lin.cms.core.result.ResultGenerator;
import com.lin.cms.demo.model.BookPO;
import com.lin.cms.demo.service.BookService;
import com.lin.cms.demo.validators.CreateOrUpdateBookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/v1/book")
public class BookApi {

    @Autowired
    private BookService bookService;

    @GetMapping("/{id}")
    public BookPO getBook(@PathVariable(value = "id") @PositiveOrZero Integer id) throws NotFound {
        BookPO book = bookService.findOneByIdAndDeleteTime(id);
        if (book == null) {
            throw new NotFound("未找到相关书籍");
        }
        return book;
    }

    @GetMapping("/")
    public List<BookPO> getBooks() throws NotFound {
        List<BookPO> books = bookService.findAll();
        if (books == null || books.size() < 1) {
            throw new NotFound("未找到相关书籍");
        }
        return books;
    }


    @GetMapping("/search/one")
    public BookPO searchBook(@RequestParam("q") String q) throws NotFound {
        BookPO book = bookService.getBookByKeyword(q);
        if (book == null) {
            throw new NotFound("未找到相关书籍");
        }
        return book;
    }


    @PostMapping("/")
    public Result createBook(@RequestBody @Valid CreateOrUpdateBookValidator validator) {
        bookService.createBook(validator);
        return ResultGenerator.genSuccessResult("新建图书成功");
    }


    @PostMapping("/{id}")
    public Result updateBook(@PathVariable("id") Integer id, @RequestBody @Valid CreateOrUpdateBookValidator validator) throws NotFound {
        BookPO book = bookService.findOneByIdAndDeleteTime(id);
        if (book == null) {
            throw new NotFound("未找到相关书籍");
        }
        bookService.updateBook(book, validator);
        return ResultGenerator.genSuccessResult("更新图书成功");
    }


    @DeleteMapping("/{id}")
    @RouteMeta(auth = "删除图书", module = "图书", mount = true)
    @GroupRequired
    public Result deleteBook(@PathVariable("id") Integer id) throws NotFound {
        // 软删除，逻辑删除
        BookPO book = bookService.findOneByIdAndDeleteTime(id);
        if (book == null) {
            throw new NotFound("未找到相关书籍");
        }
        bookService.deleteById(book.getId());
        return ResultGenerator.genSuccessResult("删除图书成功");
    }


}
