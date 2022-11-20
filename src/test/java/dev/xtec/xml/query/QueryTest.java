package dev.xtec.xml.query;

import org.basex.core.Context;
import org.basex.query.QueryException;
import org.basex.query.QueryProcessor;
import org.basex.query.iter.Iter;
import org.basex.query.value.Value;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QueryTest {

    @Test
    void flowr() throws Exception {

        Context context = new Context();
        String query = """
                for $prod in doc('src/test/resources/query/catalog.xml')/catalog/product
                where $prod/@dept = "ACC"
                order by $prod/name
                return data($prod/name)
                """;

        try (QueryProcessor proc = new QueryProcessor(query, context)) {

            // Store the pointer to the result in an iterator:
            Iter iter = proc.iter();

            assertEquals("Deluxe Travel Bag", iter.next().toJava());
        }
    }

    public static void print(final String query, final Context context) throws QueryException {
        try (QueryProcessor proc = new QueryProcessor(query, context)) {
            // Execute the query
            Value result = proc.value();

            // Print result as string.
            System.out.println(result);
        }
    }
}
