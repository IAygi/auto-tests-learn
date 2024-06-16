package ru.iaygi.db.grigiryi;

public class Sql {
    public static final String GET_SALES = "SELECT * FROM sales";
    public static final String GET_PRODUCT_TYPE = "SELECT * FROM product_types";

    public String updateProduct(String name) {
        return String.format("UPDATE public.product_types SET \"type_name\" = '%s' WHERE id = 1", name);
    }

    public String deleteSales(int id) {
        return String.format("DELETE FROM public.sales WHERE product_id = %d", id);
    }
}
