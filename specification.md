Specification for module B that group 10 has to implement.
Created by group 38.



class Review
fields:
long ID should be the primary key
// this is the most important field and both groups should understand this association 
@ManyToOne @JoinColumn
(name = "book_id")
  Book book

String text field (for writing the review)
Int rating field (for giving a rating for the book, scale should be from 0-5)
final String date (for the date when the review was writte, this should be final because this should not be changed)
String reviewerName
methods:
it should have all the getter and setter method (you can use  @Data annotation)

interface ReviewRepository extends JpaRepository


class ReviewService
no fields
autowiring bookrepository and reviewrepository

methods:
Review saveReview()
List<Review> getReviewsByBookId()
Double calculateAverageRating()
Review updateReview() //this is done by review id
void deleteReview() //this id done by review id

@RestController 
@RequestMapping("/api/reviews")
class ReviewController
no fields
autowiring with reviewService

@postmapping("/book/{bookId}")
ResponseEntity<Review> createReview()
@gettmapping ("/book/{bookId}")
List<Review> getReviewsByBookId()
@getmapping ("/book/{bookId}/average-rating")
Double getAverageRating()
@PutMapping("/{reviewId}")
 ResponseEntity<Review> updateReview
@DeleteMapping("/{reviewId}")
 ResponseEntity<Void> deleteReview




CSV/Jason handling




class FileService
no fields
autowiring reviewrepository
methods:

void importReviewsFromCSV()
void exportReviewsToCSV()

class FileController
no fields
autowiring fileservice
methods:

@PostMapping
 ResponseEntity<Void> importReviewsFromCSV()
@GetMapping
 void exportReviewsToCSV()


Design pattern:
The builder pattern. (you can use the @builder annotation)