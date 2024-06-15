package ru.iaygi.db.grigiryi;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MethodsDb {
    @Step("DB: getProductType")
    public List getProductType(ResultSet resultSet) {
        log.debug("GET all product type's");
        List<ProductDto> list;

        try {
            list = new ArrayList<>();
            while (resultSet.next()) {
                ProductDto product = new ProductDto();
                product.id(resultSet.getInt("id"));
                product.type_name(resultSet.getString("type_name"));
                list.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Step("DB: getSales")
    public List getSales(ResultSet resultSet) {
        log.debug("GET all sales");
        List<SalesDto> list;

        try {
            list = new ArrayList<>();
            while (resultSet.next()) {
                SalesDto sales = new SalesDto();
                sales.order_id(resultSet.getInt("order_id"));
                sales.product_id(resultSet.getInt("product_id"));
                sales.quantity(resultSet.getInt("quantity"));
                list.add(sales);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
