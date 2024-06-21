package library;

public record Book(String title, String author, int pagesAmount) {

    @Override
    public String title() {
        return title;
    }

    @Override
    public String author() {
        return author;
    }

    @Override
    public int pagesAmount() {
        return pagesAmount;
    }
}
