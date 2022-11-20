package daw4.db;

import org.basex.core.Context;
import org.basex.query.QueryException;
import org.basex.query.QueryProcessor;
import org.basex.query.value.Value;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This example demonstrates how items can be bound to variables with
 * the XQuery processor.
 */
class BindVariablesTest {

    @Test
    void bind() throws QueryException {

        Context context = new Context();

        String query =
                "declare variable $var1 as xs:string external;\n" +
                        "declare variable $var2 external;\n" +
                        "($var1, $var2)";

        // Create a query processor
        try (QueryProcessor proc = new QueryProcessor(query, context)) {

            // Define the items to be bound
            String string = "Hello World!\n";
            String number = "123";

            // Bind the variables
            proc.variable("var1", string);
            proc.variable("var2", number, "xs:integer");

            // Execute the query
            Value result = proc.value();

            assertEquals(123L,result.itemAt(1).toJava());

        }

        context.close();
    }
}