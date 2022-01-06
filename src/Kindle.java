/**
 * Design Kindle, which can support 3 type of book format: PDF, MOBI and EPUB.
 *
 * Consider using ENUM for book format.
 * Consider using simple factory to create reader for each format.
 */

import java.util.ArrayList;
import java.util.List;

public class Kindle {

    private List<Book> mBooks;
    private EBookReaderFactory readerFactory;

    public Kindle() {
        //write your code here
        mBooks = new ArrayList<>();
        readerFactory = new EBookReaderFactory();
    }

    public String readBook(Book book) throws Exception {
        //write your code here
        EBookReader ebookReader = readerFactory.createReader(book);
        if (ebookReader == null) {
            throw new Exception("can not read this format");
        }

        return ebookReader.displayReaderType() + ", book content is: " + ebookReader.readBook();
    }

    public void downloadBook(Book b) {
        //write your code here
        mBooks.add(b);
    }

    public void uploadBook(Book b) {
        //write your code here
        mBooks.add(b);
    }

    public void deleteBook(Book b) {
        //write your code here
        mBooks.remove(b);
    }
}

enum Format {
    EPUB("epub"), PDF("pdf"), MOBI("mobi");

    private String content;

    Format(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}

class Book {
    private Format format;

    public Book(Format format) {
        this.format = format;
    }

    public Format getFormat() {
        return format;
    }
}

abstract class EBookReader {

    protected Book book;

    public EBookReader(Book b){
        this.book = b;
    }

    public abstract String readBook();
    public abstract String displayReaderType();
}

class EBookReaderFactory {

    public EBookReader createReader(Book b) {
        if (b.getFormat() == Format.EPUB) {
            return new EpubReader(b);
        } else if (b.getFormat() == Format.PDF) {
            return new PdfReader(b);
        } else if (b.getFormat() == Format.MOBI) {
            return new MobiReader(b);
        } else {
            return null;
        }
    }
}

class EpubReader extends EBookReader{

    public EpubReader(Book b) {
        super(b);
    }

    @Override
    public String readBook() {
        return this.book.getFormat().getContent();
    }

    @Override
    public String displayReaderType() {
        // TODO Auto-generated method stub
        return "Using EPUB reader";
    }
}

class MobiReader extends EBookReader {

    public MobiReader(Book b) {
        super(b);
        // TODO Auto-generated constructor stub
    }

    @Override
    public String readBook() {
        return this.book.getFormat().getContent();
    }

    @Override
    public String displayReaderType() {
        // TODO Auto-generated method stub
        return "Using MOBI reader";
    }

}
class PdfReader extends EBookReader{

    public PdfReader(Book b) {
        super(b);
        // TODO Auto-generated constructor stub
    }

    @Override
    public String readBook() {
        return this.book.getFormat().getContent();
    }

    @Override
    public String displayReaderType() {
        // TODO Auto-generated method stub
        return "Using PDF reader";
    }
}