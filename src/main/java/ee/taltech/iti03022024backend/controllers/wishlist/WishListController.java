package ee.taltech.iti03022024backend.controllers.wishlist;

import ee.taltech.iti03022024backend.dto.book.BookDtoOut;
import ee.taltech.iti03022024backend.dto.wishlist.WishListDtoIn;
import ee.taltech.iti03022024backend.dto.wishlist.WishListDtoOut;
import ee.taltech.iti03022024backend.services.wishlist.WishListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("api/wish_list")
@RestController
@Tag(name = "Wish List", description = "API for managing wish list.")
public class WishListController {

    private final WishListService wishListService;

    @Operation(
            summary = "Add Book to wish list.",
            description = "Adds a book to user's wish list."
    )
    @ApiResponse(responseCode = "200", description = "Book has been added to wish list successfully.")
    @PostMapping()
    public ResponseEntity<WishListDtoOut> addToWishList(@RequestBody WishListDtoIn wishListDtoIn) {
        WishListDtoOut wishListDtoOut = wishListService.addToWishList(wishListDtoIn);
        return new ResponseEntity<>(wishListDtoOut, HttpStatus.OK);
    }

    //get by user id
    @Operation(
            summary = "Return wish list",
            description = "Returns books in user's personal wish list using user's id."
    )
    @ApiResponse(responseCode = "200", description = "Wish list has been retrieved successfully.")
    @GetMapping("{userId}")
    public ResponseEntity<List<BookDtoOut>> getWishList(@PathVariable Long userId) {
        List<BookDtoOut> bookDtoOutList = wishListService.getUserBooks(userId);
        return new ResponseEntity<>(bookDtoOutList, HttpStatus.OK);
    }

    @Operation(
            summary = "Remove book from wish list",
            description = "Removes book from users wishlist (from wish list table), based on userId and bookId."
    )
    @ApiResponse(responseCode = "204", description = "Book has been removed form wish list successfully.")
    @DeleteMapping("{userId}/{bookId}")
    public ResponseEntity<Void> deleteFromWishList(@PathVariable Long userId, @PathVariable Long bookId) {
        wishListService.deleteBookFromWishList(userId, bookId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
