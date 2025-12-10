CREATE TABLE stocks (
                        product_id UUID PRIMARY KEY,
                        quantity INTEGER NOT NULL,
                        reserved_quantity INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE reservations (
                              id UUID PRIMARY KEY,
                              order_id UUID NOT NULL,
                              product_id UUID NOT NULL,
                              quantity INTEGER NOT NULL,
                              status VARCHAR(20) NOT NULL,
                              created_at TIMESTAMP NOT NULL,
                              CONSTRAINT fk_stock FOREIGN KEY (product_id) REFERENCES stocks(product_id)
);