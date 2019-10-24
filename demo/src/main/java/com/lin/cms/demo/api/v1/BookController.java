package com.lin.cms.demo.api.v1;

import com.lin.cms.demo.model.BookDO;
import com.lin.cms.demo.service.BookService;
import com.lin.cms.demo.dto.book.CreateOrUpdateBookDTO;
import com.lin.cms.core.annotation.GroupRequired;
import com.lin.cms.core.annotation.RouteMeta;
import com.lin.cms.exception.NotFound;
import com.lin.cms.core.result.Result;
import com.lin.cms.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/v1/book")
@Validated
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/{id}")
    public BookDO getBook(@PathVariable(value = "id") @Positive(message = "id必须为正整数") Long id) {
        BookDO book = bookService.findOneByIdAndDeleteTime(id);
        if (book == null) {
            throw new NotFound("未找到相关书籍");
        }
        return book;
    }

    @GetMapping("")
    public List<BookDO> getBooks() {
        List<BookDO> books = bookService.findAll();
        return books;
    }


    @GetMapping("/search/one")
    public BookDO searchBook(@RequestParam("q") String q) {
        BookDO book = bookService.getBookByKeyword("%" + q + "%");
        if (book == null) {
            throw new NotFound("未找到相关书籍");
        }
        return book;
    }


    @PostMapping("")
    public Result createBook(@RequestBody @Validated CreateOrUpdateBookDTO validator) {
        bookService.createBook(validator);
        return ResultGenerator.genSuccessResult("新建图书成功");
    }


    @PutMapping("/{id}")
    public Result updateBook(@PathVariable("id") @Positive(message = "id必须为正整数") Long id, @RequestBody @Validated CreateOrUpdateBookDTO validator) {
        BookDO book = bookService.findOneByIdAndDeleteTime(id);
        if (book == null) {
            throw new NotFound("未找到相关书籍");
        }
        bookService.updateBook(book, validator);
        return ResultGenerator.genSuccessResult("更新图书成功");
    }


    @DeleteMapping("/{id}")
    @RouteMeta(auth = "删除图书", module = "图书", mount = true)
    @GroupRequired
    public Result deleteBook(@PathVariable("id") @Positive(message = "id必须为正整数") Long id) {
        BookDO book = bookService.findOneByIdAndDeleteTime(id);
        if (book == null) {
            throw new NotFound("未找到相关书籍");
        }
        bookService.deleteById(book.getId());
        return ResultGenerator.genSuccessResult("删除图书成功");
    }


}
