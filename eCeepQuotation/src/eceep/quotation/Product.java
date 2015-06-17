package eceep.quotation;

public interface Product {
    void saveBeforeSerialized();

    void restoreAfterDeserialized();
}
